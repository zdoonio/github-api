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

  def getNumberCommitsByUsersOfRepo(orgName: String) = Action { implicit request =>

    val membersAndContributions = GithubComponent.getMembersAndCommits(orgName: String)

    if(membersAndContributions._1.nonEmpty)
      Ok(JsonWriter.responseWrites.writes(ResponseDTO[List[ContributionsDTO]] (
        Some("200"),
        Some(membersAndContributions._1),
        Some(membersAndContributions._2)))
      )
     else
      BadRequest(JsonWriter.responseWrites.writes(ResponseDTO[List[ContributionsDTO]] (
        Some("400"),
        Some(membersAndContributions._1),
        Some(membersAndContributions._2)))
      )

  }

  def getAccessToken = Action {
    Ok(views.html.index())
  }

}
