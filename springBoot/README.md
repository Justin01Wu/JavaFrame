## This a Spring boot test project

### Configure
Spring boot can get configure setting from many resources:
- Command line parameters
- JNDI properties
- JVM properties like: --spring.main.show-banner=false
- OS environment variables, like: export spring_main_show_banner=false
- application.properties in config folder of current Jar, like spring.main.show-banner=false
- application.properties in current Jar folder
- application.properties in current Jar
- any text file in @PropertySource
- default value

In this list, the top one override the bottom one if they have the same setting name

#### Server http port
Spring boot used this to set server port:
`server.port=9090'

You can see Spring boot has a bad naming conversion: 
We don't know a setting is Spring boot reserved setting name or just one was created by a developer.  

### cache
#### template cache
By default, Spring boot will load templates into the memory and ignore all modification. 
It is inconvenient for the developer, So you use this to disable it:
`spring.thymeleaf.cache=false1`

For production, you should never do it.

#### Java code cache
By default, Spring boot will load Java Class into the memory and ignore all Java modification.
It is inconvenient for the developer, So you use this to disable it:
```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
```

In this way, Spring boot will automatically detect your changing and load them(hot swapping).
For production, you should never do it.