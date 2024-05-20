
public interface stackIterator<T> { //This is a generic stack iterator.

    //This is an abstract method to check if the stack elements array has the next element. 
    public boolean hasNext();

    //This gets the next elements from the stack array. 
    public T next();
}
