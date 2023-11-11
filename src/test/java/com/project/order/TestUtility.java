package com.project.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.order.model.PatchData;
import com.project.order.request.OrderRequest;

public class TestUtility {

    public static OrderRequest createOrderRequest(){
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrigin(new String[]{"23.2324","87.8615"});
        orderRequest.setDestination(new String[]{"22.5709","88.5111"});
        return orderRequest;
    }
    public static OrderRequest createBadOrderRequest(){
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrigin(new String[]{"23.2324"});
        orderRequest.setDestination(new String[]{"22.5709","88.5111"});
        return orderRequest;
    }

    public static PatchData createPatchData(){
        return new PatchData("TAKEN");
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
