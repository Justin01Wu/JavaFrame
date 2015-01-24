package scjp.design.pattern;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyTopic extends Observable {

	private ArrayList<Observer> observers = new ArrayList<Observer>();

	private String msg;

	public MyTopic(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
		setChanged();
		notifyObservers(this, msg);
	}

	public void notifyObservers(Observable observable, String msg) {
		System.out.println("Notifying to all the subscribers when MyTopic is changed");
		for (Observer ob : observers) {
			ob.update(observable, this.getMsg());
		}
	}

	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void deleteObserver(Observer observer) {
		observers.remove(observer);

	}

}
