package com.project.order.util;

import com.project.order.exception.InvalidDataException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class OrderUtil {

    private static final int LATITUDE_MIN = -90;
    private static final int LATITUDE_MAX = 90;
    private static final int LONGITUDE_MIN = -180;
    private static final int LONGITUDE_MAX = 180;
    public static final String DIRECTIONS_API = "https://maps.googleapis.com/maps/api/directions/json?";
    public static final String API_KEY = "";

    public static final String RESPONSE_STATUS = "/status";
    public static final String ROUTES = "/routes";
    public static final String LEGS = "/legs";
    public static final String DISTANCE_VALUE = "/distance/value";

    public static boolean validate(String value, int index) throws InvalidDataException {
        switch (index) {
            case 0:
                double latitude = Double.parseDouble(value);
                return latitude >= LATITUDE_MIN && latitude <= LATITUDE_MAX;
            case 1:
                double longitude = Double.parseDouble(value);
                return longitude >= LONGITUDE_MIN && longitude <= LONGITUDE_MAX;
            default:
                return true;
        }
    }
}
