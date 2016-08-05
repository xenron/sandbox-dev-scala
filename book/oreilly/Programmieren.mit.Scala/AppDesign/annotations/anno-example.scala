// code-examples/AppDesign/annotations/anno-example.scala

class Persist(tableName: String, params: Map[String,Any]) 
  extends StaticAnnotation

@Persist("ACCOUNTS", Map("dbms" -> "MySql", "writeAutomatically" -> true))
class Account(val balance: Double)
