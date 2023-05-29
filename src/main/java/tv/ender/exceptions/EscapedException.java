package tv.ender.exceptions;

public class EscapedException extends RuntimeException {
    public EscapedException(Throwable t) {
        super(t);
    }

    public <T> Class<T> escapedClass() {
        return (Class<T>) this.getCause().getClass();
    }
}
