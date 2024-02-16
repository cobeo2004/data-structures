package DataStructures.Interfaces;

@FunctionalInterface
public interface ITestCase<T> {
    boolean test(T e);
}
