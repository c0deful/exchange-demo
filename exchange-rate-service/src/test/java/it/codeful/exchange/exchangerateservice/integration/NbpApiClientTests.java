package it.codeful.exchange.exchangerateservice.integration;

import com.google.common.util.concurrent.UncheckedExecutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Disabled("intended for manual testing only")
public class NbpApiClientTests {

    @Autowired
    private NbpApiClient client;

    @Test
    void retrieveUSD() throws ExecutionException {
        BigDecimal usdAsk = client.getAskExchangeRate("USD");
        BigDecimal usdBid = client.getBidExchangeRate("USD");
        assertNotNull(usdAsk);
        assertNotNull(usdBid);
    }

    @Test
    void retrieveCHF() throws ExecutionException {
        BigDecimal chfAsk = client.getAskExchangeRate("CHF");
        BigDecimal chfBid = client.getBidExchangeRate("CHF");
        assertNotNull(chfAsk);
        assertNotNull(chfBid);
    }

    @Test
    void retrievePLN() {
        UncheckedExecutionException e = assertThrows(UncheckedExecutionException.class,
                () -> client.getAskExchangeRate("PLN"));
        Assertions.assertEquals(HttpClientErrorException.NotFound.class, e.getCause().getClass());
    }
}
