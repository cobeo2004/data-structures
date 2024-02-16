package DataStructures.Implementations;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import DataStructures.Interfaces.IArray;
import DataStructures.Interfaces.ITestCase;


@SuppressWarnings("unchecked")
public class Array<T> implements IArray<T>{

    private transient T[] arr;
    private transient int size;

    public Array(Integer length) {
        if(length == null || length == 0)
            this.arr = (T[]) new Object[1];
        else
            this.arr = (T[]) new Object[length];

        this.size = 0;
    }

    public Array(T... vals) {
        this.arr = (T[]) new Object[vals.length];
        for (int i = 0; i < vals.length; i++) {
            this.arr[i] = vals[i];
        }
        this.size = vals.length;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T get(int index) {
        if(index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException("Inputted index out of bounds");
        return (T) this.arr[index];
    }

    @Override
    public void set(int index, T val)  {
        if(index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException("Inputted index out of bounds");
        this.arr[index] = val;
    }

    @Override
    public void push(T val) {
        if(this.size == this.arr.length)
            this.arr = Arrays.copyOf(this.arr, this.arr.length * 2);

        this.arr[this.size++] = val;
    }

    @Override
    public void push(T... vals) {
        for(T v: vals) {
            if(this.size == this.arr.length)
                this.arr = Arrays.copyOf(this.arr, this.arr.length * 2);

            this.arr[this.size++] = v;
        }
    }

    @Override
    public T pop() {
        if(this.size == 0)
            throw new IllegalStateException("Array is empty");

        T e = (T) this.arr[this.size - 1];
        this.arr[this.size - 1] = null;
        this.size--;
        return e;
    }

    @Override
    public void unshift(T val) {
        if(this.size == this.arr.length)
            this.arr = Arrays.copyOf(this.arr, this.arr.length * 2);

        this.arr[0] = val;
        this.size++;
    }

    @Override
    public T shift() {
        if(this.size == 0)
            throw new IllegalStateException("Array is empty");

            T e = (T) this.arr[0];
            System.arraycopy(this.arr, 1, this.arr, 0, this.size - 1);
            this.arr[this.size - 1] = null;
            this.size--;
            return e;
    }

    @Override
    public void insert(int index, T val) {
        if(index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException("Index out of bounds");

        if(this.size == this.arr.length)
            this.arr = Arrays.copyOf(this.arr, this.arr.length * 2);

        System.arraycopy(this.arr, index, this.arr, index + 1, this.size - index);
        this.arr[index] = val;
        this.size++;
    }

    @Override
    public T delete(int index) {
        if(index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException("Index out of bounds");

        T e = this.arr[index];
        System.arraycopy(this.arr, index + 1, this.arr, index, this.size - index - 1);
        this.arr[this.size - 1] = null;
        this.size--;
        return e;
    }

    @Override
    public int lastIndexOf(T e) {
        return lastIndexOf(e, this.size - 1);
    }

    @Override
    public int lastIndexOf(T e, int fromIdx) {
        if(fromIdx > this.size || fromIdx < 0)
            throw new IndexOutOfBoundsException("Inputted index out of bounds");

        for(int i = fromIdx; i >= 0; i--) {
            if(this.arr[i].equals(e))
                return i;
        }
        return -1;
    }

    @Override
    public Array<T> slice() {
        return this.slice(0, this.size - 1);
    }

    @Override
    public Array<T> slice(int start) {
        return this.slice(start, this.size - 1);
    }

    @Override
    public Array<T> slice(int start, int end) {
        if(start < 0)
            start = Math.max(start + this.size, 0);
        if(end < 0)
            end = Math.max(this.size + end, 0);

        end = Math.min(end, this.size);

        int nSize = Math.max(end - start, 0);
        Object[] nArr = new Object[nSize];
        System.arraycopy(this.arr, start, nArr, 0, nSize);
        Array<T> arr = new Array<>();
        arr.arr = (T[]) nArr;
        arr.size = nSize;
        return arr;
    }

    @Override
    @Deprecated
    public Array<T> splice(int start) {
        return this.splice(start, this.size - start);
    }

    @Override
    @Deprecated
    public Array<T> splice(int start, int delCount) {
        return this.splice(start, delCount, (T[]) new Object[0]);
    }

    @Override
    @Deprecated
    public Array<T> splice(int start, int deleteCount, T... vals) {
        //TODO implement this function!
        throw new UnsupportedOperationException("This function is not yet implemented");
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean includes(T val) {
        for (T t : arr) {
            if(t.equals(val))
                return true;
        }
        return false;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        for (int i = 0; i < this.arr.length; i++) {
            if(this.arr[i] == null)
                continue;
            action.accept(arr[i]);
        }
    }

    @Override
    public String toString() {
        if(this.size == 0)
            return "[]";
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < this.size - 1; i++) {
            builder.append(this.arr[i]).append(", ");
        }
        builder.append(this.arr[this.size - 1]).append("]");

        return builder.toString();
    }


    @Override
    public void sort() {
        _quickSort(this.arr, 0, this.size - 1);
    }

    private void _quickSort(T[] arr, int low, int high) {
        if(low < high) {
            int parIndex = this._partition(low, high);
            this._quickSort(arr, low, parIndex - 1);
            this._quickSort(arr, parIndex + 1, high);
        }
    }

    private int _partition(int low, int high) {
        T pivot = this.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if(((Comparable<T>) this.get(j)).compareTo(pivot) <= 0) {
                i++;
                this._swap(i, j);
            }
        }
        this._swap(i + 1, high);
        return i + 1;
    }

    private void _swap(int fIndex, int sIndex) {
        T temp = this.get(fIndex);
        this.set(fIndex, this.get(sIndex));
        this.set(sIndex, temp);
    }

    @Override
    public void reverse() {
        for (int i = 0; i < this.size / 2; i++) {
            T t = this.get(i);
            set(i, get(this.size - i - 1));
            set(this.size - i - 1, t);
        }
    }

    @Override
    public T find(ITestCase<T> testCase) {
        for (int i = 0; i < this.size; i++) {
            T e = this.arr[i];
            if(testCase.test(e))
                return e;
        }
        return null;
    }

    @Override
    public int findIndex(ITestCase<T> testCase) {
        for (int i = 0; i < this.size; i++) {
            T e = this.arr[i];
            if(testCase.test(e))
                return i;
        }
        return -1;
    }

    @Override
    public T findLast(ITestCase<T> testCase) {
        for (int i = this.size - 1; i >= 0; i--) {
            T e = this.arr[i];
            if(testCase.test(e))
                return e;
        }
        return null;
    }

    @Override
    public int findLastIndex(ITestCase<T> testCase) {
        for (int i = this.size - 1; i >= 0; i--) {
            T e = this.arr[i];
            if(testCase.test(e))
                return i;
        }
        return -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrIterator();
    }

    private class ArrIterator implements IArray.ArrIterator<T> {
        private int currentIndex = 0;
        @Override
        public boolean hasNext() {
            while(currentIndex < size && arr[currentIndex] == null)
                currentIndex++;
            return currentIndex < size;
        }

        @Override
        public T next() {
            if(!this.hasNext())
                throw new NoSuchElementException();
            return arr[this.currentIndex++];
        }

    }

}
