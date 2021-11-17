package com.addi.validatortesterservice.controllers;

import com.addi.validatortesterservice.domain.Lead;
import com.addi.validatortesterservice.services.CriminalValidatorServiceWrapper;
import com.addi.validatortesterservice.services.InternalValidatorServiceWrapper;
import com.addi.validatortesterservice.services.NationalValidatorServiceWrapper;
import com.google.common.base.Function;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.StringType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/tester")
public class TesterController {
    private static final Logger logger = LogManager.getLogger(TesterController.class);

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    InternalValidatorServiceWrapper internalValidatorService;
    @Autowired
    NationalValidatorServiceWrapper nationalValidatorService;
    @Autowired
    CriminalValidatorServiceWrapper criminalValidatorService;

    @PostMapping(value = "/sendLeads", consumes = "application/json", produces = "application/json")
    public Object sendLeadToValidate()  {
        List<ServiceInstance> list =
                discoveryClient.getInstances("INTERNAL-VALIDATOR-SERVICE");
        ServiceInstance internalValidatorServiceInstance = list.get(0);
        URI internalValidatorServiceInstanceURI = internalValidatorServiceInstance.getUri();

        Lead candidate = generateLead();
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

        Object lead = new
                  RestTemplate().postForObject(internalValidatorServiceInstanceURI+
                "/internalvalidator/validateLead", request, Object.class);
        return lead;
    }

    @PostMapping(value = "/getAcceptedLeads", consumes = "application/json", produces = "application/json")
    public Object getAcceptedLeads()  {
        List<ServiceInstance> list =
                discoveryClient.getInstances("INTERNAL-VALIDATOR-SERVICE");
        ServiceInstance internalValidatorServiceInstance = list.get(0);
        URI internalValidatorServiceInstanceURI = internalValidatorServiceInstance.getUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        logger.debug("SERVICE-URI: "+ internalValidatorServiceInstanceURI);
        logger.debug("requestHeader: "+ headers.toString());
        HttpEntity<String> request =
                new HttpEntity<>(null, headers);
        ResponseEntity<Object> response = new RestTemplate().exchange(
                internalValidatorServiceInstanceURI+
                            "/internalvalidator/authorizedLeads",
                HttpMethod.GET,
                request,
                Object.class
        );

        logger.debug("response body:" +response.getBody());
        return response.getBody();
    }

    @PostMapping(value = "/nationalServiceValidate", consumes = "application/json", produces = "application/json")
    public Object validatePublicRegistry(@RequestBody Lead candidate) {

        List<ServiceInstance> list =
                discoveryClient.getInstances("NATIONAL-REGISTRY-SERVICE");
        ServiceInstance internalValidatorServiceInstance = list.get(0);
        URI internalValidatorServiceInstanceURI = internalValidatorServiceInstance.getUri();
        logger.debug("SERVICE-URI: "+ internalValidatorServiceInstanceURI);

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

        Object validation = new
                RestTemplate().postForObject(internalValidatorServiceInstanceURI+
                "/thirdparty/nationalvalidator/validateRegistry", request, Object.class);
        return validation;
    }

    @PostMapping(value = "/criminalServiceValidate", consumes = "application/json", produces = "application/json")
    public Object validateCriminalRegistry(@RequestBody Lead candidate) {
        List<ServiceInstance> list =
                discoveryClient.getInstances("CRIMINAL-REGISTRY-SERVICE");
        ServiceInstance internalValidatorServiceInstance = list.get(0);
        URI internalValidatorServiceInstanceURI = internalValidatorServiceInstance.getUri();
        logger.debug("SERVICE-URI: "+ internalValidatorServiceInstanceURI);

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

        Object validation = new
                RestTemplate().postForObject(internalValidatorServiceInstanceURI+
                "/thirdparty/criminalvalidator/validateRegistry", request, Object.class);
        return validation;

    }

    @PostMapping(value = "/completeUseCase", consumes = "application/json", produces = "application/json")
    public Boolean completeUseCase(@RequestBody Lead candidate) throws ExecutionException, InterruptedException {

        CompletableFuture<Lead>  internalValidatorResult = internalValidatorService.validateWithInternal(candidate);
        CompletableFuture<Boolean>  nationalValidatorResult = nationalValidatorService.validatePublicRegistry(candidate);
        CompletableFuture<Boolean>  criminalValidatorResult = criminalValidatorService.validateCriminalRegistry(candidate);

        // Wait until they are all done
        logger.debug("completeUsecase");

        CompletableFuture<Boolean> allWithFailFast =
                CompletableFuture.allOf(nationalValidatorResult,criminalValidatorResult)
                .thenApply( __ -> nationalValidatorResult.join() && criminalValidatorResult.join() );

        Boolean nationalAndCriminalValidatorResult = allWithFailFast.get();
        Lead lead = internalValidatorResult.get();
        return  nationalAndCriminalValidatorResult && lead.isAccepted();
    }

    @PostMapping(value = "/repeatableCompleteUseCase", consumes = "application/json", produces = "application/json")
    public void repeatableCompleteUseCase() throws ExecutionException, InterruptedException {
        IntStream.range(0, 100)
                .forEach((i) -> {
                    Lead lead = generateLead();
                    try {
                        System.out.print (completeUseCase(lead));
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
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
