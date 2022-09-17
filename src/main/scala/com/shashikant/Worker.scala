package com.shashikant

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest}
import akka.stream.Materializer
import com.shashikant.CommentSeq._
import spray.json.JsValue

import java.io.{BufferedWriter, File, FileWriter}
import scala.collection.immutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.io.Source
import scala.util.{Failure, Success, Try, Using}
import spray.json._
class Worker(implicit materializer: Materializer, ac: ActorSystem) {


	def sendRequest: Future[Done] = {
		val request: HttpRequest = HttpRequest(
			method = HttpMethods.GET,
			uri = "https://jsonplaceholder.typicode.com/comments"
		)

		Http().singleRequest(request).flatMap { response =>
			response.entity.toStrict(300.millis).map(_.data).map(_.utf8String).map {
				data => storeData(data)
					Done
			}
		}
	}

	def storeData(fileContent: String): Unit = {
		val file = new File("src/main/resources/commentData.json")
		if (file.exists){
			println("File Already exits")
		} else {
			val bw = new BufferedWriter(new FileWriter(file))
			bw.write(fileContent)
			bw.close()
			println("Data Store")
		}
	}

	def filterData(domain: String): Future[Either[String, Seq[Comment]]] = {

		val fileContents: Try[String] = Using(Source.fromFile("src/main/resources/commentData.json")) { source => source.mkString
		}

 fileContents match {
			case Success(data) => Try(data.parseJson.convertTo[CommentSeq]) match {
				case Success(res) =>

					val res12: List[Comment] = res.comment.filter{
						res12: Comment => val res13: String = res12.email.split("@")(1)
							res13.split("[.]")(1) == domain
					}
					CommentSeq(res12)
					Future(Right(res12))
				case Failure(e) => Future(Left("Failure"))
			}
			case Failure(e) => Future(Left("Failure"))
		}
	}

	def getFileData(fileName: String): Future[Either[String, Map[String, Int]]] = {
		import scala.io.Source
		val file = new File(fileName)
		if (file.exists){
			val fileContents: Try[String] = Using(Source.fromFile(fileName)) { source => source.mkString
			}

			fileContents match {
				case Success(content) => Try(content.parseJson.convertTo[CommentSeq]) match {
					case Success(data) =>
						val res2 = data.comment.map(_.email).map{
						case s"${_}@${domain}" => domain
					}.map{
							case s"${_}.${domain}" => domain
						}.groupBy(x=>x).map(y=>(y._1,y._2.length))
						Future(Right(res2))
				}
				case _ =>  Future(Left("Failure in getting data"))
			}
		}
		else {
			Future(Left("File not Exist"))
		}
	}
}
