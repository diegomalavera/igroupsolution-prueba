package com.igroupsolution.prueba.api;

import com.igroupsolution.prueba.entity.Customer;
import com.igroupsolution.prueba.entity.ListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class CustomerApi extends FlowApi {

    private static Logger LOGGGER = LoggerFactory.getLogger(CustomerApi.class);

    public Customer create(Customer customer) throws FlowException {
        try {
            Map<String, String> params = new TreeMap<>();
            params.put("name", customer.getName());
            params.put("email", customer.getEmail());
            params.put("externalId", customer.getExternalId());
            String signature = getSignature(params);
            params.put("apiKey", getFlowApiKey());
            params.put("s", signature);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(getData(params), headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(
                    getApiEndpoint("/customer/create", null),
                    HttpMethod.POST,
                    request,
                    Customer.class
            ).getBody();
        } catch (HttpStatusCodeException ex) {
            throw new FlowException("Error al crear el cliente");
        }
    }

    public Customer update(Customer customer) throws FlowException {
        try {
            Map<String, String> params = new TreeMap<>();
            params.put("customerId", customer.getCustomerId());
            params.put("name", customer.getName());
            params.put("email", customer.getEmail());
            params.put("externalId", customer.getExternalId());
            String signature = getSignature(params);
            params.put("apiKey", getFlowApiKey());
            params.put("s", signature);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(getData(params), headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(
                    getApiEndpoint("/customer/edit", null),
                    HttpMethod.POST,
                    request,
                    Customer.class
            ).getBody();
        } catch (HttpStatusCodeException ex) {
            throw new FlowException("Error al actualizar el cliente");
        }
    }

    public Customer delete(String customerId) throws FlowException {
        try {
            Map<String, String> params = new TreeMap<>();
            params.put("customerId", customerId);
            String signature = getSignature(params);
            params.put("apiKey", getFlowApiKey());
            params.put("s", signature);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            System.out.println(getData(params));
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(getData(params), headers);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.exchange(
                    getApiEndpoint("/customer/delete", null),
                    HttpMethod.POST,
                    request,
                    Customer.class
            ).getBody();
        } catch (HttpStatusCodeException ex) {
            LOGGGER.info(ex.getResponseBodyAsString());
            throw new FlowException("Error al eliminar el cliente");
        }
    }

    public List<Customer> list() throws FlowException {
        ListResponse<Customer> response = null;
        try {
            String signature = getSignature(new TreeMap<>());
            System.out.println("signature-list=" + signature);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(
                    getApiEndpoint("/customer/list", getApiQuery(signature)),
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<ListResponse<Customer>>() {
                    }).getBody();
            return response.getData();
        } catch (HttpStatusCodeException ex) {
            System.out.println(ex.getResponseBodyAsString());
            throw new FlowException("Error al consultar los clientes");
        }
    }
}
