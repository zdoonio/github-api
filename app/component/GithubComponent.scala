package component

import github4s.Github
import cats.effect.{ConcurrentEffect, IO}
import github4s.GithubResponses.GHResponse
import github4s.domain.User
import model.ContributionsDTO

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
    }.groupBy(_._2).mapValues(_.map(_._1)).map{ case (x, y) => (y, x) }.map { data =>
      ContributionsDTO(data._1.head, data._2)
    }.toList.sortWith(_.contributions.getOrElse(0) > _.contributions.getOrElse(0))

  }

  def getContributorsFromRepository(orgName: String, repoName: String) = {
    Github[IO](accessToken).repos.listContributors(orgName, repoName, Some("true")).unsafeRunSync match {
      case Left(e) => {
        println(e.getMessage)
        List()
      }

      case Right(right) => right.result
    }
  }

  def getAccessToken() = {
    val newAuth = Github[IO](None).auth.newAuth(
      "",
      "#",
      List("public_repo"),
      "New access token",
      "",
      "")
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
