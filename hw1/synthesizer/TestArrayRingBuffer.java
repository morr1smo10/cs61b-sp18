package synthesizer;
//import edu.princeton.cs.algs4.In;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        //ArrayRingBuffer arb = new ArrayRingBuffer(10)
        BoundedQueue<Integer> x = new ArrayRingBuffer(3);
        assertTrue(x.isEmpty());
        x.enqueue(0);
        assertEquals((int) x.dequeue(), 0);
        x.enqueue(1);
        x.enqueue(2);
        assertEquals((int) x.peek(), 1);
        x.enqueue(3);
        assertTrue(x.isFull());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
