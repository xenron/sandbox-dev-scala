package chapter03;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;

public class Parser {

	private static final String L_BRACKET = "(";
	private static final String R_BRACKET = ")";

	private class Tokenizer {

		private static final String NUM_PATTERN = "(\\d+).*";
		private String s;
		private final Pattern p;

		public Tokenizer(final String s) {
			this.s = s;
			this.p = Pattern.compile(NUM_PATTERN);
		}

		public boolean nextTokenIs(final String tok) {
			if (s.isEmpty()) {
				return false;
			}
			return s.startsWith(tok);
		}

		public void consume(final String tok) {
			s = s.replaceFirst(Pattern.quote(tok), "");
		}

		public boolean nextTokenIsNumber() {
			return s.matches(NUM_PATTERN);
		}

		public int consumeANumber() {
			if (s.matches(NUM_PATTERN)) {
				final Matcher m = p.matcher(s);
				Validate.isTrue(m.matches(), "Could not extract number from <"
						+ s + ">");
				final String numStr = m.group(1);
				s = s.replaceFirst(Pattern.quote(numStr), ""); 
				return Integer.valueOf(numStr);
			}

			throw new IllegalArgumentException("Number expected");
		}

	};

	private final Tokenizer tokenizer;

	public Parser(final String s) {
		tokenizer = new Tokenizer(s);
	}

	private int factor() {
		if (tokenizer.nextTokenIsNumber()) {
			final int num = tokenizer.consumeANumber(); // 1
			return num;
		}
		if (tokenizer.nextTokenIs(L_BRACKET)) { 
			tokenizer.consume(L_BRACKET); // 2
			final int num = expr();
			if (!tokenizer.nextTokenIs(R_BRACKET)) {
				throw new IllegalArgumentException("Syntax error - ) missing");
			}
			tokenizer.consume(R_BRACKET);  // 3
			return num;
		}
		throw new IllegalArgumentException("Either number or ( expected");
	}

	private int term() {
		int val = factor();     // 4
		while (tokenizer.nextTokenIs("*")) { // 5
			tokenizer.consume("*");
			val *= factor(); //  6
		}
		return val;
	}


	public int expr() {
		int val = term();           //  7
		while (tokenizer.nextTokenIs("+")) {   // 8      
			tokenizer.consume("+");
			val += term();   // 9
		}
		return val;
	}

	

}
