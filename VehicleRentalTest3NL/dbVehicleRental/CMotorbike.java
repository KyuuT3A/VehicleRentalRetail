/**
 * Child class CMotorbike
 * Overrides getHowToDrive method
 */
class CMotorbike extends CVehicle {

    /**
     * Constructor to initialize CMotorbike with wheels and MPG.
     * @param intWheels Number of wheels
     * @param intNumOfMPG Miles per gallon
     */
    public CMotorbike(int intWheels, int intNumOfMPG) {
        super(intWheels, intNumOfMPG);
    }

    /**
     * Provides instructions on how to drive the motorbike.
     * @return Driving instructions specific to a motorbike
     */
    @Override
    public String getHowToDrive() {
        return "Handle bars";
    }
}