package com.project.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.order.exception.*;
import com.project.order.model.OrderData;
import com.project.order.model.PatchData;
import com.project.order.model.Response;
import com.project.order.repository.OrderRepository;
import com.project.order.request.OrderRequest;
import com.project.order.response.OrderResponse;
import com.project.order.util.OrderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.project.order.util.OrderUtil.*;

@Service
@Slf4j
public class OrderService {


    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(final RestTemplate restTemplate, final OrderRepository orderRepository) {
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository;
    }

    public OrderResponse createOrder(OrderRequest orderRequest) throws InvalidDataException, OrderPresentException, ApiResponseException {
        OrderResponse response = new OrderResponse();
        boolean isInvalidOriginLatitude = OrderUtil.validate(orderRequest.getOrigin()[0], 0);
        boolean isInvalidDestinationLatitude = OrderUtil.validate(orderRequest.getDestination()[0], 0);
        boolean isInvalidOriginLongitude = OrderUtil.validate(orderRequest.getOrigin()[1], 1);
        boolean isInvalidDestinationLongitude = OrderUtil.validate(orderRequest.getDestination()[1], 1);

        if (!isInvalidOriginLatitude || !isInvalidDestinationLatitude || !isInvalidOriginLongitude || !isInvalidDestinationLongitude) {
            throw new InvalidDataException("Invalid latitude/longitude.Please check");
        }


        String apiString = createApiString(orderRequest);
        log.info("Making API Call : " + apiString);
        int calculatedDistance = extractAndCalculateDistance(apiString);
        OrderData order = new OrderData();
        String id = UUID.randomUUID().toString();
        Optional<OrderData> orderObj = orderRepository.findById(id);
        if (orderObj.isPresent()) {
            throw new OrderPresentException("Issue with creating order id.Please try after some time");
        }
        order.setId(id);
        log.info("created order id : " + id);
        response.setId(id);
        order.setDistance(calculatedDistance);
        response.setDistance(calculatedDistance);
        order.setStatus(Response.UNASSIGNED.toString());
        response.setStatus(Response.UNASSIGNED.toString());
        order.setStartLatitude(orderRequest.getOrigin()[0]);
        order.setStartLongitude(orderRequest.getOrigin()[1]);
        order.setDestLongitude(orderRequest.getDestination()[1]);
        order.setDestLatitude(orderRequest.getDestination()[0]);
        orderRepository.save(order);
        return response;
    }

    public String createApiString(OrderRequest orderRequest) {
        StringBuffer stringBuffer = new StringBuffer(DIRECTIONS_API);
        stringBuffer.append("origin=").append(orderRequest.getOrigin()[0]).append(",").append(orderRequest.getOrigin()[1])
                .append("&").append("destination=")
                .append(orderRequest.getDestination()[0]).append(",").append(orderRequest.getDestination()[1])
                .append("&").append("key=")
                .append(API_KEY);
        return stringBuffer.toString();
    }

    private Integer extractAndCalculateDistance(String apiResponseString) throws ApiResponseException {
        String distanceInM = null;
        try {
            String responseObject = restTemplate.getForObject(apiResponseString, String.class);
            final JsonNode jsonNode = new ObjectMapper().readTree(responseObject);
            if (jsonNode.at(ROUTES).size() < 1) {
                throw new ApiResponseException("No distance data found.Please check");
            }
            String response = jsonNode.at(RESPONSE_STATUS).asText();
            if (!response.equalsIgnoreCase("OK")) {
                throw new ApiResponseException("External Service error.Please try after some time");
            }
            distanceInM = jsonNode.at(ROUTES).get(0).at(LEGS).get(0).at(DISTANCE_VALUE).asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("calculated distance : " + distanceInM);
        return new BigInteger(distanceInM).intValue();
    }

    public PatchData takeOrder(String id, PatchData patchData) throws OrderNotFoundException, OrderNotTakenException, ApiResponseException, InvalidDataException {
        validateTakeOrderStatus(patchData,id);
        Optional<OrderData> orderFromDb = orderRepository.findById(id);
        if (orderFromDb.isEmpty()) {
            log.info("order with id " + id + " was not found. Please check");
            throw new OrderNotFoundException("order not found for id " + id);
        }
        OrderData order = orderFromDb.get();
        if (!Objects.equals(order.getStatus(), Response.SUCCESS.toString())) {
            synchronized (this) {
                if (!Objects.equals(order.getStatus(), Response.SUCCESS.toString())) {
                    if(Objects.equals(patchData.getVal(), Response.TAKEN.toString())){
                        order.setStatus(Response.SUCCESS.toString());
                        log.info("order with id " + id + " is  assigned status : " + Response.SUCCESS);
                    }
                    else{
                        validateTakeOrderStatus(patchData,id);
                    }
                    orderRepository.save(order);
                }
            }
        } else {
            log.info("order with id " + id + " is already taken");
            throw new ApiResponseException("order with id " + id + " is already taken");
        }

        return new PatchData(Response.SUCCESS.toString());
    }

    private void validateTakeOrderStatus(PatchData data,String id) throws InvalidDataException {
        if(!(Objects.equals(data.getVal(), Response.TAKEN.toString()))){
            log.info("order with id " + id + " was  not taken. Accepted Value for status to take order : TAKEN");
            throw new InvalidDataException("Accepted Value for status to take order : TAKEN ");
        }
    }

    public List<OrderResponse> findAllOrders(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<OrderResponse> pagingOrder = orderRepository.findAllOrders(pageRequest);
        return pagingOrder.getContent();

    }
}
