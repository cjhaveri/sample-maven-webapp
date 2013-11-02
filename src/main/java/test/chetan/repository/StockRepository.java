package test.chetan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.chetan.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
	
	public Stock findByTickerSymbol(String companyName);

}
