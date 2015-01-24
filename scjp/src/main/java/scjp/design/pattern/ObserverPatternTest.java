package scjp.design.pattern;

import java.util.Observer;

public class ObserverPatternTest {
	
    public static void main(String[] args) {

        MyTopic topic = new MyTopic("orig msg");
         
        Observer obj1 = new MyTopicSubscriber();
        Observer obj2 = new MyTopicSubscriber();
         
        //register observers to the subject
        topic.addObserver(obj1);
        topic.addObserver(obj2);
        
        topic.setMsg("New Message");
        
        topic.setMsg("New Message 2");
    }
}
