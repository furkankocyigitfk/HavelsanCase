package com.example.center.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LocationWithAngleDto {
    private LocationDto locationDto;
    private Double angle;
}
