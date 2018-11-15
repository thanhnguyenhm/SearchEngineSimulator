package pa2;

/**
 * A Binary Search Tree node
 */
public class Node {
    int key;
    Node left, right, p;

    private Link url;

    // Constructor
    public Node(int key, Link url) {
        this.key = key;
        this.left = null;
        this.right = null;
        this.p = null;
        this.url = url;
    }

    public Link getUrl() {
        return url;
    }
}
