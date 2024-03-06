package com.sopra.parkingsystem.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentComponent {
    private final Environment environment;

    @Autowired
    public EnvironmentComponent(Environment environment) {
        this.environment = environment;
    }

    public boolean isProd() {
        return environment.getActiveProfiles().length > 0 && environment.getActiveProfiles()[0].equals("prod");
    }

    public boolean isDev() {
        return environment.getActiveProfiles().length > 0 && environment.getActiveProfiles()[0].equals("dev");
    }

}
