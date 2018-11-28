package pa3;

import pa2.Link;

/**
 * A Red Black Tree node
 */
public class RBTreeNode {
    public int key;
    public int color; // BLACk is 0; RED is 1
    public RBTreeNode left, right, p;

    private Link url;

    // Constructor
    public RBTreeNode(int key, Link url) {
        this.key = key;
        this.left = null;
        this.right = null;
        this.p = null;
        this.url = url;
        this.color = 1;
    }

    public Link getUrl() {
        return url;
    }
}
