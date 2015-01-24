package scjp.design.pattern;

import java.util.Observable;
import java.util.Observer;

public class MyTopicSubscriber implements Observer {

	public void update(Observable obs, Object arg1) {
		if (obs instanceof MyTopic) {
			MyTopic myTopic = (MyTopic) obs;
			String msg = myTopic.getMsg();
			System.out.println("MyTopic get new msg: " +msg);
		}

	}

}