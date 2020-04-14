package com.discovery.soap;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.discovery.algo.Alogaritham;
import com.discovery.entity.Graph;
import com.discovery.entity.Node;
import com.discovery.entity.PlanetNames;
import com.discovery.entity.PlanetRoutes;
import com.discovery.entity.ShortestPath;
import com.discovery.repository.PlanetNameRepository;
import com.discovery.repository.PlanetRouteRepository;
import com.discovery.repository.ShortestDistancePathRepository;
import com.discovery.service.ShortestDistancePathService;



@Endpoint
public class ShortPathEndpoint {

    private static final String NAMESPACE_URI = "http://www.musecs.com/springsoap/gen";
	private static final Logger log = LoggerFactory.getLogger(ShortestDistancePathService.class);
	
    @Autowired
    PlanetNameRepository planetNameRepository;

    @Autowired
    PlanetRouteRepository planetRouteRepository;

    @Autowired
    ShortestDistancePathRepository shortestDistancePathRepository;

   
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getShortPathRequest")
    @ResponsePayload
    public GetShortPathResponse getCountry(@RequestPayload GetShortPathRequest request) {
    	GetShortPathResponse response = new GetShortPathResponse();
    	ShortDistance shortDistance = new ShortDistance();
    	String shortestDistance =shortestPath(request.getSource(), request.getDestionation());
    	shortDistance.setRoute(shortestDistance);
    	response.setShortDistance(shortDistance);
        return response;
    }
    
    private String shortestPath(String sourceNode, String destinationNode) {
    	log.info(" start :: shortestPath ");
        List<PlanetNames> planetNames = (List<PlanetNames>)planetNameRepository.findAll();
        List<Node> listNode = new ArrayList<>();
        planetNames.forEach(s -> {
            Node node = new Node(s.getPlanetNode());
            listNode.add(node);
        });

        List<PlanetRoutes> routes = (List<PlanetRoutes>)planetRouteRepository.findAll();
        listNode.forEach(n -> {
            addDestination(n, listNode, routes);
        });

        Graph graph1 = new Graph();
        for (Node node : listNode) {
            graph1.addNode(node);
        }
        graph1 = Alogaritham.calculateShortestPathFromSource(graph1, listNode.get(0));

        System.out.println(" after graph1 ");
        StringBuffer sb = new StringBuffer();
        for( Node node:graph1.getNodes()) {

            if(node.getName().equalsIgnoreCase(destinationNode)) {
                for(Node n: node.getShortestPath()) {
                    System.out.println(" getShortestPath  >>>>>>>>>>> "+n.getName());
                    sb.append(n.getName()).append("->");
                }
            }

        }
        

        for (PlanetNames planetName : planetNames) {
            ShortestPath shortestPath = new ShortestPath();
            for (Node node : graph1.getNodes()) {
                if (node.getName().equalsIgnoreCase(planetName.getPlanetNode())) {
                    shortestPath.setId(planetName.getId());
                    shortestPath.setPlanetNode(node.getName());
                    shortestPath.setPlanetName(planetName.getPlanetSourceName());
                    shortestPath.setPath(node.getPath());
                }

            }
           shortestDistancePathRepository.save(shortestPath);
        }
        log.info(" end :: shortestPath ");
        return sb.append(destinationNode).toString();
    }
    
    private void addDestination(Node n, List<Node> listNode, List<PlanetRoutes> routes) {
    	log.info(" start :: addDestination ");
        routes.forEach(r -> {
            if (r.getPlanetSource().equalsIgnoreCase(n.getName())) {
                listNode.forEach(l -> {
                    if (l.getName().equalsIgnoreCase(r.getPlanetDestination())) {
                        n.addDestination(l, r.getDistance());
                    }
                });
            }
        });
        log.info(" end :: addDestination s");
    }
}
