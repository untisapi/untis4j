package org.bytedream.untis4j;

import java.io.IOException;

/**
 * Exception if an error occurs while trying to log in
 *
 * @version 1.0
 * @since 1.0
 */
public class LoginException extends IOException {

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }

}
