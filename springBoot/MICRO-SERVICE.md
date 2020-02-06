## This page discuss Micro-Service
By Justin.wu  

Micro-Service is hot, but like many technology, People exaggerate it's benefits.
We can easily find those so called benefits. But people rarely think about drawbacks.
So I mainly discuss drawbacks here: 

### Drawbacks

- How do you know an application is too big to be a monolithic? 
  Many applications are severely hurt by bad designs, bad code quality, unclear requirement, unclear road map, wrong technical direction. 
  They are cheated that the micro-service can solve maintainance issues
- We had RPC, Web service, RMI , SOA and Corba before, why do so many people think micro-service is the silver bullet?
- Heterogeneous environment is our difficulties, they usually come from companies merging. 
   But now people used micro-service to open Pandora's box: 
   Now we are creating heterogeneous environments for no good reason:
   https://blog.overops.com/5-ways-to-not-f-up-your-microservices-in-production/
- How to manage the array of Microservices?
   We have 10 Micro-Services, We have QA, Dev, UAT,and Prod. 
   so we need to manage 40 Micro-Service instances which is risky: 
   which code will be merged into which branch of which services and deploy to where
- Strategy question: 
   I found many good questions on micro-services in a few days, 
   should we use research and prototype to figure out those questions or directly use real project to figure out?
- How to locate root cause of an error(troubleshooting):
   when an error happens, how do we know what is the root cause in the long call chain? 
   Like serviceA called service B, and service B called service C. 
   every team needs some time to find if it is this service issue or other service issue.
- Fixing a bug:  
  how to decide which service fix the issue if it can be fixed in multiple services? 
  Every team will try to  hide its mistaken and push the fixing to other sides. 
  It will cost much more if we fixed on wrong services.
  For example, I found a bug in the new SICS server. 
  But the vendor refused to accept and insisted us to try different ways which can’t help until 3 weeks has passed. 
  I am lucky because I have a chance to see their source code and I understand their code a little bit.
- This article is created by Justin.wu justin01.wu@gmail.com, please contact me if you like to discuss  
* Complicated relationship: 
  * How do we handle complicated relationship when a new version is not backwards compatible?
  * How do we know what will be afftected if we adjust some behaviours?
  * How do we know what will be afftected if we remove an deprecated function?
* What is cost for this direction:
    * developing cost, running cost, and maintain cost, Data management cost (back up data, copy to testing environment)
    * learning curve
    * Did we compare the benefits and costs before we go to this direction?

* How to handle Microservices security:
    * centralized authentication centre to manage all types of users?
    * How about external users or inner users?
    * who manage roles? different services needs different roles and users
    * Different services needs different way to handle authorization: self managed vs centralized management...
       some need department info, some need roles, some need multiple level of department and roles
	* relationship with AD if it is inner users    

* How to handle dependency:
  * inner dependency and external dependency
  * how to handle security between them?
  *  How to communicate with legacy system     
  
- How to handle version:     
   How to deprecate functions?
    how to know who is using what?
    how to know call stack?    
    how to declare a deprecation: good document?   
-  How to handle transaction cross multiple services?
    Data streaming: debezium and Kafka: 
    https://www.youtube.com/watch?v=MrV0DqTqpFU&t=224s
    events? 
- Compare docker vs VM and JVM? Compare traditional cluster with docker ?
- How does a web UI handle cross domain request easily?
- How do we protect our services when it is exposed to the public cloud?
- Every service needs a SSL certificate? How much does it cost? Self signed certificate?

- How can we mark a function or service is deprecated on micro service level


