package ca.justa.doclet;

public class JavaDocCaller {

	public static void main(String[] args) {
		String[] myArgs = {
				"-doclet", "ca.justa.doclet.MyDoclet",
				"-sourcepath", "C:/samples/WebApp/WebApp/jersey2/src/main/java/",
				"wu.justin.rest2",
		};
		
		com.sun.tools.javadoc.Main.execute(myArgs);
	}

}
