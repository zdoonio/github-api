package controllers

import component.GithubComponent
import javax.inject._
import model.{ContributionsDTO, ResponseDTO}
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
        200,
        Some(membersAndContributions._1),
        membersAndContributions._2))
      )
     else
      NotFound(JsonWriter.responseWrites.writes(ResponseDTO[List[ContributionsDTO]] (
        404,
        Some(membersAndContributions._1),
        membersAndContributions._2))
      )

  }

  def getAccessToken = Action {
    Ok(views.html.index())
  }

}
