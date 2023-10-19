package org.filmland.category;

import org.filmland.entities.Subscription;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    List<Subscription> findAllByUserName(String userName);

    boolean existsByUserNameAndCategoryId(String email, long id);

    Subscription findByUserNameAndCategoryId(String email, long id);
}
