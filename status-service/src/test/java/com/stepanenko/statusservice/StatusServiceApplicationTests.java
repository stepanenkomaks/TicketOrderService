package com.stepanenko.statusservice;

import com.stepanenko.statusservice.model.Status;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StatusServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@SneakyThrows
	@Test
	void shouldGetStatus() {
		//GIVEN
		List<String> statuses = Stream.of(Status.values()).map(Enum::toString).toList();

		//WHEN
		String responseStatus = mockMvc.perform(get("http://localhost:8083/status")
						.param("orderId", "1"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		//THEN
		assertTrue(statuses.contains(responseStatus));
	}

}
