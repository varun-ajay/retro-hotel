package com.example.hotel.controller;

import com.example.hotel.dto.CreateCustomerRequest;
import com.example.hotel.model.Customer;
import com.example.hotel.service.CustomerFileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerFileService service;

    public CustomerController(CustomerFileService service) {
        this.service = service;
    }

    @GetMapping
    public List<Customer> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<Customer> add(@Valid @RequestBody CreateCustomerRequest req) throws IOException {
        Customer c = new Customer(UUID.randomUUID().toString(), req.getName(), req.getPhoneNumber(), req.getCheckInRoom());
        return ResponseEntity.ok(service.add(c));  // returns full customer with ID
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) throws IOException {
        boolean removed = service.deleteById(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
