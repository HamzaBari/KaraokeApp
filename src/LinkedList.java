
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> implements ListElements<E> {

    private Node<E> firstNode;   //First node in the list.
    private Node<E> lastNode;   //Last node in the list.
    private int sizeOfList; //This will have the current size of the list.

    public LinkedList() {
        this.firstNode = null;
        this.lastNode = null;
        this.sizeOfList = 0;
    }

    //This will be the new Node of this E.
    //Node Inner Class.
    //Generic Node (T Referst to the T in the Node)
    class Node<T> {

        private T data; //Data field of type T.
        private Node<T> next;   //This points to the next node in the list.

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        //Get the data field
        public T getData() {
            return this.data;
        }

        //Set the data field
        public void setData(T data) {
            this.data = data;
        }

        //Get the next value in the List.
        public Node<T> getNext() {
            return this.next;
        }

        //Set the next value in the List
        public void setNext(Node<T> next) {
            this.next = next;
        }

    }

    class LibaryListIterator implements Iterator<E> { //This is a class for Iterator through the list.

        private Node<E> current;    //The current element in the list.
        private Node<E> currentBefore;  //Previous elemnt.
        private Node<E> currentBefore2; //Previous element behiend another previous element.
        private boolean hasBeenRemoved; //Checking if the element in the list has been removed or not.

        public LibaryListIterator() {
            current = firstNode;
            currentBefore = null;
            currentBefore2 = null;
            hasBeenRemoved = false; //The removed list is going to be set to false at the beggining.
        }

        //Does the elemnt have a next element in the List. hasNext will tell you weather there is a next element in the list.
        public boolean hasNext() {
            return current != null; //This will return true if the elemnt in the list is not null. The list will become null if there are no elemnts where this will become false.
        }

        //Next element in the List. Each time you get the next elemebt in the list by iteratoring.
        public E next() {
            if (current == null) {  //If the values in the list are null.
                throw new NoSuchElementException();
            }
            E currentValue = current.getData(); //Saving the element in the varible as current varible will chnage when it will get the next item again. Before goign to the next the data is saved.
            currentBefore2 = currentBefore; //Before elemnt becomes before.
            currentBefore = current; //This is the previous elemnt in the list.           
            current = current.getNext();    //Get the next element in the list.
            hasBeenRemoved = false; //This when when the remove can be called again. This way this can be called again after being true. 
            return currentValue; //Returning data inside of the element. This is where the data is saved.
        }

        //Remove removes the most recent element from the list.
        public void remove() {
            if (currentBefore == null || hasBeenRemoved) {    //This means that the next element has never been called.
                throw new IllegalStateException();
            }
            if (currentBefore2 == null) {    //This means the next has been called once. 
                //The node trying to remove the previous will be the first elemnt in the list. 
                firstNode = current; //we have a new current here in the list as the previous node has been skipped.
            } else {
                currentBefore2.setNext(current); //Were skipping over the node that is tried to be removed.
                currentBefore = currentBefore2;  //Updating the previous node.
            }
            sizeOfList--;   //As elements are being removed from the list therefore, the size of the list is decreasing. 
            hasBeenRemoved = true; //As the element in the list has been removed then this value will become true which means the elemnt in the list has been removed.
        }
    }

    //This returns the current size data member from the top.
    public int size() {
        return this.sizeOfList;
    }

    //Returns ture if the list contians 0 elements.
    public boolean isEmpty() {
        return this.size() == 0;
    }

    public boolean contains(Object o) {
        //Checking every single element in the list.
        for (E element : this) {
            //This expression will return true if the list contains the specfied element.
            if (o == null ? element == null : o.equals(element)) {
                return true;
            }
        }
        return false; //After going through the whole list.
    }

    public Iterator<E> iterator() {
        return new LibaryListIterator(); //Calls the Class. 
    }

    //This method adds the element at the end of the list.
    public boolean add(E e) {
        if (isEmpty()) {  //If the list is empty
            firstNode = new Node(e, null); //This is creating the first node with the first value e, the next value will be null as your adding to the list and the list is empty.
            lastNode = firstNode;   //The last node is equal to the second node.
        } else {    //If the list is not empty
            lastNode.setNext(new Node(e, null)); //This will be the next node after the last/previous Node.
            lastNode = lastNode.getNext(); //This gets the next element in the list.
        }
        sizeOfList++; //This increases the size of the list the number of elements in the list are being added.
        return true;
    }

    //Remove specfied element from the list. 
    public boolean remove(Object o) {
        Iterator<E> i = this.iterator();

        //This loops util the list has a next element.
        while (i.hasNext()) {
            E element = i.next();   //This gets the next element from the list. 

            if (o == null ? element == null : o.equals(element)) {
                i.remove(); //This is called to remove that element from the list. 
                return true; //we have removed the element. 
            }
        }
        return false;   //If the element doesn't remove.
    }

    public void clear() {
        firstNode = null;   //Set the first node in the list to null.
        lastNode = null;    //Set the next node in the list to null.
        sizeOfList = 0; //Set the size of the list to 0.
    }

    public E get(int index) {
        if (index < 0 || index >= size()) { //If the index inputted is less tahn 0 or greate then the actual indexes of the size then it will return an exception.
            throw new IndexOutOfBoundsException();
        }

        if (index < size() - 1) {
            Node<E> current = firstNode;

            //Its going to be looping until it find the node requird in the list.
            for (int j = 0; j < index; j++) {
                current = current.getNext(); //Goign to the next node each time and when the loop ends the current will be the actual node at the index.
            }

            return current.getData(); //This will get the element at that index.
        }

        //Node is at the right end of the list.
        return lastNode.getData();

    }

    public void add(int index, E element) {
        if (index < 0 || index > size()) { //If the index of less then the list element or the index is greater than the size.
            throw new IndexOutOfBoundsException(); //The array list is out of bound, the list is out of size.
        }

        if (index == size()) { //If the index equals the size.
            add(element); //This will add the element at the end of the list.
            return;
        }

        if (index == 0) { //Adding to the beggining to the list which already has elements in it.
            firstNode = new Node(element, firstNode);   //The current first node is going to be the second node inside the list now.
        } else { //Adding an element in the list in the middle of the list Not from the beggining.

            //the current becomes the firtNode.
            Node<E> current = firstNode;

            //Its going to be looping until it find the node requird in the list.
            for (int j = 0; j < index; j++) {
                current = current.getNext();    //Goign to the next node each time and when the loop ends the current will be the actual node at the index og where the new node needs to be put.
            }
            //Current is the node before the index where we need to add to the list.

            current.setNext(new Node(element, current.getNext())); //The pointer is going to be the current next pointer is set to.
        }
        sizeOfList++;   //Increasing the size of the list.
    }

    //Remove a elemnts from a specfied position. 
    public E remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();  //This is if the array is bigger then the size or less then 0 value if given by the user. 
        }

        Iterator<E> i = this.iterator();

        //We wnat to return an index.
        for (int j = 0; j < index; j++) {
            i.next();   //gets the next element.
        }

        E element = i.next();   //This is where we get that element.
        i.remove(); //This is where we remove that element from the list after getting it above.

        return element;
    }

}
