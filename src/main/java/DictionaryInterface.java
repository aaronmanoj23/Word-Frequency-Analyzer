import java.util.Iterator;

public interface DictionaryInterface<K, V> {
    V add(K key, V value);
    V getValue(K key);
    Iterator<K> getKeyIterator();
    int getCollisionCount();
}

