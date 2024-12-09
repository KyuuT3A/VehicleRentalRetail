//import for sql Connection, ResultSet, Statement
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

/**
* Class: dbVehicleRentalSQL
* Provides functionality to connect to a database and manage vehicle rental operations.
* @author Nicolas Le
* @since 12/6/2024
* @version 1.0
* */
public class dbVehicleRentalSQL
{
		//define the Connection
		private static Connection m_conAdministrator;
		
		
		/**
	     * Main method: intro to database processing.
	     * Connects to the database and displays records on the console.
	     * @param args Command line arguments
	     */
		public static void main(String[] args) {
	        try {
	            // Connect to the database and display pickup locations
	            if (OpenDatabaseConnectionSQLServer()) {
	                LoadPickupLocations("TLocations", "intLocationID", "strLocationName", "strAddress", "strCity", "strState", "strZip");
	                ProcessCustomerInput();
	            } else {
	                System.out.println("Error loading the table.");
	            }
	        } catch (Exception e) {
	            System.out.println("An error occurred: " + e.getMessage());
	        } finally {
	            CloseDatabaseConnection();
	        }
	    }

		/**
		 * Creates the customers inputs 
		 */
		
		public static void ProcessCustomerInput() {
	        // Variables to store customer inputs
	        String strFirstName, strLastName, strPhoneNumber, strEmail, strPickupDate;
	        int intRentalDays, intVehicleCount;

	        @SuppressWarnings("unused")
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	        try {
	            System.out.println("\nEnter Customer Details");
	            System.out.println("-----------------------------------------------");

	            // Collect first name
	            System.out.print("First Name: ");
	            strFirstName = ReadStringFromUser();

	            // Collect last name
	            System.out.print("Last Name: ");
	            strLastName = ReadStringFromUser();

	            // Collect and validate phone number
	            do {
	                System.out.print("Phone number (###-###-#### or ##########): ");
	                strPhoneNumber = ReadStringFromUser();
	            } while (!IsValidPhoneNumber(strPhoneNumber));

	            // Collect and validate email address
	            do {
	                System.out.print("Email: ");
	                strEmail = ReadStringFromUser();
	            } while (!IsValidEmailAddress(strEmail));

	            // Collect and validate pickup date
	            do {
	                System.out.print("Pick up date (MM-DD-YYYY or MM/DD/YYYY): ");
	                strPickupDate = ReadStringFromUser();
	            } while (!IsValidDate(strPickupDate));

	            // Collect number of rental days
	            do {
	                System.out.print("Number of days to rent: ");
	                intRentalDays = ReadIntegerFromUser();
	            } while (intRentalDays <= 0);

	            // Collect number of vehicles to rent
	            do {
	                System.out.print("Number of vehicles to rent (1-3): ");
	                intVehicleCount = ReadIntegerFromUser();
	            } while (intVehicleCount < 1 || intVehicleCount > 3);

	            // Output customer details
	            System.out.printf("\nCustomer Details:\nName: %s %s\nPhone: %s\nEmail: %s\nPickup Date: %s\nRental Days: %d\nVehicles: %d\n",
	                    strFirstName, strLastName, strPhoneNumber, strEmail, strPickupDate, intRentalDays, intVehicleCount);

	            // Process vehicle selection and pricing
	            ProcessVehicleSelection(intVehicleCount, intRentalDays);

	        } catch (Exception e) {
	            System.out.println("An error occurred while processing input: " + e.getMessage());
	        }
	    }

		/**
	     * Processes vehicle selection and calculates rental prices.
	     * @param intVehicleCount Number of vehicles to rent
	     * @param intRentalDays Number of days to rent the vehicles
	     */
		public static void ProcessVehicleSelection(int intVehicleCount, int intRentalDays) {
		    // Variables
		    String[] strVehicleNames = {"Car", "Motorbike", "Trailer"};
		    double[] dblVehiclePrices = {50.0, 75.0, 100.0};

		    int[] intSelectedVehicle = new int[intVehicleCount];
		    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		    double dblTotalRentalPrice = 0.0;

		    try {
		        // Display available vehicles
		        System.out.println("\nAvailable Vehicles:");
		        for (int i = 0; i < strVehicleNames.length; i++) {
		            System.out.printf("%d. %s ($%.2f per day)\n", i + 1, strVehicleNames[i], dblVehiclePrices[i]);
		        }

		        // Allow the user to select vehicles
		        for (int i = 0; i < intVehicleCount; i++) {
		            int intSelectedVehicleIndex;
		            do {
		                System.out.printf("Select vehicle %d (1-%d): ", i + 1, strVehicleNames.length);
		                intSelectedVehicleIndex = Integer.parseInt(reader.readLine()) - 1;

		                if (intSelectedVehicleIndex < 0 || intSelectedVehicleIndex >= strVehicleNames.length) {
		                    System.out.println("Please select a valid vehicle.");
		                }
		            } while (intSelectedVehicleIndex < 0 || intSelectedVehicleIndex >= strVehicleNames.length);

		            intSelectedVehicle[i] = intSelectedVehicleIndex;
		        }

		        // Calculate and display the details for selected vehicles
		        System.out.println("\nVehicle Rental Details:");
		        for (int i = 0; i < intSelectedVehicle.length; i++) {
		            int intVehicleIndex = intSelectedVehicle[i];
		            String strVehicleName = strVehicleNames[intVehicleIndex];
		            double dblPricePerDay = dblVehiclePrices[intVehicleIndex];
		            double dblVehicleTotal = dblPricePerDay * intRentalDays;

		            // Total rental
		            dblTotalRentalPrice += dblVehicleTotal;
		            System.out.printf("Vehicle: %s | Price/Day: $%.2f | Days: %d | Total: $%.2f\n",
		                    strVehicleName, dblPricePerDay, intRentalDays, dblVehicleTotal);
		        }

		        // Display total rental price
		        System.out.printf("\nTotal Rental Price: $%.2f\n", dblTotalRentalPrice);

		    } catch (Exception e) {
		        System.out.println("An error occurred while processing vehicle selection: " + e.getMessage());
		    }
		}

	

		/**
		 * Method: IsValidDate - Check if date entered is valid
		 * @param strResponse
		 * Date entered by user
		 * @return blnIsValidDate
		 */
		private static boolean IsValidDate(String strDate) 
		{
			boolean blnIsValidDate = false;
			try
			{
				// Declare variable
				String strStart = "^";
				String strStop = "$";
				String strDash = "\\-";
				String strSlash = "\\/";
				String strPattern1 = "";
				String strPattern2 = "";
				// set string pattern
				// MM-DD-YYYY
				strPattern1 = strStart + "\\d{2}" + strDash + "\\d{2}" + strDash + "\\d{4}" + strStop;
				// MM/DD/YYYY
				strPattern2 = strStart + "\\d{2}" + strSlash + "\\d{2}" + strSlash + "\\d{4}" + strStop;
				
				// does it match
				if( strDate.matches(strPattern1) == true ||
						strDate.matches(strPattern2) == true)
				{
					// yes
					blnIsValidDate = true;					
				}
			}
			catch(Exception excError)
			{
				// display error message
				System.out.println(excError);
			}
			// return
			return blnIsValidDate;
		}

		/**
		 * Loads the list of car rental pickup locations from the database.
		 *
		 * @param strTable Table name (TLocations)
		 * @param strPrimaryKeyColumn Primary key column name (intLocationID)
		 * @param strLocationNameColumn Location name column name (strLocationName)
		 * @param strAddressColumn Address column name (strAddress)
		 * @param strCityColumn City column name (strcity)
		 * @param strStateColumn State column name (strState)
		 * @param strZipColumn ZIP column name (strZip)
		 * @return True if data is successfully loaded, false otherwise
		 */
		public static boolean LoadPickupLocations(String strTable, String strPrimaryKeyColumn, String strLocationNameColumn, String strAddressColumn, 
                String strCityColumn, String strStateColumn, String strZipColumn) 
		{
			boolean blnResult = false;

			try {
				String strSelect = "SELECT " + strPrimaryKeyColumn + ", " + strLocationNameColumn + ", " + strAddressColumn + ", " +
						strCityColumn + ", " + strStateColumn + ", " + strZipColumn +
						" FROM " + strTable + " ORDER BY " + strPrimaryKeyColumn;

				Statement sqlCommand = m_conAdministrator.createStatement();
				ResultSet rstTSource = sqlCommand.executeQuery(strSelect);

				System.out.println("Here are the pickup locations - we will call you with a location confirmation:");

				while (rstTSource.next()) {
					int intID = rstTSource.getInt(strPrimaryKeyColumn);
					String strLocation = rstTSource.getString(strLocationNameColumn);
					String strAddress = rstTSource.getString(strAddressColumn);
					String strCity = rstTSource.getString(strCityColumn);
					String strState = rstTSource.getString(strStateColumn);
					String strZip = rstTSource.getString(strZipColumn);

					System.out.printf("ID: %-2d Name: %-10s Address: %-15s City: %-10s State: %-2s Zip: %-6s\n",
							intID, strLocation, strAddress, strCity, strState, strZip);
				}

				rstTSource.close();
				sqlCommand.close();
				blnResult = true;
			} catch (Exception e) {
				System.out.println("Error loading table: " + e.getMessage());
			}
			return blnResult;
		}


		
		
		/**
		 * OpenDatabaseConnectionSQLServer - get SQL db connection
		 * @return blnResult
		 */
		public static boolean OpenDatabaseConnectionSQLServer( )
		{
			boolean blnResult = false;
			
			try
			{
				SQLServerDataSource sdsTeamsAndPlayers = new SQLServerDataSource( );
				//tg-comment out --sdsTeamsAndPlayers.setServerName( "localhost" ); // localhost or IP or server name
				sdsTeamsAndPlayers.setServerName( "localhost\\SQLExpress" ); // SQL Express version
				sdsTeamsAndPlayers.setPortNumber( 1433 );
				sdsTeamsAndPlayers.setDatabaseName( "dbVehicleRental" );				
				// Login Type:				
					// Windows Integrated
					//tg-comment out --sdsVehicleRental.setIntegratedSecurity( true );					
					// OR					
					// SQL Server
				     sdsTeamsAndPlayers.setUser( "sa" );
					 sdsTeamsAndPlayers.setPassword( "JavaRocks#1" );					
				// Open a connection to the database
				m_conAdministrator = sdsTeamsAndPlayers.getConnection(  );				
				// Success
				blnResult = true;
			}
			catch( Exception excError )
			{
				// Display Error Message
				System.out.println( "Cannot connect - error: " + excError );
				// Warn about SQL Server JDBC Drivers
				System.out.println( "Make sure download MS SQL Server JDBC Drivers");
			}			
			return blnResult;
		}
				
		 /**
	     * Closes the database connection.
	     * @return True if the connection is successfully closed, false otherwise
	     */ 
		public static boolean CloseDatabaseConnection( )
		{
			boolean blnResult = false;			
			try
			{
				// Is there a connection object?
				if( m_conAdministrator != null )
				{
					// Yes, close the connection if not closed already
					if( m_conAdministrator.isClosed( ) == false ) 
					{
						m_conAdministrator.close( );						
						// Prevent JVM from crashing
						m_conAdministrator = null;
					}
				}
				// Success
				blnResult = true;
			}
			catch( Exception excError )
			{
				// Display Error Message
				System.out.println( excError );
			}
			
			return blnResult;
		}
		
		/**
		 * Method: IsValidPhoneNumber - Check if phone number entered is in correct format
		 * @param strPhoneNumber
		 * Phone number entered by user
		 * @return blnIsValidPhoneNumber
		 */
		private static boolean IsValidPhoneNumber(String strPhoneNumber) 
		{
			boolean blnIsValidPhoneNumber = false;
			try
			{
				// Decalre variable
				String strStart = "^";
				String strStop = "$";
				String strDash = "\\-";
				String strPattern1 = "";
				String strPattern2 = "";
				
				// String pattern
				// ###-###-####
				strPattern1 = strStart + "\\d{3}" + strDash + "\\d{3}" + strDash + "\\d{4}" + strStop;
				// ###########
				strPattern2 = strStart + "\\d{10}" + strStop;
				
				// does it match any of the format?
				if(strPhoneNumber.matches(strPattern1) == true ||
						strPhoneNumber.matches(strPattern2)	== true)
				{
					//yes
					blnIsValidPhoneNumber = true;
				}
			}
			catch(Exception excError)
			{
				// display error message
				System.out.println(excError);
			}
			// return result
			return blnIsValidPhoneNumber;
		}
		
		/**
		 * Method: IsValidEmailAddress - Check if email entered is valid
		 * @param strResponse
		 * Email entered by user
		 * @return blnIsValidEmailAddress
		 */
		private static boolean IsValidEmailAddress(String strEmailAddress) 
		{
			boolean blnIsValidEmailAddress = false;
			try
			{
				// Declare variable
				String strStart = "^";
				String strStop = "$";
				String strPattern = "";
				
				// set string pattern
				strPattern = strStart + "[a-zA-Z][a-zA-Z0-9\\.\\-]*" + "@" + "[a-zA-Z][a-zA-Z0-9\\.\\-]*\\.[a-zA-Z]{2,6}" + strStop;
				
				// does it match?
				if(strEmailAddress.matches(strPattern) == true)
				{
					//yes
					blnIsValidEmailAddress = true;
				}
			}
			catch(Exception excError)
			{
				// Display error message
				System.out.println(excError);
			}
			return blnIsValidEmailAddress;
		}
		
		
		/**
		 * Method ReadIntegerFromUser - Get user input
		 * @return intValue
		 */
		public static int ReadIntegerFromUser( )
		{

			int intValue = 0;

			try
			{
				String strBuffer = "";	

				// Input stream
				BufferedReader burInput = new BufferedReader( new InputStreamReader( System.in ) ) ;

				// Read a line from the user
				strBuffer = burInput.readLine( );
				
				// Convert from string to integer
				intValue = Integer.parseInt( strBuffer );
			}
			catch( Exception excError )
			{
				System.out.println( excError.toString( ) );
			}
			

			// Return integer value
			return intValue;
		}	

		/**
		 * Method: ReadStringFromUser - Get input from user
		 * @return strBuffer
		 */
		public static String ReadStringFromUser( )
		{

			String strBuffer = "";

			try
			{
				// Input stream
				BufferedReader burInput = new BufferedReader( new InputStreamReader( System.in ) ) ;

				// Read a line from the user
				strBuffer = burInput.readLine( );
			}
			catch( Exception excError )
			{
				System.out.println( excError.toString( ) );
			}

			// Return string
			return strBuffer;
		}
		
		/**
		 * Method: IsInteger- checks if string is an integer
		 * @param strResponse
		 * String to check
		 * @return blnNumeric
		 */
		public static boolean IsInteger(String strResponse) {
			boolean blnNumeric = true;
			
			try
			{
				Integer.parseInt(strResponse);
			}
			catch( NumberFormatException e )
			{
				blnNumeric = false;
			}
			
			return blnNumeric;
		}
		
}