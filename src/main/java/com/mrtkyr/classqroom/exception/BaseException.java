package com.mrtkyr.classqroom.exception;

import com.mrtkyr.classqroom.enums.MessageType;

public class BaseException extends RuntimeException {
    private final MessageType messageType;

    public BaseException() {
        this.messageType = MessageType.GENERAL_EXCEPTION;
    }

    public BaseException(ErrorMessage errorMessage) {
        super(errorMessage.prepareErrorMessage());
        this.messageType = errorMessage.getMessageType();
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
