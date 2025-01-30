import java.util.Iterator;

import java.util.NoSuchElementException;

public class HashedDictionary <K, V> implements DictionaryInterface<K, V> {
    private static final int DEFAULT_CAPACITY = 11;
    private static final double LOAD_FACTOR = 0.5;

    private Entry<K, V>[] hashTable;
    private int numberOfEntries;
    private int collisionCount;

    private class Entry<S, T> {
        private S key;
        private T value;

        public Entry(S key, T value) {
            this.key = key;
            this.value = value;
        }
    }

    @SuppressWarnings("unchecked")
    public HashedDictionary(int initialCapacity) {
        hashTable = (Entry<K, V>[]) new Entry[initialCapacity];
        numberOfEntries = 0;
        collisionCount = 0;
    }

    @Override
    public V add(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }

        int hashIndex = getHashIndex(key);
        int originalIndex = hashIndex;
        boolean foundSpot = false;
        V oldValue = null;

        while (!foundSpot) {
            Entry<K, V> currentEntry = hashTable[hashIndex];
            
            if (currentEntry == null || currentEntry.key == null) {
                // Empty or previously removed spot found
                hashTable[hashIndex] = new Entry<>(key, value);
                foundSpot = true;
                numberOfEntries++;
            } else if (currentEntry.key.equals(key)) {
                // Key exists, update value
                oldValue = currentEntry.value;
                currentEntry.value = value;
                foundSpot = true;
            } else {
                // Collision detected
                collisionCount++;
                hashIndex = (hashIndex + 1) % hashTable.length; // Linear probing
                if (hashIndex == originalIndex) {
                    throw new IllegalStateException("Hash table is full.");
                }
            }
        }

        return oldValue;
    }

    private int getHashIndex(K key) {
        int hash = key.hashCode();
        return Math.abs(hash % hashTable.length);
    }


    @Override
    public V getValue(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }

        int hashIndex = getHashIndex(key);
        int originalIndex = hashIndex;

        while (hashTable[hashIndex] != null) {
            Entry<K, V> currentEntry = hashTable[hashIndex];
            if (currentEntry.key != null && currentEntry.key.equals(key)) {
                return currentEntry.value; // Found the key, return value
            }
            hashIndex = (hashIndex + 1) % hashTable.length; // Linear probing
            if (hashIndex == originalIndex) {
                break; // Back to starting point, key not found
            }
        }

        return null; // Key not found
    }


	public Iterator<K> getKeyIterator() {
	    return new Iterator<K>() {
	        private int currentIndex = 0;
	        private int elementsReturned = 0;
	        
	        @Override
	        public boolean hasNext() {
	            while (currentIndex < hashTable.length && (hashTable[currentIndex] == null || hashTable[currentIndex].key == null)) {
	                currentIndex++;
	            }
	            return elementsReturned < numberOfEntries;
	        }

	        @Override
	        public K next() {
	            if (!hasNext()) {
	                throw new NoSuchElementException();
	            }
	            K key = hashTable[currentIndex].key;
	            currentIndex++;
	            elementsReturned++;
	            return key;
	}
	    };
	}

	@Override
	public int getCollisionCount() {
		return collisionCount;
	}
}
