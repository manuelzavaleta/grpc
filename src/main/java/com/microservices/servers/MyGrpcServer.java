package com.microservices.servers;

import com.microservices.services.GreetingService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class MyGrpcServer {
  public static void main(String[] args) throws InterruptedException, IOException {
    Server server = ServerBuilder.forPort(9080)
        .addService(new GreetingService())
        .build();
    server.start();
    server.awaitTermination();
    System.out.println("Server started at port 9080");
  }
}
