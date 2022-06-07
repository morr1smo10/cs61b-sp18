public class HelloNumbers {
    public static void main(String[] args) {
	int sum = 0;
        int x = 0;
        while (x < 10) {
            System.out.print(sum + " ");
	    x = x + 1;
            sum = sum + x;
        }
    }
}