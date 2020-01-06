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
