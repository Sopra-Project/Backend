package com.sopra.parkingsystem.controller;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.sopra.parkingsystem.service.InspectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/inspector")
public class InspectorController {

    private final InspectorService inspectorService;

    @Autowired
    public InspectorController(InspectorService inspectorService) {
        this.inspectorService = inspectorService;
    }


    @GetMapping("/validate/car/{registration}")
    public JsonObject inspectCar(@PathVariable String registration) {
        return inspectorService.isCarRegistered(registration);
    }
}
