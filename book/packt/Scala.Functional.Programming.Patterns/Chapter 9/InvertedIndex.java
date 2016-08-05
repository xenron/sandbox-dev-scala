import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InvertedIndex {
	public static void main(final String[] args) {
		final List<AuthorAndTitle> authorsToTitles = new ArrayList<>();
		
		authorsToTitles.add(new AuthorAndTitle("Carr", "And So To Murder"));
		authorsToTitles.add(new AuthorAndTitle("Carr", "The Arabian Nights Murder"));
		authorsToTitles.add(new AuthorAndTitle("Christie", "The Murder Of Roger Ackroyd"));
		authorsToTitles.add(new AuthorAndTitle("Christie", "The Sittaford Mystery"));
		authorsToTitles.add(new AuthorAndTitle("Carr", "The Mad Hatter Mystery"));
		authorsToTitles.add(new AuthorAndTitle("Carr", "The Plague Court Murders"));

		Set<String> ignoreWordsSet = new HashSet<>();
		ignoreWordsSet.add("To");
		ignoreWordsSet.add("The");
		ignoreWordsSet.add("And");
		ignoreWordsSet.add("So");
		ignoreWordsSet.add("Of");
		
		final Map<String, List<String>> invertedIdx = new HashMap<>();

		
		for (final AuthorAndTitle aTot: authorsToTitles) {
			final String title = aTot.getTitle();
			final String[] words = title.split("\\W");
			
			for (final String w: words) {
				if (ignoreWordsSet.contains(w)) {
					continue; // skip the word
				}
				if (!invertedIdx.containsKey(w)) {
					invertedIdx.put(w, new ArrayList<String>());
				}
				final List<String> authNameList = invertedIdx.get(w);
				final String authName = aTot.getAuthName();
				authNameList.add(authName);
			}
		}
		
		for (Map.Entry<String, List<String>> e: invertedIdx.entrySet()) {
			System.out.println(e.getKey() + " -> " + e.getValue());
		}
	}
}

