package hashing;

import java.util.BitSet;

public interface IData<T> extends IRecord<T>{
     public BitSet getHash();
     public boolean myEquals(T data);
     public T createClass();
}
