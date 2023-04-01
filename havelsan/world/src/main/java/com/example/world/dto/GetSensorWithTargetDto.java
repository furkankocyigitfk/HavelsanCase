package com.example.world.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetSensorWithTargetDto {
    private LocationDto sensor;
    private LocationDto target;
}
