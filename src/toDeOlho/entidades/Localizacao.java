package toDeOlho.entidades;

import java.io.Serializable;

public class Localizacao implements Serializable  {
	
	private static final long serialVersionUID = 387862885186314555L;
	private int id;
	private double latitude;
	private double longitude;
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	
	
}
