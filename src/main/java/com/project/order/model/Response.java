package com.project.order.model;

public enum Response {
    UNASSIGNED("UNASSIGNED"),
    TAKEN("TAKEN"),
    SUCCESS("SUCCESS");

    private String value;

    Response(String value) {
        this.value = value;
    }
}
