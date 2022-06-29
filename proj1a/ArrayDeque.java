public class ArrayDeque<T>{
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] array;

    public ArrayDeque(){
       array = (T[]) new Object[8];
       size = 0;
       nextFirst = 3;
       nextLast = 4;
    }

    private void resize(){
        if (nextFirst == nextLast){
            T[] temp = (T[]) new Object[array.length*2];
            System.arraycopy(array,0,temp,0,nextLast);
            System.arraycopy(array,nextFirst+1,temp,nextFirst+array.length+1,size-nextFirst);
            nextFirst = nextFirst + array.length;
            array = temp;
        }
        else if (array.length >= 16){
            if((double) size < (array.length/4.0)){
                T[] temp = (T[]) new Object[array.length/2];
                System.arraycopy(array,0,temp,0,nextLast);
                System.arraycopy(array,nextFirst+1,temp,nextFirst-(array.length+1)/2+1,array.length-nextFirst-1);
                nextFirst = nextFirst-(array.length+1)/2;
                array = temp;
            }
            else return;
        }
        else return;
    }

    private int increment(int value){
        return (value+1)%array.length;
    }

    private int decrement(int value){
        return (value-1+array.length)%array.length;
    }

    public void addFirst(T item){
        resize();
        array[nextFirst] = item;
        nextFirst = decrement(nextFirst);
        size++;
    }

    public void addLast(T item){
        resize();
        array[nextLast] = item;
        nextLast = increment(nextLast);
        size++;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        if (nextLast > nextFirst){
            for (int i = nextFirst+1; i < nextLast; i++){
                System.out.print(array[i] + " ");
            }
            System.out.println();
        }
        else {
            for (int i = nextFirst+1; i < array.length; i++){
                System.out.print(array[i] + " ");
            }
            for (int i = 0; i < nextLast; i++){
                System.out.print(array[i] + " ");
            }
            System.out.println();
        }
    }

    public T removeFirst(){
        if(size == 0) return null;
        resize();
        nextFirst = increment(nextFirst);
        size--;
        return array[nextFirst];
    }

    public T removeLast(){
        if(size == 0) return null;
        resize();
        nextLast = decrement(nextLast);
        size--;
        return array[nextLast];
    }

    public T get(int index){
        if (index >= size) return null;
        else return array[(nextFirst+1+index)%array.length];
    }

    /*
    public static void main (String args[]){
        ArrayDeque<Integer> example = new ArrayDeque<Integer>();
        example.addFirst(0)
        example.addFirst(1)
        example.size()
        example.addLast(3)
        example.size()
        example.addLast(5)
        example.size()
        example.addFirst(7)
        example.addFirst(8)
        example.addFirst(9)
    }

     */
}