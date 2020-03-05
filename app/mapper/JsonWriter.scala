package mapper

import model.{ContributionsDTO, ResponseDTO}
import play.api.libs.json.{Json, Writes}


/**
  * Created by Dominik Zduńczyk on 04.02.2020.
  *
  * Json objects mapper
  */
object JsonWriter {

  implicit val contributionsWrites: Writes[ContributionsDTO] = (data: ContributionsDTO) => Json.obj(
    "name" -> data.name,
    "contributions" -> data.contributions
  )

  implicit val responseWrites: Writes[ResponseDTO[List[ContributionsDTO]]] = (data: ResponseDTO[List[ContributionsDTO]]) => Json.obj(
    "code" -> data.code,
          "result" -> data.result,
          "message" -> data.message
  )
}