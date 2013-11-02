package test.chetan.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stock")
public class StockDTO {

	// @XmlElement(name="company")
	private String companyName;

	// @XmlElement(name="ticker")
	private String tickerSymbol;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTickerSymbol() {
		return tickerSymbol;
	}

	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}

}
