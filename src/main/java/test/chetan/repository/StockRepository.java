package test.chetan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import test.chetan.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {

}
