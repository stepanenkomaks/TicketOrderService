package com.stepanenko.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stepanenko.orderservice.client.GetStatusClient;
import com.stepanenko.orderservice.dto.OrderRequestDto;
import com.stepanenko.orderservice.event.OrderEvent;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
@ActiveProfiles("test")
class OrderServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private GetStatusClient getStatusClient;

	@MockBean
	private StreamBridge streamBridge;

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
	public void shouldPlaceAnOrder() {
		//GIVEN
		OrderRequestDto orderRequestDto = createOrderRequest();
		String orderRequestString = objectMapper.writeValueAsString(orderRequestDto);

		//WHEN
		when(getStatusClient.getStatus(1L)).thenReturn("DONE");
		when(streamBridge.send("order-topic", new OrderEvent(1L, "DONE"))).thenReturn(true);

		mockMvc.perform(post("/order")
						.content(orderRequestString)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value("1"))
				.andExpect(jsonPath("$.status").value("DONE"));
	}

	public OrderRequestDto createOrderRequest() {
		return OrderRequestDto.builder()
				.ticketId(1L)
				.credentials("Stepanenko Maks")
				.sum(200)
				.build();
	}
}
