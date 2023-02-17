package com.stepanenko.ticketservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stepanenko.ticketservice.dto.OrderInfoResponseDto;
import com.stepanenko.ticketservice.dto.RouteRequestDto;
import com.stepanenko.ticketservice.dto.TicketRequestDto;
import com.stepanenko.ticketservice.services.GetOrderInfoService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
public class TicketServiceTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GetOrderInfoService getOrderInfoService;


    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15.1");

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry properties) {
        properties.add("DB_URL", container::getJdbcUrl);
        properties.add("DB_USERNAME", container::getUsername);
        properties.add("DB_PASSWORD", container::getPassword);
    }

    @Test
    @SneakyThrows
    public void shouldCreateRoute() {
        //GIVEN
        RouteRequestDto routeRequestDto = createRouteRequest();
        String routeRequestString = objectMapper.writeValueAsString(routeRequestDto);

        //WHEN
        mockMvc.perform(post("/routes")
                        .content(routeRequestString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    @Sql(value = "/add_route.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldCreateTicket() {
        //GIVEN
        TicketRequestDto ticketRequestDto = createTicketRequest();
        String routeRequestString = objectMapper.writeValueAsString(ticketRequestDto);

        //WHEN
        mockMvc.perform(post("/tickets")
                        .content(routeRequestString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    @Sql(value = "/add_route.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/add_free_tickets.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldTakeATicket() {
        //GIVEN
        OrderInfoResponseDto orderInfoResponse = new OrderInfoResponseDto(2L, "DONE");

        //WHEN
        when(getOrderInfoService.getOrderInfo("Stepanenko Maks", 200, 11L)).thenReturn(orderInfoResponse);

        String result = mockMvc.perform(patch("/routes/3/take")
                .param("credentials", "Stepanenko Maks"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .split(",")[1];

        //THEN
        Assertions.assertEquals("\"value\":11}", result);
    }

    @Test
    @SneakyThrows
    @Sql(value = "/add_route.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldNotTakeATicket() {
        //WHEN
        mockMvc.perform(patch("/routes/3/take")
                        .param("credentials", "Stepanenko Maks"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$..message").
                        value("There are no available tickets for this route!"));
    }

    @Test
    @SneakyThrows
    @Sql(value = "/add_route.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldGetTheRoute() {
        //WHEN
        mockMvc.perform(get("/routes/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.placeFrom").value("Dnipro"))
                .andExpect(jsonPath("$.placeTo").value("Lviv"))
                .andExpect(jsonPath("$.departureTime").value("1648933200000"))
                .andExpect(jsonPath("$.price").value(200))
                .andExpect(jsonPath("$.availableTickets").value(0));
    }

    @Test
    @SneakyThrows
    public void shouldNotGetTheRoute() {
        //WHEN
        mockMvc.perform(get("/routes/3"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$..message").
                        value("Route not found!"));
    }

    @Test
    @SneakyThrows
    @Sql(value = "/add_route.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldGetAllRoutes() {
        //WHEN
        String response = mockMvc.perform(get("/routes"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ArrayList<Object> list = objectMapper.readValue(response, ArrayList.class);

        //THEN
        Assertions.assertEquals(3, list.size());
    }

    @Test
    @SneakyThrows
    @Sql(value = "/add_route.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/add_tickets.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldGetABookedTicketInfo() {
        //WHEN
        mockMvc.perform(get("/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.credentials").value("Stepanenko Maks"))
                .andExpect(jsonPath("$.orderStatus").value("DONE"))
                .andExpect(jsonPath("$.route.id").value(3L))
                .andExpect(jsonPath("$.route.placeFrom").value("Dnipro"))
                .andExpect(jsonPath("$.route.placeTo").value("Lviv"))
                .andExpect(jsonPath("$.route.price").value(200))
                .andExpect(jsonPath("$.route.availableTickets").value(1));
    }

    @Test
    @SneakyThrows
    public void shouldNotGetABookedTicketInfo() {
        //WHEN
        mockMvc.perform(get("/tickets/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$..message").
                        value("Ticket not found!"));
    }

    private TicketRequestDto createTicketRequest() {
        return TicketRequestDto.builder()
                .routeId(1L)
                .build();
    }

    private RouteRequestDto createRouteRequest() {
        return RouteRequestDto.builder()
                .placeFrom("Dnipro")
                .placeTo("Lviv")
                .departureTime(new Date())
                .price(200)
                .build();
    }
}
