import component.GithubComponent
import github4s.domain.User
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite

import scala.concurrent.duration.Duration
import scala.concurrent._

/**
  * Created by Dominik Zduńczyk on 07.10.2020.
  */
class GithubComponentSpec extends PlaySpec with GuiceOneAppPerSuite {

  "GithubComponent githubTokenVault" should {
    "contains elements" when {
      "I start the application" in {
        val token = GithubComponent.githubTokenVault.unsafeRunSync()

        token.nonEmpty mustBe true
      }
    }

  }

  "GithubComponent getContributorsFromRepository" should {
    "contains elements" when {
      "search for organization" in {
        val future = GithubComponent.getContributorsFromRepository("edukaton", "Bona-Fide-Front")
        val result = Await.result(future, Duration.Inf)
        val equal = List(User(17704558, "mlukasz7", "https://avatars2.githubusercontent.com/u/17704558?v=4", "https://github.com/mlukasz7", None, None, None, None, None, None, Some("https://api.github.com/users/mlukasz7/followers"), Some("https://api.github.com/users/mlukasz7/following{/other_user}"), "User", None, None, Some(27)),
          User(4675493, "IgnetStudio", "https://avatars0.githubusercontent.com/u/4675493?v=4", "https://github.com/IgnetStudio", None, None, None, None, None, None, Some("https://api.github.com/users/IgnetStudio/followers"), Some("https://api.github.com/users/IgnetStudio/following{/other_user}"), "User", None, None, Some(13)),
          User(7470040, "zdoonio", "https://avatars1.githubusercontent.com/u/7470040?v=4", "https://github.com/zdoonio", None, None, None, None, None, None, Some("https://api.github.com/users/zdoonio/followers"), Some("https://api.github.com/users/zdoonio/following{/other_user}"), "User", None, None, Some(3)))
        result mustBe equal
      }
    }

  }

  "GithubComponent getContributorsFromRepository" should {
    "not contains elements" when {
      "search for bad name of organization" in {
        val future = GithubComponent.getContributorsFromRepository("tezt", "nonexistingrepo")
        val result = Await.result(future, Duration.Inf)
        result.isEmpty mustBe true
      }
    }

  }

  "GithubComponent getMembersAndCommits" should {
    "not contains elements" when {
      "search for bad name of organization" in {
        val future = GithubComponent.getMembersAndCommits("tezt12345")
        val result = Await.result(future, Duration.Inf)
        result._1.isEmpty mustBe true
      }
    }

  }
}
