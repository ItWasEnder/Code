package tv.ender.lib.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Result<T> {
    private final boolean successful;
    private final String message;
    private final T holder;

    public static <T> Result<T> fail(String message) {
        return new Result<>(false, message, null);
    }

    public static <T> Result<T> fail(T holder, String message) {
        return new Result<>(false, message, holder);
    }

    public static <T> Result<T> pass(T holder, String message) {
        return new Result<>(true, message, holder);
    }
}