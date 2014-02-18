package com.vidz
/**
*** All DB Initialization should go here...
**/
import scala.slick.driver.H2Driver.simple._
import slicksupport.Arms
import slicksupport.Vidz
import com.mchange.v2.c3p0.ComboPooledDataSource
object Main extends App{
  
  val cpds = new ComboPooledDataSource
  
  val db = Database.forDataSource(cpds)
  
  implicit val session = db.createSession()
  
  def create_tables(db: Database) {
    db withSession {
      (Arms.ddl ++ Vidz.ddl).create
    }
  }

  def drop_tables(db: Database) {
    println("dropping data")
    db withSession {
      (Arms.ddl ++ Vidz.ddl).drop
    }
  }
  def load_data(db: Database) {
    db withSession {
      // Insert the arms
      Arms.insert(1, "Directed Works")
      Arms.insert(2, "Good")
      Arms.insert(3, "Lost Lost")
      Arms.insert(4, "LPTV")
      Arms.insert(5, "Tech Tips")
      Arms.insert(6, "Finishing")
      Arms.insert(7, "Manifesto")
      Vidz.insert("asd",1,1,"asd","preview.jpeg")
      Vidz.insert("asasdd",1,2,"asd","preview.jpeg")
      Vidz.insert("asasdacasdd",1,3,"asd","preview.jpeg")
      Vidz.insert("asasdsd",2,1,"asd","preview.jpeg")
      Vidz.insert("asasasdd",2,2,"asd","preview.jpeg")
      Vidz.insert("aasdasdassd",3,1,"asd","preview.jpeg")
      Vidz.insert("asasdasdd",3,2,"asd","preview.jpeg")
      Vidz.insert("aasdjsadsd",3,3,"asd","preview.jpeg")
      Vidz.insert("asadsasd",4,1,"asd","preview.jpeg")
      Vidz.insert("aascsd",4,2,"asd","preview.jpeg")
      Vidz.insert("assdsdd",4,3,"asd","preview.jpeg")
      Vidz.insert("asaajd",4,4,"asd","preview.jpeg")
      Vidz.insert("assdsasdsd",4,5,"asd","preview.jpeg")
      Vidz.insert("assdsd",5,1,"asd","preview.jpeg")
      Vidz.insert("asdddddddd",5,2,"asd","preview.jpeg")
      Vidz.insert("assssssd",5,3,"asd","preview.jpeg")
      Vidz.insert("asdssddd",6,1,"asd","preview.jpeg")
      Vidz.insert("asaad",6,2,"asd","preview.jpeg")
      Vidz.insert("asddd",6,3,"asd","preview.jpeg")
      Vidz.insert("asdsdd",6,4,"asd","preview.jpeg")
      Vidz.insert("asaaaad",7,1,"asd","preview.jpeg")
    }
  }

  def run(db: Database) {
      drop_tables(db)
      create_tables(db)
      load_data(db)
  }
  run(db)
}
