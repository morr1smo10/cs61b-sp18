public class HorribleSteve {
    public static void main(String [] args) {
        int i = 0;
        for (int j = 0; i < 500; ++j) {
            if (!Flik.isSameNumber(i, j)) {
                break; // break exits the for loop!
            }
            ++i;
        }
        System.out.println("i is " + i);
    }


}
