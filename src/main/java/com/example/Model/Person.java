package com.example.Model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Person {
  private Integer id;
  private String name;


  public Person(String Name, Integer ID) {
    super();
    this.id = ID;
    this.name = Name;

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Person() {
    super();

  }

}
