package com.addi.validatortesterservice.services;

import com.addi.validatortesterservice.controllers.TesterController;
import com.addi.validatortesterservice.domain.Lead;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.StringType;
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
public class InternalValidatorServiceWrapper {
    private static final Logger logger = LogManager.getLogger(InternalValidatorServiceWrapper.class);

    @Autowired
    DiscoveryClient discoveryClient;

    @Async
    public CompletableFuture<Lead> validateWithInternal(Lead candidate) {
        List<ServiceInstance> list =
                discoveryClient.getInstances("INTERNAL-VALIDATOR-SERVICE");
        ServiceInstance internalValidatorServiceInstance = list.get(0);
        URI internalValidatorServiceInstanceURI = internalValidatorServiceInstance.getUri();

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

        logger.debug("SERVICE-URI: "+ internalValidatorServiceInstanceURI);
        logger.debug("requestHeader: "+ headers.toString());
        logger.debug("requestBody: "+ leadJsonObject.toString());
        logger.debug("request: "+ request.toString());

        Lead lead = new
                RestTemplate().postForObject(internalValidatorServiceInstanceURI+
                "/internalvalidator/validateLead", request, Lead.class);
        return CompletableFuture.completedFuture(lead);
    }

    private Lead generateLead() {
        int length = 10;
        MockNeat mock = MockNeat.threadLocal();
        String firstName = mock.strings().size(length).type(StringType.LETTERS).get();
        String lastName = mock.strings().size(length).type(StringType.LETTERS).get();
        String email = mock.emails().val();
        int id = mock.ints().range(1,Integer.MAX_VALUE).get();
        Boolean isAccepted = Boolean.FALSE;

        Lead candidate = new Lead(id,firstName,lastName,email,isAccepted);
        return candidate;
    }


}
