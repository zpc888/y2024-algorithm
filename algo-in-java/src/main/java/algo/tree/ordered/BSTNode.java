package algo.tree.ordered;

import java.util.Optional;

/**
 * Node for Binary Search Tree, which is a binary tree with the following properties:
 * 1. The left subtree of a node contains only nodes with keys less than the node's key.
 * 2. The right subtree of a node contains only nodes with keys greater than the node's key.
 * 3. Both the left and right subtrees must also be binary search trees.
 * <p>
 * <b>Note:</b> No duplicated key is allowed.
 * @param <K> key used to compare
 * @param <V> value stored in the node representing the domain meanings
 */
public class BSTNode<K extends Comparable<K>, V> {
    private BSTNode<K, V> left;
    private BSTNode<K, V> right;
    private final K key;
    private final V value;

    public BSTNode(K key, V value) {
        this(key, value, null, null);
    }

    public BSTNode(K key, V value, BSTNode<K, V> left, BSTNode<K, V> right) {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public BSTNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(BSTNode<K, V> left) {
        this.left = left;
    }

    public BSTNode<K, V> getRight() {
        return right;
    }

    public void setRight(BSTNode<K, V> right) {
        this.right = right;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public K getFloorKey(K key) {
        return doGetFloorKey(key, true);
    }

    private K doGetFloorKey(K key, boolean returnIfEq) {
        K floor = null;
        BSTNode<K, V> curr = this;
        while (curr != null) {
            int comp = key.compareTo(curr.getKey());
            if (comp < 0) {
                floor = curr.getKey();
                curr = curr.left;
            } else if (comp > 0) {
                curr = curr.right;
            } else {    // equals
                if (returnIfEq) {
                    floor = key;
                    break;
                } else {
                    curr = curr.right;
                }
            }
        }
        return floor;
    }

    private K doGetCeilingKey(K key, boolean returnIfEq) {
        K ceiling = null;
        BSTNode<K, V> curr = this;
        while (curr != null) {
            int comp = key.compareTo(curr.getKey());
            if (comp > 0) {
                ceiling = curr.getKey();
                curr = curr.right;
            } else if (comp < 0) {
                curr = curr.left;
            } else {    // equals
                if (returnIfEq) {
                    ceiling = key;
                    break;
                } else {
                    curr = curr.left;
                }
            }
        }
        return ceiling;
    }

    public K getCeilingKey(K key) {
        return doGetCeilingKey(key, true);
    }

    public K getAboveFloorKey(K key) {
        return doGetFloorKey(key, false);
    }

    public K getBelowCeilingKey(K key) {
        return doGetCeilingKey(key, false);
    }

    public boolean containsKey(K key) {
        int comp = key.compareTo(getKey());
        if (comp < 0) {
            if (left != null) {
                return left.containsKey(key);
            }
            return false;
        } else if (comp > 0) {
            if (right != null) {
                return right.containsKey(key);
            }
            return false;
        } else {    // equals
            return true;
        }
    }

    public V getValueByKey(K key) {
        int comp = key.compareTo(getKey());
        if (comp < 0) {
            if (left != null) {
                return left.getValueByKey(key);
            }
            return null;
        } else if (comp > 0) {
            if (right != null) {
                return right.getValueByKey(key);
            }
            return null;
        } else {    // equals
            return value;
        }
    }

    public BSTNode<K, V> remove(K key) {
        int comp = key.compareTo(getKey());
        if (comp < 0) {
            if (left != null) {
                left = left.remove(key);
            }
            return this;
        } else if (comp > 0) {
            if (right != null) {
                right = right.remove(key);
            }
            return this;
        } else {    // equals
            if (left == null) {
                return right;
            } else if (right == null) {
                return left;
            } else {
                BSTNode<K, V> min = right;
                BSTNode<K, V> parent = null;
                while (min.left != null) {
                    parent = min;
                    min = min.left;
                }
                if (parent != null) {
                    // right has left child
                    parent.right = min.right;
                    min.right = null;       // left is already null
                    parent.left = null;     // break the line
                    min.left = left;
                    min.right = right;
                } else {
                    // right has no left child
                    min.left = left;
                }
                left = null;
                right = null;
                return min;
            }
        }
    }

    protected BSTNode<K, V> newNode(K key, V value) {
        return new BSTNode<>(key, value);
    }

    public BSTNode<K, V> add(K key, V value) {
		int comp = key.compareTo(getKey());
		if (comp < 0) {
			if (left == null) {
				left = newNode(key, value);
			} else {
				left = left.add(key, value);
			}
			return this;
		} else if (comp > 0) {
			if (right == null) {
				right = newNode(key, value);
			} else {
				right = right.add(key, value);
			}
			return this;
		} else {	// equals
			throw new IllegalStateException("Duplicate key is not supported. " +
					"Key = " + key);
		}
    }

    public boolean isValid() {
        return isKeyInRange(left, null, key) && isKeyInRange(right, key, null);
    }

    private boolean isKeyInRange(BSTNode<K, V> node, K min, K max) {
        if (node == null) {
            return true;
        }
        K me = node.getKey();
        // not in the range -- BST has no duplicated key
        if ((min != null && me.compareTo(min) <= 0) || (max != null && me.compareTo(max) >= 0)) {
            return false;
        }
        return isKeyInRange(node.left, min, me) && isKeyInRange(node.right, me, max);
    }
}
