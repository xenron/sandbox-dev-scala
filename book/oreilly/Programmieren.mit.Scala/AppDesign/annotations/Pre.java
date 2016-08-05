// code-examples/AppDesign/annotations/Pre.java

package org.contract4j5.contract;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Pre {
  /**
   * "value" ist der Testausdruck, dessen Auswertung true oder false ergeben muss.
   * Es muss ein in Ihrer Skriptsprache gültiger Ausdruck sein.
   */
  String value() default "";

  /**
   * Eine optionale Meldung, die zusammen mit der Standardmeldung ausgegeben wird, wenn
   * der Kontrakt scheitert.
   */
  String message() default "";
}
