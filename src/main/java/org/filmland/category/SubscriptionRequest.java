package org.filmland.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionRequest {
    private String email;
    private String availableCategory;
}
