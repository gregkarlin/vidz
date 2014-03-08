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
      videos.list.map { case (s1, s2, s3, s4) => "\"" + s1 + "\", " + "\"" + s2 + "\", " + "\"" + s3 + "\", " + "\"" + s4 + "\"" } mkString ("{ \"aaData\": [ [","] , [","] ] }")
    }
  }

  get("/arm_length/:arm") {
    db withSession {
      (for {
        v <- Vidz if v.armID === params("arm").toInt
      } yield v.title.count).list(0)
    }
  }
  
  get("/json/vidz") {
    
    db withSession {
      val videos = (for {
        v <- Vidz
        s <- v.arm 
      } yield(v.title, s.name, v.position, v.file_path, v.thumb_nail_path))
        //videos.list.map { case (s1, s2, s3, s4, s5) => Video(s1,s2,s3,s4,s5) }
        videos.list.foldLeft(Map[String,List[(String,Int,String,String)]]())((vidMap,element) =>  
          if (vidMap.contains(element._2)) vidMap ++ Map(element._2-> (vidMap(element._2) ++ List((element._1,element._3,element._4,element._5))))
          else vidMap ++ Map(element._2->List((element._1,element._3,element._4,element._5)))
        )
    }
  }

  post("/file") {
    val file = fileParams("files")
    val thumb_nail = fileParams("thumb_nail")
    val title = params("title")  
    val arm = params("arm").toInt
    val position = params("position").toInt
    val file_path = "movies/" + file.name
    val thumb_nail_path = "thumbs/" + thumb_nail.name
    db withSession {
      Vidz.insert(title,arm,position,file_path,thumb_nail_path)
      val videos = Vidz.filter(_.arm == arm ).filter(_.position >= position)
      //videos.map( vid => Vidz.filter(_.title === vid.title).map(_.position).update(4))
    }

    val out = new FileOutputStream(file_path)
      out.write(file.get())
      out.close()
    val thumb_out = new FileOutputStream(thumb_nail_path)
      thumb_out.write(thumb_nail.get())
      thumb_out.close()
    Ok()
  } 

  get("/upload"){
    contentType="text/html"
    layoutTemplate("upload.ssp", "page" -> "upload", "leader" -> "Upload your new jimjam :[ ")
  } 

  get("/videos"){
    contentType="text/html"
    layoutTemplate("videos.ssp", "page" -> "videos", "leader" -> "All your films :|")
  } 

  get("/monster"){
    contentType="text/html"
    layoutTemplate("monster.ssp", "layout" -> "")
  }
  
  get("/movie/:movie_src"){
    contentType="text/html"
    layoutTemplate("movie.ssp", "layout" -> "")
  }
}

