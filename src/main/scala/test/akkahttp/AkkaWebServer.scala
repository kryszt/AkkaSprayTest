package test.akkahttp

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

/**
  */
object AkkaWebServer extends App {


  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  //hi-level way
  val route =
    path("bomb") {
      get {
        parameters('count.as[Int] ? 1) {
          count =>
            // XML is marshalled to `text/xml` by default, so we simply override here
            val string = List.fill(count)("This is a string that will be repeated so many times that we'll get a response soo big that something will have to fail eventually. ").mkString
            complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, string))
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 9000)
}
