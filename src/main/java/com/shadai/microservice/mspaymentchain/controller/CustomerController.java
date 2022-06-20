package com.shadai.microservice.mspaymentchain.controller;

import com.shadai.microservice.mspaymentchain.entity.Customer;
import com.shadai.microservice.mspaymentchain.repository.CustomerRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepo;

    public CustomerController(CustomerRepository customerRepo){
        this.customerRepo = customerRepo;
    }

   @GetMapping("/{id}")
    ResponseEntity<Customer> getCustomer(@PathVariable (value = "id") Long customerId){
        Customer customer = customerRepo.getById(customerId);
        if(customer == null){
            return new ResponseEntity<> (null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    @GetMapping()
    List<Customer> getAllCustomers(){
        return customerRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Customer> createPost(@RequestBody Customer customer){
        customerRepo.save(customer);
        return new ResponseEntity<>(customer,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateComment(@PathVariable (value = "id") Long customerId,
                                                @RequestBody Customer customer){
        Customer updatedCustomer = customerRepo.getById(customerId);
        if(updatedCustomer == null){
            return new ResponseEntity<> (null, HttpStatus.NOT_FOUND);
        }
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setPhone(customer.getPhone());
        updatedCustomer = customerRepo.save(updatedCustomer);                                                         
        return new ResponseEntity<> (updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable (value = "id") Long customerId){
        customerRepo.deleteById(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
