package org.filmland.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CategoryResponseMessage {
    private List<CategoryInfo> availableCategories;
    private List<CategoryInfo> subscribedCategories;

    @Data
    public static class CategoryInfo {
        private String name;
        private int availableContent;
        private double price;
        private int remainingContent;
        private Date startDate;
    }
}
