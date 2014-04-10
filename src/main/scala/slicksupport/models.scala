package slicksupport

import scala.slick.driver.H2Driver.simple._

object DbComponent {

// Definition of the ARMS table
class Arms(tag: Tag) extends Table[(Int, String)](tag, "ARMS") {
  def id = column[Int]("ARM_ID", O.PrimaryKey) // This is the primary key column
  def name = column[String]("ARM_NAME")

  // Every table needs a * projection with the same type as the table's type parameter
  def * = (id, name)
}

val arms = TableQuery[Arms]

// Definition of the COFFEES table
class Vidz(tag: Tag) extends Table[Video](tag, "VIDZ") {
  def title = column[String]("VID_TITLE")
  def armID = column[Int]("ARM_ID")
  def position = column[Int]("POSITION")
  def file_path = column[String]("FILE_PATH")
  def thumb_nail_path = column[String]("THUMB_NAIL_PATH")
  def * = (title, armID, position, file_path, thumb_nail_path) <> (Video.tupled, Video.unapply)

  // A reified foreign key relation that can be navigated to create a join
  def arm = foreignKey("ARM_FK", armID, arms)(_.id)
  
}

val vidz = TableQuery[Vidz]
}
//Bring on the case classes
case class Video(title:String, arm:Int,position:Int,file_path:String,thumb_nail_path:String)

