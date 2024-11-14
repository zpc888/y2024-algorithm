package algo.tree.ordered.sb;

import algo.tree.ordered.BSTNode;

/**
 * Source: https://wcipeg.com/wiki/Size_Balanced_Tree
 * <p>
 *     The balance factor is the size of nephew subtree, i.e.
 *     <ul>
 *         <li>size(T.left)  >= size(T.right.left), size(T.right.right)</li>
 *         <li>size(T.right) >= size(T.left.right), size(T.left.left)</li>
 *     </ul>
 * </p>
 * <p>
 *     While AVL balance factor is the height of sibling subtree, i.e.
 *     <ul>
 *         <li>-1 <= height(T.left) - height(T.right) <= 1</li>
 *     </ul>
 * </p>
 */
public class SBNode<K extends Comparable<K>, V> extends BSTNode<K, V> {
    private int size;

    public SBNode(K key, V value) {
        this(key, value, null, null);
    }

    public SBNode(K key, V value, SBNode<K, V> left, SBNode<K, V> right) {
        super(key, value, left, right);
        int leftSize = left == null ? 0 : ((SBNode<K, V>) left).getSize();
        int rightSize = right == null ? 0 : ((SBNode<K, V>) right).getSize();
        this.size = 1 + leftSize + rightSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isBalanced() {
        int leftSize = 0, rightLeftSize = 0, rightRightSize = 0;
        int rightSize = 0, leftRightSize = 0, leftLeftSize = 0;

        SBNode<K, V> left = (SBNode<K, V>) getLeft();
        SBNode<K, V> right = (SBNode<K, V>) getRight();
		if (left != null) {
			leftSize = left.getSize();
			leftLeftSize = left.getLeft() == null ? 0 : ((SBNode<K, V>) left.getLeft()).getSize();
			leftRightSize = left.getRight() == null ? 0 : ((SBNode<K, V>) left.getRight()).getSize();
		}
		
		if (right != null) {
			rightSize = right.getSize();
			rightRightSize = right.getRight() == null ? 0 : ((SBNode<K, V>) right.getRight()).getSize();
			rightLeftSize = right.getLeft() == null ? 0 : ((SBNode<K, V>) right.getLeft()).getSize();
		}

		return leftSize >= rightLeftSize && leftSize >= rightRightSize
			&& rightSize >= leftRightSize && rightSize >= leftLeftSize;
    }

    /*
                         50                      30
                       /    \                  /    \
                     30      70              20      50
                    /  \    /               / \     / \
                   20  40  60              10  25  40  70
                  / \                                  /
                10   25                               60

     */
    public SBNode<K, V> rotateRight(SBNode<K, V> node) {
        SBNode<K, V> left = (SBNode<K, V>) node.getLeft();
        node.setLeft(left.getRight());
        left.setRight(node);
        node.fixSizeShadowlyIfNeeded();
        left.fixSizeShadowlyIfNeeded();
        return left;
    }

    public SBNode<K, V> rotateLeft(SBNode<K, V> curr) {
        SBNode<K, V> right = (SBNode<K, V>) curr.getRight();
        curr.setRight(right.getLeft());
        right.setLeft(curr);
        curr.fixSizeShadowlyIfNeeded();
        right.fixSizeShadowlyIfNeeded();
        return right;
    }

    @Override
    protected SBNode<K, V> newNode(K key, V value) {
        return new SBNode<>(key, value);
    }

    @Override
    public SBNode<K, V> add(K key, V value) {
        SBNode<K, V> newRoot = (SBNode<K, V>) super.add(key, value);
        SBNode<K, V> left = (SBNode<K, V>) newRoot.getLeft();
        SBNode<K, V> right = (SBNode<K, V>) newRoot.getRight();
        int leftHeight = left == null ? 0 : left.getSize();
        int rightHeight = right == null ? 0 : right.getSize();
        newRoot.setSize(1 + leftHeight + rightHeight);
        return rebalance(newRoot);
    }

    // The reference suggests not to re-balance the tree after deletion, only re-balance when adding
    @Override
    public SBNode<K, V> remove(K key) {
        int comp = key.compareTo(getKey());
        if (comp < 0) {
            if (getLeft() != null) {
                setLeft(getLeft().remove(key));
            }
            return rebalance(this);
        } else if (comp > 0) {
            if (getRight() != null) {
                setRight(getRight().remove(key));
            }
            return rebalance(this);
        } else {
            // found the key
            if (getLeft() == null) {
                return (SBNode<K, V>) getRight();      // no re-balance since already balanced
            } else if (getRight() == null) {
                return (SBNode<K, V>) getLeft();
            } else {
                SBNode<K, V> min = (SBNode<K, V>) getRight();
                SBNode<K, V> parent = null;
                while (min.getLeft() != null) {
                    parent = min;
                    min = (SBNode<K, V>) min.getLeft();
                }
                if (parent != null) {
                    // right has left child
                    parent.setLeft(null);     // break the line
                    parent.setLeft(min.getRight());
                    min.setRight(null);       // left is already null
                    parent.fixSizeShadowlyIfNeeded();   // no need to re-balance since it won't break the balance
                    min.setLeft(getLeft());
                    min.setRight(getRight());
                } else {
                    // right has no left child
                    min.setLeft(getLeft());
                }
                setLeft(null);
                setRight(null);
                return rebalance(min);
            }
        }
    }

    public SBNode<K, V> rebalance(SBNode<K, V> node) {
        int leftSize = 0, rightLeftSize = 0, rightRightSize = 0;
        int rightSize = 0, leftRightSize = 0, leftLeftSize = 0;

        SBNode<K, V> left = (SBNode<K, V>) getLeft();
        SBNode<K, V> right = (SBNode<K, V>) getRight();
        if (left != null) {
            leftSize = left.getSize();
            leftLeftSize = left.getLeft() == null ? 0 : ((SBNode<K, V>) left.getLeft()).getSize();
            leftRightSize = left.getRight() == null ? 0 : ((SBNode<K, V>) left.getRight()).getSize();
        }

        if (right != null) {
            rightSize = right.getSize();
            rightRightSize = right.getRight() == null ? 0 : ((SBNode<K, V>) right.getRight()).getSize();
            rightLeftSize = right.getLeft() == null ? 0 : ((SBNode<K, V>) right.getLeft()).getSize();
        }

        if (leftLeftSize > rightSize) {
            // LL violation
            return rotateRight(node);
        }
        if (leftRightSize > rightSize) {
            // LR violation
            node.setLeft(rotateLeft(left));
            node.fixSizeShadowlyIfNeeded();
            return rotateRight(node);
        }
        if (rightRightSize > leftSize) {
            // RR violation
            return rotateLeft(node);
        }
        if (rightLeftSize > leftSize) {
            // RL violation
            node.setRight(rotateRight(right));
            node.fixSizeShadowlyIfNeeded();
            return rotateLeft(node);
        }
        return node;
    }

    private void fixSizeShadowlyIfNeeded() {
        SBNode<K, V> left = (SBNode<K, V>) getLeft();
        SBNode<K, V> right = (SBNode<K, V>) getRight();
        int leftSize = left == null ? 0 : left.getSize();
        int rightSize = right == null ? 0 : right.getSize();
        setSize(1 + leftSize + rightSize);
    }

    public static <K extends Comparable<K>, V> boolean sbEquals(SBNode<K, V> n1, SBNode<K, V> n2) {
        if (n1 == n2) {
            return true;
        }
        if (n1 == null || n2 == null) {
            return false;
        }
        return n1.getKey().equals(n2.getKey())
                && n1.getValue().equals(n2.getValue())
                && n1.getSize() == n2.getSize()
                && sbEquals((SBNode<K, V>) n1.getLeft(), (SBNode<K, V>) n2.getLeft())
                && sbEquals((SBNode<K, V>) n1.getRight(), (SBNode<K, V>) n2.getRight());
    }
}
