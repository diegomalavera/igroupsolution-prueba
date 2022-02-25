package com.igroupsolution.prueba.rest;

import com.igroupsolution.prueba.api.CustomerApi;
import com.igroupsolution.prueba.api.FlowException;
import com.igroupsolution.prueba.entity.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class TestRestController {

    private CustomerApi customerApi;

    public TestRestController(CustomerApi customerApi) {
        this.customerApi = customerApi;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() throws FlowException {
        return new ResponseEntity<>(customerApi.list(), HttpStatus.OK);
    }
}
