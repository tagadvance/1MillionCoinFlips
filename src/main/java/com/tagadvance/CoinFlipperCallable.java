package com.tagadvance;

import java.util.concurrent.Callable;

import com.google.common.base.Preconditions;

public class CoinFlipperCallable implements Callable<Integer> {

	public static final int MIN_TIMES = 1;

	private final CoinFlipper coinFlipper;
	private final int times;

	public CoinFlipperCallable(CoinFlipper coinFlipper, int times) {
		super();
		this.coinFlipper = Preconditions.checkNotNull(coinFlipper,
				"coinFlipper must not be null");
		Preconditions.checkArgument(times >= MIN_TIMES,
				"times must be at least " + MIN_TIMES);
		this.times = times;
	}

	@Override
	public Integer call() throws Exception {
		return coinFlipper.flip(times);
	}

}