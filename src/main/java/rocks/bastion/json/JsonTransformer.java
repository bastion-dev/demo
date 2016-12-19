package rocks.bastion.json;

import com.google.gson.Gson;
import spark.ResponseTransformer;
import spark.Spark;

/**
 * {@link ResponseTransformer} for converting {@link Spark} responses to JSON.
 */
public class JsonTransformer implements ResponseTransformer {

    private final Gson gson;

    public JsonTransformer() {
        gson = new Gson();
    }

    @Override
    public String render(final Object model) {
        return gson.toJson(model);
    }

    public <T> T fromJson(final String json, final Class<T> type) {
        return gson.fromJson(json, type);
    }
}