package com.example.Eventbus;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class RunEventbus {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    EventBus eventBus = vertx.eventBus();

    eventBus.consumer("eventbus", r -> {
      System.out.println("This is 1 " + r.body());
    });
    eventBus.consumer("eventbus", r -> {
      System.out.println("This is 2 " + r.body());
    });

    eventBus.publish("eventbus", "Message");
  }
}
