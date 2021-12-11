package stanhebben.zenscript;

import stanhebben.zenscript.util.ZenPosition;

/**
 * Error logger. Implementations can forward errors to their own error logging
 * system.
 *
 * @author Stan Hebben
 */
public interface IZenErrorLogger extends IZenLogger {
    
    /**
     * Called when an error is detected during compilation.
     *
     * @param position error position
     * @param message  error message
     */
    void error(ZenPosition position, String message);
    
    /**
     * Called when a warning is generated during compilation.
     *
     * @param position warning position
     * @param message  warning message
     */
    void warning(ZenPosition position, String message);
    
    /**
     * Called to generate a info during compilation.
     *
     * @param position info position
     * @param message  info message
     */
    void info(ZenPosition position, String message);

    default void error(ZenPosition start, ZenPosition end, String message) {
        error(start, message);
    }

    default void warning(ZenPosition start, ZenPosition end, String message) {
        warning(start, message);
    }

    default void info(ZenPosition start, ZenPosition end, String message) {
        info(start, message);
    }

}
