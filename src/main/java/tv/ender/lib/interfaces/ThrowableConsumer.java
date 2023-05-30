package tv.ender.lib.interfaces;

@FunctionalInterface
public interface ThrowableConsumer<T extends Throwable, V> {
    void accept(V object) throws T;
}
