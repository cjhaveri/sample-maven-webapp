package test.chetan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import test.chetan.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
	
	Stock findByTickerSymbol(String companyName);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Stock s")
	void deleteAllData();
	
}
