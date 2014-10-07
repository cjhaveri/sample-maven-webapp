package test.chetan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comments {
	
	@Id
	@Column(name = "comment_id", nullable = false)
	@GeneratedValue
	private int id;

	@Column(name="comments", length=25)
	private String comments;
	
	@ManyToOne (targetEntity = Stock.class)
	@JoinColumn(name = "stock_id", nullable = true)
	private Stock stockId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public Stock getStockId() {
		return stockId;
	}

	public void setStockId(Stock stockId) {
		this.stockId = stockId;
	}
	
	
	
	

}
