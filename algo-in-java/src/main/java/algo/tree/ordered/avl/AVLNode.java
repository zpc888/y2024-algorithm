package algo.tree.ordered.avl;

import algo.tree.ordered.BSTNode;

import java.util.function.BiConsumer;

/**
 * source: https://en.wikipedia.org/wiki/AVL_tree
 * @param <K>
 * @param <V>
 */
public class AVLNode<K extends Comparable<K>, V> extends BSTNode<K, V> {
    private int height;

    public AVLNode(K key, V value) {
        this(key, value, null, null);
    }

    public AVLNode(K key, V value, AVLNode<K, V> left, AVLNode<K, V> right) {
        super(key, value, left, right);
        int leftHeight = left == null ? 0 : ((AVLNode<K, V>) left).getHeight();
        int rightHeight = right == null ? 0 : ((AVLNode<K, V>) right).getHeight();
        this.height = 1 + Math.max(leftHeight, rightHeight);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isBalanced() {
        AVLNode<K, V> left = (AVLNode<K, V>) getLeft();
        AVLNode<K, V> right = (AVLNode<K, V>) getRight();
		int leftHeight = left == null ? 0 : left.height;
		int rightHeight = right == null ? 0 : right.height;
		return Math.abs(leftHeight - rightHeight) <= 1
			&& (left == null || left.isBalanced())
			&& (right == null || right.isBalanced());
    }

	public int getActualHeightAndReportMismatched(BiConsumer<AVLNode<K, V>, Integer> wrongHeightAction) {
        AVLNode<K, V> left = (AVLNode<K, V>) getLeft();
        AVLNode<K, V> right = (AVLNode<K, V>) getRight();
        int leftHeight = left == null ? 0 : left.getActualHeightAndReportMismatched(wrongHeightAction);
        int rightHeight = right == null ? 0 : right.getActualHeightAndReportMismatched(wrongHeightAction);
        int actualHeight = 1 + Math.max(leftHeight, rightHeight);
        if (actualHeight != height) {
            wrongHeightAction.accept(this, actualHeight);
        }
        return actualHeight;
	}

    @Override
    protected AVLNode<K, V> newNode(K key, V value) {
        return new AVLNode<>(key, value);
    }

    @Override
    public AVLNode<K, V> add(K key, V value) {
        AVLNode<K, V> newRoot = (AVLNode<K, V>) super.add(key, value);
        AVLNode<K, V> left = (AVLNode<K, V>) newRoot.getLeft();
        AVLNode<K, V> right = (AVLNode<K, V>) newRoot.getRight();
        int leftHeight = left == null ? 0 : left.getHeight();
        int rightHeight = right == null ? 0 : right.getHeight();
        newRoot.setHeight(1 + Math.max(leftHeight, rightHeight));
        return rebalance(newRoot);
    }

    @Override
    public AVLNode<K, V> remove(K key) {
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
                return (AVLNode<K, V>) getRight();      // no re-balance since already balanced
            } else if (getRight() == null) {
                return (AVLNode<K, V>) getLeft();
            } else {
                AVLNode<K, V> min = (AVLNode<K, V>) getRight();
                AVLNode<K, V> parent = null;
                while (min.getLeft() != null) {
                    parent = min;
                    min = (AVLNode<K, V>) min.getLeft();
                }
                if (parent != null) {
                    // right has left child
//                    parent.setLeft(null);     // break the line
                    parent.setLeft(min.getRight());
                    min.setRight(null);       // left is already null
                    parent.fixHeightShadowlyIfNeeded();   // no need to re-balance since it won't break the balance
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

    public AVLNode<K, V> rebalance(AVLNode<K, V> node) {
        AVLNode<K, V> left = (AVLNode<K, V>) node.getLeft();
        AVLNode<K, V> right = (AVLNode<K, V>) node.getRight();
        int leftHeight = left == null ? 0 : left.getHeight();
        int rightHeight = right == null ? 0 : right.getHeight();
        int diff = leftHeight - rightHeight;
        if (diff > 1) {
            // LL or LR violation
            AVLNode<K, V> leftLeft = (AVLNode<K, V>) left.getLeft();
            AVLNode<K, V> leftRight = (AVLNode<K, V>) left.getRight();
            int leftLeftH = leftLeft == null ? 0 : leftLeft.getHeight();
            int leftRightH = leftRight == null ? 0 : leftRight.getHeight();
            if (leftLeftH >= leftRightH) {
                // LL violation
				return rotateRight(node);
            } else {
                // LR violation
				node.setLeft(rotateLeft(left));
				node.fixHeightShadowlyIfNeeded();
				return rotateRight(node);
            }
        } else if (diff < -1) {
            // RR or RL violation
			AVLNode<K, V> rightRight = (AVLNode<K, V>) right.getRight();
			AVLNode<K, V> rightLeft = (AVLNode<K, V>) right.getLeft();
			int rightRightH = rightRight == null ? 0 : rightRight.getHeight();
			int rightLeftH = rightLeft == null ? 0 : rightLeft.getHeight();
			if (rightRightH >= rightLeftH) {
				// RR violation
				return rotateLeft(node);
			} else {
				// RL violation
				node.setRight(rotateRight(right));
				node.fixHeightShadowlyIfNeeded();
				return rotateLeft(node);
			}
        }
        // still balanced, NO LL, LR, RR, RL violation so no need to rotate
        return node;
    }

	public AVLNode<K, V> rotateRight(AVLNode<K, V> curr) {
		AVLNode<K, V> left = (AVLNode<K, V>) curr.getLeft();
		curr.setLeft(left.getRight());
		left.setRight(curr);
		curr.fixHeightShadowlyIfNeeded();
		left.fixHeightShadowlyIfNeeded();
		return left;
	}

	public AVLNode<K, V> rotateLeft(AVLNode<K, V> curr) {
		AVLNode<K, V> right = (AVLNode<K, V>) curr.getRight();
		curr.setRight(right.getLeft());
		right.setLeft(curr);
		curr.fixHeightShadowlyIfNeeded();
		right.fixHeightShadowlyIfNeeded();
		return right;
	}

	private void fixHeightShadowlyIfNeeded() {
        AVLNode<K, V> left = (AVLNode<K, V>) getLeft();
        AVLNode<K, V> right = (AVLNode<K, V>) getRight();
        int leftHeight = left == null ? 0 : left.getHeight();
        int rightHeight = right == null ? 0 : right.getHeight();
		setHeight(Math.max(leftHeight, rightHeight) + 1);
	}


    @Override
    public boolean isValid() {
        return isBalanced() && super.isValid();
    }

    public static <K extends Comparable<K>, V> boolean avlEquals(AVLNode<K, V> n1, AVLNode<K, V> n2) {
        if (n1 == n2) {
            return true;
        }
        if (n1 == null || n2 == null) {
            return false;
        }
        return n1.getKey().equals(n2.getKey())
            && n1.getValue().equals(n2.getValue())
            && n1.getHeight() == n2.getHeight()
            && avlEquals((AVLNode<K, V>) n1.getLeft(), (AVLNode<K, V>) n2.getLeft())
            && avlEquals((AVLNode<K, V>) n1.getRight(), (AVLNode<K, V>) n2.getRight());
    }
}
