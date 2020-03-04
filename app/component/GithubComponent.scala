package component

import github4s.Github
import cats.effect.{ConcurrentEffect, IO}
import github4s.GithubResponses.GHResponse
import github4s.domain.User
import model.ContributionsDTO

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Dominik Zduńczyk on 03.03.2020.
  */
object GithubComponent {

  implicit val IOContextShift = IO.contextShift(global)
  val accessToken = sys.env.get("GH_TOKEN")

  def getMembers(orgName: String) :List[ContributionsDTO] = {
    val listOrgRepos = Github[IO](accessToken).repos.listOrgRepos(orgName)

    listOrgRepos.unsafeRunSync match {
      case Left(e) => println(s"Something went wrong: ${e.getMessage}")

      case Right(r) => {
        r.result.foreach { repo =>
          Github[IO](accessToken).repos.listContributors(orgName, repo.name, Some("true")).unsafeRunSync match {
            case Left(e) => println(s"Something went wrong: ${e.getMessage}")
            case Right(right) => println(right.result)
          }

        }

      }
    }
    List()
  }

}
