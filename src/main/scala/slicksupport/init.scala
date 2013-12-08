package slicksupport
/**
*** All DB Initialization should go here...
**/
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession

object Init {
  def create_tables(db: Database) {
    db withSession {
      (Arms.ddl ++ Vidz.ddl).create
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
    }
  }

  def run(db: Database) {
    try {
      create_tables(db)
      load_data(db)
    } catch {
      case _ : Throwable => 
    }
  }
}
