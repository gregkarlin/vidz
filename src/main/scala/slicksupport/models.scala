package slicksupport

import scala.slick.driver.H2Driver.simple._

// Definition of the ARMS table
object Arms extends Table[(Int, String)]("ARMS") {
  def id = column[Int]("ARM_ID", O.PrimaryKey) // This is the primary key column
  def name = column[String]("ARM_NAME")

  // Every table needs a * projection with the same type as the table's type parameter
  def * = id ~ name
}

// Definition of the COFFEES table
object Vidz extends Table[(String, Int, Int, String)]("VIDZ") {
  def title = column[String]("VID_TITLE", O.PrimaryKey)
  def armID = column[Int]("ARM_ID")
  def position = column[Int]("POSITION")
  def file_path = column[String]("FILE_PATH")
  def * = title ~ armID ~ position ~ file_path

  // A reified foreign key relation that can be navigated to create a join
  def arm = foreignKey("ARM_FK", armID, Arms)(_.id)
}

//Bring on the case classes
case class Video(title:String, arm:String,position:Int,file_path:String)
