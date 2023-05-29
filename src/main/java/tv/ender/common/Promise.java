package tv.ender.common;

import java.util.concurrent.atomic.AtomicReference;

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
