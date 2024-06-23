package com.cts.feign;

import com.cts.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("ORDER-SERVICE")
public interface OrderFeignClient {

    @GetMapping(value = "/api/order/{id}")
    OrderDTO findById(@PathVariable("id") int id);
    @GetMapping(value = "/api/order")
    ResponseEntity<List> getAllCustomers();
}
