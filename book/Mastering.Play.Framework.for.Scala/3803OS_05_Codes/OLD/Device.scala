package models

import play.modules.reactivemongo.ReactiveMongoPlugin
import play.api.libs.json.{JsObject, JsValue, Json}

object Device {

	def db = ReactiveMongoPlugin.db 
	def collection = db.collection("devices")

	def registerDevice(deviceId: String,
	 				   ownerId: String,
	  				   deviceDetails: JsObject)={ 
	  var newDevice = Json.obj("deviceId"-> deviceId, "ownerId" -> ownerId.trim) 
	  val config = (deviceDetails \ "config").asOpt[JsObject] 
	  val info = (deviceDetails \ "info").asOpt[JsObject] 
	  if (!conf.isDefined) 
	    newDevice = newDevice ++ Json.obj("config" -> Json.parse("{}")) 
	  if (!metadata.isDefined) 
	    newDevice = newDevice ++ Json.obj("info" -> Json.parse("{}")) 

	 collection.insert[JsValue](newDevice) 
	}

	def fetchDevice(deviceId: String) = { 
	  val findDevice = Json.obj("deviceId" -> deviceId.trim) 
	  val qb = QueryBuilder().query(findDevice) 
	  collection.find[JsValue](qb).toList 
	} 

	def removeDeviceById(deviceId: String) = { 
	  val removeDoc = Json.obj("deviceId" -> deviceId) 
	  collection.remove[JsValue](removeDoc) 
	}

	def updateConfiguration(deviceId: String,
							ownerId: String,
							updatedField: JsObject) = {
	  val configurationKey = updatedField.keys.head
	  val toUpdate = (JsPath\"configuration"\ configurationKey).json.put(updatedField\configurationKey)
	  val setData = Json.obj("$set" -> toUpdate)
	  val documentToUpdate = Json.obj("deviceId" -> deviceId, "ownerId" -> ownerId)
	  collection.update[JsValue, JsValue](documentToUpdate, setData)
	}

}