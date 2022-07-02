public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> holder = new ArrayDeque<>();
        int temp = word.length();
        for (int i = 0; i < temp; i++) {
            holder.addLast(word.charAt(i));
        }
        return holder;
    }

    public boolean isPalindrome(String word) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }

        Deque temp = wordToDeque(word);

        int middle = word.length() / 2;
        for (int i = 0; i < middle; i++) {
            if (temp.removeFirst() == temp.removeLast()) {
                continue;
            } else {
                return false;
            }
        }
        return true;

        /*
        if(word.length() == 0 || word.length() == 1) {
            return true;
        }
        boolean temp = true;
        int middle = word.length() / 2;
        for (int i = 0; i < middle; i++) {
            if (word.charAt(i) == word.charAt(word.length()- i - 1)) {
                temp = true;
            } else {
                temp = false;
                return temp;
            }
        }
        return temp;

         */
    }

    public boolean isPalindrome(String word, CharacterComparator c) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }

        Deque temp = wordToDeque(word);

        int middle = word.length() / 2;
        for (int i = 0; i < middle; i++) {
            if (c.equalChars((Character) temp.removeFirst(), (Character) temp.removeLast())) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

}
