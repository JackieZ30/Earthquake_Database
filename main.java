package Program6;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

/**
 * CPS142 - Spring 2022
 * Program06 - Main Test Program.
 * 4/22/2022
 * @author Kan Xing Zheng
 * Purpose: Creating a program using Streams API that analyzes our Earthquake data file to produce a
 * depth histogram displaying minimum magnitude, maximum magnitude, average magnitude, 
 * and total number of earthquakes within various depth ranges.
 */

public class main 
{
	private static DecimalFormat df = new DecimalFormat("#.##");
	public static final String EARTHQUAKE_FILE = "earthquakes.csv";
	
	public static void main(String[] args) 
	{
		// loading the earthquake file into a Earthquake array
		Earthquake[] earthquakes = loadEarthquakes(EARTHQUAKE_FILE);
		// creating a HashSet
		Set<Earthquake> earthquakeList = new HashSet<>();
		// storing all the data from the earthquake array into the new HashSet
		for(Earthquake earthquake : earthquakes)
		{
			earthquakeList.add(earthquake);
		}
		// Creating a Stream from the hashset
		Stream<Earthquake> stream = earthquakeList.stream();
		
		// welcome message and titles 
		System.out.println("Welcome to the Earthquake Database!");
		System.out.println();
		System.out.println("Depth/Magnitude Histogram Analysis:");
		System.out.println();
		System.out.println("       Mininum    Maximum    Mininum    Maximum    Average         ");
		System.out.println("Range    Depth      Depth  Magnitude  Magnitude  Magnitude    Count");
		System.out.println("-------------------------------------------------------------------");
		
		// the starting depth range
		double minDepth = 0;
		double maxDepth = 25;
		
		// for loop for each depth range (10 in total)
		for(int i = 1; i <= 10; i++)
		{
			// creating new depth range variables in order to get around using "final" keyword, as
			// lambda expressions only allow "final" variables
			double minDepthTemp = minDepth;
			double maxDepthTemp = maxDepth;
			// using filter method from Intermediate Operations, to create a filtered
			// stream that only includes Earthquake objects within the current depth range
			stream = earthquakeList.stream(); // creating a stream from the hashset
			stream = stream.filter(earthquake -> {
				return (earthquake.getDepth() >= minDepthTemp && earthquake.getDepth() <= maxDepthTemp);
			});
			
			// reducing the Earthquake stream to another array of Earthquake objects
			// in other words, storing filtered data into a new array
			Earthquake[] earthquakeA = stream.toArray(Earthquake[]::new);
			
			// creating a stream with the new Earthquake array "earthquakeA", which contains data from current depth range
			stream = Stream.of(earthquakeA);
			// finding minimum magnitude within current depth range using Stream min method
			Optional<Earthquake> earthquakeMinMag = stream.min((earthquake1, earthquake2) -> {
				return Double.compare(earthquake1.getMagnitude(),earthquake2.getMagnitude());
			});
			
			
			// creating a stream with the new Earthquake array "earthquakeA", which contains data from current depth range
			stream = Stream.of(earthquakeA);
			// finding maximum magnitude within current depth range using Stream maximum method
			Optional<Earthquake> earthquakeMaxMag = stream.max((earthquake1, earthquake2) -> {
				return Double.compare(earthquake1.getMagnitude(), earthquake2.getMagnitude());
			});
			
			// creating a stream with the new Earthquake array "earthquakeA", which contains data from current depth range
			stream = Stream.of(earthquakeA);
			// finding the sum of the magnitudes within current depth range using Stream reduce method
			double sum = stream.mapToDouble(Earthquake::getMagnitude).
					reduce(0,Double::sum);
			
			// total amount of earthquakes in this current depth range
			double total = earthquakeA.length;
			// finding the average magnitude by using the sum of the magnitude and divide that by the 
			// number of earthquakes found within the current depth range
			double averageMag = sum / total;
			
			// displaying all the information to the console shown in Section 4
			System.out.print(String.format("%5d", i));
			System.out.print("    ");
			System.out.print(String.format("%5s", df.format(minDepthTemp)));
			System.out.print("      ");
			System.out.print(String.format("%5s", df.format(maxDepthTemp)));
			System.out.print("      ");
			System.out.print(String.format("%5s", df.format(earthquakeMinMag.get().getMagnitude())));
			System.out.print("      ");
			System.out.print(String.format("%5s", df.format(earthquakeMaxMag.get().getMagnitude())));
			System.out.print("      ");
			System.out.print(String.format("%5s", df.format(averageMag)));
			System.out.print("    ");
			System.out.print(String.format("%5s", df.format(total)));
			System.out.println();

			// minDepth and maxDepth + 25 in order to move onto the next depth range
			minDepth = minDepth + 25;
			maxDepth = maxDepth + 25;
		}
	}

	/**
	 * This method loads Earthquake objects from a file and returns
	 * a regular Java Array of Earthquake objects in a randomized order.
	 * @param filename
	 * @return
	 */
	public static Earthquake[] loadEarthquakes(String filename)  {
		
		// Declare the output array
		Earthquake[] earthquakes = {};
		
		// Declare some file IO controls
		File file = new File(filename);
		Scanner scanner = null;
		
		try {
			// Load the Earthquake data from the file
			ArrayList<Earthquake> data = new ArrayList<>();
			scanner = new Scanner(file);
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				data.add(new Earthquake(scanner.nextLine()));
			}
			
			// Copy the earthquake data in random order
			earthquakes = new Earthquake[data.size()];
			Random rand = new Random();
			for (int i=0; data.size()>0; i++) {
				earthquakes[i] = data.remove(rand.nextInt(data.size()));
			}
		} 
		catch(Exception ex) {
			System.out.println("Unable to load the data in \""+ filename + "\"");
		}
		finally {
			if (scanner!=null) {
				scanner.close();
			}
		}
		
		// return the results
		return earthquakes;
		
	}
	

}
