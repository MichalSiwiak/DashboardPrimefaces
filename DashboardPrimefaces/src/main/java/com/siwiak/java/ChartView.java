package com.siwiak.java;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.event.SlideEndEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean
@SessionScoped
public class ChartView implements Serializable {

	private static final long serialVersionUID = 1L;
	private BarChartModel barModel;
	private PieChartModel pieModel;

	private String market = "REGION A";
	private String category = "CATEGORY A";
	private String period = "LAST 6 MONTHS";
	private int limit = 5;

	private String name;
	private double valueSalesLY;
	private double valueSalesTY;
	private double change;

	private ArrayList<ResultRow> resultRows = new ArrayList<ResultRow>();
	private static Map<String, String> periods;
	private static Map<String, String> markets;
	private static Map<String, String> categories;

	@PostConstruct
	public void init() {
		initResultRows();
		createBarModel();
		createPieModel();

		periods = new LinkedHashMap<String, String>();
		periods.put("LAST MONTH", "LAST MONTH"); // label, value
		periods.put("LAST 6 MONTHS", "LAST 6 MONTHS");
		periods.put("LAST YEAR", "LAST YEAR");

		markets = new LinkedHashMap<String, String>();
		markets.put("REGION A", "REGION A");
		markets.put("REGION B", "REGION B");
		markets.put("REGION C", "REGION C");

		categories = new LinkedHashMap<String, String>();
		categories.put("CATEGORY A", "CATEGORY A");
		categories.put("CATEGORY B", "CATEGORY B");
		categories.put("CATEGORY C", "CATEGORY C");
		categories.put("CATEGORY D", "CATEGORY D");
		categories.put("CATEGORY E", "CATEGORY E");
		categories.put("CATEGORY F", "CATEGORY F");
	}

	private ArrayList<ResultRow> initResultRows() {
		try {
			Class.forName(Database.driver);
			Connection connection = DriverManager.getConnection(Database.dbPath, Database.user, Database.password);
			Statement statement = connection.createStatement();
			Database sqlQuery = new Database(market, category, period, limit);
			ResultSet resultSet = statement.executeQuery(sqlQuery.getDataToChart());

			while (resultSet.next()) {
				resultRows.add(new ResultRow(resultSet.getString("ITEM"), resultSet.getDouble("Sum(VALUE_SALES_YA)"),
						resultSet.getDouble("Sum(VALUE_SALES)"), getChange()));
			}

			if (statement != null) {
				statement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}
		return resultRows;
	}

	private void createBarModel() {
		barModel = new BarChartModel();
		ChartSeries lastYear = new ChartSeries();
		ChartSeries thisYear = new ChartSeries();

		for (ResultRow resultRow : resultRows) {
			lastYear.set(resultRow.getName(), resultRow.getValueSalesLY());
			thisYear.set(resultRow.getName(), resultRow.getValueSalesTY());
		}

		lastYear.setLabel("Last Year");
		thisYear.setLabel("This Year");

		barModel.addSeries(lastYear);
		barModel.addSeries(thisYear);
		//barModel.setTitle("Bar Chart");
		barModel.setLegendPosition("ne");
		barModel.setLegendRows(0);
	}

	private void createPieModel() {
		pieModel = new PieChartModel();

		for (ResultRow resultRow : resultRows) {
			pieModel.set(resultRow.getName(), resultRow.getValueSalesTY());
		}
		//pieModel.setTitle("Simple Pie");
	
	}

	public void periodChanged(ValueChangeEvent event) {
		resultRows.clear();
		period = event.getNewValue().toString();
		initResultRows();
		FacesMessage message = new FacesMessage("Dashboard updated", "Period: " + event.getNewValue());
		FacesContext.getCurrentInstance().addMessage(null, message);
		createBarModel();
		createPieModel();
	}

	public void marketChanged(ValueChangeEvent event) {
		resultRows.clear();
		market = event.getNewValue().toString();
		initResultRows();
		FacesMessage message = new FacesMessage("Dashboard updated", "Period: " + event.getNewValue());
		FacesContext.getCurrentInstance().addMessage(null, message);
		createBarModel();
		createPieModel();
	}

	public void categoryChanged(ValueChangeEvent event) {
		resultRows.clear();
		category = event.getNewValue().toString();
		initResultRows();
		FacesMessage message = new FacesMessage("Dashboard updated", "Period: " + event.getNewValue());
		FacesContext.getCurrentInstance().addMessage(null, message);
		createBarModel();
		createPieModel();
	}

	public void onSlideEnd(SlideEndEvent event) {
		resultRows.clear();
		limit = event.getValue();
		initResultRows();
		FacesMessage message = new FacesMessage("Dashboard updated", "Number: " + event.getValue());
		FacesContext.getCurrentInstance().addMessage(null, message);
		createBarModel();
		createPieModel();
	}

	public Map<String, String> getPeriodInMap() {
		return ChartView.periods;
	}

	public Map<String, String> getMarketInMap() {
		return ChartView.markets;
	}

	public Map<String, String> getCategoryInMap() {
		return ChartView.categories;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public ArrayList<ResultRow> getResultRows() {
		return resultRows;
	}

	public String getName() {
		return name;
	}

	public double getValueSalesLY() {
		return valueSalesLY;
	}

	public double getValueSalesTY() {
		return valueSalesTY;
	}

	public double getChange() {
		return change;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}