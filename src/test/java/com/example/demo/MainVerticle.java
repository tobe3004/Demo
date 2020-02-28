package com.example.demo;

import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import java.util.function.Supplier;

public class MainVerticle implements Verticle, Supplier<Verticle> {
  @Override
  public Vertx getVertx() {
    return null;
  }

  @Override
  public void init(Vertx vertx, Context context) {

  }

  @Override
  public void start(Future<Void> startFuture) throws Exception {

  }

  @Override
  public void stop(Future<Void> stopFuture) throws Exception {

  }

  @Override
  public Verticle get() {
    return null;
  }
}
