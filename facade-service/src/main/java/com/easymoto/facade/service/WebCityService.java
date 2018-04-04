package com.easymoto.facade.service;

import com.easymoto.facade.dto.City;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

/**
 * Hide the access to the microservice inside this local service.
 * 
 * @author Carlos E. Santin <cesantin@gmail.com>
 */
@Service
public class WebCityService {

  @Autowired
  @LoadBalanced
  protected RestTemplate restTemplate;

  protected String serviceUrl;

  protected Logger logger = Logger.getLogger(WebCityService.class
      .getName());

  public WebCityService(String serviceUrl) {
    this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
        : "http://" + serviceUrl;
  }

  public City findById(Integer id) {
    logger.info("findById() invoked: for " + id);
    return restTemplate.getForObject(serviceUrl + "/city/{number}",
        City.class, id);
  }

  public City addCity(Map<String, String> payload) {
    logger.info("addCity() invoked");
    ResponseEntity<City> entity = restTemplate.postForEntity(serviceUrl + "/city/add", payload,
        City.class);
    return entity.getBody();
  }

  public void removeCity(Integer id) {
    logger.info("removeCity() invoked: for " + id);
    String completeServiceUrl = String.format("%s/city/remove/%s", serviceUrl, id);
    restTemplate.exchange(completeServiceUrl, HttpMethod.POST, null, Void.class);
  }

  public City updateCity(Map<String, String> payload) {
    logger.info("updateCity() invoked");
    
    ResponseEntity<City> entity = restTemplate.postForEntity(serviceUrl + "/city/update", payload,
        City.class);
    City cityUpdated = entity.getBody();
    
    restTemplate.postForEntity(serviceUrl + "/distance/add", payload,
        City.class);

    return cityUpdated;
    // logger.info("reloading city "+cityUpdated.getId());
    // return findById(cityUpdated.getId());
  }

}