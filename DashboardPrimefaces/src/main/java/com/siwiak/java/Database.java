package com.siwiak.java;

public class Database {

	public final static String driver = "com.mysql.jdbc.Driver";
	public final static String dbPath = "***";
	public final static String user = "***";
	public final static String password = "***";

	private String market;
	private String category;
	private String period;
	private int limit;

	public Database(String market, String category, String period, int limit) {
		this.market = market;
		this.category = category;
		this.period = period;
		this.limit = limit;
	}

	public Database(String market, String period) {
		this.market = market;
		this.period = period;
	}

	public String getDataToChart() {
		String sqlQuery = "SELECT ITEM, Sum(VALUE_SALES_YA), Sum(VALUE_SALES) "
				+ "FROM 25164740_777.DATA GROUP BY ITEM, MARKET, CATEGORY, PERIOD " + "HAVING MARKET='" + getMarket()
				+ "' AND CATEGORY='" + getCategory() + "' AND PERIOD='" + getPeriod() + "' "
				+ "ORDER BY Sum(VALUE_SALES) DESC " + "LIMIT " + getLimit();
		return sqlQuery;
	}

	public String getMarket() {
		return market;
	}

	public String getCategory() {
		return category;
	}

	public String getPeriod() {
		return period;
	}

	public int getLimit() {
		return limit;
	}

}
