package test.chetan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.chetan.model.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {
	
}
