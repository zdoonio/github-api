package utils

/**
  * Created by ${USER_dabrowski} on 04.03.2020.
  */
object UtilsFunctions {

  def sumList[ T ] (list: List[Option[T]])(implicit ev: Numeric[T]): Option[T] = {
    list.foldLeft(Option(ev.zero)) { case (acc, el) =>
      el.flatMap(value => acc.map(ac => ev.plus(ac, value)))
    }
  }

}
