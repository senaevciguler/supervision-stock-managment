package com.kiwi.utils;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public abstract class Result {
    String message;
    Object payload;
    List<String> messageCodes = new ArrayList<>();

    Result() { }

    <T extends Result> T setMessage(String message) {
        this.message = message;
        return (T) this;
    }

    @JsonGetter
    public abstract boolean isSuccess();

    public static class Success extends Result {
        @Builder
        public Success(String message, Object payload) {
            this.message = message;
            this.payload = payload;
        }
        @Override
        public boolean isSuccess() {
            return true;
        }

        public static Success create(String message) {
            Success s = Success.builder().build();
            return s.setMessage(message);
        }
    }

    public static class Fail extends Result {
        @Builder
        public Fail(String message, Object payload) {
            this.message = message;
            this.payload = payload;
        }
        @Override
        public boolean isSuccess() {
            return false;
        }

        public static Fail create(String message) {
            Fail s = Fail.builder().build();
            return s.setMessage(message);
        }

        public static Fail create(List<String> errorCodes) {
            Fail fail = Fail.builder().build();
            fail.setMessageCodes(errorCodes);
            return fail;
        }
    }
}
