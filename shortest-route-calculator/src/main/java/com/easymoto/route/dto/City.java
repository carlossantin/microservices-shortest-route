package com.easymoto.route.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;
import java.util.ArrayList;

/**
 *  Data Transfer Object for Cities
 * 
 * @author Carlos E. Santin <cesantin@gmail.com>
 */
@JsonRootName("City")
public class City {
  
  protected Integer id;
  protected String name;
  protected List<CityDistance> distances = new ArrayList<CityDistance>();

  protected City() {
  }
  
  public String getName() {
    return this.name;
  }

  protected void setName(String name) {
    this.name = name;
  }
  
  public Integer getId() {
    return this.id;
  }

  protected void setId(Integer id) {
    this.id = id;
  }

  public List<CityDistance> getDistances() {
    return this.distances;
  }

  @Override
  public String toString() {
   return String.format("%s [%s]", name, id);
  }
}
