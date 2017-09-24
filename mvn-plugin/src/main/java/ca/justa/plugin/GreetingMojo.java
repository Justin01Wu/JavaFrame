package ca.justa.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
 
/**
 * Says "Hi" to the user.  this comes from 
 * https://maven.apache.org/guides/plugin/guide-java-plugin-development.html 
 *  
 please follow those to run it 
   mvn clean install
   mvn -Dsayhi.name=Rita ca.justa.plugin:hello-maven-plugin:1.0:sayhi
   
   mvn -Dsayhi.name="Rita Wang" ca.justa.plugin:hello-maven-plugin:1.0:sayhi
 */
@Mojo( name = "sayhi", defaultPhase = LifecyclePhase.COMPILE )
public class GreetingMojo extends AbstractMojo {	
	
	@Parameter( property = "sayhi.name", defaultValue = "Justin Wu" )
    private String name;
	
	public void execute() throws MojoExecutionException {
		getLog().info("");  // generate an empty line
		getLog().info("    ====== >>>> Hello, " + name + "!");
	}
}