// improve from Daemons.java Justin Wu 2002-12-7
// show daemon thread, you will see the program will stop after 1 second running
// while ignoring daemon thread,
// otherwise if daemon has a dead loop , the program won't stop


package scjp.testthread;
import java.io.*;

class Daemon21 extends Thread {
	private static final int SIZE = 10;
	private Thread[] t = new Thread[SIZE];

	public Daemon21() {
		setDaemon(true);
		start();
	}

	public void run() {
		/*
		 * Thread t[]=new Thread[this.getThreadGroup().activeCount()];
		 * this.getThreadGroup().enumerate(t); for(int i = 0; i <
		 * this.getThreadGroup().activeCount(); i++){
		 * System.out.println(t[i].getName()); System.out.println( "t[" + i +
		 * "].isDaemon() == " + t[i].isDaemon()); }
		 */
		while (true)
			yield();
	}
}

class DaemonSpawn2 extends Thread {
	public DaemonSpawn2(int i) {
		System.out.println("DaemonSpawn " + i + " started");
		this.setName("DaemonSpawn" + i);
		start();
	}

	public void run() {
		try {
			sleep(1000);
		} catch (Exception e) {
		}
		if (this.getName().equals("DaemonSpawn1")) {
			Thread t[] = new Thread[this.getThreadGroup().activeCount()];
			this.getThreadGroup().enumerate(t);
			for (int i = 0; i < this.getThreadGroup().activeCount(); i++) {
				System.out.println(t[i].getName());
				System.out.println("t[" + i + "].isDaemon() = " + t[i].isDaemon());
			}
		}
//    while(true)
//      yield();
	}
}

public class Daemons2 {
	public static void main(String[] args) throws IOException {
		Thread t;
		for (int i = 0; i < 3; i++)
			t = new DaemonSpawn2(i);
		Thread d = new Daemon21();
		System.out.println("d.isDaemon() = " + d.isDaemon());
		// Allow the daemon threads to
		// finish their startup processes:
		// System.out.println("Press any key");
		// System.in.read();
	}
}
