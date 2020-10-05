package model

/**
  * Created by Dominik Zduńczyk on 04.03.2020.
  *
  * Response DTO case class
  */
case class ResponseDTO[A](code : Int, result : Option[A], message : String)
