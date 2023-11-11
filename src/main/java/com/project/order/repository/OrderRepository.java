package com.project.order.repository;

import com.project.order.model.OrderData;
import com.project.order.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderData, String> {

    @Query("Select new com.project.order.response.OrderResponse(o.id, o.distance, o.status) from OrderData o")
    public Page<OrderResponse> findAllOrders(Pageable pageable);
}
