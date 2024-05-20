
import java.util.Arrays;

//This data structure has been implemented for the song playlist e.g. to play next songs and remove from the playlist. 
public class Stack<T> implements StackIterable<T> { //implements the iterable interface for the iterator of the stack. //This is a generic implementation of the stack.

    //Data memebers of the stack.
    private T[] stackElements;  //This array contians all the elements inside the stack.
    private int size;   //This contians the current size of the stack.

    //Contructor to construct a new stack e.g. playlist. 
    public Stack() {
        stackElements = (T[]) new Object[1];    //creates an array for the stackElements or you could say array to contian the songs.
        size = 0;   //The values starts from 0.
    }

    //This function checks if the stack is empty or not.
    public boolean isEmpty() {
        return size == 0;
    }

    //This function returns the current size of the stack.
    public int size() {
        return size;
    }

    //This function updates the size of the stack as the new values have been inputted. 
    public void updateStackSize(int updateSize) {
        T[] updatingSize = (T[]) new Object[updateSize];    //Updating the stack vlaues with its new size of array.
        for (int i = 0; i < size(); i++) {  //Gets all the stackElements
            updatingSize[i] = stackElements[i]; //updated size is gettiing exchnaged with the stackElements.
        }
        stackElements = updatingSize;   //Updets the stackElements
    }

    //Adding/pushing items into the stack
    public void push(T element) {
        if (size == stackElements.length) { //if the size of the length of the stack elements 
            updateStackSize(2 * stackElements.length);  //This will update the size by doublling it. it will create more space in the stackElement array.
        }
        stackElements[size] = element;  //The elemnt pushed into the stack will be added to the stackElements array.
        size++; //This will also incrse the size of the stack. 
    }

    //Removing/popping items from the stack
    public T pop() {
        T item = stackElements[--size]; //This will remove the item from the top of the stackElements array and minus the size.
        stackElements[size] = null; //The size will become null if all the values have been popped out of the stackElements.
        if (size > 0) { //if the size is greater than 0
            updateStackSize(stackElements.length / 1);  //this will update the stackElement size by divinding it by one and it will reduce the stackElements array size since itmes have been removed.
        } else if (size == stackElements.length / 4) {  //if the size equals the stackelements then it iwll divide by 4.
            updateStackSize(stackElements.length / 1);  //it will ipdate the size by dividing it by 1 and it will reducse the size as elelemts have bee removed, and this it what it will update.
        }
        return item;    //This will return the item which has been popped ou of the stackElements array.
    }

    //This method will return the next element in the stackElements array.
    public T peek() {
        return stackElements[size];
    }

    //Converts the stack into a string.
    public String toStringArray() {
        String toArray = Arrays.toString(stackElements);
        return toArray;
    }

    //Returns the stackElements in array. 
    @Override
    public String toString() {
        return "" + toStringArray();
    }

    //Retuns the current vlaues in the stackElements array into strings values. 
    public String viewAll() {
        return Arrays.deepToString(stackElements).replaceAll("\\[", " ").replaceAll("]", "").replaceAll(",", "\n").replaceAll("null", ""); //Regex has been used for better format.
    }

    //This function is used for removing the element from the stackElements array. 
    public Object remove(Object obj) {
        if (obj == null) {  //if the object is null.
            return obj; //this will just rerun the object back as null.
        }

        for (int i = 0; i < size; i++) {    //gets all the elements in the stackElements array.
            if (obj.equals(stackElements[i])) { //if the object is equal to the stackElements 
                stackElements[i] = stackElements[--size];   //this removes the element from the stackElemnts array and the size is reduced.
                break;  //Once the element has been found it will break from the for loop to progress foward. 
            }
        }
        return obj; //This will return the current object which had been deleted or null. 
    }

    //This function will search the element in the stackElemments array and then it will remove it from the stack array.
    public boolean searchAndRemove(T value, Stack<String> stack) { //this function takes a value and the stack. 
        boolean foundStatus = false;    //checking if the stack is found or not which starts from false. 
        for (int i = 0; i < size; i++) {    //gets all the elements in the array.
            T element = stackElements[i];   //the stackelemnts are assigned to the generic T element
            if (element.equals(value)) {    //if the element equals the value given.
                foundStatus = true; //This will turn the status to true as its been found.
                remove(value);  //This will delete that value from the stackElemnts array once it has been searched. 
            }
        }

        return foundStatus; //This will return the status true or false depending on the result. 
    }

    //This function will add the elemnt at the bottom of the stack. 
    public static void addToBottomOfTheStack(Stack<String> stack, String element) {
        if (stack.isEmpty()) {  //if the stack is empty
            stack.push(element);    //pushing the element into the stack.
            return;
        }
        String newElement = stack.pop();    //This will pop the values from rhe stack 
        addToBottomOfTheStack(stack, element);  //calling the function recursively.
        stack.push(newElement); //push the new element which has been popped at the bottom of the stack.
    }

    //This function is used for reversing the stack values so the values which are at the bottom of the stack will be added on top as it will turn the array around.
    public void reverseStack(Stack<String> stack) {
        if (!stack.isEmpty()) { //if the stack is not empty

            String element = stack.pop();   //this will pop the element from the stack. 
            reverseStack(stack);    //this is recursively reversing the stack. 

            addToBottomOfTheStack(stack, element);  //This is adding the popped out value from the stack. 
        }
    }

    //This function returns the new stack iterator to iterate thorugh the stack. 
    public stackIterator<T> iterator() {
        return new StackIterator();
    }

    class StackIterator implements stackIterator<T> {   //This implements the iterator from the stack iterator interface. 

        private int i = size;   //This is an integer value of the size of the stack. 

        public boolean hasNext() {  //This function checks if the value has the the next value inside it. 
            return i > 0;
        }

        public T next() {   //This returns the next element in the stackElements array. 
            return stackElements[--i];  //This is substracting the stackElements array size. 
        }
    }

}
