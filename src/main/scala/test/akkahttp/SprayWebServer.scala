package test.akkahttp

import akka.actor.{Actor, ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import spray.can.Http
import spray.http.MediaTypes._
import spray.routing._

import scala.concurrent.duration._

/**
  */
object SprayWebServer extends App {

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[MyServiceActor], "demo-service")

  implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(service, interface = "0.0.0.0", port = 9000)

}

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with MyService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}

// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService {

  val myRoute =
    path("bomb") {
      get {
        parameters('count.as[Int] ? 1) {
          count =>
            respondWithMediaType(`text/plain`) {
              // XML is marshalled to `text/xml` by default, so we simply override here
              val string = List.fill(count)("This is a string that will be repeated so many times that we'll get a response soo big that something will have to fail eventually. ").mkString
              complete {
                string
              }
            }
        }
      }
    }
}