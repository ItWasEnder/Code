package tv.ender.lib.common;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Represents a CompletableFuture that can be completed except it's easier to type.
 *
 * @param <T> the type of the promise
 */
public class Promise<T> extends AtomicReference<T> {
    public Promise(T initialValue) {
        super(initialValue);
    }

    public Promise() {
        super();
    }

    public static <T> Promise<T> of(T initialValue) {
        return new Promise<>(initialValue);
    }
}
