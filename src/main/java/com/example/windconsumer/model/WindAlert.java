package com.example.windconsumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WindAlert {
    private String alertType;
    private String riskLevel;
    private Aircraft aircraft;
    private Weather weather;
    private String alertTime;
    private Integer messageSequence;
    private Long timestamp;

    @Data
    public static class Aircraft {
        private Integer altitude;
        private String beaconCode;
        private Integer verticalVelocity;
        private String aircraftType;
        private String departureAirport;
        private Integer assignedAltitude;
        private Double latitude;
        private String flightRules;
        private String callSign;
        private Double longitude;
        private String destinationAirport;
        private String status;
    }

    @Data
    public static class Weather {
        private String nearestStationCode;
        private String windSpeedUnit;
        private Integer alertThreshold;
        
        @JsonProperty("distanceToStation(mile)")
        private Double distanceToStationMile;
        
        private String nearestStationName;
        private Integer windSpeed;
    }
}
