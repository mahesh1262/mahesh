Application: ShortestDistancePath
Purpose : to find out the distance between any two planets (source and distance)
========================================================================================

Package Structure
---------------------
com.discovery.algo --> Dijkstra algorithm to find out shortest path
com.discovery.controller --> Rest Controller for import the file and 
					to load the UI and deligate the request to the service and repository.
com.discovery.service --> Service classes.
com.discovery.repositoy --> repository classes 
						and CRUD operational methods for graph domain object.
com.discovery.entity --> entities goes here.

graph files
------------------

	/resources folder 

Application POrt 
--------------------

application start with 8888 port 

Frontend pages
-----------------
html files place under the resources/templates


to start the application 
--------------------------
DemoApplication .java ---> starting point of the service 
run this class to read the graph file and persist with H2 Database on application start up.
so that data is ready after start up.

URLs to the load the UI page 
-----------------------------
http://localhost:9000/ --> it loads the frondend page where you can select source and destination planet

on submission, the URI /findshortestpath will be called and displays the result.

and there is Back button to resubmit the page with difference planets.



how run the application.
---------------------------

mvn clean install --> it generate the .jar in the below path 
	\target\ShortestPathService-0.0.1-SNAPSHOT.jar