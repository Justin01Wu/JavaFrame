package jdktest;


/**
	test which is running firstly, test how to get class path
 */
class Candy {

    static {
        System.out.println("Loading Candy");
    }

    String getPath() {
        String className = getClass().getName();

        if (!className.startsWith("/")) {
            className = "/" + className;
        }
        className = className.replace('.', '/');
        className = className + ".class";

        java.net.URL classUrl = getClass().getResource("/jdktest/second/Pet.class");
//    java.net.URL classUrl =   getClass().getResource(className);

        if (classUrl != null) {
            System.out.println("Entry Path in " + classUrl.getFile());
            String path = classUrl.getFile();
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            int cc = path.lastIndexOf(className);
            if (cc > 0) {
                path = path.substring(0, cc);
            }
            if (!path.endsWith("/")) {
                path = path + "/";
            }
            return path;
        } else {
            System.out.println(" Class '" + className + "' not found in  '" +
                    System.getProperty("java.class.path") + "'");
            return null;
        }
    }
}

class Gum {

    static {
        System.out.println("Loading Gum");
    }

    public Gum() {
    }
}

public class ClassPath {
    //Main method
    public static void main(String[] args) {
        Candy a1 = new Candy();
        
        @SuppressWarnings("unused")
		jdktest.second.Pet apet = new jdktest.second.Pet();
        
        System.out.println(a1.getPath());
        try {
        
        	@SuppressWarnings("unused")
			Class<?> mm = Class.forName("jdktest.Candy");
            //mm.getResource();
        	
            Class<Gum> xx = Gum.class;
            Object vv = xx.newInstance();
            System.out.println(vv.getClass().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
