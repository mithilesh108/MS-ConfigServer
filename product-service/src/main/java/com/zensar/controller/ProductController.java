package com.zensar.controller;

import com.zensar.dto.ProductDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private static final List<ProductDTO> list = new ArrayList<>();

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping
    public ResponseEntity getAllUsers() {
        System.out.println("Feign cleint");
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getUser(@PathVariable int id) {
        System.out.println("Id : "+id +" list.size() "+list.size());
        System.out.println(discoveryClient.getServices()); //[product-service, user-service]  (ALL RUNNING SERVICE)
        System.out.println(discoveryClient.getInstances("USER-SERVICE"));
        //appName = USER-SERVICE, hostName = LAPTOP-V9DTTRHP, status = UP, ipAddr = 192.168.1.2, port = 8081
        return ResponseEntity.status(HttpStatus.OK).body(list.size() > id ? list.get(id-1) : null);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody ProductDTO user) {
        System.out.println("User dto : " + user);
        return list.add(user)
                ? ResponseEntity.status(HttpStatus.CREATED).body("user created id : " + user.getId())
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user not created");

    }

    @PostConstruct
    public void init() {
        System.out.println("controller's init method");
        list.add(new ProductDTO(1, "mobile", "electronic", 20000));
        list.add(new ProductDTO(2, "pant", "garments", 1000));
        list.add(new ProductDTO(3, "shirt", "garments", 2900));
        list.add(new ProductDTO(4, "tv", "electronic", 10000));
        list.add(new ProductDTO(5, "battery", "electronic", 5000));
    }
}
