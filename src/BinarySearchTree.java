
public class BinarySearchTree<K extends Comparable<K>, V> {

    public class Node {

        private Node left, right;   //These are the children nodes.
        private Node parent;    //This is the parentOfValue node.
        private K key;  //This is the vlaue of the key for the node.
        private V value;    //This is the value which is associated with the key.

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            parent = left = right = empty;
        }

        @Override
        public String toString() {
            return "" + value;
        }

    }

    private Node root;
    private Node empty;

    public BinarySearchTree() {
        empty = new Node(null, null);
        root = empty;
    }

    public boolean treeIsEmpty() {
        return root == empty;
    }

    public Node getRoot() {
        if (root == empty) {
            return empty;
        } else {
            return root;
        }
    }

    public String toString() {
        if (root == empty) {
            return "This value dosen't exist.";
        } else {
            return "" + root;
        }
    }

    public Node set(K key, V value) {
        Node node = new Node(key, value);
        Node Value = root;
        Node parentOfValue = empty;

        while (Value != empty) {
            parentOfValue = Value;

            if (key.compareTo(Value.key) < 0) {
                Value = Value.left;
            } else {
                Value = Value.right;
            }
        }

        node.parent = parentOfValue;

        if (parentOfValue == empty) {
            root = node;
        } else {
            if (key.compareTo(parentOfValue.key) < 0) {
                parentOfValue.left = node;
            } else {
                parentOfValue.right = node;
            }
        }

        return node;
    }
    

    public Node search(K key) {
        Node node = root;

        while (node != empty && key.compareTo(node.key) != 0) {
            if (key.compareTo(node.key) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        if (node == empty) {
            return null;
        } else {
            return node;
        }
    }
    
    public Node searching(V value) {
        if(root == null) {
            return null;
        }
        
        if (root.key == value) {
            return root;
        }
        
        if (root.key > value) {
        
        } 
    
    }

}
