package rocks.bastion.sushi;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import rocks.bastion.Bastion;
import rocks.bastion.core.json.JsonRequest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void createSushi_201forFirstCreate_409forSecondCreate() {
        final String requestBody = "{" +
                "\"name\":\"Tuna Sashimi\", " +
                "\"price\":2.50" +
                "}";
        JsonRequest request = JsonRequest.postFromString("http://localhost:8080/sushi", requestBody);

        Bastion.request(request)
                .withAssertions((statusCode, response, model) -> assertThat(statusCode).isEqualTo(201)).call();

        Bastion.request(request)
                .withAssertions((statusCode, response, model) -> assertThat(statusCode).isEqualTo(409)).call();
    }

    @Test
    public void createSushi_bindModel() {
        final String requestBody = "{" +
                "\"name\":\"Salmon Nigiri\", " +
                "\"price\":1.50" +
                "}";
        JsonRequest request = JsonRequest.postFromString("http://localhost:8080/sushi", requestBody);

        Bastion.request(request)
                .bind(Sushi.class)
                .withAssertions((statusCode, response, sushi) -> {
                    assertThat(statusCode).isEqualTo(201);
                    assertThat(sushi).isNotNull();
                    assertThat(sushi.getName()).isEqualTo("Salmon Nigiri");
                    assertThat(sushi.getPrice()).isEqualTo(new BigDecimal(1.50));
                }).call();
    }
}