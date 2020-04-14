package com.discovery.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.discovery.entity.PlanetNames;
import com.discovery.entity.PlanetRoutes;
import com.discovery.repository.PlanetNameRepository;
import com.discovery.repository.PlanetRouteRepository;
import com.discovery.service.ShortestDistancePathService;

/**
 * @author Mahesh.Bonagiri
 */

@SpringBootApplication (scanBasePackages = "com.discovery.*")
@EnableJpaRepositories("com.discovery.*")
@EntityScan( basePackages = {"com.discovery.*"} )
@EnableTransactionManagement
public class DemoApplication implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    PlanetRouteRepository shortestPathRepository;

    @Autowired
    PlanetNameRepository planetNameRepository;

    @Autowired
    ShortestDistancePathService shortestDistancePathService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
        	log.info(" Load PlanetNames ");
            importPlanetNamesFile();
            log.info("  Import File ");
            importPlanetRouteFile();
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void importPlanetRouteFile() throws IOException {
    	log.info(" start:: importFile ");
        File file = new ClassPathResource("planetroutes.txt").getFile();
        try {
            BufferedReader b = new BufferedReader(new FileReader(file));
            String readLine = "";
            b.readLine();
            while ((readLine = b.readLine()) != null) {
                PlanetRoutes planetRoute = new PlanetRoutes();
                planetRoute.setId(Long.parseLong(readLine.substring(0, 4).trim()));
                planetRoute.setPlanetSource(readLine.substring(4, 8).trim());
                planetRoute.setPlanetDestination(readLine.substring(8, 12).trim());
                planetRoute.setDistance(Float.parseFloat(readLine.substring(12).trim()));
                shortestPathRepository.save(planetRoute);

            }
            b.close();
            log.info(" end:: importFile ");
            List<PlanetRoutes> routeList = (List<PlanetRoutes>)shortestPathRepository.findAll();
            log.info(" size of the list is from H2 DB "+routeList.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void importPlanetNamesFile() throws IOException {
    	log.info(" start:: importPlanetNamesFile ");
        File file = new ClassPathResource("planetnames.txt").getFile();
        try {
            BufferedReader b = new BufferedReader(new FileReader(file));
            String readLine = "";
            b.readLine();
            while ((readLine = b.readLine()) != null) {
                PlanetNames planetNames = new PlanetNames();
                planetNames.setPlanetNode(readLine.substring(0, 4).trim());
                planetNames.setPlanetSourceName(readLine.substring(4).trim());
                planetNameRepository.save(planetNames);
            }
            b.close();
            log.info(" end:: importPlanetNamesFile ");
            List<PlanetNames> planetNamesList = (List<PlanetNames>)planetNameRepository.findAll();
            log.info(" size of the list is from H2 DB "+planetNamesList.size());

        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }


}

