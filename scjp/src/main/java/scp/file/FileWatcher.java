package scp.file;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

//  https://www.baeldung.com/java-nio2-watchservice
public class FileWatcher {
	
	public static void main(String[] args) throws IOException, InterruptedException {
        WatchService watchService
          = FileSystems.getDefault().newWatchService();
 
        Path path = Paths.get(System.getProperty("user.home"));
        System.out.println("watching folder: " + path);
        
        // If a watched file is not located on a local storage device 
        // then it is implementation specific if changes to the file can be detected. In particular, 
        // it is not required that changes to files carried out on remote systems be detected.
        //  https://stackoverflow.com/questions/48919086/java-watch-service-not-working-for-remote-files-mounted-in-the-local-server
        // https://stackoverflow.com/questions/8476419/java-watchservice-not-generating-events-while-watching-mapped-drives
        
        path.register(
          watchService, 
            StandardWatchEventKinds.ENTRY_CREATE, 
              StandardWatchEventKinds.ENTRY_DELETE, 
                StandardWatchEventKinds.ENTRY_MODIFY);
 
        WatchKey key;
        while ((key = watchService.take()) != null) {   // blocks until an event occurs.
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
            }
            key.reset();
            //when the WatchKey instance is returned by either of the poll or take APIs, 
            //it will not capture more events if it's reset API is not invoked:
        }
    }
}
