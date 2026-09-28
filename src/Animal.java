/**
 * Abstract base class representing a generic animal in the zoo.
 * Stores common attributes: name, age, colour, and weight.
 * All specific animal types inherit from this class.
 */
public abstract class Animal {

    // Private instance variables - encapsulation (information hiding)
    private String name;
    private int    age;
    private String colour;
    private double weight;

    /**
     * Parameterised constructor - initialises all four core attributes.
     *
     * @param name   the animal's name
     * @param age    the animal's age in years
     * @param colour the animal's colour
     * @param weight the animal's weight in kg
     */
    public Animal(String name, int age, String colour, double weight) {
        this.name   = name;
        this.age    = age;
        this.colour = colour;
        this.weight = weight;
    } // end constructor

    // ----------------------------- Getters --------------------------------

    public String getName()   { return name;   }
    public int    getAge()    { return age;     }
    public String getColour() { return colour;  }
    public double getWeight() { return weight;  }

    // ----------------------------- Setters --------------------------------

    public void setName(String name)     { this.name   = name;   }
    public void setAge(int age)          { this.age    = age;    }
    public void setColour(String colour) { this.colour = colour; }
    public void setWeight(double weight) { this.weight = weight; }

    // ------------------------- Abstract method ----------------------------

    /**
     * Returns a sound and description for this animal.
     * Must be overridden by every concrete subclass.
     *
     * @return a String describing the animal and its sound
     */
    public abstract String makeSound();

    // ------------------------- Utility method -----------------------------

    /**
     * Returns a formatted summary of the animal's core details.
     *
     * @return String containing name, age, colour and weight
     */
    public String getDetails() {
        return "Name: "   + name
             + " | Age: "    + age    + " years"
             + " | Colour: " + colour
             + " | Weight: " + weight + " kg";
    } // end getDetails

    /**
     * Returns the type of animal (class name) as a String.
     * Used when writing records to file.
     *
     * @return the simple class name of this animal
     */
    public String getType() {
        return this.getClass().getSimpleName();
    } // end getType

} // end class Animal
