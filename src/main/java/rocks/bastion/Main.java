package rocks.bastion;

import rocks.bastion.sushi.SushiServer;

/**
 * Intended to start-up the {@link SushiServer} as a standalone server for use when demonstrating the API via an external
 * tool such as Postman.
 */
public class Main {

    public static void main(String... args) {
        SushiServer.start();
    }

}
