package models;


public class User extends Users {
  private int id;
  private final String name;
  private final String password;

  private final String salt;

  public User(int id, String name, String password, String salt) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.salt = salt;
  }

  public boolean validate() {
    return getName().length() < 40;
  }

  public User copy(int id) {
    return new User(id, getName(), getPassword(), getSalt());
  }

  public int getId() {return id;}

  public String getSalt(){return salt;}

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", password='********'" +
      '}';
  }

  @Override
  public int compareTo(Users element) {
    return this.getId() - element.getId();
  }

  public void setId(int a) {
    this.id = a;
  }
}