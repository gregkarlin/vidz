package slicksupport

import org.scalatra.ScalatraServlet
import org.scalatra._
import scalate.ScalateSupport
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import org.scalatra.servlet.{FileUploadSupport, MultipartConfig, SizeConstraintExceededException}
import java.io._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

case class SlickApp(db: Database) extends ScalatraServlet with SlickRoutes with JacksonJsonSupport 

  
trait SlickRoutes extends ScalatraServlet with VidServerStack with FileUploadSupport with JacksonJsonSupport{

protected implicit val jsonFormats: Formats = DefaultFormats
  val db: Database

  before() {
    contentType = formats("json")
  }

  get("/vidz") {
    db withSession {
     val videos = (for {
        v <- Vidz
        s <- v.arm
      } yield(v.title, v.position, v.file_path, s.name))
      videos.list.map { case (s1,s2,s3,s4) => List(s1,s2,s3,s4) }
    }
  }

  get("/json/vidz") {
    db withSession {
      val videos = (for {
        v <- Vidz
        s <- v.arm 
      } yield(v.title, s.name, v.position, v.file_path))
        videos.list.map { case (s1, s2, s3, s4) => Video(s1,s2,s3,s4) }
    }
  }

  post("/file") {
    val file = fileParams("files")
    val title = params("title")  
    val arm = params("arm").toInt
    val position = params("position").toInt
    val file_path = "vidz/" + file.name
    db withSession {
      Vidz.insert(title,arm,position,file_path)
    }

    val out = new FileOutputStream(file_path)
      out.write(file.get())
      out.close()
    Ok()
  } 

  get("/upload"){
    contentType="text/html"
    layoutTemplate("upload.ssp", "page" -> "upload", "leader" -> "Upload your new jimjam `}")
  } 

  get("/videos"){
    contentType="text/html"
    layoutTemplate("videos.ssp", "page" -> "videos", "leader" -> "All your films :|")
  } 
}

