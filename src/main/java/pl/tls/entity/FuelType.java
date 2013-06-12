package pl.tls.entity;

/**
 *
 * @author Tomasz.Lenczyk
 */
public enum FuelType {

	OIL("Oil"),
	GASOLINE("Gasoline");
	private String fuelType;

	private FuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getFuletType() {
		return this.fuelType;
	}
}
