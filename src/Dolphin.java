/**
  Dolphin - a specific type of animal that can swim.
  Adds to Animal and adds in the Swimmable interface.
  Added instance variable: trainedTricks (number of tricks learned).
 */
public class Dolphin extends Animal implements Swimmable {

    // Additional instance variable specific to Dolphin
    private int trainedTricks;

    /**
     * Parameterised constructor for Dolphin.
     *
     * @param name          the dolphin's name
     * @param age           the dolphin's age in years
     * @param colour        the dolphin's colour
     * @param weight        the dolphin's weight in kg
     * @param trainedTricks the number of tricks the dolphin has learned
     */
    public Dolphin(String name, int age, String colour,
                   double weight, int trainedTricks) {
        super(name, age, colour, weight); // call Animal constructor
        this.trainedTricks = trainedTricks;
    } // end constructor

    // ------------------- Getter and Setter for extra variable ---------------

    public int getTrainedTricks() { return trainedTricks; }

    public void setTrainedTricks(int trainedTricks) {
        this.trainedTricks = trainedTricks;
    } // end setTrainedTricks

    // -------------------- Overridden Animal method --------------------------

    /**
     * Returns a sound and brief description of this dolphin.
     * Overrides the abstract makeSound() method from Animal.
     *
     * @return formatted String with sound and animal description
     */
    @Override
    public String makeSound() {
        return "Click-click! I am " + getName() + ", a "
                + getAge() + " year old " + getColour() + " dolphin.";
    } // end makeSound

    // -------------------- Swimmable interface methods -----------------------

    /**
     * Describes how the dolphin swims.
     */
    @Override
    public void swim() {
        System.out.println(getName() + " leaps gracefully through the waves!");
    } // end swim

    /**
     * Does
     * a fin health check on the dolphin.
     */
    @Override
    public void checkFinHealth() {
        System.out.println("[Fin Health Check] " + getName()
                + "'s dorsal fin and tail flukes are examined - all looking healthy.");
    } // end checkFinHealth

    // -------------------- Utility method ------------------------------------

    /**
     * Returns a full details string including the dolphin-specific variable.
     *
     * @return formatted String with all dolphin details
     */
    @Override
    public String getDetails() {
        return super.getDetails() + " | Trained Tricks: " + trainedTricks
                + " | Type: Dolphin";
    } // end getDetails

} // end class Dolphin
