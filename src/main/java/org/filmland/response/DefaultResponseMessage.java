package org.filmland.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultResponseMessage {
    private String status;
    private String message;

    public DefaultResponseMessage(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
