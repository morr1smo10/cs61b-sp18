import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome() {
        assertTrue(palindrome.isPalindrome("abcba"));
        assertFalse(palindrome.isPalindrome("Adccba"));
        assertTrue(palindrome.isPalindrome("A"));
        assertTrue(palindrome.isPalindrome("0"));
        assertFalse(palindrome.isPalindrome("ab"));
    }

    @Test
    public void testOffByOne() {
        CharacterComparator c = new OffByOne();
        assertFalse(palindrome.isPalindrome("abcba", c));
        assertTrue(palindrome.isPalindrome("flake", c));
        assertTrue(palindrome.isPalindrome("A", c));
        assertTrue(palindrome.isPalindrome("0", c));
        assertTrue(palindrome.isPalindrome("ab", c));
        assertTrue(palindrome.isPalindrome("ba", c));
        assertFalse(palindrome.isPalindrome("aa", c));
        assertFalse(palindrome.isPalindrome("Aa", c));
    }

    @Test
    public void testOffByN() {
        CharacterComparator c = new OffByN(5);
        assertFalse(palindrome.isPalindrome("abcba", c));
        assertTrue(palindrome.isPalindrome("bidding", c));
        assertTrue(palindrome.isPalindrome("couth", c));
        assertTrue(palindrome.isPalindrome("0", c));
        assertTrue(palindrome.isPalindrome("interjoin", c));
        assertTrue(palindrome.isPalindrome("upsup", c));
        assertFalse(palindrome.isPalindrome("aa", c));
        assertFalse(palindrome.isPalindrome("Aa", c));
    }

}
