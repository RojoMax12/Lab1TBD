package com.lab.backend.Entities;

public class Route_ContainerEntity {
    Long route_container_id;
    Long container_id;
    Long route_id;

    public Long getRoute_container_id() {
        return route_container_id;
    }

    public void setRoute_container_id(Long route_container_id) {
        this.route_container_id = route_container_id;
    }

    public Long getContainer_id() {
        return container_id;
    }

    public void setContainer_id(Long container_id) {
        this.container_id = container_id;
    }

    public Long getRoute_id() {
        return route_id;
    }

    public void setRoute_id(Long route_id) {
        this.route_id = route_id;
    }
}
