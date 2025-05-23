## Learning Spring boot project

### Benefits
+ Easy to start
+ Solve dependency hell

### Drawbacks
+ Dependency bound together, hard to adjust
+ Dependency Injection makes code static analysis harder, hard to refactor
+ hard to get rid of it, fully coupled with Spring, bad for long existing application
+ some default setting is not good, like logging setting: has thread number, hasn't code line, which is not good for developers:
```
    2020-03-16 09:55:41.457  INFO 11928 --- [nio-9090-exec-9] com.justa.springboot.db.UserManager      : triggered by user saving: 6
```
+ it is heavy to start, so not good for lambda style function, which ask to start quickly with tons of instances.

### Log
Need to adjust those items for developers:
+ Date is not necessary, only time, developers can't debug an issue for a whole day 
+ Pid is not necessary, developer env only has an user
+ Thread name, developer env only has an user
+ class name only need one level, developer can easily find the package name in the IDE, name conflict is rare case
+ need add code line number, developers need this to locate the error

 
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

## Error handling
- Spring boot will stop if it detect any error: <img src="img/springBootStartError.png">



### Cache
#### template cache
By default, Spring boot will load templates into the memory and ignore all modification. 
It is inconvenient for the developer, So you use this to disable it:
`spring.thymeleaf.cache=false`

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
But it has a side affection: 
+ it will restart the server almost everytime when you change the Java code, you maybe lose the session status if you have it
+ it will restart the server everytime when you change the html or javascript code, which is not necessary

In this way, Spring boot will automatically detect your changing and load them(hot swapping).
For production, you should never do it.

### Spring related tips
#### @Autowired
+ @Autowired can apply to instance variables, constructor and setter
+ @Autowired can also apply to a no setter method if it will be called by spring 
+ @Autowired will get nullPointerException on static variables, even it didn't complain on the compiling. 
    So you have to use instance variables or use this way:
	```Java
	@Value("${app.mysetting:}")
    public void setMyStaticVar(String value){

        ConfigService.StaticVariable = value;
    }
	```
+ You can use @Qualifier to specify a dedicated type of the bean when you have multiple implementations:
    +  define a name on the bean: `@Component("SimpleBean")`
    +  use a name on the @Qualifier: `@Qualifier("SimpleBean")`
+ you can use @Scope to define a bean scope: `@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)`
+ @Bean annotates a method that creates a bean in a configuration class;
    @Component annotates classes to mark them as bean definitions for the Spring Container    
+ @Autowired cannot be used to autowire no bean values, so you have to use @Value	
+ There are JSR equivalent
    + @Component -> @Named
	+ @Qualifier -> @Qualifier
	+  @Autowired -> @Inject
	+  @Autowired + @Qualifier -> @Resource
	
#### @Value 
+ @Value can inject a string from configure file: `@Value("${driver.class.name}")`
+ @Value can also inject a string from another bean: `@Value("#{dbProps.driverClassName}")`


#### others    
    
+ @AllArgsConstructor will automatically wire all instance variables without @Autowired when you have only one implementation
+ Spring bean must be marked as stereotype annotations: @Component or its children: @Repository, @Service, @Controller
	otherwise, it will complain 
    + @Component is a generic stereotype for any Spring-managed component.
    + @Service annotates classes at the service layer.
    + @Repository annotates classes at the persistence layer, which will act as a database repository.	
    + @Repository’s job is to catch persistence-specific exceptions and re-throw them as one of Spring’s unified unchecked exceptions.
+ Spring used this order to initialize a bean:  constructor(can have parameter), setter, initMethod(with @PostConstruct) 
    By the way, @PostConstruct comes from JSR250


### Spring includes 7 different Bean scopes:

+ Singleton, this is default scope, it used as Stateless session management
+ Prototype, on Stateful session management 
+ Request, web related
+ Session, web related
+ Global session, similar to the Session scope but it’s only applicable in the context of Portlet based web applications.
+ Application, very similar to the Singleton scope, different for each of the contexts
+ Websocket

### security
When security is enabled, many inner URL is blocked, we need to set exceptions for those URL. 
But some inner path is hidden in the code, we don't know the URL. 
For example, H2-console is not working properly when security is enabled.

### Run as a service
+ Usually your code is in a war file, and OS administrator already set your web server as a service.
+ But as a SpringBoot, your code itself has container, which make it difficult to set it as a service.
+ To solve this issue, Spring Boot provide war build option with a little code changing: 
    + adding an SpringBootServletInitialize
    + use Profile to design prod only parameters

## JPA
Spring boot will automatically run schema.sql and data.sql in the resources folder when 
- it has spring-boot-starter-data-jpa dependency
- spring.jpa.hibernate.ddl-auto=none in the application.properties

The spring.jpa.hibernate.ddl-auto is special, depending on runtime conditions, it has different defaults:
- If an embedded database is used and no schema manager (such as Liquibase or Flyway) is handling the DataSource, it defaults to create-drop. 
- In all other cases, it defaults to none.

In this project, we used H2 embedded database, so spring boot will:
+ run schema.sql and data.sql firstly, 
+ then re-create those tables from ddl-auto.

This means schema.sql and data.sql won't work by default

To make sure our Code matched the database, we can set to this:
- spring.jpa.hibernate.ddl-auto=validate<img src="img/schema_validate_failure.png">

Hibernate usually is case sensitive, even the database server is case insensitive 

### general mapping
Hibernate will automatically find many errors when it is starting. For example:
- it will throw this error if getter didn't match setter:
    `org.hibernate.PropertyNotFoundException: Could not locate setter method for property [abc.validation.domain.Item#price]`

###Enum mapping
- By default, Hibernate maps an enum to a number. It uses the ordinal value.
- You can map the enum value to a String like this way:

```java
  @Enumerated(EnumType.STRING)
  private Rating rating;
```
- You can create a Custom Mapping by implementing AttributeConverter
- You can tell the JPA provider to use your customized enum converter automatically in any entity 

```java
	@Converter(autoApply = true)
```
- please go [here](https://thoughts-on-java.org/hibernate-enum-mappings/) for details. 

### Swagger
+ Swagger will automatically generate sample json based on your current setting for your Java Json mapper(like Jackson)
+ it won't fail if the java object is not a pure java bean, but the result is incorrect.
    for example, you has a pair of methods on doubleAsString, then it will generate`"doubleAsString": "string"`
    
   But it will fail if you post a general string because it expected a string wchih can be converted to a double like this:`"3.14159265"`




