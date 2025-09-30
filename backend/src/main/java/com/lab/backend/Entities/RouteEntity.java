package com.lab.backend.Entities;

import java.util.Date;

public class RouteEntity {
    private Long id;
    private Long id_driver;
    private Date date_hour;
    private String route_status;
    private Long id_central;
    private Long id_pick_up_point;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_driver() {
        return id_driver;
    }

    public void setId_driver(Long id_driver) {
        this.id_driver = id_driver;
    }

    public Date getDate_hour() {
        return date_hour;
    }

    public void setDate_hour(Date date_hour) {
        this.date_hour = date_hour;
    }

    public String getRoute_status() {
        return route_status;
    }

    public void setRoute_status(String route_status) {
        this.route_status = route_status;
    }

    public Long getId_central() {
        return id_central;
    }

    public void setId_central(Long id_central) {
        this.id_central = id_central;
    }

    public Long getId_pick_up_point() {
        return id_pick_up_point;
    }

    public void setId_pick_up_point(Long id_pick_up_point) {
        this.id_pick_up_point = id_pick_up_point;
    }
}
