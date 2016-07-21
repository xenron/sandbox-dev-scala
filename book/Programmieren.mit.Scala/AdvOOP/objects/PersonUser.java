// code-examples/AdvOOP/objects/PersonUser.java

package objects;

public class PersonUser {
  public static void main(String[] args) {
    // Die folgende Zeile lässt sich nicht kompilieren
    // Person buck = Person.apply("Buck Trends", 100);
    Person buck = PersonFactory.make("Buck Trends", 100);
    System.out.println(buck);
  }
}