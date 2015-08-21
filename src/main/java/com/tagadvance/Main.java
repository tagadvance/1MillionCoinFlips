package com.tagadvance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Tag <tagadvance@gmail.com>
 * @see https://www.reddit.com/r/askscience/comments/3hp4ig/if_i_flip_a_coin_1000000_times_what_are_the_odds/
 */
public class Main {

	public static final int COIN_FLIPS = 1000000, DEFAULT_ITERATIONS = 10000;
	public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		int iterations = DEFAULT_ITERATIONS;
		if (args.length > 0) {
			try {
				iterations = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"iterations must be an integer", e);
			}
		}

		long timeStart = System.currentTimeMillis();

		List<Future<Integer>> futures = new ArrayList<>();

		ExecutorService service = createScalableExecutorService();
		for (int i = 0; i < iterations; i++) {
			Random random = new Random();
			CoinFlipper coinFlipper = new CoinFlipper(random);
			CoinFlipperCallable callable = new CoinFlipperCallable(coinFlipper,
					COIN_FLIPS);
			Future<Integer> future = service.submit(callable);
			futures.add(future);
		}
		service.shutdown();
		service.awaitTermination(1, TimeUnit.MINUTES);

		BigDecimal zeroSum = BigDecimal.ZERO;
		for (Future<Integer> future : futures) {
			int sum = future.get();
			if (sum == 0) {
				zeroSum = zeroSum.add(BigDecimal.ONE);
			}
		}

		BigDecimal divisor = BigDecimal.valueOf(iterations);
		int scale = 4;
		BigDecimal chance = zeroSum
				.divide(divisor, scale, RoundingMode.HALF_UP);
		BigDecimal percent = chance.multiply(ONE_HUNDRED).setScale(2);
		System.out.printf("There is a %s%% chance of 0 sum occuring.%n",
				percent);

		long timeEnd = System.currentTimeMillis();
		double difference = timeEnd - timeStart;
		double secondsElapsed = difference / 1000;
		System.out.printf("%.2f seconds have elapsed.%n", secondsElapsed);
	}

	public static ExecutorService createScalableExecutorService() {
		Runtime runtime = Runtime.getRuntime();
		int cores = runtime.availableProcessors();
		return Executors.newFixedThreadPool(cores);
	}

}