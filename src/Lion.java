/**
 * Lion - a specific type of land animal.
 * Adds to Animal (no interface because lions cant fly or swim).
 * Additional instance variable: maneColour (the colour of the lion's mane).
 */
public class Lion extends Animal {

    // Additional instance variable specific to Lion
    private String maneColour;

    /**
     * Parameterised constructor for Lion.
     *
     * @param name       the lion's name
     * @param age        the lion's age in years
     * @param colour     the lion's body colour
     * @param weight     the lion's weight in kg
     * @param maneColour the colour of the lion's mane
     */
    public Lion(String name, int age, String colour,
                double weight, String maneColour) {
        super(name, age, colour, weight); // call Animal constructor
        this.maneColour = maneColour;
    } // end constructor

    // ------------------- Getter and Setter for extra variable ---------------

    public String getManeColour() { return maneColour; }

    public void setManeColour(String maneColour) {
        this.maneColour = maneColour;
    } // end setManeColour

    // -------------------- Overridden Animal method --------------------------

    /**
     * Returns a sound and brief description of this lion.
     * Overrides the abstract makeSound() method from Animal.
     *
     * @return formatted String with sound and animal description
     */
    @Override
    public String makeSound() {
        return "Roar! I am " + getName() + ", a "
                + getAge() + " year old " + getColour() + " lion.";
    } // end makeSound

    // -------------------- Utility method ------------------------------------

    /**
     * Returns a full details string including the lion-specific variable.
     *
     * @return formatted String with all lion details
     */
    @Override
    public String getDetails() {
        return super.getDetails() + " | Mane Colour: " + maneColour
                + " | Type: Lion";
    } // end getDetails

} // end class Lion
