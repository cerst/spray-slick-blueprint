spray-slick-blueprint
=====================

This blueprint (partially) implements the typical book store example using
* Spray (-routing, -json, -can) - 1.2-RC3 (Akka 2.2.3)
* Slick - 1.0.1
* PostgreSQL - 9.1-901  (access configuration resides in src/main/resources/application.conf)
* ScalaTest - 2.0
* Mockito - 1.9.5

It is build with SBT including the intellij and revolver plugin
* use gen-idea to generate sources for intellij idea
* use re-start/ re-stop to start/stop the application from SBT
* optionally add --resetDb as cmd arg to drop (if exsists) and create the database schema

The application features a simple REST endpoint which allows you to
* add a new book via POST to 'api/store/books'
* retrieve all books of a collection via GET to 'api/store/collections/$id'

NOTE: The purpose of this blueprint is not to deliver a fully working example. It is merely a reference or starting point for creating applications with the framework stack specified above. 

 

