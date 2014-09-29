package test.chetan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.chetan.model.Misc;

@Repository
public interface MiscRepository extends JpaRepository<Misc, Integer> {
	
}
