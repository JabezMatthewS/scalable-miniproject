package com.courier.tracking.hub.config;

import com.courier.tracking.hub.entity.HubFacility;
import com.courier.tracking.hub.repository.HubFacilityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final HubFacilityRepository repository;

    public DataInitializer(HubFacilityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new HubFacility("HUB-NORTH-01", "Chicago Logistics Hub", "Chicago"));
            repository.save(new HubFacility("HUB-SOUTH-02", "Atlanta Regional Terminal", "Atlanta"));
            repository.save(new HubFacility("HUB-WEST-03", "Los Angeles Sorting Center", "Los Angeles"));
        }
    }
}
