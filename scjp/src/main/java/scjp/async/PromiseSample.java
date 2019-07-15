package scjp.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

// comes from https://stackoverflow.com/questions/14541975/whats-the-difference-between-a-future-and-a-promise
public class PromiseSample {

	public static void main(String[] args) {
		Supplier<Integer> momsPurse = () -> {

			System.out.println("Mom is busying, please wait 5 seconds..." );
			try {
				Thread.sleep(5000);// mom is busy
			} catch (InterruptedException e) {
				;
			}

			return 100;

		};

		ExecutorService ex = Executors.newFixedThreadPool(10);

		CompletableFuture<Integer> promise = CompletableFuture.supplyAsync(momsPurse, ex);
		promise.thenAccept(u -> System.out.println("Thank you mom for $" + u));
	}

}
