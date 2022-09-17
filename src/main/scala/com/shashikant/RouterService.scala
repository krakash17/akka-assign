package com.shashikant


import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route


import scala.concurrent.ExecutionContext
class RouterService(val worker: Worker) {

	def route: Route = path("comment-domain") {
		get {
			onSuccess(worker.getFileData("src/main/resources/commentData.json")) {
				case Right(data) =>
					complete(data.toList.toString())
					case Left(e) => complete(e)
			}
		}
	} ~ path("domain" / Segment){ domain =>
		get {
			onSuccess(worker.filterData(domain)) {
				case Right(data) =>
					complete(data.toString())
				case Left(e) => complete(e)
			}
		}
	}
}
