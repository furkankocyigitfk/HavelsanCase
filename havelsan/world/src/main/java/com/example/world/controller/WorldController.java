package com.example.world.controller;

import com.example.world.service.WorldService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/world")
public class WorldController {
    @Autowired
    private WorldService worldService;

    @GetMapping("/simulate")
    public void simulate() throws JsonProcessingException {
        worldService.simulate();
    }
}
