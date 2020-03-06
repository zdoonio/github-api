
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * Created by Dominik Zduńczyk on 05.03.2020.
  */
class ApiControllerSpec extends PlaySpec with GuiceOneAppPerSuite  {

  "ApiController" should {
    "not return 404" when {
      "I go to the route /" in {
        val result = route(app, FakeRequest(GET, "/"))
        status(result.get) must not be NOT_FOUND
      }
    }

  }

  "ApiController" should {
    "should return 200" when {
      "I go to the route /" in {
        val result = route(app, FakeRequest(GET, "/"))
        status(result.get) mustBe OK
      }
    }

  }

  "ApiController" should {
    "not return 404" when {
      "I go to the route /org/org-name/contributors" in {
        val result = route(app, FakeRequest(GET, "/org/org-name/contributors"))
        status(result.get) must not be NOT_FOUND
      }
    }

  }

  "ApiController" should {
    "return 400" when {
      "I go to the route /org/org-name/contributors" in {
        val result = route(app, FakeRequest(GET, "/org/org-name/contributors"))
        status(result.get) mustBe BAD_REQUEST
      }
    }

  }

  "ApiController" should {
    "return 200" when {
      "I go to the route /org/edukaton/contributors" in {
        val result = route(app, FakeRequest(GET, "/org/edukaton/contributors"))
        status(result.get) mustBe OK
      }
    }

  }
}
