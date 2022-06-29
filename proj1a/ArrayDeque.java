public class ArrayDeque<T>{
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] array;

    public ArrayDeque() {
       array = (T[]) new Object[8];
       size = 0;
       nextFirst = 3;
       nextLast = 4;
    }

    private void resize(int newsize) {
        T[] temp = (T[]) new Object[newsize];
        if(nextFirst >= nextLast - 1) {
            int rear = array.length - nextFirst;
            System.arraycopy(array, increment(nextFirst), temp, 0, rear - 1);
            System.arraycopy(array, 0, temp, rear - 1, nextLast);
        }
        else {
            System.arraycopy(array, increment(nextFirst), temp, 0, size);
        }
        nextFirst = newsize - 1;
        nextLast = size;
        array = temp;
    }

    private int increment(int value) {
        return (value+1)%array.length;
    }

    private int decrement(int value) {
        return (value-1+array.length)%array.length;
    }

    public void addFirst(T item) {
        if (size == array.length) {
            resize(array.length * 2);
        }
        array[nextFirst] = item;
        nextFirst = decrement(nextFirst);
        size++;
    }

    public void addLast(T item) {
        if (size == array.length) {
            resize(array.length * 2);
        }
        array[nextLast] = item;
        nextLast = increment(nextLast);
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (nextLast > nextFirst) {
            for (int i = nextFirst+1; i < nextLast; i++) {
                System.out.print(array[i] + " ");
            }
            System.out.println();
        }
        else {
            for (int i = nextFirst+1; i < array.length; i++) {
                System.out.print(array[i] + " ");
            }
            for (int i = 0; i < nextLast; i++) {
                System.out.print(array[i] + " ");
            }
            System.out.println();
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (array.length >= 16 && (double) size < (array.length / 4.0)) {
            resize(array.length / 2);
        }
        nextFirst = increment(nextFirst);
        size--;
        return array[nextFirst];
    }

    public T removeLast() {
        if(size == 0) {
            return null;
        }
        if (array.length >= 16 && (double) size < (array.length / 4.0)) {
            resize(array.length / 2);
        }
        nextLast = decrement(nextLast);
        size--;
        return array[nextLast];
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        else {
            return array[(nextFirst+1+index)%array.length];
        }
    }
}