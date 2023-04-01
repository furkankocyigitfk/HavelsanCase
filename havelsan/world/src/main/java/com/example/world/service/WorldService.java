package com.example.world.service;

import com.example.world.dto.GetSensorWithTargetDto;
import com.example.world.dto.LocationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WorldService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void simulate() throws JsonProcessingException {
//        LocationDto target = getLocationRandomlyLocated();
//        LocationDto sensor1 = getLocationRandomlyLocated();
//        LocationDto sensor2 = getLocationRandomlyLocated();

        LocationDto target = new LocationDto(-1, 5);
        LocationDto sensor1 = new LocationDto(-5, 1);
        LocationDto sensor2 = new LocationDto(5, -1);

        System.out.println("Target : " + target);
        System.out.println("Sensor1 : " + sensor1);
        System.out.println("Sensor2 : " + sensor2);

        GetSensorWithTargetDto responseSensor1 = new GetSensorWithTargetDto();
        responseSensor1.setSensor(sensor1);
        responseSensor1.setTarget(target);

        GetSensorWithTargetDto responseSensor2 = new GetSensorWithTargetDto();
        responseSensor2.setSensor(sensor2);
        responseSensor2.setTarget(target);

        kafkaTemplate.send("sensor1", objectMapper.writeValueAsString(responseSensor1));
        kafkaTemplate.send("sensor2", objectMapper.writeValueAsString(responseSensor2));
    }

    private LocationDto getLocationRandomlyLocated() {
        LocationDto location = new LocationDto();
        location.setX(getRandomNumber(-500, 500));
        location.setY(getRandomNumber(-500, 500));

        return location;
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
