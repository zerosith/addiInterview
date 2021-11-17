package com.addi.validatortesterservice.services;

import com.addi.validatortesterservice.domain.Lead;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class NationalValidatorServiceWrapper {

    @Autowired
    DiscoveryClient discoveryClient;
    private static final Logger logger = LogManager.getLogger(NationalValidatorServiceWrapper.class);

    @Async
    public CompletableFuture<Boolean> validatePublicRegistry(Lead candidate) {

        List<ServiceInstance> list =
                discoveryClient.getInstances("NATIONAL-REGISTRY-SERVICE");

        ServiceInstance internalValidatorServiceInstance = list.get(0);
        URI internalValidatorServiceInstanceURI = internalValidatorServiceInstance.getUri();
        logger.debug("SERVICE-URI: "+ internalValidatorServiceInstanceURI);
        logger.debug("USING EXTERNAL NATIONAL VALIDATOR SERVICE");


        JSONObject leadJsonObject = new JSONObject();
        leadJsonObject.put("id",candidate.getId() );
        leadJsonObject.put("firstName",candidate.getFirstName() );
        leadJsonObject.put("lastName",candidate.getLastName());
        leadJsonObject.put("email",candidate.getEmail());
        leadJsonObject.put("isAccepted",candidate.isAccepted());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //Inserting headers into request
        //Inserting lead into request body
        HttpEntity<String> request =
                new HttpEntity<>(leadJsonObject.toString(), headers);

        Boolean validation = new
                RestTemplate().postForObject(internalValidatorServiceInstanceURI+
                "/thirdparty/nationalvalidator/validateRegistry", request, Boolean.class);
        return  CompletableFuture.completedFuture(validation);
    }


}
