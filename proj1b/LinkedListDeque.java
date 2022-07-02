public class LinkedListDeque<T> implements Deque<T> {
    private Node sentinel;
    private int size;

    private class Node {
        private Node prev;
        private T item;
        private Node next;

        Node(Node a, T b, Node c) {
            prev = a;
            item = b;
            next = c;
        }
    }

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /*
    public void addFirst(T item){
        sentinel.next = new Node(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size++;
    }
     */

    @Override
    public void addFirst(T item) {
        sentinel.next.prev = new Node(sentinel, item, sentinel.next);
        sentinel.next = sentinel.next.prev;
        size++;
    }

    /*
    public void addLast(T item){
        sentinel.prev = new Node(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size++;
    }
     */

    @Override
    public void addLast(T item) {
        sentinel.prev.next = new Node(sentinel.prev, item, sentinel);
        sentinel.prev = sentinel.prev.next;
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
        sentinel = sentinel.next;
        while (sentinel.item != null) {
            System.out.print(sentinel.item + " ");
            sentinel = sentinel.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        } else {
            Node temp = sentinel.next;
            sentinel.next = temp.next;
            //line 74 and line 76 could switch place, but not the comment line case
            temp.next.prev = sentinel;
            //sentinel.next.prev = sentinel; this case should follow original sequence
            size--;
            return temp.item;
        }
    }

    @Override
    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        } else {
            Node temp = sentinel.prev;
            sentinel.prev = temp.prev;
            temp.prev.next = sentinel; //line 88 and line 89 could switch place
            size--;
            return temp.item;
        }
    }

    @Override
    public T get(int index) {
        Node temp = sentinel.next;
        T holder = null;
        for (int i = 0; i <= index; i++) {
            if (temp.item == null) {
                return null;
            }
            holder = temp.item;
            temp = temp.next;
        }
        return holder;
    }

    private T recHelper(Node node, int index) {
        if (node == sentinel) {
            return null;
        }
        if (index == 0) {
            return node.item;
        }
        return recHelper(node.next, index - 1);
    }

    public T getRecursive(int index) {
        return recHelper(sentinel.next, index);
    }
}
