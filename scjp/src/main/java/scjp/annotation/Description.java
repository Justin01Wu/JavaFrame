// define an annotation: Description 
//  it comes from :
// http://www.javaeye.com/topic/36659

package scjp.annotation;
   
    import java.lang.annotation.Documented;  
    import java.lang.annotation.ElementType;  
    import java.lang.annotation.Retention;  
    import java.lang.annotation.RetentionPolicy;  
    import java.lang.annotation.Target;  
      
   @Target(ElementType.TYPE)  
   // it means this annotation can be applied to Class,Interface,Enum and Annotation
   
   @Retention(RetentionPolicy.RUNTIME)  
   // it means this annotation will be carry to source, class on runtime environment
   
   @Documented  
   public @interface Description {  
       String value();  
   }  