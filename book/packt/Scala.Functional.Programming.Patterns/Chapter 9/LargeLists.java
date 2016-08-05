public class LargeLists {
	public static void main(final String[] args) {
		final int take = 5;
		for (int i = 1000, n = 0; i < 1000000 && n < take; ++i) {
			final int j = i + 1;
			final int k = j * 2;
			if (k % 4 != 0) {
				System.out.println(k);
				++n;
			}
		}
	}
}

