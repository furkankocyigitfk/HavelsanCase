package com.example.center;

import com.example.center.dto.FunctionDto;
import com.example.center.dto.LocationWithAngleDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @Autowired
    private ObjectMapper objectMapper;
    private LocationWithAngleDto s1;
    private LocationWithAngleDto s2;

    @KafkaListener(topics = "center", groupId = "group-id")
    public void listen(String message) throws JsonProcessingException {
        if (s1 == null) {
            s1 = objectMapper.readValue(message, LocationWithAngleDto.class);
        } else {
            s2 = objectMapper.readValue(message, LocationWithAngleDto.class);
        }
        if (s1 != null && s2 != null) {
            System.out.println("s1: " + s1);
            System.out.println("s2: " + s2);
            FunctionDto sf1 = getCoefficients(s1);
            FunctionDto sf2 = getCoefficients(s2);

            Integer x = (int) Math.round((sf2.getC() - sf1.getC()) / (sf1.getM() - sf2.getM()));
            Integer y = (int) Math.round(sf1.getM() * x + sf1.getC());

            System.out.println("Target is found(" + x + ", " + y + ") by Center");
            s1 = null;
            s2 = null;
        }
    }

    private FunctionDto getCoefficients(LocationWithAngleDto locationWithAngleDto) {
        FunctionDto functionDto = new FunctionDto();
        functionDto.setM(Math.tan(Math.toRadians(locationWithAngleDto.getAngle())));
        functionDto.setC(locationWithAngleDto.getLocationDto().getY() - functionDto.getM() * locationWithAngleDto.getLocationDto().getX());

        return functionDto;
    }
}
