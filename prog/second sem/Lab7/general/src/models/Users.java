package models;

import genutilities.Validatable;

import java.io.Serializable;

public abstract class Users implements Comparable<Users>, Validatable, Serializable {
  abstract public int getId();
  public String getName;

}