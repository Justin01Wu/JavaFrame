package scp.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

// https://dzone.com/articles/locking-files-in-java
public class FileLockTest implements Runnable {
	private FileChannel fc;
	private RandomAccessFile randomAccessFile;
	private String data;

	public FileLockTest(String fileName, String data) throws FileNotFoundException {
		randomAccessFile = new RandomAccessFile(fileName, "rw");
		this.data = data;
	}

	public void writeToFileWithLock(String data) throws IOException {
		fc = randomAccessFile.getChannel();
		ByteBuffer buffer = null;
		try (FileLock fileLock = fc.tryLock()) {  // will throw verlappingFileLockException if it is locked by others
			if (null != fileLock) {
				buffer = ByteBuffer.wrap(data.getBytes());
				buffer.put(data.toString().getBytes());
				buffer.flip();
				while (buffer.hasRemaining())
					fc.write(buffer);
			}
		}
	}

	public void run() {
		try {
			writeToFileWithLock(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		String fileName = FileLockTest.class.getClassLoader().getResource("lockedFile.txt").getFile();

		FileLockTest o1 = new FileLockTest(fileName, "String aaaaaaaaa");
		FileLockTest o2 = new FileLockTest(fileName, "String bbbbbbbbb");

		Thread t1 = new Thread(o1);
		Thread t2 = new Thread(o2);

		t1.setName("Thread_t1");
		t2.setName("Thread_t2");

		t1.start();
		t2.start();

		// usually thread 1 will succeed, thread 2 will throw verlappingFileLockException on fc.tryLock
	}
}
