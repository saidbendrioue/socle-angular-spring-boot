/**
 *
 */
package com.bycnit.socle.service.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

/**
 * Exception thrown when there is an authentication problem
 *
 * @author S.BENDRIOUE
 */
public class AuthenticationException extends AbstractThrowableProblem {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    public AuthenticationException(final String message, final StatusType status, final Throwable e) {
        super(ErrorConstants.DEFAULT_TYPE, message, status);

        setStackTrace(e.getStackTrace());
    }

    public AuthenticationException(final String message, final Throwable e) {
        this(message, Status.FORBIDDEN, e);
    }
}
