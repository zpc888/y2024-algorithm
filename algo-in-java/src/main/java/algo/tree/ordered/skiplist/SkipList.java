package algo.tree.ordered.skiplist;

import algo.tree.ordered.IOrderedRecord;

import java.util.Random;

/**
 * <p>
 * Source: https://en.wikipedia.org/wiki/Skip_list
 * </p>
 * <p>
 * Although it is not a tree, it is a data structure that can be used to implement
 * an ordered set having O(log n) complexity for insertion, deletion, and search.
 * </p>
 */
public class SkipList<K extends Comparable<K>, V> implements IOrderedRecord<K, V> {
    private static final int MAX_LEVEL = 32;
    private final int maxLevel;
    private final Random random;
    private SkipListNode<K, V> head;
    private int level;
    private int size = 0;

    public SkipList() {
        this(MAX_LEVEL);
    }

    public SkipList(int maxLevel) {
        this.maxLevel = maxLevel;
        this.random = new Random();
        this.level = 0;
        // head contains all forward nodes
        this.head = new SkipListNode<>(null, null, maxLevel);
    }

    @Override
    public boolean add(K key, V value) {
		SkipListNode<K, V> current = head;
        // update[i] is the node that precedes the insertion point at level i 
        SkipListNode<K, V>[] update = new SkipListNode[maxLevel + 1];
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].key.compareTo(key) < 0) {
                current = current.forward[i];
            }
            update[i] = current;
        }
		current = current.forward[0];	// 0-level has all items
		// already existed
		if (current != null && current.key.compareTo(key) == 0) {
			current.value = value;		// just updated to new value, no add
			return false;
		}
		size++;
		// add key, value into a random level, i.e.[0, myLevel] forward add-all
		int newLevel = randomLevel();
		if (newLevel > level) {
			for (int i = level + 1; i <= newLevel; i++) {
				// new increased levels points to head, where aleady contains
				// level + 1 to newLevel forward (actually up to max-level)
				update[i] = head;
			}
			level = newLevel;		// increase to new-level
		}

		SkipListNode<K, V> newNode = new SkipListNode<>(key, value, newLevel);
		for (int i = 0; i <= newLevel; i++) {
			// insert newNode into [0, newLevel] levels
			newNode.forward[i] = update[i].forward[i];
			update[i].forward[i] = newNode;
		}
        return true;
    }

	private int randomLevel() {
		int ret = 0;
		while (ret < maxLevel && random.nextDouble() < 0.5) {
			ret++;
		}
		return ret;
	}

    @Override
    public boolean contains(K key) {
		SkipListNode<K, V> current = getLastLessThanNode(key);
		current = current.forward[0]; 	// level 0 contains all items
		return current != null && current.key.compareTo(key) == 0;
    }

	private SkipListNode<K, V> getLastLessThanNode(K key) {
		SkipListNode<K, V> current = head;
		for (int i = level; i >= 0; i--) {
			while (current.forward[i] != null && current.forward[i].key.compareTo(key) < 0) {
				// lt needs to jump at high level, until it cannot jump
				// fall down to lower level to jump, until it cannot jump
				// loop util reaching level-0
				current = current.forward[i];	
			}
		}
		return current;
	}

    @Override
    public boolean delete(K key) {
		SkipListNode<K, V> current = head;
		SkipListNode<K, V>[] update = new SkipListNode[maxLevel + 1];
		for (int i = level; i >= 0; i--) {
			while (current.forward[i] != null && current.forward[i].key.compareTo(key) < 0) {
				current = current.forward[i];
			}
			update[i] = current;
		}
		current = current.forward[0];
		if (current == null || current.key.compareTo(key) != 0) {
			return false;
		}
		size--;
		for (int i = 0; i <= level; i++) {
			if (update[i].forward[i] != current) {
				break;		// coarse-grained jump at high level already
			}
			// delete current node at level-i
			update[i].forward[i] = current.forward[i];
		}
		// after deletion, decrease the level if no items any more
		while (level > 0 && head.forward[level] == null) {
			level--;
		}
		return true;
    }

    @Override
    public V getValue(K key) {
		SkipListNode<K, V> current = getLastLessThanNode(key);
		current = current.forward[0]; 	// level 0 contains all items
		return current == null ? null : (current.key.compareTo(key) == 0 ? current.value : null);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public K getFloorKey(K key) {
		SkipListNode<K, V> current = getLastLessThanNode(key);
		current = current.forward[0]; 	// level 0 contains all items
        return current == null ? null : current.key;
    }

    @Override
    public K getBelowCeilingKey(K key) {
		SkipListNode<K, V> current = getLastLessThanNode(key);
        return current == head ? null : current.key;
    }

    @Override
    public K getAboveFloorKey(K key) {
		SkipListNode<K, V> current = getLastLessThanNode(key);
		current = current.forward[0]; 	// level 0 contains all items
		if (current == null) {
			return null;
		} else {
			if (current.key.compareTo(key) == 0) {
				return current.forward[0] == null ? null : current.forward[0].key;
			} else {
				return current.key;
			}
		}
    }

    @Override
    public K getCeilingKey(K key) {
		SkipListNode<K, V> current = getLastLessThanNode(key);
		SkipListNode<K, V> l0Fwd = current.forward[0]; 	// level 0 contains all items
        return l0Fwd == null ? (current == head ? null : current.key) : (
				l0Fwd.key.compareTo(key) == 0 ? l0Fwd.key 
				: (current == head ? null : current.key));
    }

    static class SkipListNode<K extends Comparable<K>, V> {
        final K key;
        V value;
        final SkipListNode<K, V>[] forward;

        public SkipListNode(K key, V value, int level) {
            this.key = key;
            this.value = value;
            this.forward = new SkipListNode[level + 1];
        }
    }
}
