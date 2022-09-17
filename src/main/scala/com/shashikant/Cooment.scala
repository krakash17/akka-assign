package com.shashikant

import  spray.json._
case class CommentSeq(comment: List[Comment])

case class EmailSeq(domain: Int)

case class Comment(postId: Int, id: Int, name: String, email: String, body: String)


//object CommentResponse {

//  implicit val comment: OFormat[Comment] = Json.format[Comment]
//  implicit val commentSeq: OFormat[CommentSeq]= Json.format[CommentSeq]
//
// implicit val commentwrite: OWrites[Comment] = Json.writes[Comment]
// implicit val commentSeqwrite: OWrites[CommentSeq] = Json.writes[CommentSeq]
//
// def fromDomain(users: List[Comment]): CommentSeq =
//  CommentSeq(
//   users
//  )
//}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
	implicit val comment = jsonFormat5(Comment)
	implicit val commentSeq=  jsonFormat1(CommentSeq.apply)
}

object CommentSeq   extends JsonSupport {
	// implicit val comment: RootJsonFormat[Comment] = jsonFormat5(Comment)
	// implicit val commentSeq: RootJsonFormat[CommentSeq] = jsonFormat1(CommentSeq)
	//  implicit val comment: OFormat[Comment] = Json.format[Comment]
	//  implicit val commentSeq: OFormat[CommentSeq]= Json.format[CommentSeq]
	//
	//  implicit val commentwrite: OWrites[Comment] = Json.writes[Comment]
	//  implicit val commentSeqwrite: OWrites[CommentSeq] = Json.writes[CommentSeq]
	def fromDomain(users: List[Comment]): CommentSeq =
		CommentSeq(
			users
		)


	def extractDomain(email: String): Option[String] = email match {
		case s"${_}.${domain}" => Some(domain)
		case _ => None
	}
}