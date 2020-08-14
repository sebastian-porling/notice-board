package se.experis.academy.session.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.academy.session.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
