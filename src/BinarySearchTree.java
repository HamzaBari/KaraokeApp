
//This data structure has been implemented for the searching of the songs. 
public class BinarySearchTree<K extends Comparable<K>, V> { //generic implemantation of a binary search tree with comapreable class for the key and values.

    //Node inner class
    public class Node {

        private Node left, right;   //These are the children nodes.
        private Node parent;    //This is the parentOfValue node.
        private K key;  //This is the vlaue of the key for the node.
        private V value;    //This is the value which is associated with the key.

        public Node(K key, V value) {   //Contructor to get the key and the value.
            this.key = key; //The key
            this.value = value; //The value
            parent = left = right = empty;  //parent vlaues on the left and right, including the empty nodes. 
        }

        @Override
        public String toString() {
            return "" + value;  //Returns the vlaue in string.
        }

    }

    private Node root;  //Root of the tree
    private Node empty; //Empty node in the tree

    public BinarySearchTree() {
        empty = new Node(null, null);   //sets the empty as null vlaues.
        root = empty;   //if root is empty then the key values will be null.
    }

    //This function checks if the tree is empty or not.
    public boolean treeIsEmpty() {
        return root == empty;   //If the root has nothing and it;s empty then it will return null.
    }

    //This function gets the root of the tree thereofre, if the tree if empty then it will reurn null, but it the root exists then it will return root.
    public Node getRoot() {
        if (root == empty) { //If the root is emtty
            return empty;   //This will return an empty which will just be null. 
        } else {
            return root;    //if the root is not empty then it will return the root value. 
        }
    }

    //Returns the root in string format
    @Override
    public String toString() {
        if (root == empty) {
            return "This value dosen't exist.";
        } else {
            return "" + root;
        }
    }

    //Adding the values into the binary search tree
    public Node set(K key, V value) {
        Node node = new Node(key, value);   //The node gets the key and the vlaues
        Node Value = root;  //This value is the root. 
        Node parentOfValue = empty; //The parent value is going to be empty. Since the firt root won't have a parent. 

        while (Value != empty) {    //while the value given is not empty. 
            parentOfValue = Value;  //Parent of the value would become the actual value. 
            if (key.compareTo(Value.key) < 0) { //if key of value is less than 0 
                Value = Value.left; //if vale key comapred is less then 0 then the value will be placed on the left side of the binary search tree.
            } else {
                Value = Value.right;    //if value key comapred is not less than 0 then the value will be placed on the right side of the binary search tree.
            }
        }

        node.parent = parentOfValue;    //The node parent will be the parent value. 

        if (parentOfValue == empty) {   //if the parent value is empty
            root = node;    //root is assigned to a node. 
        } else {
            //This is where it will add more key to the parent node. key is assigned to a value. This is more like balancing the tree. 
            if (key.compareTo(parentOfValue.key) < 0) { //if the key comapredto the parent vlaue of key is less than 0 
                parentOfValue.left = node; //if key of value comapred is less then 0 then the parent value will be placed on the left side of the binary search tree node.
            } else {
                parentOfValue.right = node; //if key of value comapred is not less then 0 then the parent value will be placed on the right side of the binary search tree node.
            }
        }

        return node;    //This returns the node. 
    }

    //This is the main algorithm uses for searching a song from the libary. This searches for the value by the key. 
    public Node search(K key) {
        Node node = root;   //The node is assigned to the root. 

        //while the node is not empty and the key is comapred to the node key is not equal to 0. 
        while (node != empty && key.compareTo(node.key) != 0) {
            if (key.compareTo(node.key) < 0) {
                node = node.left;   //if the given key matches the node key which is less than 0 then the search element will be from the left side of the tree. 
            } else {
                node = node.right;  //if the given key matches the node key which is not less than 0 then the search element will be from the right side of the tree. 
            }
        }

        if (node == empty) {
            return null;    //If node is empty then it will just return back null which means the node has nothing.
        } else {
            return node;    //This will return back the value which is inside the node. 
        }
    }
    
}
