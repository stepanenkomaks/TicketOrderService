# TicketOrderService 
## Stepanenko Maks 
---
### Stack of technologies :smiley:

- `Spring Boot` 
- `RabbitMQ`
- `Postgres and Flyway`
- `TestContainers`
- `Micrometer and Zipkin`
- `Prometheus and Grafana`

You will like those project!

---
### General info :man_student:
This is a pet project based on the simulation of the bus ticket system of booking a ticket on the route.

It includes 7 microservices:
1. api-gateway (port for all services is 8080)
2. config-service (stores all configuration of ticket-, order- and status-service)
3. discovery-service 
4. notification-service (gets order info from order-service via RabbitMQ and show it, using logs)
5. ticket-service (manage routes, tickets and make an order on a route; communicates with order-service via http)
6. order-service (makes an order; communicates with status-service via http)
7. status-service (gives one of three statuses (NEW/DONE/FAILED) to given order)

All the services can be started using images from docker-compose file.

---
### Additional info about order-service :wink:
Order service has a `status handler` which acts this way:
- If the `status`,recieved from `status-service`, is `NEW` status handler starts
- Every second it makes a request to `status-service` to update `status`
- If `status` value is `DONE`/`FINISHED` `status handler` stops and return the `status`
- If during 1 minute `status` was not updated (still `NEW`) `status handler` returns `FAILED`

---
### How to start service locally :construction_worker:

No additional configurations are needed to start any service. All configurations are in application.yaml (or application-docker.yml, when using docker).

#### Docker images

In order to start the project you will need to run the docker containers. 

[image download site](https://hub.docker.com) - Docker hub

For a guide for usage with Docker, [checkout the docs](https://github.com/maildev/maildev/blob/master/docs/docker.md).

All properties for all required images are in docker-compose.yml file. So, to run the project you just need to select the directory where this file exist and then run the following command:

Run project
````
docker compose up
````
Enjoy the application :sunglasses:

---
### Usage :star:
The user of this project can perform next actions:

In ticket service:
- Create new `route`
- Get all `routes`
- Get `route` info by providing route id
- Create new `ticket` for existing route `route`
- Book `ticket` for existing `route`
- Get `booked ticket` info

In order service:
- Create `order`

In status service:
- Get `status` for provided order id

#### To test the microservices (2 ways):
- [Open the Swagger](http://localhost:8083/swagger-ui/index.html) - change port to see swagger documentation for different services 
- Use Postman for custom queries

#### To trace the microservices:
- [Zipkin](http://localhost:9411)

#### To monitor the microservices (2 ways):
- [Prometheus](http://localhost:9090) - see the info on 4 services (notification-, status-, order- and ticket-service)
- [Grafana](http://localhost:3000) - visualize data from Prometheus

### IMPOTRANT :fire: :fire:
- bootstrap.yml - those files call a separate service (`config-service`)
 so that all your configurations and settings are pulled from a separate service and are secured  
- To create grafana dashboard use file Grafana_Dashboard.json

---
### THANKS :heart:
---
