package net.braveesnow.facade_implementation;

public enum RequestType {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String requestName;

    /**
     * Allows the request type enums to represent a string.
     * @param name The name of the request.
     */
    private RequestType(String name) {
        this.requestName = name;
    }

    /**
     * Converts the {@code RequestType} enum into a string.
     * @return The HTTP request as a string.
     */
    public String getRequestName() {
        return this.requestName;
    }
}