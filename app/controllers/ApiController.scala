package controllers

import component.GithubComponent
import javax.inject._
import model.{ContributionsDTO, ResponseDTO}
import play.api.libs.json.Json
import play.api.mvc._
import mapper.JsonWriter


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ApiController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getNumberOfRepo(orgName: String) = Action { implicit request =>

    Ok(JsonWriter.responseWrites.writes(ResponseDTO[List[ContributionsDTO]] (
      Some("200"),
      Some(GithubComponent.getMembers(orgName: String)),
      Some("Ok")))
    )

  }

}