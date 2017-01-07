package rocks.bastion.sushi;

import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import rocks.bastion.Bastion;
import rocks.bastion.core.GeneralRequest;
import rocks.bastion.core.json.JsonRequest;
import rocks.bastion.junit.BastionRunner;

public class SushiServerTest {

    @BeforeClass
    public static void setup() {
        SushiServer.start();
    }

    @AfterClass
    public static void teardown() {
        SushiServer.stop();
    }

    @Test
    public void createSushi_sushiRetrievedWithGet() {
        JsonRequest request = JsonRequest.postFromString("http://localhost:8080/sushi", "{\"name\":\"Salmon Nigiri\", \"price\":1.50}");
        Bastion.request(request)
                .thenDo((statusCode, response, model) -> {
                    Assertions.assertThat(model).isNotNull();
                    Assertions.assertThat(statusCode).isEqualTo(201);
                }).call();
    }
}