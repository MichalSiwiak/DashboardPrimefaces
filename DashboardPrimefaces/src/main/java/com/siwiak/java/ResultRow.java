package com.siwiak.java;

import java.io.Serializable;

public class ResultRow implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private Double valueSalesLY;
	private Double valueSalesTY;

	public ResultRow(String name, Double valueSalesLY, Double valueSalesTY, Double change) {
		this.name = name;
		this.valueSalesLY = valueSalesLY;
		this.valueSalesTY = valueSalesTY;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValueSalesLY() {
		return Math.round(valueSalesLY * 100.0) / 100.0;
	}

	public void setValueSalesLY(Double valueSalesLY) {
		this.valueSalesLY = valueSalesLY;
	}

	public Double getValueSalesTY() {
		return Math.round(valueSalesTY * 100.0) / 100.0;
	}

	public void setValueSalesTY(Double valueSalesTY) {
		this.valueSalesTY = valueSalesTY;
	}

	public double getChange() {
		double result = valueSalesTY / valueSalesLY - 1;
		return Math.round(result * 100.0) / 100.0;
	}

}