package wordFinder;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WordFinderUserTest {
    public static final String FILE_ANY = "file://any";
    public static final String WORD = "";
    private WordFinderUser wordFinderUser;
    private WordFinder wordFinder = Mockito.mock(WordFinder.class);
    final static String FIRST_SENTENCE = "Doing Test";
    final static String SECOND_SENTENCE = "Let's do it";


    @BeforeEach
    void beforeEach() {
        wordFinderUser = new WordFinderUser(wordFinder);
    }

    @Test
    void doWordNullTest() {
        when(wordFinder.getSentence(any())).thenReturn(null);
        assertDoesNotThrow(()-> wordFinderUser.doWord(FILE_ANY, WORD) );
    }

    @Test
    void doWordEmptySetTest()  {
        when(wordFinder.getSentence(any())).thenReturn(new HashSet<>());
        try {
            wordFinderUser.doWord(FILE_ANY, WORD);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        verify(wordFinder, times(0)).checkIfWordInSentence(any(),any());
        verify(wordFinder, times(0)).writeSentenceToResult(any());
    }

    @Test
    void doWordIfTrueTest() {
        when(wordFinder.getSentence(any())).thenReturn(new HashSet<>(Arrays.asList(FIRST_SENTENCE, SECOND_SENTENCE)));
        when(wordFinder.checkIfWordInSentence(FIRST_SENTENCE, WORD)).thenReturn(true);
        when(wordFinder.checkIfWordInSentence(SECOND_SENTENCE, WORD)).thenReturn(false);
        try {
            wordFinderUser.doWord(FILE_ANY, WORD);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        verify(wordFinder, times(1)).writeSentenceToResult(FIRST_SENTENCE);
        verify(wordFinder, times(0)).writeSentenceToResult(SECOND_SENTENCE);
    }

    @Test
    void doWordURLForming() {
        final ArgumentCaptor<URL> argument = ArgumentCaptor.forClass(URL.class);
        try {
            wordFinderUser.doWord(FILE_ANY, WORD);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        verify(wordFinder).getSentence(argument.capture());
        assertEquals(FILE_ANY, argument.getValue().toString());
    }

    @Test
    void doWordBadURL() {
        assertThrows(MalformedURLException.class, () -> wordFinderUser.doWord("", ""));
    }
}