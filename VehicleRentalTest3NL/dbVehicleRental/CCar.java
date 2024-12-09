/**
 * Child class CCar
 * Overrides getHowToDrive method
 */
class CCar extends CVehicle {

    /**
     * Constructor to initialize CCar with wheels and MPG.
     * @param intWheels Number of wheels
     * @param intNumOfMPG Miles per gallon
     */
    public CCar(int intWheels, int intNumOfMPG) {
        super(intWheels, intNumOfMPG);
    }

    /**
     * Provides instructions on how to drive the car.
     * @return Driving instructions specific to a car
     */
    @Override
    public String getHowToDrive() {
        return "Steering wheel";
    }
}