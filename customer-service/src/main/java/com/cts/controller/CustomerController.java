package com.cts.controller;

import com.cts.dto.CustomerDTO;
import com.cts.feign.OrderFeignClient;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {
    private List<CustomerDTO> listDto = new ArrayList<>();

    @Autowired
    private OrderFeignClient orderFeignClient;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/{custId}")
    public ResponseEntity findById(@PathVariable("custId") int id){
        log.info("CustomerController class getCustomerById method");
        System.out.println(discoveryClient.getServices());// [customer-service, order-service]
        System.out.println(discoveryClient.getInstances("order-service")); // no case-sensitive
        return listDto.size() > id
                ? ResponseEntity.status(HttpStatus.OK).body(listDto.get(id-1))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Record not found");
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        log.info("CustomerController class getAllCustomers method");
        System.out.println("Id : "+orderFeignClient.findById(2));
        System.out.println("All Order : "+orderFeignClient.getAllCustomers());
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    @PostConstruct
    private void init(){
        listDto.add(new CustomerDTO(1, "mithilesh", "jamni"));
        listDto.add(CustomerDTO.builder().id(2).name("pappu").address("hyd").build());
        listDto.add(new CustomerDTO(3, "ravi", "hyd"));
        listDto.add(CustomerDTO.builder().id(4).name("satish").address("hyd").build());
        System.out.println(listDto);
    }
}
