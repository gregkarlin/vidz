package slicksupport

import org.scalatra.ScalatraServlet
import org.scalatra._
import scalate.ScalateSupport
import scala.slick.driver.H2Driver.simple._
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
    db withSession { implicit session: Session =>
     val videos = (for {
        v <- DbComponent.vidz
        s <- v.arm
      } yield(v.title, v.position, v.file_path, s.name))
      videos.list.map { case (s1, s2, s3, s4) => "\"" + s1 + "\", " + "\"" + s2 + "\", " + "\"" + s3 + "\", " + "\"" + s4 + "\"" } mkString ("{ \"aaData\": [ [","] , [","] ] }")
    }
  }

  get("/arm_length/:arm") {
    db withSession { implicit session: Session =>
      val z = for { v <- DbComponent.vidz if v.armID === params("arm").toInt} yield v
      val d = z.list.map(_.title)
      d.toList.length.toString
    }
    
  }
  
  get("/json/vidz") {
    
    db withSession {  implicit session: Session =>
      val videos = (for {
        v <- DbComponent.vidz
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
    db withSession {  implicit session: Session =>
      DbComponent.vidz.insert(Video(title,arm,position,file_path,thumb_nail_path))
      println(s"inserting video for arm: $arm it position $position")
      val videos = DbComponent.vidz.filter(_.armID === arm ).filter(_.position >= position).run
      videos.map( vid => DbComponent.vidz.filter(_.title === vid.title).map(_.position).update(vid.position.asInstanceOf[Int]+1))
    }
    println("db updated")
    val out = new FileOutputStream(file_path)
      out.write(file.get())
      out.close()
    val thumb_out = new FileOutputStream(thumb_nail_path)
      thumb_out.write(thumb_nail.get())
      thumb_out.close()
    
    "Videos Updated"
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

