Minimalistic example of akka-http and Spray web service.
Created to test connection issue when calling endpoint with `Connection: close` header.

Both services are almost clean copy of official examples. There is no extra configuration for any service. 

test.akkahttp.AkkaWebServer
test.akkahttp.SprayWebServer

Running service, respectively:
`sbt "run-main test.akkahttp.AkkaWebServer"`
`sbt "run-main test.akkahttp.SprayWebServer"`

Both start a http://localhost:9000/bomb endpoint, that takes a 'count' parameter co control size of response.

Example wget call to test connection (count=20000 means about 2.5MB of output):
`wget --no-http-keep-alive http://localhost:9000/bomb?count=20000`

For AkkaWebServer, response get cut after 0.5-1MB, while on SprayWebServer response gets through even for large (20+MB) responses.
For both services, without any headers, responses are sent uncut.
