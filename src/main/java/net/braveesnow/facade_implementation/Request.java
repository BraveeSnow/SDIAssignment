package net.braveesnow.facade_implementation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.Map.Entry;

public class Request {
    private static final int DEFAULT_HTTP_PORT = 80;

    private String url;
    private int port;

    /**
     * Default constructor for {@link http://127.0.0.1:80/}.
     */
    public Request() {
        this.url = "127.0.0.1";
        this.port = DEFAULT_HTTP_PORT;
    }

    /**
     * Creates an instance using the specified URL with port 80.
     * @param hostUrl The URL to make the request to.
     */
    public Request(String hostUrl) {
        this.url = hostUrl;
        this.port = DEFAULT_HTTP_PORT;
    }

    /**
     * Creates an instance using the specified URL and port.
     * @param hostUrl The URL to make the request to.
     * @param servicePort The port to use.
     */
    public Request(String hostUrl, int servicePort) {
        this.url = hostUrl;
        this.port = servicePort;
    }

    /**
     * Sets the URL to use for the request.
     * @param hostUrl The URL to make the request to.
     */
    public void setUrl(String hostUrl) {
        this.url = hostUrl;
    }

    /**
     * Sets the port for the request.
     * @param servicePort The port to use.
     */
    public void setPort(int servicePort) {
        this.port = servicePort;
    }

    /**
     * Attempt to make the request to the specified host.
     * @param type The type of request to make.
     * @return The unformatted response from the host.
     * @throws IOException An I/O error occurred while making the requst.
     */
    public String request(RequestType type) throws IOException {
        return this.request(type, null);
    }

    /**
     * Attempt to make the request to the specified host given a map of header fields to use.
     * @param type The type of request to make.
     * @param headerFields A map of HTTP headers.
     * @return The unformatted response from the host.
     * @throws IOException An I/O error occurred while making the requst.
     */
    public String request(RequestType type, Map<String, String> headerFields) throws IOException {
        HeaderBuilder header = new HeaderBuilder(this.url);
        String resStr = new String();
        Socket sock;
        String headerStr;
        OutputStream out;
        InputStream in;
        
        if (headerFields != null) {
            for (Entry<String, String> entry : headerFields.entrySet()) {
                header.addField(entry.getKey(), entry.getValue());
            }
        }

        sock = new Socket(InetAddress.getByName(this.url), this.port);
        headerStr = header.build(type);

        // Write HTTP header to host
        out = sock.getOutputStream();
        out.write(headerStr.getBytes());
        out.flush();
        
        // Receive content from host
        in = sock.getInputStream();
        resStr = new String(in.readAllBytes());

        // Close streams
        out.close();
        in.close();
        sock.close();

        return resStr;
    }
}
