package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import org.joda.time.DateTime

case class User(id: Option[Long], 
                loginId: String, 
                name: Option[String], 
                dob: Option[Long])

object User{
	def add(loginId:String,
			password:String,
			name:String="anonymous",
			dateOfBirth:DateTime):Option[Long] ={

		val dob = dateOfBirth.getMillis
		DB.withConnection { 
		    implicit connection =>
			val userId  = SQL"""INSERT INTO user(login_id,password,name,
			  dob) VALUES($loginId,$password,$name,$dob)""".executeInsert()
			
			userId
	    }
	}

	def updatePassword(userId:Long,
					   password:String) = {
		DB.withConnection { 
		    implicit connection =>
			SQL"""UPDATE user SET password=$password WHERE id = $userId""".executeUpdate()
		}
	}

	def userRow:RowParser[User] = {
	    get[Option[Long]]("id") ~
	      get[String]("login_id") ~
	      get[Option[String]]("name") ~
	      get[Option[Long]]("dob") map {
	      case id ~ login_id ~ name ~ dob =>  User(id, login_id, "****", name,dob)
	    }
	}

	def getAll = {
		DB.withConnection {
     		implicit connection =>
	        val query = "SELECT id,login_id,name,contact_no,dob,address FROM user"
	        SQL(query).as(userRow.*)
    	}
	}
}