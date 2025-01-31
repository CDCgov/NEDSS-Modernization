package gov.cdc.nedss.geocoding.geodata;

import gov.cdc.nedss.geocoding.util.GeoCodingUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Geodata utilities class.
 * 
 * @author rdodge
 *
 */
public class GeoDataUtils {

	/** Static inner class used to pass command-line information. */
	public static class CommandLineOptions {
		public boolean isMultiMode;
		public String clientType;
		public String propertiesFile;
		public String inputFile;
	}

	// Main Method Support //

	/**
	 * Processes and validates command-line arguments.
	 * 
	 * @param args Command-line arguments
	 * @param options Parsed command-line options (in/out)
	 * @return
	 */
	public static boolean processArgs(String args[], CommandLineOptions options) {

		boolean result = false;

		// Loop over every element except the last one //
		for (int i = 0; i < args.length - 1; i++) {
			if ("-m".equals(args[i])) {
				options.isMultiMode = true;
			}
			else if (i > 0 && "-p".equals(args[i - 1])) {
				options.propertiesFile = args[i];
			}
			else if (i > 0 && "-c".equals(args[i - 1])) {
				options.clientType = args[i];
			}
		}

		// Process last element //
		if (args.length >= 1) {
			options.inputFile = args[args.length - 1];
			result = true;
		}

		return result;
	}

	/**
	 * Reads a <code>Properties</code> object from the indicated file.
	 * 
	 * @param filename
	 * @return
	 */
	public static Properties readPropertiesFromFile(String filename) {

		Properties properties = null;
		if (filename != null) {
			properties = new Properties();
			try {
				properties.load(new FileInputStream(filename));
			}
			catch (Exception e) {
				System.err.println("Cannot read from properties file \"" +
						filename + "\": " + GeoCodingUtils.getStackTrace(e));
			}
		}
		return properties;
	}

	/**
	 * Reads a list of input addresses from the specified file and
	 * returns the list as an array.
	 * 
	 * @param filename
	 * @param addressConverter
	 * @return
	 */
	public static GeoDataInputAddress[] readInputAddressesFromFile(String filename,
			GeoDataAddressConverter addressConverter) {

		List<Object> addressList = new ArrayList<Object> ();
		GeoDataInputAddress inputAddress = null;

		int addressIndex = 0;
		String line = null;

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {
			// Open input file for reading //
			fileReader = new FileReader(filename);
			bufferedReader = new BufferedReader(fileReader);

			// Read each address available //
			String[] fields = null;
			while ((line = bufferedReader.readLine()) != null) {

				// Ignore comments and whitespace //
				if (line.trim().length() <= 0 || line.trim().startsWith("#")) {
					continue;  // LOOP SHORTCUT
				}

				// Data Dictionary: Address Line 1|Address Line 2|City|State|Zip //
				fields = line.split("\\|", -1);
				if (fields.length < 5) {
					System.err.println("Found only " + fields.length +
							" field(s) on line #" + addressIndex + " (expected A1|A2|C|S|Z).");
					continue;  // LOOP SHORTCUT
				}

				// Set address //
				inputAddress = addressConverter.newInputAddress();
				inputAddress.setInputAddress(fields[0], fields[1], fields[2], fields[3], fields[4]);
				addressList.add(inputAddress);

				addressIndex++;
			}
		}
		catch (Exception e) {
			System.err.println("Received the following exception [Address #" +
					addressIndex + "][Line=\"" + line + "\"]: " + GeoCodingUtils.getStackTrace(e));
		}
		finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				}
				catch (IOException e) {}
			}
		}

		// Exit //
		return (GeoDataInputAddress[]) addressList.toArray(new GeoDataInputAddress[addressList.size()]);
	}

	/**
	 * Main method helper.  Expects a command line of the form
	 * 
	 *     java <<classname>> [-m] [-p <properties-filename>]
	 *          [-c <client type>] <address-input-filename>
	 * 
	 * where the final argument must contain the path to a text
	 * file with the following format:
	 * 
	 * ### Blank and comment lines are ignored
	 * #
	 * 123 Address Street|Suite 100|Ville|GA|30001
	 * 456 Address Blvd||Ville|GA|
	 * ||||30303
	 * 
	 * The <code>-m</code> switch enables multi-address mode, which
	 * processes all input addresses in one batch.
	 * 
	 * If no <code>-p</code> switch is provided, the geodata client
	 * will be passed a null properties object.
	 * 
	 * The word following the <code>-c</code> switch (client type)
	 * is used to dereference entries in the specified properties
	 * file.
	 * 
	 * @param options
	 * @param clientInstance
	 */
	public static void runClientSession(CommandLineOptions options,
			GeoDataClientBase clientInstance) {

		GeoDataInputAddress[] inputAddresses = null;
		GeoDataOutputAddress[] outputAddresses = null;
		GeoDataResult[] results = null;

		try {
			// Read properties from file (if applicable) //
			Properties properties = readPropertiesFromFile(options.propertiesFile);

			// Set client type to default if none set //
			if (options.clientType == null || options.clientType.length() <= 0) {
				options.clientType = GeoDataClient.DEFAULT_CLIENT_TYPE;
			}
			System.out.println("Client type is \"" + options.clientType + "\".");

			// Initialize client //
			clientInstance.init(properties, options.clientType);

			// Display config settings //
			System.out.println();
			System.out.println(clientInstance.getConfig().toString());
			System.out.println();

			// Read input addresses from input file //
			inputAddresses = readInputAddressesFromFile(options.inputFile,
					clientInstance.getFactory().getAddressConverter());

			if (options.isMultiMode) {
				System.out.println("Using MULTI mode.");

				results = clientInstance.lookupAddresses(inputAddresses);  // Client call - multi
			}
			else {
				System.out.println("Using SINGLE mode.");

				List<Object> resultsList = new ArrayList<Object> ();
				for (int i = 0; i < inputAddresses.length; i++) {
					resultsList.add(clientInstance.lookupAddress(inputAddresses[i]));  // Client call - single
				}
				results = (GeoDataResult[]) resultsList.toArray(new GeoDataResult[resultsList.size()]);
			}

			// Sanity check: # input addresses equals # results? //
			if (results.length != inputAddresses.length) {
				System.err.println();
				System.err.println("ERROR: The # of input addresses (" +
						inputAddresses.length + ") does not equal the # of geodata result rows (" +
						results.length + ").");
			}

			// For each input address ...
			for (int i = 0; i < results.length; i++) {

				// Display input address //
				System.out.println();
				System.out.println("ADDRESS " + i);
				System.out.println("=================");
				System.out.println(inputAddresses[i].inputToString());

				outputAddresses = results[i].getOutputAddresses();

				System.out.println("Call returned " + results[i].getNumOutputAddresses() +
						" output address(es), result type = \"" + results[i].getResultType() +
						"\", match count class = \"" + results[i].getMatchCountClass() + "\".");
				System.out.println();

				// Display output address(es) //
				for (int j = 0; j < outputAddresses.length; j++) {
					System.out.println("OUTPUT Address " + i + "-" + j +
							": -->" + outputAddresses[j].outputToString());
				}
			}
		}
		catch (Exception e) {
			System.err.println("Received the following exception: " + GeoCodingUtils.getStackTrace(e));
		}
	}
}
