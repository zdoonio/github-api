package component

import github4s.Github
import cats.effect.IO
import model.ContributionsDTO
import play.api.Configuration
import utils.UtilsFunctions

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.sys.process.Process

/**
  * Created by Dominik Zduńczyk on 03.03.2020.
  */
object GithubComponent {

  implicit val IOContextShift = IO.contextShift(global)
  val accessToken = sys.env.get("GH_TOKEN")

  def getMembersAndCommits(orgName: String): List[ContributionsDTO] = {
    val listOrgRepos = Github[IO](accessToken).repos.listOrgRepos(orgName)

    val results = listOrgRepos.unsafeRunSync match {
      case Left(e) => {
        println(e.getMessage)
        List()
      }

      case Right(r) => {
        r.result.map { repo =>
          getContributorsFromRepository(orgName, repo.name)
        }
      }
    }

    results.flatMap { result =>
      result.map { user =>
        (user.login, user.contributions)
      }
    }.groupBy(_._1).mapValues(_.map(_._2)).map { data =>
      ContributionsDTO(data._1, UtilsFunctions.sumList(data._2))
    }.toList.sortWith(_.contributions.getOrElse(0) > _.contributions.getOrElse(0))

  }

  def getContributorsFromRepository(orgName: String, repoName: String) = {
    Github[IO](accessToken).repos.listContributors(orgName, repoName, Some("false")).unsafeRunSync match {
      case Left(e) => {
        println(e.getMessage)
        List()
      }

      case Right(right) => right.result
    }
  }

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
