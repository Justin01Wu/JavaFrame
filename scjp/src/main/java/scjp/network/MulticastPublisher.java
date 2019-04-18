package scjp.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

// it comes from https://www.baeldung.com/java-broadcast-multicast
//please run it on another machine from MulticastPublisher
public class MulticastPublisher {
 
    public static void multicast(String multicastMessage) throws IOException {
    	DatagramSocket socket = new DatagramSocket();
    	InetAddress group = InetAddress.getByName("230.0.0.0");
    	byte[] buf = multicastMessage.getBytes();
 
        DatagramPacket packet 
          = new DatagramPacket(buf, buf.length, group, 4446);
        socket.send(packet);
        socket.close();
    }
    
    public static void main(String[] args) throws IOException {
    	MulticastPublisher.multicast("hello");
    	MulticastPublisher.multicast("end");
    }

}
