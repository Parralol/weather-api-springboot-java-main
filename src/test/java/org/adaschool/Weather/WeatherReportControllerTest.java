package org.adaschool.Weather;

import static org.mockito.Mockito.when;

import org.adaschool.Weather.controller.WeatherReportController;
import org.adaschool.Weather.data.WeatherReport;
import org.adaschool.Weather.service.WeatherReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherReportController.class)
class WeatherReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherReportService weatherReportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWeatherReport() throws Exception {
        // Mock the service to return a dummy WeatherReport
        WeatherReport mockWeatherReport = new WeatherReport();
        mockWeatherReport.setTemperature(72.5);
        mockWeatherReport.setHumidity(65.0);

        when(weatherReportService.getWeatherReport(37.8267, -122.4233))
                .thenReturn(mockWeatherReport);

        // Simulate the GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/weather-report")
                        .param("latitude", "37.8267")
                        .param("longitude", "-122.4233"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").value(72.5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.humidity").value(65.0));
    }

    @Test
    void testGetWeatherReportWithInvalidParams() throws Exception {
        // Simulate the request with missing parameters
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/weather-report"))
                .andExpect(status().isBadRequest());
    }
}
