package net.yewton.asobiba.mcp.server

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.slf4j.LoggerFactory
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.requiredBody
import java.util.stream.Collectors

@Service
@Suppress("MagicNumber")
class WeatherService {
    private val restClient: RestClient = RestClient.builder()
        .baseUrl(BASE_URL)
        .defaultHeader("Accept", "application/geo+json")
        .build()

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Points(
        val properties: Props?
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Props(
            val forecast: String
        )
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Forecast(
        val properties: Props
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Props(
            val periods: List<Period>
        )

        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Period(
            val number: Int,
            val name: String,
            val startTime: String,
            val endTime: String,
            val isDayTime: Boolean,
            val temperature: Int,
            val temperatureUnit: String,
            val temperatureTrend: String,
            val probabilityOfPrecipitation: Map<*, *>,
            val windSpeed: String,
            val windDirection: String,
            val icon: String,
            val shortForecast: String,
            val detailedForecast: String
        )
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Alert(
        val features: List<Feature>
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Feature(
            val properties: Properties
        )

        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Properties(
            val event: String,
            val areaDesc: String,
            val severity: String,
            val description: String,
            val instruction: String?
        )
    }

    /**
     * Get forecast for a specific latitude/longitude
     * @param latitude Latitude
     * @param longitude Longitude
     * @return The forecast for the given location
     */
    @Tool(description = "Get weather forecast for a specific latitude/longitude")
    fun getWeatherForecastByLocation(latitude: Double, longitude: Double): String {
        LOGGER.atInfo().log("Getting weather forecast for location: {}, {}", latitude, longitude)
        val points: Points = restClient.get()
            .uri("/points/{latitude},{longitude}", latitude, longitude)
            .retrieve()
            .requiredBody()
        if (points.properties == null) {
            return "Unable to get forecast for location: $latitude, $longitude"
        }
        val forecast: Forecast =
            restClient.get().uri(points.properties.forecast).retrieve().requiredBody()

        val forecastText: String = forecast.properties.periods.stream().map { p ->
            java.lang.String.format(
                """
                %s:
                Temperature: %s %s
                Wind: %s %s
                Forecast: %s
                
                """.trimIndent(),
                p.name,
                p.temperature,
                p.temperatureUnit,
                p.windSpeed,
                p.windDirection,
                p.detailedForecast
            )
        }.collect(Collectors.joining())

        return forecastText
    }

    /**
     * Get alerts for a specific area
     * @param state Area code. Two-letter US state code (e.g. CA, NY)
     * @return Human-readable alert information
     */
    @Tool(description = "Get weather alerts for a US state. Input is Two-letter US state code (e.g. CA, NY)")
    fun getAlerts(@ToolParam(description = "Two-letter US state code (e.g. CA, NY") state: String): String {
        LOGGER.atInfo().log("Getting alerts for state: {}", state)

        val alert: Alert =
            restClient.get().uri("/alerts/active/area/{state}", state).retrieve().requiredBody()
        LOGGER.atInfo().log("Alert: {}", alert)

        return alert.features
            .stream()
            .map { f: Alert.Feature ->
                String.format(
                    """
                    Event: %s
                    Area: %s
                    Severity: %s
                    Description: %s
                    Instructions: %s
                    
                    """.trimIndent(),
                    f.properties.event,
                    f.properties.areaDesc,
                    f.properties.severity,
                    f.properties.description,
                    f.properties.instruction
                )
            }
            .collect(Collectors.joining("\n"))
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(WeatherService::class.java)
        private const val BASE_URL = "https://api.weather.gov"

        @JvmStatic
        fun main(args: Array<String>) {
            val client = WeatherService()
            println(client.getWeatherForecastByLocation(35.68345, 139.75688))
            println(client.getAlerts("AZ"))
        }
    }
}
