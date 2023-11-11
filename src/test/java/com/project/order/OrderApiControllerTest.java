package com.project.order;

import com.project.order.controller.OrderApiController;
import com.project.order.exception.ExceptionHandler;
import com.project.order.service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.project.order.TestUtility.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderApiControllerTest {

    @InjectMocks
    private OrderApiController orderApiController;

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderApiController).setControllerAdvice(ExceptionHandler.class).build();
    }

    @Test
    public void createOrder201Created() throws Exception {
        mockMvc.perform(post("/orders").content(asJsonString(createOrderRequest())).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }

    @Test
    public void createOrder400BadRequest() throws Exception {
        mockMvc.perform(post("/orders").content(asJsonString(createBadOrderRequest())).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void takeOrder200Ok() throws Exception {
        mockMvc.perform(patch("/orders/abc-123").content(asJsonString(createPatchData())).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void getOrder200Ok() throws Exception {
        mockMvc.perform(get("/orders?page=1&limit=1")).andExpect(status().isOk());
    }

    @Test
    public void getOrder400BadRequest() throws Exception {
        mockMvc.perform(get("/orders?page=0&limit=1")).andExpect(status().isBadRequest());
    }

}
