package com.shashikant

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}
object Server extends App {

	implicit val system: ActorSystem = ActorSystem("system")
	implicit val materializer: ActorMaterializer = ActorMaterializer()
	val config: Config = ConfigFactory.load("application.conf")
	val address = config.getString("app.host")
	val port = config.getInt("app.port")

	val worker = new Worker

	worker.getFileData("src/main/resources/commentData.json")
	val routerService = new RouterService(worker)

	val binding = Http().newServerAt(address, port).bind(routerService.route)
	println(s"Server is listening on $address: $port ")

}
