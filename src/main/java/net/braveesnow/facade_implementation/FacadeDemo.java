package net.braveesnow.facade_implementation;

import java.io.IOException;

public class FacadeDemo {
    public static void main(String argsp[]) throws IOException {
        Request req = new Request();

        try {
            for (RequestType type : RequestType.values()) {
                System.out.printf("Made %s request:\n%s\n\n", type.getRequestName(), req.request(type));
            }
        } catch (Exception e) {
            System.err.println("Encountered error: " + e.getLocalizedMessage());
        }
    }
}
