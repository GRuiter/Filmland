package org.filmland.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareRequest {
    private String email;
    private String customer;
    private String subscribedCategory;
}
