import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testRandom() {
        StudentArrayDeque<Integer> temp1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> temp2 = new ArrayDequeSolution<>();

        StringBuilder msg = new StringBuilder();

        int holder = 0;
        for (int i = 0; i < 100; i++) {
            double selector = StdRandom.uniform();
            if (selector < 0.25) {
                temp1.addFirst(i);
                temp2.addFirst(i);
                holder++;
                msg.append("addFirst(" + i + ")\n");
                assertEquals(msg.toString(), temp2.get(0), temp1.get(0));
            } else if (selector < 0.5) {
                temp1.addLast(i);
                temp2.addLast(i);
                holder++;
                msg.append("addLast(" + i + ")\n");
                assertEquals(msg.toString(), temp2.get(holder - 1), temp1.get(holder - 1));
            } else if (selector < 0.75) {
                if (temp2.isEmpty()) {
                    msg.append("isEmpty()\n");
                    assertTrue(msg.toString(), temp1.isEmpty());
                    continue;
                }
                Integer x = temp2.removeFirst();
                Integer y = temp1.removeFirst();
                holder--;
                msg.append("removeFirst()\n");
                assertEquals(msg.toString(), x, y);
            } else {
                if (temp2.isEmpty()) {
                    msg.append("isEmpty()\n");
                    assertTrue(msg.toString(), temp1.isEmpty());
                    continue;
                }
                Integer x = temp2.removeLast();
                Integer y = temp1.removeLast();
                holder--;
                msg.append("removeLast()\n");
                assertEquals(msg.toString(), x, y);
            }
        }
    }
}
