package std_extend;

import java.util.Objects;

public class Pair<K,V> implements Comparable<Pair<K,V>>{
    private final K key;
    private final V value;

    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }

    public Pair(K key){
        this.key = key;
        this.value = null;
    }

    public K getKey(){
        return key;
    }

    public V getValue(){
        return value;
    }

    public int compareTo(Pair anotherPair) {
        if(anotherPair == null) return -1;
        if (Objects.equals(this.getKey(), anotherPair.getKey())) {
            return this.getValue().toString().compareTo(anotherPair.getValue().toString());
        }
        return this.getKey().toString().compareTo(anotherPair.getKey().toString());
    }
    public int compareTo2(Pair anotherPair) {
        if(anotherPair == null) return -1;
        if (Objects.equals(this.getValue(), anotherPair.getValue())) {
            return this.getKey().toString().compareTo(anotherPair.getKey().toString());
        }
        return this.getValue().toString().compareTo(anotherPair.getValue().toString());
    }

    public String toString() {
        return "(" + key + ", " + value + ")";
    }

    public boolean equals(Object o) {
        if(o == null) return false;
        if(o.getClass() != Pair.class) return false;
        return this.getKey().equals(((Pair<?, ?>) o).getKey()) && this.getValue().equals(((Pair<?, ?>) o).getValue());
    }

}
