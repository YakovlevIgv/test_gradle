package wordFinder;

import java.net.URL;
import java.util.Set;

public class wordFinderImpl1 implements WordFinder {
    @Override
    public Set<String> getSentence(URL resource) {
        return null;
    }

    @Override
    public boolean checkIfWordInSentence(String sentence, String word) {
        return false;
    }

    @Override
    public void writeSentenceToResult(String sentence) {

    }
}
