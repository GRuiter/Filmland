package org.filmland.category;

import org.filmland.entities.Category;
import org.filmland.entities.Subscription;
import org.filmland.response.CategoryResponseMessage;
import org.filmland.response.DefaultResponseMessage;
import org.filmland.scheduler.PaymentScheduler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentScheduler paymentScheduler;

    public CategoryController(CategoryRepository categoryRepository, SubscriptionRepository subscriptionRepository, PaymentScheduler paymentScheduler) {
        this.categoryRepository = categoryRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.paymentScheduler = paymentScheduler;
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseMessage> getCategories(@RequestParam String username) {
        CategoryResponseMessage response = new CategoryResponseMessage();

        List<Category> allCategories = new ArrayList<>();
        categoryRepository.findAll().forEach(allCategories::add);

        List<CategoryResponseMessage.CategoryInfo> availableCategories = new ArrayList<>();
        List<CategoryResponseMessage.CategoryInfo> subscribedCategories = getSubscribedCategories(username);

        Set<String> subscribedCategoryNames = subscribedCategories.stream()
                .map(CategoryResponseMessage.CategoryInfo::getName)
                .collect(Collectors.toSet());

        for (Category category : allCategories) {
            CategoryResponseMessage.CategoryInfo categoryInfo = new CategoryResponseMessage.CategoryInfo();
            categoryInfo.setName(category.getName());
            categoryInfo.setAvailableContent(category.getAvailableContent());
            categoryInfo.setPrice(category.getPrice());

            if (subscribedCategoryNames.contains(category.getName())) {
                for (CategoryResponseMessage.CategoryInfo subscribedCategory : subscribedCategories) {
                    if (subscribedCategory.getName().equals(category.getName())) {
                        categoryInfo.setRemainingContent(subscribedCategory.getRemainingContent());
                        categoryInfo.setStartDate(subscribedCategory.getStartDate());
                        break;
                    }
                }
            }

            availableCategories.add(categoryInfo);
        }

        response.setAvailableCategories(availableCategories);
        response.setSubscribedCategories(subscribedCategories);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/subscribe")
    public DefaultResponseMessage subscribeToCategory(@RequestBody SubscriptionRequest subscriptionRequest) {
        String email = subscriptionRequest.getEmail();
        String categoryName = subscriptionRequest.getAvailableCategory();

        Category category = categoryRepository.findByName(categoryName);

        // Check if the user is already subscribed to this category
        boolean isAlreadySubscribed = subscriptionRepository.existsByUserNameAndCategoryId(email, category.getId());

        if (isAlreadySubscribed) {
            return new DefaultResponseMessage("Subscription failed", "User is already subscribed to this category");
        }

        // Create a new Subscription
        Subscription subscription = new Subscription();
        subscription.setName(categoryName);
        subscription.setRemainingContent(category.getAvailableContent());
        subscription.setPrice(category.getPrice());
        subscription.setStartDate(new Date());
        subscription.setCategoryId(category.getId());
        subscription.setUserName(email);

        subscriptionRepository.save(subscription);

        paymentScheduler.startSubscriptionScheduler(subscription);

        return new DefaultResponseMessage("Subscription succeeded", "User is subscribed to this category");
    }

    @PostMapping("/share")
    public DefaultResponseMessage shareCategory(@RequestBody ShareRequest shareRequest) {
        String email = shareRequest.getEmail();
        String customer = shareRequest.getCustomer();
        String categoryName = shareRequest.getSubscribedCategory();

        Category category = categoryRepository.findByName(categoryName);

        // Check if the user is already subscribed to this category
        boolean isAlreadySubscribed = subscriptionRepository.existsByUserNameAndCategoryId(customer, category.getId());

        if (isAlreadySubscribed) {
            return new DefaultResponseMessage("Sharing failed", "The new user is already subscribed to this category");
        }

        // Create a new Subscription for the customer
        Subscription customerSubscription = new Subscription();
        customerSubscription.setName(categoryName);
        customerSubscription.setRemainingContent(category.getAvailableContent());
        customerSubscription.setPrice(category.getPrice());
        customerSubscription.setStartDate(new Date());
        customerSubscription.setCategoryId(category.getId());
        customerSubscription.setUserName(customer);

        // Save the customer's Subscription entity
        subscriptionRepository.save(customerSubscription);

        // Update the original user's Subscription entity to share the cost and remaining content
        Subscription originalSubscription = subscriptionRepository.findByUserNameAndCategoryId(email, category.getId());

        if (originalSubscription != null) {
            originalSubscription.setRemainingContent(originalSubscription.getRemainingContent() / 2);
            originalSubscription.setPrice(originalSubscription.getPrice() / 2);

            subscriptionRepository.save(originalSubscription);

            return new DefaultResponseMessage("Sharing succeeded", "User has successfully shared the subscription");
        } else {
            return new DefaultResponseMessage("Sharing failed", "Original user's subscription not found");
        }
    }

    public List<CategoryResponseMessage.CategoryInfo> getSubscribedCategories(String username) {
        List<CategoryResponseMessage.CategoryInfo> subscribedCategories = new ArrayList<>();
        List<Subscription> subscribedCategoryEntities = subscriptionRepository.findAllByUserName(username);

        for (Subscription subscriptionEntity : subscribedCategoryEntities) {
            CategoryResponseMessage.CategoryInfo categoryInfo = new CategoryResponseMessage.CategoryInfo();
            categoryInfo.setName(subscriptionEntity.getName());
            categoryInfo.setAvailableContent(subscriptionEntity.getRemainingContent());
            categoryInfo.setPrice(subscriptionEntity.getPrice());
            categoryInfo.setStartDate(subscriptionEntity.getStartDate());
            subscribedCategories.add(categoryInfo);
        }
        return subscribedCategories;
    }
}
