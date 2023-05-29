package tv.ender.interfaces;

@FunctionalInterface
public interface ThrowableSupplier<T extends Throwable, V> {
    V get() throws T;
}
