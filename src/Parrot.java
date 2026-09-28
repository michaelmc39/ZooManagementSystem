/**
 * Parrot - a specific type of animal that can fly.
 * Extends Animal and implements the Flyable interface.
 * Additional instance variable: vocabularySize (number of words known).
 */
public class Parrot extends Animal implements Flyable {

    // Additional instance variable specific to Parrot
    private int vocabularySize;

    /**
     * Parameterised constructor for Parrot.
     *
     * @param name           the parrot's name
     * @param age            the parrot's age in years
     * @param colour         the parrot's colour
     * @param weight         the parrot's weight in kg
     * @param vocabularySize the number of words the parrot knows
     */
    public Parrot(String name, int age, String colour,
                  double weight, int vocabularySize) {
        super(name, age, colour, weight); // call Animal constructor
        this.vocabularySize = vocabularySize;
    } // end constructor

    // ------------------- Getter and Setter for extra variable ---------------

    public int getVocabularySize() { return vocabularySize; }

    public void setVocabularySize(int vocabularySize) {
        this.vocabularySize = vocabularySize;
    } // end setVocabularySize

    // -------------------- Overridden Animal method --------------------------

    /**
     * Returns a sound and brief description of this parrot.
     * Overrides the abstract makeSound() method from Animal.
     *
     * @return formatted String with sound and animal description
     */
    @Override
    public String makeSound() {
        return "Squawk! I am " + getName() + ", a "
                + getAge() + " year old " + getColour() + " parrot.";
    } // end makeSound

    // -------------------- Flyable interface methods -------------------------

    /**
     * Describes how this parrot flies.
     */
    @Override
    public void fly() {
        System.out.println(getName() + " spreads its colourful wings and soars through the air!");
    } // end fly

    /**
     * Performs a wing health check on this parrot.
     */
    @Override
    public void checkWingHealth() {
        System.out.println("[Wing Health Check] " + getName()
                + "'s wings are inspected - feathers and joints look healthy.");
    } // end checkWingHealth

    // -------------------- Utility method ------------------------------------

    /**
     * Returns a full details string including the parrot-specific variable.
     *
     * @return formatted String with all parrot details
     */
    @Override
    public String getDetails() {
        return super.getDetails() + " | Vocabulary: " + vocabularySize + " words"
                + " | Type: Parrot";
    } // end getDetails

} // end class Parrot
