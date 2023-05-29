package tv.ender.interfaces;

@FunctionalInterface
public interface ThrowableRunnable<T extends Throwable> {
    void run() throws T;
}
