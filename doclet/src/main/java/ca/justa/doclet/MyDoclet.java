package ca.justa.doclet;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;

/**
this is a doclet, please use this command to run it:

javadoc -doclet ca.justa.doclet.MyDoclet -docletpath .\target\justin-doclet.jar C:\samples\JavaFrame\JavaFrame\scjp\src\main\java\scjp\testclass\inherit\HughJampton.java

*/

public class MyDoclet {
	
	public static boolean start(RootDoc root){
        
        for( ClassDoc aClass : root.classes() ){
        	System.out.println("     =======    ");
        	System.out.println("  qualifiedName = "  + aClass.qualifiedName());
        	System.out.println("  name = " + aClass.name());
        	System.out.println("  commentText = " + aClass.commentText());
        	System.out.println("  rawCommentText = " + aClass.getRawCommentText());
        	System.out.println("  dimension = " + aClass.dimension());
        	System.out.println("  qualifiedTypeName = "+aClass.qualifiedTypeName());
        	System.out.println("  simpleTypeName = " + aClass.simpleTypeName());

        }
        return true;
    
	}
	
	public static void main(String[] args) {
		

	}
}
