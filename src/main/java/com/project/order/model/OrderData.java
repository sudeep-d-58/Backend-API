package com.project.order.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "OrderData")
@Data
@Getter
@Setter
public class OrderData {

    @Id
    @NotNull
    @Column(name = "id")
    private String id;

    @Column(name = "distance")
    @NotNull
    private int distance;

    @Column(name = "status")
    @NotNull
    private String status;

    @Column(name = "originLat")
    @NotNull
    private String startLatitude;

    @Column(name = "originLong")
    @NotNull
    private String startLongitude;

    @Column(name = "destLat")
    @NotNull
    private String destLatitude;

    @Column(name = "destLong")
    @NotNull
    private String destLongitude;

}
