package tests;

import org.junit.jupiter.api.Test;
import tv.ender.lib.exceptions.EscapedException;
import tv.ender.lib.exceptions.Handle;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class HandlerTest {

    @Test
    public void testSupplierSimple() {
        try {
            Handle.throwable(() -> {
                if (true) {
                    throw new TestException("Test");
                }

                return null;
            });
        } catch (Throwable t) {
            fail();
        }
    }

    @Test
    public void testSupplierHandleException() {
        Handle.throwable(() -> {
            if (true) {
                throw new TestException("Test");
            }

            return null;
        }, (t) -> {
            assertSame(TestException.class, t.getClass());
        });
    }

    @Test
    public void testSupplierHandleFinally() {
        AtomicBoolean bool = new AtomicBoolean(false);

        Handle.throwable(() -> {
            if (true) {
                throw new TestException("Test");
            }

            return null;
        }, (t) -> {
            assertSame(TestException.class, t.getClass());
        }, () -> {
            bool.set(true);
        });

        assertTrue(bool.get());
    }

    @Test
    public void testRunnableSimple() {
        try {
            Handle.throwable(() -> {
                if (true) {
                    throw new TestException("Test");
                }
            });
        } catch (Throwable t) {
            fail();
        }
    }

    @Test
    public void testRunnableHandleException() {
        Handle.throwable(() -> {
            if (true) {
                throw new TestException("Test");
            }
        }, (t) -> {
            assertSame(TestException.class, t.escapedClass());
            assertSame(EscapedException.class, t.getClass());
        });
    }

    @Test
    public void testRunnableHandleFinally() {
        AtomicBoolean bool = new AtomicBoolean(false);

        Handle.throwable(() -> {
            if (true) {
                throw new TestException("Test");
            }
        }, (t) -> {
            assertSame(TestException.class, t.escapedClass());
            assertSame(EscapedException.class, t.getClass());
        }, () -> {
            bool.set(true);
        });

        assertTrue(bool.get());
    }

    private static class TestException extends Exception {
        public TestException(String message) {
            super(message);
        }
    }
}
