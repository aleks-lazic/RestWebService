package ch.hes.openfoodRest;

public class Nutrient {

	private String name;
	private String unit;
	private double per_hundred;
	private double per_portion;
	private double per_day;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public double getPer_hundred() {
		return per_hundred;
	}
	public void setPer_hundred(double per_hundred) {
		this.per_hundred = per_hundred;
	}
	public double getPer_portion() {
		return per_portion;
	}
	public void setPer_portion(double per_portion) {
		this.per_portion = per_portion;
	}
	public double getPer_day() {
		return per_day;
	}
	public void setPer_day(double per_day) {
		this.per_day = per_day;
	}


}
