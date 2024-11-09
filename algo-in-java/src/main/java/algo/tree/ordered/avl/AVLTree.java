package algo.tree.ordered.avl;

import algo.tree.ordered.IOrderedRecord;

public class AVLTree<K extends Comparable<K>, V> implements IOrderedRecord<K, V> {
    private AVLNode<K, V> root;
    private int size;

    @Override
    public boolean add(K key, V value) {
        if (root == null) {
            root = new AVLNode<>(key, value);
        } else {
            root = root.add(key, value);
        }
        size++;
        return true;
    }

    @Override
    public boolean delete(K key) {
        if (contains(key)) {
            root = root.remove(key);
            size--;
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(K key) {
        if (root == null) {
            return false;
        }
        return root.containsKey(key);
    }

    @Override
    public V getValue(K key) {
        if (root == null) {
            return null;
        }
        return root.getValueByKey(key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public K getFloorKey(K key) {
        return root == null ? null : root.getFloorKey(key);
    }

    @Override
    public K getCeilingKey(K key) {
        return root == null ? null : root.getCeilingKey(key);
    }

    @Override
    public K getAboveFloorKey(K key) {
        return root == null ? null : root.getAboveFloorKey(key);
    }

    @Override
    public K getBelowCeilingKey(K key) {
        return root == null ? null : root.getBelowCeilingKey(key);
    }
}