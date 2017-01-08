# Bastion Demo Project

This project consists of an API that can be used to write Bastion tests for during a demonstration. The API consists of a single `Sushi` resource which we can perform `POST` and `GET` requests on.

## Sushi API

 The API expects all requests be in `json`, therefore a `Content-Type` header with `application/json` should be specified. If this header is not specified the API will return a `415` response.

Sample `Sushi` entity.
```json
{
    "id" : 123,
    "name" : "Salmon Nigiri",
    "price" : 1.50
}
```
 
 `POST /sushi`
 
 Creates a new Sushi entity and returns a sushi with a newly generated `id`. If the Sushi being created shares its `name` with a previously requested Sushi, the API will return a `409` conflict response.
 
 `GET /sushi`
 
 Returns all the previosuly created sushi.
 
 `GET /sushi/{id}`
 
 Returns the details of the sushi identified by the `id` path parameter. If a sushi is not identified, a `404` response is returned instead.