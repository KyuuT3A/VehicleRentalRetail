/**
 * Parent class CVehicle
 * Encapsulates attributes and methods for vehicles
 * @author Nick
 */
public class CVehicle {

    // Properties (encapsulated with private access)
    private int intWheels;
    private int intNumOfMPG;

    /**
     * Constructor to initialize the CVehicle with wheels and MPG.
     * @param intWheels Number of wheels
     * @param intNumOfMPG Miles per gallon
     */
    public CVehicle(int intWheels, int intNumOfMPG) {
        this.intWheels = intWheels;
        this.intNumOfMPG = intNumOfMPG;
    }

    /**
     * Gets the number of wheels.
     * @return Number of wheels
     */
    public int getWheels() {
        return intWheels;
    }

    /**
     * Sets the number of wheels.
     * @param intWheels Number of wheels
     */
    public void setWheels(int intWheels) {
        this.intWheels = intWheels;
    }

    /**
     * Gets the miles per gallon.
     * @return Miles per gallon
     */
    public int getMPG() {
        return intNumOfMPG;
    }

    /**
     * Sets the miles per gallon.
     * @param intNumOfMPG Miles per gallon
     */
    public void setMPG(int intNumOfMPG) {
        this.intNumOfMPG = intNumOfMPG;
    }

    /**
     * Provides instructions on how to drive the vehicle.
     * @return Driving instructions
     */
    public String getHowToDrive() {
        return "Generic driving instructions";
    }
}