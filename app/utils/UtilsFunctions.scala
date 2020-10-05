package utils

/**
  * Created by Dominik Zduńczyk on 04.03.2020.
  */
object UtilsFunctions {

  /**
    * Sum list of objects on given type
    *
    * @param list list of elements to sum
    * @param ev implicit numeric type
    * @tparam T type
    * @return sum value
    */
  def sumList[ T ] (list: List[Option[T]])(implicit ev: Numeric[T]): Option[T] = {
    list.foldLeft(Option(ev.zero)) { case (acc, el) =>
      el.flatMap(value => acc.map(ac => ev.plus(ac, value)))
    }
  }

}
