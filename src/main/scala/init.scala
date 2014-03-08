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
    println("loading data")
    db withSession {
      // Insert the arms
      Arms.insert(1, "Directed Works")
      Arms.insert(2, "Good")
      Arms.insert(3, "Lost Lost")
      Arms.insert(4, "LPTV")
      Arms.insert(5, "Tech Tips")
      Arms.insert(6, "Finishing")
      Arms.insert(7, "Manifesto")
      Vidz.insert("sample.mp4",1,1,"sample.mp4","preview.jpeg")
      Vidz.insert("assample.mp4d",1,2,"sample.mp4","preview.jpeg")
      Vidz.insert("assample.mp4acsample.mp4d",1,3,"sample.mp4","preview.jpeg")
      Vidz.insert("assample.mp4sd",2,1,"sample.mp4","preview.jpeg")
      Vidz.insert("asassample.mp4d",2,2,"sample.mp4","preview.jpeg")
      Vidz.insert("asample.mp4sample.mp4assd",3,1,"sample.mp4","preview.jpeg")
      Vidz.insert("assample.mp4sample.mp4d",3,2,"sample.mp4","preview.jpeg")
      Vidz.insert("asample.mp4jsadsd",3,3,"sample.mp4","preview.jpeg")
      Vidz.insert("asadssample.mp4",4,1,"sample.mp4","preview.jpeg")
      Vidz.insert("aascsd",4,2,"sample.mp4","preview.jpeg")
      Vidz.insert("assdsdd",4,3,"sample.mp4","preview.jpeg")
      Vidz.insert("asaajd",4,4,"sample.mp4","preview.jpeg")
      Vidz.insert("assdssample.mp4sd",4,5,"sample.mp4","preview.jpeg")
      Vidz.insert("assdsd",5,1,"sample.mp4","preview.jpeg")
      Vidz.insert("sample.mp4ddddddd",5,2,"sample.mp4","preview.jpeg")
      Vidz.insert("assssssd",5,3,"sample.mp4","preview.jpeg")
      Vidz.insert("sample.mp4ssddd",6,1,"sample.mp4","preview.jpeg")
      Vidz.insert("asaad",6,2,"sample.mp4","preview.jpeg")
      Vidz.insert("sample.mp4dd",6,3,"sample.mp4","preview.jpeg")
      Vidz.insert("sample.mp4sdd",6,4,"sample.mp4","preview.jpeg")
      Vidz.insert("asaaaad",7,1,"sample.mp4","preview.jpeg")
    }
  }

  def run(db: Database) {
      drop_tables(db)
      create_tables(db)
      load_data(db)
  }
  run(db)
}
