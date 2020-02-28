package com.example.Rest;

import com.example.Logger.Log;
import com.example.Model.Person;
import com.example.demo.Tables;
import com.example.demo.tables.Library;
import com.google.gson.Gson;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.json.JSONArray;
import org.jooq.tools.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;

public class RestCRUD extends AbstractVerticle {

  @Override
  public void start() throws IOException {

    //Create the router
    Router router = Router.router(Vertx.vertx());

    //Allow to get the body of HTTP request
    router.route().handler(BodyHandler.create());

    //Create endpoints
    router.get("/list").handler(this::getLists);
    router.get("/list/:id").handler(this::findValues);
    router.post("/list").handler(this::createValues);
    router.put("/list/:id").handler(this::updateValues);
    router.delete("/list/:id").handler(this::deleteValues);

    //Create the server
    Vertx.vertx().createHttpServer().requestHandler(router::accept).listen(9191);
  }

  //Connect DB
  public static Connection getConnection() {
    String user = "postgres";
    String password = "12345";
    String url = "jdbc:postgresql://127.0.0.1:5432/jooq";
    try {
      Connection connection = DriverManager.getConnection(url, user, password);
      return connection;
    } catch (Exception e) {
      throw new RuntimeException("Error connecting to the database", e);
    }
  }

  //Get list values
  private void getLists(RoutingContext context) {
    try {
      Connection connection = RestCRUD.getConnection();
      DSLContext create = DSL.using(connection, SQLDialect.POSTGRES);
      Result<Record> result = create.select().from(Tables.LIBRARY).fetch();

      JSONObject responseValuesJson = new JSONObject();
      JSONArray list = new JSONArray();

      for (Record r : result) {
        int id = r.get(Library.LIBRARY.ID);
        String name = r.get(Library.LIBRARY.NAME);
        JSONObject formValuesJson = new JSONObject();
        formValuesJson.put("id", id);
        formValuesJson.put("name", name);
        list.add(formValuesJson);
      }
      responseValuesJson.put("List", list);

      context.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(responseValuesJson));
    } catch (Exception e) {
      context.response()
        .setStatusCode(400)
        .putHeader("content-type", "application/json ; charset=utf-8")
        .end(Json.encode("Error: " + e.getMessage()));
    }
  }

  //Get 1 value in list
  private void findValues(RoutingContext context) {
    try {
      Connection connection = RestCRUD.getConnection();
      DSLContext create = DSL.using(connection, SQLDialect.POSTGRES);
      String idListRequest = context.request().getParam("id");
      Result<Record> result = create.select().from(Tables.LIBRARY).where(Tables.LIBRARY.ID.eq(Integer.valueOf(idListRequest))).fetch();

      JsonObject formValuesJson = new JsonObject();
      for (Record r : result) {
        int idListDB = r.get(Library.LIBRARY.ID);
        String name = r.get(Library.LIBRARY.NAME);
        formValuesJson.put("ID", idListDB);
        formValuesJson.put("Name", name);
      }
      int code = 400;
      String ErrorMessage = "Person is not found!";

      context.response()
        .setStatusCode(code)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encode(formValuesJson.size() > 0 ? formValuesJson : new JsonObject().put("Error: ", ErrorMessage)));
    } catch (Exception e) {
      context.response()
        .setStatusCode(400)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encode("Error: " + e.getMessage()));
    }
  }

  //Create new value
  private void createValues(RoutingContext context) {
    try {
      Connection connection = RestCRUD.getConnection();
      DSLContext create = DSL.using(connection, SQLDialect.POSTGRES);

      String val = context.getBodyAsString();
      Person personDTO = new Gson().fromJson(val, Person.class);
      int result = create.insertInto(Tables.LIBRARY, Tables.LIBRARY.ID, Tables.LIBRARY.NAME)
        .values(personDTO.getId(), personDTO.getName())
        .execute();

      int code = 400;
      String messageError = "Person not created!";

      // Create a Logger
      Log mylog = new Log("log.txt");
      mylog.logger.log(Level.INFO, "Person: {0} {1}",
        new Object[]{personDTO.getId(), personDTO.getName()});

      context.response()
        .setStatusCode(code)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encode(result != 0 ? new JsonObject().put("Message:", "Person is create successful! ") : new JsonObject().put("Error: ", messageError)));

    } catch (Exception e) {
      context.response()
        .setStatusCode(400)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(e.getMessage());
    }
  }

  //Update value
  private void updateValues(RoutingContext context) {
    try {
      int code = 400;
      String messageError = "Person is not updated!";
      Connection connection = RestCRUD.getConnection();
      DSLContext create = DSL.using(connection, SQLDialect.POSTGRES);

      String valueIdUpdate = context.getBodyAsString();
      Person personDTO = new Gson().fromJson(valueIdUpdate, Person.class);

      int result = create.update(Tables.LIBRARY)
        .set(Tables.LIBRARY.NAME, personDTO.getName())
        .where(Tables.LIBRARY.ID.eq(45))
        .execute();

      context.response()
        .setStatusCode(code)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encode(result != 0 ?
          new JsonObject().put("Message: ", "Person is update successful!") : new JsonObject().put("Error: ", messageError)));

    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      e.printStackTrace();
    }
  }

  //Delete value
  private void deleteValues(RoutingContext context) {
    try {
      Connection connection = RestCRUD.getConnection();
      DSLContext create = DSL.using(connection, SQLDialect.POSTGRES);
      String valueIdDelete = context.request().getParam("id");
      int result = create.delete(Tables.LIBRARY).where(Tables.LIBRARY.ID.eq(Integer.valueOf(valueIdDelete))).execute();

      int code = 404;
      String messageError = "Person is not removed!";

      context.response()
        .setStatusCode(code)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encode(result != 0 ?
          new JsonObject().put("Message:", "Person has been removed!") : new JsonObject().put("Error: ", messageError)));
    } catch (Exception e) {
      context.response()
        .setStatusCode(400)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encode(e.getMessage()));
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    new RestCRUD().start();
  }
}
