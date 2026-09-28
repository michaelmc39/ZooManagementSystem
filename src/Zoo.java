import java.io.*;
import java.util.Scanner;

/**
 * Zoo class - manages a collection of Animal objects stored in an array.
 * Provides methods to add, remove, modify, search, report, perform daily care,
 * and read/write animal data to and from text files.
 */
public class Zoo {

    // ----------------------- Constants ------------------------------------

    private static final int    MAX_ANIMALS = 50;        // maximum capacity
    private static final String ZOO_FILE    = "ZooDetails.txt";
    private static final String ANIMAL_FILE = "AnimalDetails.txt";

    // ----------------------- Instance variables ---------------------------

    private String  zooName;
    private Animal[] animals;   // array of Animal objects
    private int     count;      // number of animals currently stored

    /**
     * Constructor - This starts the zoo with a name and an empty animal array.
     *
     * @param zooName the name of the zoo
     */
    public Zoo(String zooName) {
        this.zooName = zooName;
        this.animals = new Animal[MAX_ANIMALS];
        this.count   = 0;
    } // end constructor

    // ========================= ADD ANIMAL =================================

    /**
     * Adds an animal to the zoo array if capacity allows.
     *
     * @param animal the Animal object to add
     * @return true if added successfully, false if the zoo is full
     */
    public boolean addAnimal(Animal animal) {
        if (count >= MAX_ANIMALS) {
            System.out.println("Zoo is full - cannot add more animals.");
            return false;
        }
        animals[count] = animal;
        count++;
        System.out.println(animal.getName() + " has been added to " + zooName + ".");
        return true;
    } // end addAnimal

    // ========================= REMOVE ANIMAL ==============================

    /**
     * Removes an animal from the zoo by name (case-insensitive).
     * Shifts remaining elements left to fill the gap.
     *
     * @param name the name of the animal to remove
     * @return true if found and removed, false if not found
     */
    public boolean removeAnimal(String name) {
        int index = findIndexByName(name);
        if (index == -1) {
            System.out.println("No animal named '" + name + "' was found.");
            return false;
        }
        // shift elements left to close the gap
        for (int i = index; i < count - 1; i++) {
            animals[i] = animals[i + 1];
        }
        animals[count - 1] = null; // clear the last slot
        count--;
        System.out.println(name + " has been removed from " + zooName + ".");
        return true;
    } // end removeAnimal

    // ========================= MODIFY ANIMAL ==============================

    /**
     * Allows the user to modify details of an existing animal by name.
     *
     * @param scanner the Scanner for reading user input
     */
    public void modifyAnimal(Scanner scanner) {
        System.out.print("Enter the name of the animal to modify: ");
        String name  = scanner.nextLine().trim();
        int    index = findIndexByName(name);

        if (index == -1) {
            System.out.println("No animal named '" + name + "' was found.");
            return;
        }

        Animal a = animals[index];
        System.out.println("Current details: " + a.getDetails());
        System.out.println("What would you like to update?");
        System.out.println("  1. Name");
        System.out.println("  2. Age");
        System.out.println("  3. Colour");
        System.out.println("  4. Weight");
        System.out.println("  5. " + getExtraFieldLabel(a));
        System.out.print("Choice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.print("New name: ");
                String newName = scanner.nextLine().trim();
                if (!newName.isEmpty()) a.setName(newName);
                break;
            case "2":
                System.out.print("New age: ");
                int newAge = readPositiveInt(scanner);
                if (newAge >= 0) a.setAge(newAge);
                break;
            case "3":
                System.out.print("New colour: ");
                String newColour = scanner.nextLine().trim();
                if (!newColour.isEmpty()) a.setColour(newColour);
                break;
            case "4":
                System.out.print("New weight (kg): ");
                double newWeight = readPositiveDouble(scanner);
                if (newWeight > 0) a.setWeight(newWeight);
                break;
            case "5":
                updateExtraField(a, scanner);
                break;
            default:
                System.out.println("Invalid choice - no changes made.");
                return;
        }
        System.out.println("Animal updated successfully.");
        System.out.println("Updated details: " + a.getDetails());
    } // end modifyAnimal

    // ========================= VIEW ALL ANIMALS ===========================

    /**
     * Prints the details of every animal currently registered in the zoo.
     */
    public void viewAllAnimals() {
        if (count == 0) {
            System.out.println("There are no animals currently registered at " + zooName + ".");
            return;
        }
        System.out.println("\n===== Animals at " + zooName + " =====");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + animals[i].getDetails());
        }
        System.out.println("=".repeat(40));
    } // end viewAllAnimals

    // ========================= SEARCH =====================================

    /**
     * Searches for an animal by name or colour and displays its details
     * and calls makeSound() if found.
     *
     * @param query  the search term (name or colour)
     * @param byName true to search by name, false to search by colour
     */
    public void searchAnimals(String query, boolean byName) {
        boolean found = false;
        System.out.println("\n--- Search Results ---");

        for (int i = 0; i < count; i++) {
            Animal a = animals[i];
            boolean match = byName
                    ? a.getName().equalsIgnoreCase(query)
                    : a.getColour().equalsIgnoreCase(query);

            if (match) {
                System.out.println(a.getDetails());
                System.out.println(a.makeSound());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No animals found matching '" + query + "'.");
        }
        System.out.println("---------------------");
    } // end searchAnimals

    // ========================= REPORT =====================================

    /**
     * Prints a report showing the zoo name, total count of each animal type,
     * and the dominant colour across all animals.
     */
    public void printReport() {
        System.out.println("\n========== Zoo Report: " + zooName + " ==========");
        System.out.println("Total animals: " + count);

        // count each type
        int parrots  = 0;
        int dolphins = 0;
        int lions    = 0;

        for (int i = 0; i < count; i++) {
            if (animals[i] instanceof Parrot)   parrots++;
            else if (animals[i] instanceof Dolphin) dolphins++;
            else if (animals[i] instanceof Lion)    lions++;
        }

        System.out.println("  Parrots:  " + parrots);
        System.out.println("  Dolphins: " + dolphins);
        System.out.println("  Lions:    " + lions);
        System.out.println("Dominant colour: " + findDominantColour());
        System.out.println("=".repeat(45));
    } // end printReport

    // ========================= DAILY CARE =================================

    /**
     * Performs daily care for all animals.
     * Calls specialised interface methods for Flyable and Swimmable animals.
     */
    public void performDailyCare() {
        System.out.println("\n===== Daily Care at " + zooName + " =====");

        if (count == 0) {
            System.out.println("No animals to care for today.");
            return;
        }

        for (int i = 0; i < count; i++) {
            Animal a = animals[i];
            System.out.println("\n-- Caring for " + a.getName() + " --");
            System.out.println("Completed feed and health check.");

            // polymorphism via interface - specialised care
            if (a instanceof Flyable) {
                ((Flyable) a).fly();
                ((Flyable) a).checkWingHealth();
            }
            if (a instanceof Swimmable) {
                ((Swimmable) a).swim();
                ((Swimmable) a).checkFinHealth();
            }
        }
        System.out.println("\n===== Daily care complete =====");
    } // end performDailyCare

    // ========================= FILE I/O ===================================

    /**
     * Writes zoo details to ZooDetails.txt and animal records to AnimalDetails.txt.
     * Invalid animals (any blank field) are not written to disk.
     */
    public void saveToFiles() {
        saveZooDetails();
        saveAnimalDetails();
    } // end saveToFiles

    /**
     * Writes the zoo name and animal count to ZooDetails.txt.
     */
    private void saveZooDetails() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(ZOO_FILE));
            writer.println(zooName);
            writer.println(count);
            System.out.println("Zoo details saved to " + ZOO_FILE);
        } catch (IOException e) {
            System.out.println("Error saving zoo details: " + e.getMessage());
        } finally {
            if (writer != null) writer.close();
        }
    } // end saveZooDetails

    /**
     * Writes each valid animal's data to AnimalDetails.txt.
     * Skips any animal with a blank field.
     */
    private void saveAnimalDetails() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(ANIMAL_FILE));
            int saved = 0;
            for (int i = 0; i < count; i++) {
                Animal a = animals[i];
                if (a != null && isValidAnimal(a)) {
                    writer.println(buildAnimalRecord(a));
                    saved++;
                }
            }
            System.out.println(saved + " animal record(s) saved to " + ANIMAL_FILE);
        } catch (IOException e) {
            System.out.println("Error saving animal details: " + e.getMessage());
        } finally {
            if (writer != null) writer.close();
        }
    } // end saveAnimalDetails

    /**
     * Reads zoo details and animal records from the two text files
     * to re-populate the zoo on startup.
     */
    public void loadFromFiles() {
        loadZooDetails();
        loadAnimalDetails();
    } // end loadFromFiles

    /**
     * Reads the zoo name from ZooDetails.txt if it exists.
     */
    private void loadZooDetails() {
        File file = new File(ZOO_FILE);
        if (!file.exists()) return;

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            if (scanner.hasNextLine()) {
                zooName = scanner.nextLine().trim();
            }
            System.out.println("Zoo details loaded from " + ZOO_FILE);
        } catch (IOException e) {
            System.out.println("Error loading zoo details: " + e.getMessage());
        } finally {
            if (scanner != null) scanner.close();
        }
    } // end loadZooDetails

    /**
     * Reads animal records from AnimalDetails.txt and reconstructs objects.
     * Each line follows the format: TYPE|name|age|colour|weight|extraField
     */
    private void loadAnimalDetails() {
        File file = new File(ANIMAL_FILE);
        if (!file.exists()) return;

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            int loaded = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    Animal a = parseAnimalRecord(line);
                    if (a != null) {
                        animals[count] = a;
                        count++;
                        loaded++;
                    }
                }
            }
            System.out.println(loaded + " animal record(s) loaded from " + ANIMAL_FILE);
        } catch (IOException e) {
            System.out.println("Error loading animal details: " + e.getMessage());
        } finally {
            if (scanner != null) scanner.close();
        }
    } // end loadAnimalDetails

    // ========================= HELPER METHODS =============================

    /**
     * Searches the array for an animal matching the given name (case-insensitive).
     *
     * @param name the name to search for
     * @return the index in the array, or -1 if not found
     */
    private int findIndexByName(String name) {
        for (int i = 0; i < count; i++) {
            if (animals[i] != null && animals[i].getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    } // end findIndexByName

    /**
     * Determines the dominant colour among all animals in the zoo.
     * If two colours are equally common the first found is returned.
     *
     * @return the most frequently occurring colour, or "N/A" if zoo is empty
     */
    private String findDominantColour() {
        if (count == 0) return "N/A";

        String dominantColour = "";
        int    maxCount       = 0;

        for (int i = 0; i < count; i++) {
            String colour    = animals[i].getColour();
            int    colourCount = 0;

            for (int j = 0; j < count; j++) {
                if (animals[j].getColour().equalsIgnoreCase(colour)) {
                    colourCount++;
                }
            }

            if (colourCount > maxCount) {
                maxCount       = colourCount;
                dominantColour = colour;
            }
        }
        return dominantColour;
    } // end findDominantColour

    /**
     * Checks that none of an animal's String fields are blank.
     *
     * @param a the Animal to validate
     * @return true if all fields are non-empty, false otherwise
     */
    private boolean isValidAnimal(Animal a) {
        return !a.getName().isEmpty()
            && !a.getColour().isEmpty()
            && a.getAge()    >= 0
            && a.getWeight() > 0;
    } // end isValidAnimal

    /**
     * Builds a pipe-delimited record string for a given animal.
     * Format: TYPE|name|age|colour|weight|extraField
     *
     * @param a the Animal to serialise
     * @return a pipe-delimited String representing the animal
     */
    private String buildAnimalRecord(Animal a) {
        String base = a.getType() + "|" + a.getName() + "|"
                    + a.getAge()  + "|" + a.getColour() + "|" + a.getWeight();

        if (a instanceof Parrot) {
            return base + "|" + ((Parrot) a).getVocabularySize();
        } else if (a instanceof Dolphin) {
            return base + "|" + ((Dolphin) a).getTrainedTricks();
        } else if (a instanceof Lion) {
            return base + "|" + ((Lion) a).getManeColour();
        }
        return base;
    } // end buildAnimalRecord

    /**
     * Parses a pipe-delimited record string and returns an Animal object.
     *
     * @param line the pipe-delimited line from file
     * @return an Animal object, or null if the record is malformed
     */
    private Animal parseAnimalRecord(String line) {
        try {
            String[] parts = line.split("\\|");
            String type   = parts[0];
            String name   = parts[1];
            int    age    = Integer.parseInt(parts[2]);
            String colour = parts[3];
            double weight = Double.parseDouble(parts[4]);
            String extra  = parts[5];

            switch (type) {
                case "Parrot":
                    return new Parrot(name, age, colour, weight,
                                      Integer.parseInt(extra));
                case "Dolphin":
                    return new Dolphin(name, age, colour, weight,
                                       Integer.parseInt(extra));
                case "Lion":
                    return new Lion(name, age, colour, weight, extra);
                default:
                    System.out.println("Unknown animal type in file: " + type);
                    return null;
            }
        } catch (Exception e) {
            System.out.println("Skipping malformed record: " + line);
            return null;
        }
    } // end parseAnimalRecord

    /**
     * Returns a label describing the extra field for a given animal type.
     *
     * @param a the animal
     * @return a String label for the animal's extra field
     */
    private String getExtraFieldLabel(Animal a) {
        if (a instanceof Parrot)  return "Vocabulary Size (words)";
        if (a instanceof Dolphin) return "Trained Tricks";
        if (a instanceof Lion)    return "Mane Colour";
        return "Extra Field";
    } // end getExtraFieldLabel

    /**
     * Updates the type-specific extra field of an animal.
     *
     * @param a       the animal to update
     * @param scanner the Scanner for user input
     */
    private void updateExtraField(Animal a, Scanner scanner) {
        if (a instanceof Parrot) {
            System.out.print("New vocabulary size: ");
            int v = readPositiveInt(scanner);
            if (v >= 0) ((Parrot) a).setVocabularySize(v);

        } else if (a instanceof Dolphin) {
            System.out.print("New number of trained tricks: ");
            int t = readPositiveInt(scanner);
            if (t >= 0) ((Dolphin) a).setTrainedTricks(t);

        } else if (a instanceof Lion) {
            System.out.print("New mane colour: ");
            String m = scanner.nextLine().trim();
            if (!m.isEmpty()) ((Lion) a).setManeColour(m);
        }
    } // end updateExtraField

    /**
     * Reads and validates a positive integer from the Scanner.
     *
     * @param scanner the Scanner to read from
     * @return the validated integer, or -1 if invalid
     */
    public int readPositiveInt(Scanner scanner) {
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            if (value < 0) {
                System.out.println("Value must be 0 or greater.");
                return -1;
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered.");
            return -1;
        }
    } // end readPositiveInt

    /**
     * Reads and validates a positive double from the Scanner.
     *
     * @param scanner the Scanner to read from
     * @return the validated double, or -1 if invalid
     */
    public double readPositiveDouble(Scanner scanner) {
        try {
            double value = Double.parseDouble(scanner.nextLine().trim());
            if (value <= 0) {
                System.out.println("Value must be greater than 0.");
                return -1;
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered.");
            return -1;
        }
    } // end readPositiveDouble

    // ========================= GETTERS ====================================

    public String getZooName() { return zooName; }
    public int    getCount()   { return count;   }

} // end class Zoo
