package com.discovery.service;

import java.io.StringWriter;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discovery.repository.PlanetNameRepository;
import com.discovery.repository.PlanetRouteRepository;
import com.discovery.repository.ShortestDistancePathRepository;


/**
 * @author Mahesh.Bonagiri
 */
@Service
public class ShortestDistancePathService {

	private static final Logger log = LoggerFactory.getLogger(ShortestDistancePathService.class);
	
    @Autowired
    PlanetNameRepository planetNameRepository;

    @Autowired
    PlanetRouteRepository planetRouteRepository;

    @Autowired
    ShortestDistancePathRepository shortestDistancePathRepository;

    public String shortestPath(String sourceNode, String destinationNode) {
    	MessageFactory messageFactory;
    	String route = null;
		try {
			messageFactory = MessageFactory.newInstance();
			 SOAPMessage soapMessage = messageFactory.createMessage();

			 SOAPPart soapPart = soapMessage.getSOAPPart();

		        String myNamespace = "gen";
		        String myNamespaceURI = "http://www.musecs.com/springsoap/gen";

		        // SOAP Envelope
		        SOAPEnvelope envelope = soapPart.getEnvelope();
		        envelope.removeNamespaceDeclaration("SOAP-ENV");
		        envelope.addNamespaceDeclaration("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
		        envelope.setPrefix("soapenv");
		        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
		        SOAPBody soapBody = envelope.getBody();
		        soapBody.setPrefix("soapenv");
		        SOAPElement getShortPathRequest = soapBody.addChildElement("getShortPathRequest", myNamespace);
		        SOAPElement source = getShortPathRequest.addChildElement("source", myNamespace);
		        source.addTextNode(sourceNode);
		        SOAPElement destionation = getShortPathRequest.addChildElement("destionation", myNamespace);
		        destionation.addTextNode(destinationNode);
                soapMessage.saveChanges();
		        soapMessage.writeTo(System.out);
		        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
	            SOAPMessage soapResponse = soapConnection.call(soapMessage, "http://localhost:9000/ws");
	            soapResponse.writeTo(System.out);
	            String response =printSOAPResponse(soapResponse);
	            route = StringUtils.substringBetween(response, "<ns2:route>", "</ns2:route>"); 
	        
	            
	            soapConnection.close();
		} catch ( Exception e) {
			log.error("Error",e);
		}
       
        return route.replaceAll("-&gt;", "->");

}
    
    private static String printSOAPResponse(SOAPMessage soapResponse) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StringWriter stringResult = new StringWriter();
        StreamResult result = new StreamResult(stringResult);
        transformer.transform(sourceContent, result);
        return stringResult.toString();
        
    }
    
}
    
    
