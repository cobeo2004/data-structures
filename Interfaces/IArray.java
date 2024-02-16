package DataStructures.Interfaces;

import java.util.Iterator;
import java.util.function.Consumer;

public interface IArray<T> extends Iterable<T> {
    public void push(T val);
    public void push(@SuppressWarnings("unchecked") T... vals);

    public T pop();

    public void set(int index, T val);
    public T get(int index);

    public int size();

    public void unshift(T val);
    public T shift();

    public void insert(int index, T val);
    public T delete(int index);

    public boolean isEmpty();
    public boolean includes(T val);

    public int lastIndexOf(T e);
    public int lastIndexOf(T e, int fromIdx);

    public IArray<T> slice();
    public IArray<T> slice(int start);
    public IArray<T> slice(int start, int end);

    @Deprecated
    public IArray<T> splice(int start);
    @Deprecated
    public IArray<T> splice(int start, int delCount);
    @Deprecated
    public IArray<T> splice(int start, int delCount, @SuppressWarnings("unchecked") T... vals);

    public void sort();
    public void reverse();

    @Override public String toString();

    public T find(ITestCase<T> testCase);
    public int findIndex(ITestCase<T> testCase);
    public T findLast(ITestCase<T> testCase);
    public int findLastIndex(ITestCase<T> testCase);

    default boolean findDoesExists(ITestCase<T> testCase) {
        return find(testCase) != null;
    }

    default boolean indexDoesExists(ITestCase<T> testCase) {
        return findIndex(testCase) != -1;
    }

    default boolean findLastDoesExists(ITestCase<T> testCase) {
        return findLast(testCase) != null;
    }

    default boolean lastIndexDoesExistst(ITestCase<T> testCase) {
        return findLastIndex(testCase) != -1;
    }

    public void forEach(Consumer<? super T> action);
    public Iterator<T> iterator();
    interface ArrIterator<T> extends Iterator<T> {
        public boolean hasNext();
        public T next();
    }


}
