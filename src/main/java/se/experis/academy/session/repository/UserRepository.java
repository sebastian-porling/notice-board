package se.experis.academy.session.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.experis.academy.session.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    Optional<User> findById(Long aLong);

    /*
    @Query("select case when count(u) > 0 then true else false end from users u where u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("select u from users u where u.username = :username")
    User findByUsername(@Param("username") String username);
    */
    boolean existsByUsername(String username);

    User findUserByUsername(String username);
}
