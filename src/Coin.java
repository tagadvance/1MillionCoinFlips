public enum Coin {

	HEADS, TAILS;

	public static Coin getCoin(boolean b) {
		return b ? HEADS : TAILS;
	}

}
