
import java.util.Arrays;

public class Stack<T> implements StackIterable<T> {

    private T[] stackElements;
    private int size;

    public Stack() {
        stackElements = (T[]) new Object[1];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void updateStackSize(int updateSize) {
        T[] updatingSize = (T[]) new Object[updateSize];
        for (int i = 0; i < size(); i++) {
            updatingSize[i] = stackElements[i];
        }
        stackElements = updatingSize;
    }

    public void push(T element) {
        if (size == stackElements.length) {
            updateStackSize(2 * stackElements.length);
        }
        stackElements[size] = element;
        size++;
    }

    public T pop() {
        T item = stackElements[--size];
        stackElements[size] = null;
        if (size > 0) {
            updateStackSize(stackElements.length / 1);
        } else if (size == stackElements.length / 4) {
            updateStackSize(stackElements.length / 1);
        }
        return item;
    }

    public String toArray() {
        String toArray = Arrays.toString(stackElements).replaceAll("]", " ").replaceAll(",", "\n");
        return toArray;
    }

    @Override
    public String toString() {
        String toArray = Arrays.toString(stackElements).replaceAll("]", " ").replaceAll(",", "\n");
        return toArray.toString();
    }

    public void viewAll() {
        for (int i = 0; i < size; i++) {
            System.out.println(stackElements[i] + "\n");
        }
    }
    
    public String viewAllSongs() {
        for (int i = 0; i < size; i++) {
            String x = stackElements[i] + "\n";
            System.out.println(x + "ggg");
        }
        return "";
    }

    public Object remove(Object obj) {
        if (obj == null) {
            return obj;
        }

        for (int i = 0; i < size; i++) {
            if (obj.equals(stackElements[i])) {
                stackElements[i] = stackElements[--size];
                break;
            }
        }
        return obj;
    }

    public boolean searchAndRemove(T value, Stack<String> stack) {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            T element = stackElements[i];
            if (element.equals(value)) {
                found = true;
                System.out.println("Deleted");
                remove(value);
            }
        }
        System.out.println("Not-Found");
        return found;

    }

    public stackIterator<T> iterator() {
        return new StackIterator();
    }

    public void reverseStack(Stack<String> stack) {

        if (!stack.isEmpty()) {

            String element = stack.pop();
            reverseStack(stack);

            addToBottomOfTheStack(stack, element);
        }
    }

    public static void addToBottomOfTheStack(Stack<String> stack, String element) {
        if (stack.isEmpty()) {
            stack.push(element);
            return;
        }
        String newElement = stack.pop();
        addToBottomOfTheStack(stack, element);
        stack.push(newElement);
    }

    class StackIterator implements stackIterator<T> {

        private int i = size;

        public boolean hasNext() {
            return i > 0;
        }

        public T next() {
            return stackElements[--i];
        }
    }

}
