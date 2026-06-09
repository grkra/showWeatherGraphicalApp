package krawczyk.grzegorz.controller.service.client;

import krawczyk.grzegorz.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenWeatherMapWeatherAPIClientTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponseCurrentWeather;

    @Mock
    private HttpResponse<String> httpResponseWeatherForecast;

    @InjectMocks
    @Spy
    private OpenWeatherMapWeatherAPIClient openWeatherMapWeatherAPIClient;

    private final String currentWeatherHttpResponseBodyForTests = """
            {"coord":{"lon":21.0093,"lat":52.2299},"weather":[{"id":502,"main":"Rain","description":"heavy intensity rain","icon":"10d"}],"base":"stations","main":{"temp":17.03,"feels_like":16.93,"temp_min":16.47,"temp_max":17.74,"pressure":1011,"humidity":82,"sea_level":1011,"grnd_level":1000},"visibility":10000,"wind":{"speed":6.26,"deg":283,"gust":8.73},"rain":{"1h":8.24},"clouds":{"all":98},"dt":1780680090,"sys":{"type":2,"id":2035775,"country":"PL","sunrise":1780625884,"sunset":1780685465},"timezone":7200,"id":756135,"name":"Warsaw","cod":200}
            """;
    private final String weatherForecastHttpResponseBodyForTests = """
            {"cod":"200","message":0,"cnt":40,"list":[{"dt":1780693200,"main":{"temp":14.47,"feels_like":14.32,"temp_min":14.47,"temp_max":14.85,"pressure":1015,"sea_level":1015,"grnd_level":1004,"humidity":90,"temp_kf":-0.38},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10n"}],"clouds":{"all":100},"wind":{"speed":4.79,"deg":289,"gust":9.5},"visibility":10000,"pop":0.31,"rain":{"3h":0.16},"sys":{"pod":"n"},"dt_txt":"2026-06-05 21:00:00"},{"dt":1780704000,"main":{"temp":14.64,"feels_like":14.54,"temp_min":14.64,"temp_max":14.98,"pressure":1015,"sea_level":1015,"grnd_level":1005,"humidity":91,"temp_kf":-0.34},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],"clouds":{"all":99},"wind":{"speed":4.24,"deg":280,"gust":7.43},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2026-06-06 00:00:00"},{"dt":1780714800,"main":{"temp":13.76,"feels_like":13.54,"temp_min":13.4,"temp_max":13.76,"pressure":1017,"sea_level":1017,"grnd_level":1007,"humidity":90,"temp_kf":0.36},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":61},"wind":{"speed":2.48,"deg":279,"gust":4.74},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-06 03:00:00"},{"dt":1780725600,"main":{"temp":17.35,"feels_like":16.79,"temp_min":17.35,"temp_max":17.35,"pressure":1019,"sea_level":1019,"grnd_level":1008,"humidity":63,"temp_kf":0},"weather":[{"id":801,"main":"Clouds","description":"few clouds","icon":"02d"}],"clouds":{"all":21},"wind":{"speed":1.81,"deg":271,"gust":2.69},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-06 06:00:00"},{"dt":1780736400,"main":{"temp":21.75,"feels_like":21.29,"temp_min":21.75,"temp_max":21.75,"pressure":1019,"sea_level":1019,"grnd_level":1008,"humidity":50,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"clouds":{"all":0},"wind":{"speed":2.29,"deg":242,"gust":2.4},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-06 09:00:00"},{"dt":1780747200,"main":{"temp":28.21,"feels_like":27.78,"temp_min":28.21,"temp_max":28.21,"pressure":1018,"sea_level":1018,"grnd_level":1007,"humidity":39,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"clouds":{"all":3},"wind":{"speed":1.95,"deg":252,"gust":1.5},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-06 12:00:00"},{"dt":1780758000,"main":{"temp":23.79,"feels_like":23.4,"temp_min":23.79,"temp_max":23.79,"pressure":1018,"sea_level":1018,"grnd_level":1007,"humidity":45,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":70},"wind":{"speed":2.45,"deg":297,"gust":2.06},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-06 15:00:00"},{"dt":1780768800,"main":{"temp":21.79,"feels_like":21.43,"temp_min":21.79,"temp_max":21.79,"pressure":1019,"sea_level":1019,"grnd_level":1008,"humidity":54,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":84},"wind":{"speed":2.44,"deg":41,"gust":3.96},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-06 18:00:00"},{"dt":1780779600,"main":{"temp":19.73,"feels_like":19.33,"temp_min":19.73,"temp_max":19.73,"pressure":1020,"sea_level":1020,"grnd_level":1008,"humidity":60,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],"clouds":{"all":100},"wind":{"speed":1.82,"deg":88,"gust":3.65},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2026-06-06 21:00:00"},{"dt":1780790400,"main":{"temp":19.88,"feels_like":19.52,"temp_min":19.88,"temp_max":19.88,"pressure":1020,"sea_level":1020,"grnd_level":1008,"humidity":61,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04n"}],"clouds":{"all":100},"wind":{"speed":1.9,"deg":163,"gust":4.13},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2026-06-07 00:00:00"},{"dt":1780801200,"main":{"temp":19.27,"feels_like":18.87,"temp_min":19.27,"temp_max":19.27,"pressure":1020,"sea_level":1020,"grnd_level":1009,"humidity":62,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":100},"wind":{"speed":1.49,"deg":179,"gust":3.4},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-07 03:00:00"},{"dt":1780812000,"main":{"temp":20.52,"feels_like":20.27,"temp_min":20.52,"temp_max":20.52,"pressure":1021,"sea_level":1021,"grnd_level":1010,"humidity":63,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":100},"wind":{"speed":1.19,"deg":195,"gust":2.2},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-07 06:00:00"},{"dt":1780822800,"main":{"temp":25.13,"feels_like":24.85,"temp_min":25.13,"temp_max":25.13,"pressure":1020,"sea_level":1020,"grnd_level":1009,"humidity":44,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":100},"wind":{"speed":2.4,"deg":182,"gust":2.91},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-07 09:00:00"},{"dt":1780833600,"main":{"temp":25.58,"feels_like":25.19,"temp_min":25.58,"temp_max":25.58,"pressure":1019,"sea_level":1019,"grnd_level":1008,"humidity":38,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":100},"wind":{"speed":2.82,"deg":198,"gust":3.65},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-07 12:00:00"},{"dt":1780844400,"main":{"temp":24.37,"feels_like":23.88,"temp_min":24.37,"temp_max":24.37,"pressure":1018,"sea_level":1018,"grnd_level":1007,"humidity":39,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":69},"wind":{"speed":3.55,"deg":206,"gust":4.53},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-07 15:00:00"},{"dt":1780855200,"main":{"temp":18.87,"feels_like":18.8,"temp_min":18.87,"temp_max":18.87,"pressure":1019,"sea_level":1019,"grnd_level":1008,"humidity":76,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"clouds":{"all":61},"wind":{"speed":2.09,"deg":299,"gust":5.28},"visibility":10000,"pop":1,"rain":{"3h":2.08},"sys":{"pod":"d"},"dt_txt":"2026-06-07 18:00:00"},{"dt":1780866000,"main":{"temp":16.66,"feels_like":16.57,"temp_min":16.66,"temp_max":16.66,"pressure":1020,"sea_level":1020,"grnd_level":1009,"humidity":84,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10n"}],"clouds":{"all":44},"wind":{"speed":2.11,"deg":272,"gust":5.53},"visibility":10000,"pop":0.2,"rain":{"3h":0.19},"sys":{"pod":"n"},"dt_txt":"2026-06-07 21:00:00"},{"dt":1780876800,"main":{"temp":14.32,"feels_like":14.11,"temp_min":14.32,"temp_max":14.32,"pressure":1020,"sea_level":1020,"grnd_level":1009,"humidity":88,"temp_kf":0},"weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03n"}],"clouds":{"all":25},"wind":{"speed":3.27,"deg":272,"gust":8.8},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2026-06-08 00:00:00"},{"dt":1780887600,"main":{"temp":12.68,"feels_like":12.28,"temp_min":12.68,"temp_max":12.68,"pressure":1020,"sea_level":1020,"grnd_level":1009,"humidity":87,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"clouds":{"all":5},"wind":{"speed":2.99,"deg":278,"gust":8.58},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-08 03:00:00"},{"dt":1780898400,"main":{"temp":16.21,"feels_like":15.74,"temp_min":16.21,"temp_max":16.21,"pressure":1021,"sea_level":1021,"grnd_level":1009,"humidity":71,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"clouds":{"all":5},"wind":{"speed":4.19,"deg":288,"gust":7.16},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-08 06:00:00"},{"dt":1780909200,"main":{"temp":19.66,"feels_like":19.12,"temp_min":19.66,"temp_max":19.66,"pressure":1020,"sea_level":1020,"grnd_level":1009,"humidity":55,"temp_kf":0},"weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03d"}],"clouds":{"all":41},"wind":{"speed":4.6,"deg":286,"gust":6.01},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-08 09:00:00"},{"dt":1780920000,"main":{"temp":21.61,"feels_like":21.05,"temp_min":21.61,"temp_max":21.61,"pressure":1019,"sea_level":1019,"grnd_level":1008,"humidity":47,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":58},"wind":{"speed":4.87,"deg":263,"gust":5.86},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-08 12:00:00"},{"dt":1780930800,"main":{"temp":21.32,"feels_like":20.71,"temp_min":21.32,"temp_max":21.32,"pressure":1018,"sea_level":1018,"grnd_level":1007,"humidity":46,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":62},"wind":{"speed":3.59,"deg":285,"gust":4.37},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-08 15:00:00"},{"dt":1780941600,"main":{"temp":18.95,"feels_like":18.42,"temp_min":18.95,"temp_max":18.95,"pressure":1018,"sea_level":1018,"grnd_level":1007,"humidity":58,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":82},"wind":{"speed":2.5,"deg":288,"gust":4.54},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-08 18:00:00"},{"dt":1780952400,"main":{"temp":15.99,"feels_like":15.47,"temp_min":15.99,"temp_max":15.99,"pressure":1017,"sea_level":1017,"grnd_level":1006,"humidity":70,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"clouds":{"all":3},"wind":{"speed":1.19,"deg":297,"gust":1.32},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2026-06-08 21:00:00"},{"dt":1780963200,"main":{"temp":14.56,"feels_like":14,"temp_min":14.56,"temp_max":14.56,"pressure":1016,"sea_level":1016,"grnd_level":1005,"humidity":74,"temp_kf":0},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"clouds":{"all":4},"wind":{"speed":1.59,"deg":172,"gust":1.54},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2026-06-09 00:00:00"},{"dt":1780974000,"main":{"temp":13.75,"feels_like":13.22,"temp_min":13.75,"temp_max":13.75,"pressure":1016,"sea_level":1016,"grnd_level":1004,"humidity":78,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":69},"wind":{"speed":2.01,"deg":144,"gust":3.73},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-09 03:00:00"},{"dt":1780984800,"main":{"temp":17.83,"feels_like":17.37,"temp_min":17.83,"temp_max":17.83,"pressure":1015,"sea_level":1015,"grnd_level":1004,"humidity":65,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":85},"wind":{"speed":2.88,"deg":156,"gust":4.47},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-09 06:00:00"},{"dt":1780995600,"main":{"temp":23.74,"feels_like":23.4,"temp_min":23.74,"temp_max":23.74,"pressure":1014,"sea_level":1014,"grnd_level":1003,"humidity":47,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":73},"wind":{"speed":2.71,"deg":184,"gust":3.44},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-09 09:00:00"},{"dt":1781006400,"main":{"temp":27.06,"feels_like":26.86,"temp_min":27.06,"temp_max":27.06,"pressure":1013,"sea_level":1013,"grnd_level":1002,"humidity":39,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],"clouds":{"all":66},"wind":{"speed":2.79,"deg":212,"gust":3.53},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-09 12:00:00"},{"dt":1781017200,"main":{"temp":24.49,"feels_like":24.46,"temp_min":24.49,"temp_max":24.49,"pressure":1013,"sea_level":1013,"grnd_level":1002,"humidity":56,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"clouds":{"all":100},"wind":{"speed":5.44,"deg":273,"gust":5.61},"visibility":10000,"pop":0.3,"rain":{"3h":0.15},"sys":{"pod":"d"},"dt_txt":"2026-06-09 15:00:00"},{"dt":1781028000,"main":{"temp":21.02,"feels_like":21.01,"temp_min":21.02,"temp_max":21.02,"pressure":1014,"sea_level":1014,"grnd_level":1003,"humidity":70,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"clouds":{"all":97},"wind":{"speed":3.36,"deg":286,"gust":6.12},"visibility":10000,"pop":0.21,"rain":{"3h":0.12},"sys":{"pod":"d"},"dt_txt":"2026-06-09 18:00:00"},{"dt":1781038800,"main":{"temp":18.12,"feels_like":18.18,"temp_min":18.12,"temp_max":18.12,"pressure":1015,"sea_level":1015,"grnd_level":1004,"humidity":84,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10n"}],"clouds":{"all":100},"wind":{"speed":2.97,"deg":315,"gust":7.06},"visibility":10000,"pop":0.38,"rain":{"3h":0.23},"sys":{"pod":"n"},"dt_txt":"2026-06-09 21:00:00"},{"dt":1781049600,"main":{"temp":16.68,"feels_like":16.68,"temp_min":16.68,"temp_max":16.68,"pressure":1016,"sea_level":1016,"grnd_level":1005,"humidity":87,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10n"}],"clouds":{"all":100},"wind":{"speed":2.89,"deg":309,"gust":5.67},"visibility":10000,"pop":0.22,"rain":{"3h":0.15},"sys":{"pod":"n"},"dt_txt":"2026-06-10 00:00:00"},{"dt":1781060400,"main":{"temp":14.42,"feels_like":14.42,"temp_min":14.42,"temp_max":14.42,"pressure":1016,"sea_level":1016,"grnd_level":1005,"humidity":96,"temp_kf":0},"weather":[{"id":501,"main":"Rain","description":"moderate rain","icon":"10d"}],"clouds":{"all":100},"wind":{"speed":3.84,"deg":317,"gust":6.87},"visibility":10000,"pop":1,"rain":{"3h":3.95},"sys":{"pod":"d"},"dt_txt":"2026-06-10 03:00:00"},{"dt":1781071200,"main":{"temp":12.84,"feels_like":12.66,"temp_min":12.84,"temp_max":12.84,"pressure":1019,"sea_level":1019,"grnd_level":1007,"humidity":95,"temp_kf":0},"weather":[{"id":501,"main":"Rain","description":"moderate rain","icon":"10d"}],"clouds":{"all":100},"wind":{"speed":3.86,"deg":296,"gust":6.56},"visibility":8997,"pop":1,"rain":{"3h":3.83},"sys":{"pod":"d"},"dt_txt":"2026-06-10 06:00:00"},{"dt":1781082000,"main":{"temp":12.77,"feels_like":12.5,"temp_min":12.77,"temp_max":12.77,"pressure":1020,"sea_level":1020,"grnd_level":1008,"humidity":92,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"clouds":{"all":100},"wind":{"speed":2.54,"deg":311,"gust":3.97},"visibility":10000,"pop":1,"rain":{"3h":1.78},"sys":{"pod":"d"},"dt_txt":"2026-06-10 09:00:00"},{"dt":1781092800,"main":{"temp":14.79,"feels_like":14.44,"temp_min":14.79,"temp_max":14.79,"pressure":1020,"sea_level":1020,"grnd_level":1009,"humidity":81,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":100},"wind":{"speed":1.09,"deg":47,"gust":1.47},"visibility":10000,"pop":0.8,"sys":{"pod":"d"},"dt_txt":"2026-06-10 12:00:00"},{"dt":1781103600,"main":{"temp":17.42,"feels_like":16.89,"temp_min":17.42,"temp_max":17.42,"pressure":1019,"sea_level":1019,"grnd_level":1008,"humidity":64,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":100},"wind":{"speed":2.03,"deg":94,"gust":1.72},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-10 15:00:00"},{"dt":1781114400,"main":{"temp":16.36,"feels_like":15.88,"temp_min":16.36,"temp_max":16.36,"pressure":1019,"sea_level":1019,"grnd_level":1008,"humidity":70,"temp_kf":0},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],"clouds":{"all":100},"wind":{"speed":1.36,"deg":96,"gust":2.45},"visibility":10000,"pop":0,"sys":{"pod":"d"},"dt_txt":"2026-06-10 18:00:00"}],"city":{"id":756135,"name":"Warsaw","coord":{"lat":52.2297,"lon":21.0122},"country":"PL","population":1000000,"timezone":7200,"sunrise":1780625884,"sunset":1780685464}}
            """;

    @Test
    void ifCurrentLocationTrueWasPassedToGetWeatherItShouldReturnWeatherDataWithCurrentLocationTrue() throws IOException, InterruptedException {
        // given
        boolean isCurrentLocation = true;
        Location location = new Location("Warsaw", "", "");
        when(httpResponseCurrentWeather.statusCode()).thenReturn(200);
        when(httpResponseCurrentWeather.body()).thenReturn(currentWeatherHttpResponseBodyForTests);
        when(httpResponseWeatherForecast.statusCode()).thenReturn(200);
        when(httpResponseWeatherForecast.body()).thenReturn(weatherForecastHttpResponseBodyForTests);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).then(
                invocationOnMock -> {
                    HttpRequest request = invocationOnMock.getArgument(0);

                    if (request.uri().getPath().endsWith("/weather")) {
                        return httpResponseCurrentWeather;
                    } else if (request.uri().getPath().endsWith("/forecast")) {
                        return httpResponseWeatherForecast;
                    }
                    throw new IllegalArgumentException();
                }
        );

        // when
        WeatherData weatherData = openWeatherMapWeatherAPIClient.getWeather(location, isCurrentLocation);

        // then
        assertThat(weatherData.getIsCurrentLocation()).isEqualTo(isCurrentLocation);
    }

    @Test
    void ifCurrentLocationFalseWasPassedToGetWeatherItShouldReturnWeatherDataWithCurrentLocationFalse() throws IOException, InterruptedException {
        // given
        boolean isCurrentLocation = false;
        Location location = new Location("Warsaw", "", "");
        when(httpResponseCurrentWeather.statusCode()).thenReturn(200);
        when(httpResponseCurrentWeather.body()).thenReturn(currentWeatherHttpResponseBodyForTests);
        when(httpResponseWeatherForecast.statusCode()).thenReturn(200);
        when(httpResponseWeatherForecast.body()).thenReturn(weatherForecastHttpResponseBodyForTests);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).then(
                invocationOnMock -> {
                    HttpRequest request = invocationOnMock.getArgument(0);

                    if (request.uri().getPath().endsWith("/weather")) {
                        return httpResponseCurrentWeather;
                    } else if (request.uri().getPath().endsWith("/forecast")) {
                        return httpResponseWeatherForecast;
                    }
                    throw new IllegalArgumentException();
                }
        );

        // when
        WeatherData weatherData = openWeatherMapWeatherAPIClient.getWeather(location, isCurrentLocation);

        // then
        assertThat(weatherData.getIsCurrentLocation()).isEqualTo(isCurrentLocation);
    }

    @Test
    void ifNoLongitudeLatitudeWasPassedToGetWeatherItShouldReturnWeatherDataWithCompleteLocationObject() throws IOException, InterruptedException {
        // given
        boolean isCurrentLocation = false;
        Location location = new Location("Warsaw", "", "21.0093");
        when(httpResponseCurrentWeather.statusCode()).thenReturn(200);
        when(httpResponseCurrentWeather.body()).thenReturn(currentWeatherHttpResponseBodyForTests);
        when(httpResponseWeatherForecast.statusCode()).thenReturn(200);
        when(httpResponseWeatherForecast.body()).thenReturn(weatherForecastHttpResponseBodyForTests);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).then(
                invocationOnMock -> {
                    HttpRequest request = invocationOnMock.getArgument(0);

                    if (request.uri().getPath().endsWith("/weather")) {
                        return httpResponseCurrentWeather;
                    } else if (request.uri().getPath().endsWith("/forecast")) {
                        return httpResponseWeatherForecast;
                    }
                    throw new IllegalArgumentException();
                }
        );

        // when
        WeatherData weatherData = openWeatherMapWeatherAPIClient.getWeather(location, isCurrentLocation);

        // then
        assertThat(weatherData.getLocation()).extracting(
                Location::getLatitude,
                Location::getLongitude
        ).isNotEmpty();
    }

    @Test
    void ifCompleteLocationWasPassedToGetWeatherItShouldReturnWeatherDataWithTheSameLocationObject() throws IOException, InterruptedException {
        // given
        boolean isCurrentLocation = false;
        Location location = new Location("Warsaw", "21.0093", "52.2299");
        when(httpResponseCurrentWeather.statusCode()).thenReturn(200);
        when(httpResponseCurrentWeather.body()).thenReturn(currentWeatherHttpResponseBodyForTests);
        when(httpResponseWeatherForecast.statusCode()).thenReturn(200);
        when(httpResponseWeatherForecast.body()).thenReturn(weatherForecastHttpResponseBodyForTests);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).then(
                invocationOnMock -> {
                    HttpRequest request = invocationOnMock.getArgument(0);

                    if (request.uri().getPath().endsWith("/weather")) {
                        return httpResponseCurrentWeather;
                    } else if (request.uri().getPath().endsWith("/forecast")) {
                        return httpResponseWeatherForecast;
                    }
                    throw new IllegalArgumentException();
                }
        );

        // when
        WeatherData weatherData = openWeatherMapWeatherAPIClient.getWeather(location, isCurrentLocation);

        // then
        assertThat(weatherData.getLocation()).isEqualTo(location);
    }

    @Test
    void getWeatherShouldReturnProperWeatherDataObjectIfStatusCode2xx() throws IOException, InterruptedException {
        // given
        boolean isCurrentLocation = false;
        Location location = new Location("Warsaw", "", "");

        when(httpResponseCurrentWeather.statusCode()).thenReturn(200);
        when(httpResponseCurrentWeather.body()).thenReturn(currentWeatherHttpResponseBodyForTests);
        when(httpResponseWeatherForecast.statusCode()).thenReturn(200);
        when(httpResponseWeatherForecast.body()).thenReturn(weatherForecastHttpResponseBodyForTests);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).then(
                invocationOnMock -> {
                    HttpRequest request = invocationOnMock.getArgument(0);

                    if (request.uri().getPath().endsWith("/weather")) {
                        return httpResponseCurrentWeather;
                    } else if (request.uri().getPath().endsWith("/forecast")) {
                        return httpResponseWeatherForecast;
                    }
                    throw new IllegalArgumentException();
                }
        );
        when(openWeatherMapWeatherAPIClient.getCurrentDate()).thenReturn(LocalDate.of(2026,6, 5));

        CurrentWeather expectedCurrentWeatherResult = new CurrentWeather("10d",
                "Heavy intensity rain",
                "17.03",
                "16.93",
                "6.26",
                "98",
                "82",
                "1011");

        WeatherForecast expectedWeatherForecastResult = new WeatherForecast(
                new ArrayList<>(
                        List.of(
                                new WeatherForecastSingleDayEntry(LocalDate.parse("2026-06-06"), "01d", "21.75"),
                                new WeatherForecastSingleDayEntry(LocalDate.parse("2026-06-07"), "04d", "25.13"),
                                new WeatherForecastSingleDayEntry(LocalDate.parse("2026-06-08"), "03d", "19.66"),
                                new WeatherForecastSingleDayEntry(LocalDate.parse("2026-06-09"), "04d", "23.74"),
                                new WeatherForecastSingleDayEntry(LocalDate.parse("2026-06-10"), "10d", "12.77"))));

        // when
        WeatherData weatherData = openWeatherMapWeatherAPIClient.getWeather(location, isCurrentLocation);

        // then
        assertThat(weatherData.getCurrentWeather()).isEqualTo(expectedCurrentWeatherResult);
        assertThat(weatherData.getWeatherForecast()).isEqualTo(expectedWeatherForecastResult);
    }

    @Test
    void getWeatherShouldThrowIOExceptionIfStatusCodeNOT2xx() throws IOException, InterruptedException {
        // given
        boolean isCurrentLocation = false;
        Location location = new Location("Warsaw", "", "");

        when(httpResponseCurrentWeather.statusCode()).thenReturn(200);
        when(httpResponseCurrentWeather.body()).thenReturn(currentWeatherHttpResponseBodyForTests);
        when(httpResponseWeatherForecast.statusCode()).thenReturn(300);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).then(
                invocationOnMock -> {
                    HttpRequest request = invocationOnMock.getArgument(0);

                    if (request.uri().getPath().endsWith("/weather")) {
                        return httpResponseCurrentWeather;
                    } else if (request.uri().getPath().endsWith("/forecast")) {
                        return httpResponseWeatherForecast;
                    }
                    throw new IllegalArgumentException();
                }
        );

        // when
        // then
        assertThrows(IOException.class, () -> openWeatherMapWeatherAPIClient.getWeather(location, isCurrentLocation));
    }

    @Test
    void getWeatherShouldThrowIOExceptionIfResponseBodyNull() throws IOException, InterruptedException {
        // given
        boolean isCurrentLocation = false;
        Location location = new Location("Warsaw", "", "");

        when(httpResponseCurrentWeather.statusCode()).thenReturn(200);
        when(httpResponseCurrentWeather.body()).thenReturn(currentWeatherHttpResponseBodyForTests);
        when(httpResponseWeatherForecast.statusCode()).thenReturn(200);
        when(httpResponseWeatherForecast.body()).thenReturn(null);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).then(
                invocationOnMock -> {
                    HttpRequest request = invocationOnMock.getArgument(0);

                    if (request.uri().getPath().endsWith("/weather")) {
                        return httpResponseCurrentWeather;
                    } else if (request.uri().getPath().endsWith("/forecast")) {
                        return httpResponseWeatherForecast;
                    }
                    throw new IllegalArgumentException();
                }
        );

        // when
        // then
        assertThrows(IOException.class, () -> openWeatherMapWeatherAPIClient.getWeather(location, isCurrentLocation));
    }

}