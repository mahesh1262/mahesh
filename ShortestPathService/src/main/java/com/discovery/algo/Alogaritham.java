package com.discovery.algo;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.discovery.entity.Graph;
import com.discovery.entity.Node;


/**
 * @author Mahesh.Bonagiri
 */
public class Alogaritham {

	private static final Logger log = LoggerFactory.getLogger(Alogaritham.class);
	
    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
    	log.info(" start :: calculateShortestPathFromSource ");
        source.setDistance(0f);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Entry<Node, Float> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Float edgeWeigh = adjacencyPair.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeigh, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        log.info(" end :: calculateShortestPathFromSource ");
        return graph;
    }

    private static void CalculateMinimumDistance(Node evaluationNode, Float edgeWeigh, Node sourceNode) {
    	log.info(" start :: CalculateMinimumDistance ");
        Float sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
        log.info(" end :: CalculateMinimumDistance ");
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
    	log.info(" start :: getLowestDistanceNode ");
        Node lowestDistanceNode = null;
        Float lowestDistance = Float.MAX_VALUE;
        for (Node node : unsettledNodes) {
            Float nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        log.info(" end  getLowestDistanceNode ");
        return lowestDistanceNode;
    }
}