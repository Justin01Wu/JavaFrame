//  it comes from :
// http://www.javaeye.com/topic/36659

package scjp.annotation;

@Description("Justin, will be a best developer")  
 public class ApplyAnnotation implements ApplyAnnotationInterface{  
     @Name(originate="creator:Justin",community="xyz")  
     public String getName()  
     {  
         return null;  
     }  
       
     @Name(originate="Terida",community="Health care provider",number=Name.CommunityNumber.LITTLE)  
     public String getName2()  
     {  
         return "A1234567";  
     }  
     
     public String getName3()  
     {  
         return "Name3434";  
     } 
 } 