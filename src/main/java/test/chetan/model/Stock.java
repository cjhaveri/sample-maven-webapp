package test.chetan.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Stock {
	

	@Id
	@GeneratedValue
	@Column(name = "stock_id", nullable = false)
	private int id;
	
	@Column(name="ticker_symbol")
	private String tickerSymbol;
	
	@Column(name="company_name")
	private String companyName;
	
	@OneToMany(mappedBy = "stockId", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private Set<Comments> comments = new HashSet<Comments>();
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getTickerSymbol() {
		return tickerSymbol;
	}

	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Set<Comments> getComments() {
		return comments;
	}

	public void setComments(Set<Comments> comments) {
		this.comments = comments;
	}
	
	
	
	

}
