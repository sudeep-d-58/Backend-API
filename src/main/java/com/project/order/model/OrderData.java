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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderData {

    @Id
    @NotNull
    @Column(name = "id")
    String id;

    @Column(name = "distance")
    @NotNull
    int distance;

    @Column(name = "status")
    @NotNull
    String status;

    @Column(name = "originLat")
    @NotNull
    String startLatitude;

    @Column(name = "originLong")
    @NotNull
    String startLongitude;

    @Column(name = "destLat")
    @NotNull
    String destLatitude;

    @Column(name = "destLong")
    @NotNull
    String destLongitude;

}
