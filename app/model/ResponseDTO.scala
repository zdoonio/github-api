package model

/**
  * Created by Dominik Zduńczyk on 04.03.2020.
  */
case class ResponseDTO[A](code : Option[String], result : Option[A], message : Option[String])
