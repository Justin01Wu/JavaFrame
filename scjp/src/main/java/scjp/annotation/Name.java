// define an annotation: Name 
//  it comes from :
// http://www.javaeye.com/topic/36659
package scjp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//notice: this @Target is different from @Description, members are also different  
// this annotation declare 3 fields: originate, community and number
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Name {

    String originate();

    String community();

    public enum CommunityNumber {
        LITTLE, ORDINARY, WELL
    }

    CommunityNumber number() default  CommunityNumber.WELL;       
     
 } 

 
