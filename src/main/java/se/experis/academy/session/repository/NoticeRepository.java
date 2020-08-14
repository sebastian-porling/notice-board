package se.experis.academy.session.repository;

import org.springframework.data.repository.CrudRepository;
import se.experis.academy.session.model.Notice;
import se.experis.academy.session.model.User;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends CrudRepository<Notice, Long> {

    List<Notice> findAllByAuthor(User user);

    @Override
    Optional<Notice> findById(Long aLong);

    List<Notice> findAllByOrderByDateDesc();
}
