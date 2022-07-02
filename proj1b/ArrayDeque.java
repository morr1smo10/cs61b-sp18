public class ArrayDeque<T> implements Deque<T> {
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
        if (nextFirst >= nextLast - 1) {
            int rear = array.length - nextFirst;
            System.arraycopy(array, increment(nextFirst), temp, 0, rear - 1);
            System.arraycopy(array, 0, temp, rear - 1, size - (rear - 1));
        } else {
            System.arraycopy(array, increment(nextFirst), temp, 0, size);
        }
        nextFirst = newsize - 1;
        nextLast = size;
        array = temp;
    }

    private int increment(int value) {
        return (value + 1) % array.length;
    }

    private int decrement(int value) {
        return (value - 1 + array.length) % array.length;
    }

    @Override
    public void addFirst(T item) {
        if (size == array.length) {
            resize(array.length * 2);
        }
        array[nextFirst] = item;
        nextFirst = decrement(nextFirst);
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == array.length) {
            resize(array.length * 2);
        }
        array[nextLast] = item;
        nextLast = increment(nextLast);
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (isEmpty()) {
            return;
        } else if (nextLast > nextFirst + 1) {
            for (int i = nextFirst + 1; i < nextLast; i++) {
                System.out.print(array[i] + " ");
            }
            System.out.println();
        } else {
            for (int i = nextFirst + 1; i < array.length; i++) {
                System.out.print(array[i] + " ");
            }
            for (int i = 0; i < nextLast; i++) {
                System.out.print(array[i] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (array.length >= 16 && (double) size < (array.length / 4.0)) {
            resize(array.length / 2);
        }
        nextFirst = increment(nextFirst);
        T holder = array[nextFirst];
        array[nextFirst] = null;
        size--;
        return holder;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (array.length >= 16 && (double) size < (array.length / 4.0)) {
            resize(array.length / 2);
        }
        nextLast = decrement(nextLast);
        T holder = array[nextLast];
        array[nextLast] = null;
        size--;
        return holder;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size || isEmpty()) {
            return null;
        } else {
            return array[(nextFirst + 1 + index) % array.length];
        }
    }
}
