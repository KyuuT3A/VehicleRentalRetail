/**
 * Main program CVehicleFinal
 * polymorphism and functionality
 * @author Nick
 */
public class CVehicleFinal {

    /**
     * Main method to run the program.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Create instances of each vehicle type
        CCar car = new CCar(4, 25);
        CMotorbike motorbike = new CMotorbike(2, 50);
        CTrailer trailer = new CTrailer(6);

        // Display information for each vehicle
        System.out.println("Car: " + car.getHowToDrive() + ", Wheels: " + car.getWheels() + ", MPG: " + car.getMPG());
        System.out.println("Motorbike: " + motorbike.getHowToDrive() + ", Wheels: " + motorbike.getWheels() + ", MPG: " + motorbike.getMPG());
        System.out.println("Trailer: " + trailer.getHowToDrive() + ", Wheels: " + trailer.getWheels() + ", MPG: " + trailer.getMPG());
    }
}

