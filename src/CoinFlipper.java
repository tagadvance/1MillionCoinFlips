import java.util.Random;

public class CoinFlipper {

	private final Random random;

	public CoinFlipper(Random random) {
		super();
		if (random == null) {
			throw new NullPointerException("random must not be null");
		}
		this.random = random;
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