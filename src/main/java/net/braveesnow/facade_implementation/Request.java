package net.braveesnow.facade_implementation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Request {
    /**
     * Attempt to make the request to the specified host and port.
     * @param type The type of request to make.
     * @param hostUrl The URL to make the request to.
     * @param servicePort The port to make the request to.
     * @return The unformatted response from the host.
     * @throws IOException An I/O error occurred while making the requst.
     */
    public String request(RequestType type, String hostUrl, int servicePort) throws IOException {
        return this.request(type, hostUrl, servicePort, new HashMap<>());
    }

    /**
     * Attempt to make the request to the specified host and port given a map of header fields to use.
     * @param type The type of request to make.
     * @param hostUrl The URL to make the request to.
     * @param servicePort The port to make the request to.
     * @param headerFields A map of HTTP headers.
     * @return The unformatted response from the host.
     * @throws IOException An I/O error occurred while making the requst.
     */
    public String request(RequestType type, String hostUrl, int servicePort, Map<String, String> headerFields) throws IOException {
        HeaderBuilder header = new HeaderBuilder(hostUrl);
        String resStr = new String();
        Socket sock;
        String headerStr;
        OutputStream out;
        InputStream in;
        
        if (headerFields.size() > 0) {
            for (Entry<String, String> entry : headerFields.entrySet()) {
                header.addField(entry.getKey(), entry.getValue());
            }
        }

        sock = new Socket(InetAddress.getByName(hostUrl), servicePort);
        headerStr = header.build(type);

        // Write HTTP header to host
        out = sock.getOutputStream();
        out.write(headerStr.getBytes());
        out.flush();
        
        // Receive content from host
        in = sock.getInputStream();
        resStr = new String(in.readAllBytes());

        out.close();
        in.close();
        sock.close();

        return resStr;
    }
}
