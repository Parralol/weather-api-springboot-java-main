package org.adaschool.Weather;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherReport;
import org.adaschool.Weather.service.WeatherReportService;

@SpringBootTest
public class WeatherReportServiceTest {

    @InjectMocks
    private WeatherReportService weatherReportService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeatherReport() {
        // Mock the API response
        WeatherApiResponse mockApiResponse = new WeatherApiResponse();
        WeatherApiResponse.Main main = new WeatherApiResponse.Main();
        main.setTemperature(25.0);
        main.setHumidity(60);
        mockApiResponse.setMain(main);

        // Mock RestTemplate's call to the external API with both the URL and response type
        when(restTemplate.getForObject(anyString(), any(Class.class)))
                .thenReturn(mockApiResponse);

        // Call the method to test
        WeatherReport weatherReport = weatherReportService.getWeatherReport(37.8267, -122.4233);

        // Verify the results
        assertEquals(true, weatherReport.getTemperature() >= 0);
        assertEquals(true, weatherReport.getHumidity() >= 0);
    }
}