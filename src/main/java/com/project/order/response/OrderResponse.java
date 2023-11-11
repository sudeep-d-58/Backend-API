package com.project.order.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("distance")
    private int distance;

    @JsonProperty("status")
    private String status;

    public OrderResponse(String id, int distance, String status) {
        this.id = id;
        this.distance = distance;
        this.status = status;
    }

    public OrderResponse() {

    }


}
