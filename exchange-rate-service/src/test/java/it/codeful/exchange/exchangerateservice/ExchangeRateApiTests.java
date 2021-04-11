package it.codeful.exchange.exchangerateservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class ExchangeRateApiTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MockRestServiceServer mockNbp;

    @BeforeEach
    void setUpMockNbp() {
        mockNbp.expect(requestTo("/api/exchangerates/rates/c/USD"))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"table\":\"C\",\"currency\":\"dolar amerykański\",\"code\":\"USD\",\"rates\":[{\"no\":\"068/C/NBP/2021\",\"effectiveDate\":\"2021-04-09\",\"bid\":3.7796,\"ask\":3.8560}]}"));
    }

    @Test
    void buyUSD() throws Exception {
        mvc.perform(
                get("/USD/ask")
                        .param("amount", "10.00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("38.56"));
    }

    @Test
    void sellUSD() throws Exception {
        mvc.perform(
                get("/USD/bid")
                        .param("amount", "10.00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("37.79"));
    }
}
