package scjp.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

//it comes from https://www.baeldung.com/java-broadcast-multicast
// please run it on another machine from MulticastPublisher
public class MulticastReceiver extends Thread {
	protected MulticastSocket socket = null;
	protected byte[] buf = new byte[256];

	public void run()  {
		try {
			pullMsg();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void pullMsg() throws IOException {
		socket = new MulticastSocket(4446);
		InetAddress group = InetAddress.getByName("230.0.0.0");
		socket.joinGroup(group);
		
		System.out.println("waiting for message...");
		while (true) {
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);
			String received = new String(packet.getData(), 0, packet.getLength());
			if ("end".equals(received)) {
				System.out.println("get end message, so quit");
				break;
			}else {
				System.out.println(received);
			}
		}
		socket.leaveGroup(group);
		socket.close();
		
	}
	
	public static void main(String[] args) {
		MulticastReceiver receiver = new MulticastReceiver();
		Thread t =  new Thread(receiver);
		t.run();
	}

}
