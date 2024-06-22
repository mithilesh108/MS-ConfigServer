package com.cts.controller;

import com.cts.dto.OrderDTO;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    private List<OrderDTO> listDto = new ArrayList<>();

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable("id") int id){
        log.info("CustomerController class getCustomerById method");
        return listDto.size() > id
                ? ResponseEntity.status(HttpStatus.OK).body(listDto.get(id-1))
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Record not found");
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllCustomers(){
        log.info("CustomerController class getAllCustomers method");
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    @PostConstruct
    private void init(){
        listDto.add(new OrderDTO(1, "mithilesh", "jamni","TV"));
        listDto.add(OrderDTO.builder().id(2).name("pappu").address("hyd").category("Bike").build());
        listDto.add(new OrderDTO(3, "ravi", "hyd","car"));
        listDto.add(OrderDTO.builder().id(4).name("satish").address("hyd").category("Pant").build());
        System.out.println(listDto);
    }
}
