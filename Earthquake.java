package Program6;
/**
 * CPS142 - Spring 2022
 * Program 01
 * Earthquake class - represents an occurrance of an Earthquake.
 * @author Kan Xing Zheng
 *
 */
public class Earthquake implements Comparable<Earthquake> {

	private String id;
	private String time;
	private double latitude;
	private double longitude;
	private double depth;
	private double magnitude;
	private String place;
	private String state;
		
	/**
	 * Constructor taking a CSV string to initialize 
	 * fields for a new Earthquake instance.
	 * @param string
	 */
	public Earthquake(String string) {
		// Tokenize the string parameter using commas (CSV).
		String[] tokens = string.trim().split(",");
		
		// Capture fields based on column position.
		this.time = tokens[0].trim();		
		this.latitude = Double.parseDouble(tokens[1].trim()); 
		this.longitude = Double.parseDouble(tokens[2].trim()); 
		this.depth = Double.parseDouble(tokens[3].trim()); 
		this.magnitude = Double.parseDouble(tokens[4].trim()); 
		this.place = tokens[13].trim();		
		this.state = tokens[14].trim();		

	}
	
	public Earthquake(double magnitude) {
		this.magnitude = magnitude;
	}
	
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @return the magnitude
	 */
	public double getMagnitude() {
		return magnitude;
	}
	/**
	 * @return the depth
	 */
	public double getDepth() {
		return depth;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	
	
	@Override
	public String toString() {
		// Create a simple report including the location and state/country
		// of the earthquake, plus time, magnitude and depth on
		// separate lines.
		StringBuilder report = new StringBuilder();
		
		report.append(String.format("Earthquake at: %s, %s\n", this.place, this.state));
		report.append(String.format("Time:      %s\n", this.time));
		report.append(String.format("Magnitude: %5.3f\n", this.magnitude));
		report.append(String.format("Depth:     %5.3f\n", this.depth));
		
		return report.toString();
	}

	@Override
	public int compareTo(Earthquake o) {
		return Double.compare(this.magnitude, o.magnitude);
	}
	
	@Override
	public int hashCode() {
		// Use the time as the hash code
		// since it is less likely that multiple earthquakes happen
		// at the exact same time.
		return this.time.hashCode();
	}
	
}
