package edu.exceptions;

/**
 * An exception class to indicate that a user was not found.
 * This exception is typically thrown when attempting to find or access a user,
 * and no user matching the specified criteria is available.
 *
 * <p>
 * This class extends the {@link Exception} class and is intended to be used
 * in scenarios where the absence of a matching user is considered exceptional.
 * </p>
 *
 * <p>
 * The detail message and cause (if available) can be retrieved using the
 * {@link #getMessage()} and {@link #getCause()} methods, respectively.
 * </p>
 *
 * @version 1.0
 * @see Exception
 */
public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = -2414489560888085344L;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public UserNotFoundException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


}
