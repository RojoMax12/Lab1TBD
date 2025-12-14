package com.lab.backend.Entities;

import java.time.LocalTime;
import java.util.Date;
import java.sql.Time;

public class RouteEntity {
    private Long id;
    private Long id_driver;
    private Date date_;
    private LocalTime start_time;
    private LocalTime end_time;
    private String route_status;
    private Long id_central;
    private Long id_central_finish;

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

    public Date getDate_() {
        return date_;
    }

    public void setDate_(Date date_) {
        this.date_ = date_;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    // Setter compatible with different DB/JDBC driver return types.
    // sql2o may pass a String, java.sql.Time, or java.time.LocalTime.
    public void setStart_time(String start_time) {
        if (start_time == null || start_time.trim().isEmpty()) {
            this.start_time = null;
        } else {
            this.start_time = LocalTime.parse(start_time);
        }
    }

    public void setStart_time(Time start_time) {
        if (start_time == null) {
            this.start_time = null;
        } else {
            this.start_time = start_time.toLocalTime();
        }
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public LocalTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        if (end_time == null || end_time.trim().isEmpty()) {
            this.end_time = null;
        } else {
            this.end_time = LocalTime.parse(end_time);
        }
    }

    public void setEnd_time(Time end_time) {
        if (end_time == null) {
            this.end_time = null;
        } else {
            this.end_time = end_time.toLocalTime();
        }
    }

    public void setEnd_time(LocalTime end_time) {
        this.end_time = end_time;
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

    public Long getId_central_finish() {
        return id_central_finish;
    }

    public void setId_central_finish(Long id_central_finish) {
        this.id_central_finish = id_central_finish;
    }
}
