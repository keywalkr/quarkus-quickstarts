package com.keywalkr.commons.logger;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KWLoggerTest {

    @Mock
    private Logger log;

    @InjectMocks
    KWLogger cut;

    @Captor
    private ArgumentCaptor<String> logMessageCaptor;

    @Nested
    class Info {

        @Test
        void info() {
            // Arrange
            String Message = "Info";

            // Act
            cut.info(Message);

            // Assert
            verify(log).info(logMessageCaptor.capture());
            assertThat(logMessageCaptor.getValue()).isEqualTo("Message: " + Message);
        }

        @Test
        void withoutPattern() {
            assertThat(catchThrowable(() -> cut.info(null))).isInstanceOf(NullPointerException.class);
        }

        @Test
        void withPlaceholder() {
            // Act
            cut.info("User {0} is {1}", "John Doe", "Admin");

            // Assert
            verify(log).info(logMessageCaptor.capture());
            assertThat(logMessageCaptor.getValue()).isEqualTo("Message: User John Doe is Admin");
        }

        @Test
        void withNullPlaceholder() {
            // Act
            cut.info("User {0} is {1}", "John Doe", null);

            // Assert
            verify(log).info(logMessageCaptor.capture());
            assertThat(logMessageCaptor.getValue()).isEqualTo("Message: User John Doe is null");
        }
    }

}