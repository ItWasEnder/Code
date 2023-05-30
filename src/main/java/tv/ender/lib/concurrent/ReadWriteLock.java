package tv.ender.lib.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public class ReadWriteLock {
    private final java.util.concurrent.locks.ReadWriteLock lock = new ReentrantReadWriteLock();

    public Lock getReadLock() {
        return this.lock.readLock();
    }

    public Lock getWriteLock() {
        return this.lock.writeLock();
    }

    public void readLock() {
        this.lock.readLock().lock();
    }

    public void readUnlock() {
        this.lock.readLock().unlock();
    }

    public void writeLock() {
        this.lock.writeLock().lock();
    }

    public void writeUnlock() {
        this.lock.writeLock().unlock();
    }

    /* runnable injects */
    public void read(Runnable runnable) {
        this.readLock();
        try {
            runnable.run();
        } finally {
            this.readUnlock();
        }
    }

    public <T> T read(Supplier<T> supplier) {
        this.readLock();
        try {
            return supplier.get();
        } finally {
            this.readUnlock();
        }
    }

    public void write(Runnable runnable) {
        this.writeLock();
        try {
            runnable.run();
        } finally {
            this.writeUnlock();
        }
    }

    public <T> T write(Supplier<T> supplier) {
        this.writeLock();
        try {
            return supplier.get();
        } finally {
            this.writeUnlock();
        }
    }
}