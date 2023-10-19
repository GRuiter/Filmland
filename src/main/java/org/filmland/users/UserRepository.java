package org.filmland.users;

import org.filmland.entities.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Long> {
    Users findAllByEmailAndPassword(String email, String password);
}
