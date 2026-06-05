package krawczyk.grzegorz.controller.service.client;

import krawczyk.grzegorz.model.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IpApiLocationAPIClientTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    @InjectMocks
    IpApiLocationAPIClient ipApiLocationAPIClient;

    @Test
    void getLocationShouldReturnProperLocationObjectIfStatusCode2xx() throws IOException, InterruptedException {
        // given
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn("""
                {
                    "city":"Warsaw",
                    "lat":52.23,
                    "lon":21.01
                }
                """);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        // when
        Location result = ipApiLocationAPIClient.getLocation();

        // then
        assertThat(result).isEqualTo(new Location("Warsaw", "21.01", "52.23"));
    }

    @Test
    void getLocationShouldThrowIOExceptionIfStatusCodeNOT2xx() throws IOException, InterruptedException {
        // given
        when(httpResponse.statusCode()).thenReturn(300);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        // when
        // then
        assertThrows(IOException.class, () -> ipApiLocationAPIClient.getLocation());
    }

    @Test
    void getLocationShouldThrowExceptionInCaseOfConnectionError() throws IOException, InterruptedException {
        // given
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(new InterruptedException());

        // when
        // then
        assertThrows(IOException.class, () -> ipApiLocationAPIClient.getLocation());
    }

    @Test
    void getLocationShouldThrowExceptionInCaseOfResponseBodyNull() throws IOException, InterruptedException {
        // given
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn(null);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        // when
        // then
        assertThrows(IOException.class, () -> ipApiLocationAPIClient.getLocation());
    }

}