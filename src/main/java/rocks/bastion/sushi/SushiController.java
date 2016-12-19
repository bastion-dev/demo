package rocks.bastion.sushi;

import rocks.bastion.error.Error;
import rocks.bastion.error.NotFoundException;
import rocks.bastion.json.JsonTransformer;
import spark.Request;
import spark.Response;

import static java.lang.String.*;
import static spark.Spark.*;

class SushiController {

    private static final JsonTransformer jsonTransformer = new JsonTransformer();

    private static final SushiService sushiService = new SushiService();

    public static void main(final String... args) {
        port(8080);

        initializeFilters();
        initializeExceptionHandlers();

        get("/sushi", (request, response) -> sushiService.lookup(), jsonTransformer);
        get("/sushi/:id", (request, response) -> {
            final Integer id = Integer.valueOf(request.params("id"));
            return sushiService.findById(id).orElseThrow(() -> new NotFoundException(format("Sushi with id [%d] could not be found.", id)));
        }, jsonTransformer);

        post("/sushi", (request, response) -> {
            final Sushi sushi = jsonTransformer.fromJson(request.body(), Sushi.class);
            return sushiService.create(sushi.getName(), sushi.getPrice().doubleValue());
        }, jsonTransformer);

        awaitInitialization();
    }

    private static void initializeFilters() {
        before((request, response) -> {
            setResponseContentType(response);
            validateRequestContentType(request);
        });
    }

    private static void initializeExceptionHandlers() {
        exception(SushiException.class, (exception, request, response) -> handleConflictException(exception, response));
        exception(NotFoundException.class, (exception, request, response) -> handleNotFoundException(exception, response));
        exception(Exception.class, (exception, request, response) -> handleServerError(exception, response));
    }

    private static void setResponseContentType(final Response response) {
        response.header("Content-Type", "application/json");
    }

    private static void validateRequestContentType(final Request request) {
        if (!"application/json".equals(request.headers("Content-Type"))) {
            halt(415, jsonTransformer.render(new Error("Only 'application/json' is supported.")));
        }
    }

    private static void handleServerError(Exception exception, Response response) {
        response.status(500);
        response.body(jsonTransformer.render(new Error(format("An unknown error [%s] has occurred with message [%s].", exception.getClass().getSimpleName(), exception.getMessage()))));
    }

    private static void handleNotFoundException(Exception exception, Response response) {
        response.status(404);
        response.body(jsonTransformer.render(new Error(exception.getMessage())));
    }

    private static void handleConflictException(Exception exception, Response response) {
        response.status(409);
        response.body(jsonTransformer.render(new Error(exception.getMessage())));
    }
}
