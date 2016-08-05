package com.ivan.nikolov.cake

import com.ivan.nikolov.cake.model.Person
import org.mockito.Mockito
import org.scalatest.ShouldMatchers
import org.scalatest.FlatSpec
import org.scalatest.mock.MockitoSugar

import Mockito._

class UserComponentTest extends FlatSpec with ShouldMatchers with MockitoSugar with TestEnvironment {
  val className = "A"
  val emptyClassName = "B"
  val people = List(
    Person(1, "a", 10),
    Person(2, "b", 15),
    Person(3, "c", 20)
  )
  
  override val userService = new UserService
  
  when(dao.getPeopleInClass(className)).thenReturn(people)
  when(dao.getPeopleInClass(emptyClassName)).thenReturn(List())
  
  "getAverageAgeOfUsersInClass" should "properly calculate the average of all ages." in {
    userService.getAverageAgeOfUsersInClass(className) should equal(15.0)
  }
  
  it should "properly handle an empty result." in {
    userService.getAverageAgeOfUsersInClass(emptyClassName) should equal(0.0)
  }
}
