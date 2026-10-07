package com.courier.tracking.hub.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hubs")
public class HubFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String hubCode;

    @Column(nullable = false)
    private String hubName;

    @Column(nullable = false)
    private String city;

    public HubFacility() {}

    public HubFacility(String hubCode, String hubName, String city) {
        this.hubCode = hubCode;
        this.hubName = hubName;
        this.city = city;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getHubCode() { return hubCode; }
    public void setHubCode(String hubCode) { this.hubCode = hubCode; }

    public String getHubName() { return hubName; }
    public void setHubName(String hubName) { this.hubName = hubName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}
