package com.tagadvance;

import java.util.concurrent.Callable;

public class CoinFlipperCallable implements Callable<Integer> {

	public static final int MIN_TIMES = 1;

	private final CoinFlipper coinFlipper;
	private final int times;

	public CoinFlipperCallable(CoinFlipper coinFlipper, int times) {
		super();
		if (coinFlipper == null) {
			throw new NullPointerException("coinFlipper must not be null");
		}
		if (times < MIN_TIMES) {
			throw new IllegalArgumentException("times must be at least "
					+ MIN_TIMES);
		}
		this.coinFlipper = coinFlipper;
		this.times = times;
	}

	@Override
	public Integer call() throws Exception {
		return coinFlipper.flip(times);
	}

}