package timeout.slang.com.reusable;

/**
 * Like a Runnable but callers pass in data
 */
public interface Func<T> {
    public void call(T data);
}
