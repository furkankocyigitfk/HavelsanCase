package com.example.sensor2;

import com.example.sensor2.dto.GetSensorWithTargetDto;
import com.example.sensor2.dto.LocationDto;
import com.example.sensor2.dto.LocationWithAngleDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "sensor2", groupId = "group-id")
    public void listen(String message) throws JsonProcessingException {
        GetSensorWithTargetDto getSensorWithTargetDto = objectMapper.readValue(message, GetSensorWithTargetDto.class);

        LocationWithAngleDto locationWithAngleDto = new LocationWithAngleDto();
        locationWithAngleDto.setLocationDto(getSensorWithTargetDto.getSensor());
        locationWithAngleDto.setAngle(getAngle(getSensorWithTargetDto.getSensor(), getSensorWithTargetDto.getTarget()));

        kafkaTemplate.send("center", objectMapper.writeValueAsString(locationWithAngleDto));
    }

    private Double getAngle(LocationDto l1, LocationDto l2) {
        return Math.toDegrees(Math.atan((double) (l2.getY() - l1.getY()) / (double) (l2.getX() - l1.getX())));
    }
}
