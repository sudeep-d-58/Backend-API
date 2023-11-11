package com.project.order;

import com.project.order.model.OrderData;
import com.project.order.model.PatchData;
import com.project.order.repository.OrderRepository;
import com.project.order.request.OrderRequest;
import com.project.order.response.OrderResponse;
import com.project.order.service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import static com.project.order.TestUtility.createOrderRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTest {

    private OrderRepository orderRepository = Mockito.mock(OrderRepository.class);


    private RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

    @InjectMocks
    private OrderService orderService;

    @BeforeAll
    public void init() throws Exception {
        orderService = new OrderService(restTemplate, orderRepository);

    }

    @Test
    public void testGetOrder() throws Exception{
        PageRequest pageRequest = Mockito.mock(PageRequest.class);
        Page<OrderResponse> pagingOrder = Mockito.mock(Page.class);
        when(orderRepository.findAllOrders(pageRequest)).thenReturn(pagingOrder);
    }

    @Test
    public void testTakeOrder() throws Exception{
        OrderData order = Mockito.mock(OrderData.class);
        when(orderRepository.findById(anyString())).thenReturn(Optional.ofNullable(order));
        PatchData patchData =  new PatchData("TAKEN");
        assertThat(orderService.takeOrder(anyString(),patchData)).isEqualTo(new PatchData("SUCCESS"));
    }

    @Test
    public void testCreateOrder() throws Exception{
        OrderRequest orderRequest = createOrderRequest();
        when(restTemplate.getForObject(Mockito.anyString(),Mockito.any(Class.class),  ArgumentMatchers.<Object>any())).thenReturn(new Object());
        Mockito.when(restTemplate.getForObject(any(String.class),any(Class.class), any(Map.class))).thenReturn(new Object());
        Mockito.when(restTemplate.getForObject(any(URI.class),any(Class.class))).thenReturn(new Object());
        //assertThat(orderService.createOrder(orderRequest)).isEqualTo(new OrderResponse(anyString(),anyInt(),anyString()));
    }
}
