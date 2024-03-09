package com.sopra.parkingsystem.controller;

import com.sopra.parkingsystem.service.InspectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inspector")
public class InspectorController {

    private final InspectorService inspectorService;

    @Autowired
    public InspectorController(InspectorService inspectorService) {
        this.inspectorService = inspectorService;
    }


    @GetMapping("/validate/car/{registration}")
    public ResponseEntity<String> inspectCar(@PathVariable String registration) {
        return ResponseEntity.ok(inspectorService.isCarRegistered(registration).toString());
    }
}
