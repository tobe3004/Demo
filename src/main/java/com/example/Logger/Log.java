package com.example.Logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
  public Logger logger;
  FileHandler fileHandler;

  public Log(String filename) throws IOException {
    File file = new File(filename);
    if (!file.exists()) {
      file.createNewFile();
    }
    fileHandler = new FileHandler(filename, true);
    logger = logger.getLogger("Test");
    logger.addHandler(fileHandler);
    SimpleFormatter formatter = new SimpleFormatter();
    fileHandler.setFormatter(formatter);
  }
}
