// code-examples/AdvOOP/objects/PersonUserWontCompile.java
// NICHT KOMPILIERBAR

package objects;

public class PersonUserWontCompile {
  public static void main(String[] args) {
    Person buck = Person.apply("Buck Trends", 100);  // FEHLER
    System.out.println(buck);
  }
}