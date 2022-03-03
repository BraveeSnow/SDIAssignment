package net.braveesnow.facade_implementation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HeaderBuilder {
    private static final String HEADER_FORMAT = "%s %s HTTP/1.1\r\n";

    private String host;
    private String path;
    private List<String> fields = new ArrayList<>();

    /**
     * Template HTTP header for {@link http://127.0.0.1:80}.
     */
    public HeaderBuilder() {
        this.path = "/";
        this.host = "127.0.0.1";
    }

    /**
     * Creates an HTTP header for the given host.
     * @param host The URL to make the request to.
     * @throws MalformedURLException if the protocol in the given URL is invalid or unknown.
     */
    public HeaderBuilder(String hostUrl) throws MalformedURLException {
        if (!hostUrl.startsWith("http://")) {
            hostUrl = "http://" + hostUrl;
        }
        this.path = resolveHostPath(hostUrl);
        this.host = resolveHostName(hostUrl);

    }

    // Static methods

    /**
     * Resolves the path described in the given URL.
     * @param hostUrl The URL to make the request to.
     * @return The path in the URL.
     * @throws MalformedURLException if the protocol in the given URL is invalid or unknown.
     */
    public static String resolveHostPath(String hostUrl) throws MalformedURLException {
        String urlPath = new URL(hostUrl).getPath();
        return urlPath == "" ? "/" : urlPath;
    }

    /**
     * Resolves the name of the host described in the given URL.
     * @param hostUrl The URL to make the request to.
     * @return The name of the host.
     * @throws MalformedURLException if the protocol in the given URL is invalid or unknown.
     */
    public static String resolveHostName(String hostUrl) throws MalformedURLException {
        return new URL(hostUrl).getHost();
    }

    // Getters and setters

    /**
     * Gets the host from the HTTP request.
     * @return The name of the host.
     */
    public String getUrl() {
        return this.host + this.path;
    }

    /**
     * Sets the host for the HTTP request.
     * @param host The URL to make the request to.
     * @throws MalformedURLException if the protocol in the given URL is invalid or unknown.
     */
    public void setUrl(String hostUrl) throws MalformedURLException {
        this.host = resolveHostName(hostUrl);
        this.path = resolveHostPath(hostUrl);
    }

    /**
     * Adds a field to the header.
     * @param name The name of the header.
     * @param param The value of the header.
     */
    public void addField(String name, String param) {
        this.fields.add(String.format("%s: %s", name, param));
    }

    /**
     * Clears all headers present in the header.
     */
    public void clearFields() {
        this.fields.clear();
    }

    // Builder

    /**
     * Writes the new HTTP header into a string.
     * @param type The type of HTTP request to make.
     * @return The HTTP header as a string.
     */
    public String build(RequestType type) {
        StringBuilder b = new StringBuilder();
        b.append(String.format(HEADER_FORMAT, type.getRequestName(), this.path));
        b.append(String.format("Host: %s\r\n", this.host));
        for (String s : this.fields) {
            b.append(s + "\r\n");
        }

        b.append("\r\n");
        return b.toString();
    }
}
