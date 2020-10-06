package component

import github4s.Github
import cats.effect.IO
import github4s.GithubResponses.GHResponse
import github4s.domain.{Repository, User}
import model.ContributionsDTO
import play.api.Configuration
import utils.UtilsFunctions

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.sys.process.Process
import scala.concurrent.duration._

/**
  * Created by Dominik Zduńczyk on 03.03.2020.
  */
object GithubComponent {

  implicit val IOContextShift = IO.contextShift(global)
  val accessToken = sys.env.get("GH_TOKEN")

  /**
    * gets a list of users contributions modified by organization name
    *
    * @param orgName organization name
    * @return list of users contributions
    */
  def getMembersAndCommits(orgName: String): Future[(List[ContributionsDTO], String)] = {

    val listOrgRepos = Github[IO](accessToken).repos.listOrgRepos(orgName)
    val repos: Future[GHResponse[List[Repository]]] = listOrgRepos.unsafeToFuture

    repos map { value =>

      val results = value.right.get.result.map { repo =>
        Await.result(getContributorsFromRepository(orgName, repo.name), Duration.Inf)
      }

      val contributions = results.flatMap { result =>
        result._1.map { user =>
          (user.login, user.contributions)
        }
      }.groupBy(_._1).mapValues(_.map(_._2)).map { data =>
        ContributionsDTO(data._1, UtilsFunctions.sumList(data._2))
      }.toList.sortWith(_.contributions.getOrElse(0) > _.contributions.getOrElse(0))

      (contributions, results.map(_._2).toSet.mkString("\n").replaceAll("[\n\"\"]", ""))

    }

  }

  /**
    * returns a list of users contributions by repo and org name
    *
    * @param orgName  organization name
    * @param repoName repository name
    * @return list of users
    */
  def getContributorsFromRepository(orgName: String, repoName: String): Future[(List[User], String)] = {
    val contributors = Github[IO](accessToken).repos.listContributors(orgName, repoName, Some("false")).unsafeToFuture()

    contributors map { value =>
      (value.right.get.result, "Ok")
    }
  }

  /**
    * generate new authentication to api and puts it into GH_TOKEN variable
    *
    * @param configuration play configuration
    */
  def getAccessToken(configuration: Configuration) = {
    val newAuth = Github[IO](None).auth.newAuth(
      configuration.underlying.getString("github.username"),
      configuration.underlying.getString("github.password"),
      List("public_repo"),
      "New access token",
      configuration.underlying.getString("github.client.id"),
      configuration.underlying.getString("github.client.secret"))
    newAuth.unsafeRunSync match {
      case Left(e) =>
        println(s"Something went wrong: ${e.getMessage}")

      case Right(r) => {
        Process("env",
          None,
          "GH_TOKEN" -> r.result.token)
      }
    }

  }

}
