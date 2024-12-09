/**
 * Child class CTrailer
 * Overrides getHowToDrive and getMPG methods
 */
class CTrailer extends CVehicle {

    /**
     * Constructor to initialize CTrailer with wheels.
     * MPG is always zero for trailers.
     * @param intWheels Number of wheels
     */
    public CTrailer(int intWheels) {
        super(intWheels, 0); // Trailers have 0 MPG
    }

    /**
     * Provides instructions on how to use the trailer.
     * @return Driving instructions specific to a trailer
     */
    @Override
    public String getHowToDrive() {
        return "Use another vehicle to pull";
    }

    /**
     * Gets the miles per gallon for the trailer.
     * Always returns zero.
     * @return Miles per gallon (always 0 for trailers)
     */
    @Override
    public int getMPG() {
        return 0; // Trailers don't have MPG
    }
}