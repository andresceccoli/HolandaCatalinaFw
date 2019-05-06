package org.hcjf.errors;

import org.hcjf.properties.SystemProperties;
import org.hcjf.utils.Strings;

public class HCJFRuntimeException extends RuntimeException {

    private static final String EXCEPTION_TAG = "SYSTEM_EX";
    private static final String ERROR_TAG = "SYSTEM_ERR";

    public HCJFRuntimeException(String message, Object... params) {
        this(message, null, params);
    }

    public HCJFRuntimeException(String message, Throwable cause, Object... params) {
        super(Strings.createTaggedMessage(String.format(message, params), getTag(null, cause)), cause);
    }

    protected static String getTag(String customTag, Throwable cause) {
        String tag = customTag != null ? customTag : SystemProperties.get(SystemProperties.HCJF_DEFAULT_EXCEPTION_MESSAGE_TAG);
        if(cause != null) {
            if(cause instanceof Error) {
                tag = ERROR_TAG;
            } else if(cause instanceof Exception) {
                tag = EXCEPTION_TAG;
            }
        }
        return tag;
    }
}