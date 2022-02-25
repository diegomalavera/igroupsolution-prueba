package com.igroupsolution.prueba;

import com.igroupsolution.prueba.api.CustomerApi;
import com.igroupsolution.prueba.entity.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class PruebaApplication implements CommandLineRunner {

    private CustomerApi customerApi;

    public PruebaApplication(CustomerApi customerApi) {
        this.customerApi = customerApi;
    }

    @Override
    public void run(String... args) throws Exception {
        String generateId = Long.toString(new Date().getTime());
        Customer newCustomer = new Customer();
        newCustomer.setExternalId(generateId);
        newCustomer.setName("Nombre " + generateId);
        newCustomer.setEmail(generateId + "@email.com");
        customerApi.create(newCustomer);
    }

    public static void main(String[] args) {
        SpringApplication.run(PruebaApplication.class, args);
    }
}
