package com.example.sensor1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationWithAngleDto {
    private LocationDto locationDto;
    private Double angle;
}
