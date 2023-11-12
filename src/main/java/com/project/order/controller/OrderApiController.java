package com.project.order.controller;

import com.project.order.exception.*;
import com.project.order.model.PatchData;
import com.project.order.request.OrderRequest;
import com.project.order.response.OrderResponse;
import com.project.order.service.OrderService;
import com.project.order.validation.ValidateInput;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
public class OrderApiController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/orders", produces = {"application/json"})
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) throws InvalidDataException, OrderPresentException, ApiResponseException,NumberFormatException {
        if((orderRequest.getOrigin().length != 2) || (orderRequest.getDestination().length !=2)){
            throw new InvalidDataException("Please provide only 2 inputs in origin & destination each");
        }
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/orders/{id}", produces = {"application/json"})
    public ResponseEntity<PatchData> takeOrder(@PathVariable String id, @RequestBody PatchData data) throws OrderNotFoundException, OrderNotTakenException, ApiResponseException, InvalidDataException {
        return new ResponseEntity<>(orderService.takeOrder(id, data), HttpStatus.OK);
    }

    @GetMapping(value = "/orders", produces = {"application/json"})
    public ResponseEntity<List<OrderResponse>> findAllOrders(@RequestParam(value = "page", required = true, defaultValue = "1")
                                                               int page,
                                                             @RequestParam(value = "limit", required = true, defaultValue = "10")
                                                             @Valid @Size(min = 1) int limit) throws InvalidDataException {
        if((page % 1 != 0) || (limit % 1 != 0)){
            throw new InvalidDataException("Page/limit value should Integer");
        }
        if(page <1 || limit <1){
            throw new InvalidDataException("Page/limit value should be atleast 1");
        }

        return new ResponseEntity<>(orderService.findAllOrders(page - 1, limit), HttpStatus.OK);
    }
}
