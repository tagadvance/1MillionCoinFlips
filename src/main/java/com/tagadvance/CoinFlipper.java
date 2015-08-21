package com.tagadvance;

import java.util.Random;

import com.google.common.base.Preconditions;

public class CoinFlipper {

	private final Random random;

	public CoinFlipper(Random random) {
		super();
		this.random = Preconditions.checkNotNull(random,
				"random must not be null");
	}

	public int flip(int times) {
		int heads = 0;
		for (int i = 0; i < times; i++) {
			boolean b = random.nextBoolean();
			Coin coin = Coin.getCoin(b);
			if (coin == Coin.HEADS) {
				heads++;
			} else {
				heads--;
			}
		}
		return heads;
	}

}