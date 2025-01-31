package gov.cdc.nedss.util;

import java.math.BigDecimal;

public class Numeric {

	private String highRange;
	private String lowRange;
	private String comparator;
	private BigDecimal value1;
	private String separator;
	private BigDecimal value2;
	private Coded unit;

	public String getHighRange() {
		return highRange;
	}
	public void setHighRange(String highRange) {
		this.highRange = highRange;
	}
	public String getLowRange() {
		return lowRange;
	}
	public void setLowRange(String lowRange) {
		this.lowRange = lowRange;
	}
	public String getComparator() {
		return comparator;
	}
	public void setComparator(String comparator) {
		this.comparator = comparator;
	}
	public BigDecimal getValue1() {
		return value1;
	}
	public void setValue1(BigDecimal value1) {
		this.value1 = value1;
	}
	public String getSeparator() {
		return separator;
	}
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	public BigDecimal getValue2() {
		return value2;
	}
	public void setValue2(BigDecimal value2) {
		this.value2 = value2;
	}
	public Coded getUnit() {
		return unit;
	}
	public void setUnit(Coded unit) {
		this.unit = unit;
	}
}
