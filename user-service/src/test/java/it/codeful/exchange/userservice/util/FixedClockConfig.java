package it.codeful.exchange.userservice.util;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.validation.ClockProvider;
import java.time.Clock;
import java.time.ZonedDateTime;

import static java.time.ZoneOffset.UTC;

@TestConfiguration
public class FixedClockConfig {

    @Bean
    public ClockProvider getClockProvider() {
        ZonedDateTime fixedDate = ZonedDateTime.of(2021, 8, 8, 12, 0, 0, 0, UTC);
        return () -> Clock.fixed(fixedDate.toInstant(), fixedDate.getZone());
    }

}
