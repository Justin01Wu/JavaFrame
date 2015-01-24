package scjp.design.pattern;

import java.util.Observable;
import java.util.Observer;

public class ObservDemo {

	public static void main(String[] av) {

		MyView view = new MyView();
		MyModel model = new MyModel();
		model.addObserver(view);
		
		model.changeSomething();
	}
}

/** The Observer normally maintains a view on the data */
class MyView implements Observer {
	/** For now, we just print the fact that we got notified. */
	public void update(Observable obs, Object x) {
		System.out.println("update(" + obs + ");");
	}
}


/** The Observable normally maintains the data */
class MyModel extends Observable {
	public void changeSomething() {
		// Notify observers of change
		setChanged();
		notifyObservers();
	}
}