import java.util.Scanner;

/**
 The zoo app has the app class with the main method
  It has a console-based menu so the user can manage the zoo.
  It loads in data from files on startup and saves data to files on exit.
 */
public class ZooApp {

    // This is the single Zoo instance used during the session
    private static Zoo     zoo;
    private static Scanner keyboard = new Scanner(System.in);

    /**
      Here is the starting place for the system
      It loads any previously saved data and then displays the main menu.

     @param args command line arguments (This isnt used)
     */
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("  Welcome to the Zoo Management System  ");
        System.out.println("========================================");

        // Initialise the zoo - name is hard-coded as per specification
        zoo = new Zoo("Belfast City Zoo");

        // Run unit tests
        runUnitTests();

        // Requirement 10 - load previously saved data on startup
        System.out.println("\nLoading previously saved data...");
        zoo.loadFromFiles();

        // Display the main menu until the user chooses to exit
        boolean running = true;
        while (running) {
            printMenu();
            String choice = keyboard.nextLine().trim();

            switch (choice) {
                case "1":
                    addAnimalMenu();
                    break;
                case "2":
                    removeAnimalMenu();
                    break;
                case "3":
                    zoo.modifyAnimal(keyboard);
                    break;
                case "4":
                    zoo.viewAllAnimals();
                    break;
                case "5":
                    searchMenu();
                    break;
                case "6":
                    zoo.printReport();
                    break;
                case "7":
                    zoo.performDailyCare();
                    break;
                case "8":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option - please enter a number between 1 and 8.");
            }
        }

        // Requirement 9 - save all data to files when closing the system
        System.out.println("\nSaving zoo data before exit...");
        zoo.saveToFiles();
        System.out.println("\nThank you for using the Zoo Management System. Goodbye!");
        keyboard.close();

    } // end main

    // ========================= MENU DISPLAY ===============================

    /**
     * Prints the main menu options to the console.
     */
    private static void printMenu() {
        System.out.println("\n========== Main Menu ==========");
        System.out.println("  1. Add an animal");
        System.out.println("  2. Remove an animal");
        System.out.println("  3. Modify an animal");
        System.out.println("  4. View all animals");
        System.out.println("  5. Search for an animal");
        System.out.println("  6. Print zoo report");
        System.out.println("  7. Perform daily care");
        System.out.println("  8. Exit");
        System.out.println("================================");
        System.out.print("Enter your choice: ");
    } // end printMenu

    // Below is the code to add an animal

    /**
     * Shows the user how to add a new animal to the zoo.
     * Validates all user inputs before making the object.
     */
    private static void addAnimalMenu() {
        System.out.println("\n--- Add Animal ---");
        System.out.println("Select animal type:");
        System.out.println("  1. Parrot  (Flyable)");
        System.out.println("  2. Dolphin (Swimmable)");
        System.out.println("  3. Lion    (Land animal)");
        System.out.print("Choice: ");

        String typeChoice = keyboard.nextLine().trim();
        if (!typeChoice.equals("1") && !typeChoice.equals("2") && !typeChoice.equals("3")) {
            System.out.println("Invalid type selected.");
            return;
        }

        // Collect and validate common fields
        System.out.print("Name: ");
        String name = keyboard.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be blank - animal not saved.");
            return;
        }

        System.out.print("Age (years): ");
        int age = zoo.readPositiveInt(keyboard);
        if (age < 0) return;

        System.out.print("Colour: ");
        String colour = keyboard.nextLine().trim();
        if (colour.isEmpty()) {
            System.out.println("Colour cannot be blank - animal not saved.");
            return;
        }

        System.out.print("Weight (kg): ");
        double weight = zoo.readPositiveDouble(keyboard);
        if (weight < 0) return;

        // Build the correct subclass based on type choice
        Animal animal = null;

        switch (typeChoice) {
            case "1": // Parrot
                System.out.print("Vocabulary size (Amount of words known): ");
                int vocab = zoo.readPositiveInt(keyboard);
                if (vocab < 0) return;
                animal = new Parrot(name, age, colour, weight, vocab);
                break;

            case "2": // Dolphin
                System.out.print("Amount of trained tricks: ");
                int tricks = zoo.readPositiveInt(keyboard);
                if (tricks < 0) return;
                animal = new Dolphin(name, age, colour, weight, tricks);
                break;

            case "3": // Lion
                System.out.print("Mane colour: ");
                String maneColour = keyboard.nextLine().trim();
                if (maneColour.isEmpty()) {
                    System.out.println("Mane colour cannot be blank - animal not saved.");
                    return;
                }
                animal = new Lion(name, age, colour, weight, maneColour);
                break;
        }

        if (animal != null) {
            zoo.addAnimal(animal);
        }
    } // end addAnimalMenu

    // Below is the code to remove an animal

    /**
     * Shows the user how to remove an animal from the zoo by name.
     */
    private static void removeAnimalMenu() {
        System.out.println("\n--- Remove Animal ---");
        System.out.print("Enter the name of the animal to remove: ");
        String name = keyboard.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be blank.");
            return;
        }
        zoo.removeAnimal(name);
    } // end removeAnimalMenu

    // =Below is the code to search

    /**
     * Shows the search menu and does the search.
     */
    private static void searchMenu() {
        System.out.println("\n--- Search Animals ---");
        System.out.println("  1. Search by name");
        System.out.println("  2. Search by colour");
        System.out.print("Choice: ");

        String choice = keyboard.nextLine().trim();

        if (choice.equals("1")) {
            System.out.print("Enter name to search: ");
            String query = keyboard.nextLine().trim();
            zoo.searchAnimals(query, true);

        } else if (choice.equals("2")) {
            System.out.print("Enter colour to search: ");
            String query = keyboard.nextLine().trim();
            zoo.searchAnimals(query, false);

        } else {
            System.out.println("Invalid search option.");
        }
    } // end searchMenu
    /**
     * Simple unit tests to verify core functionality.
     * Tests addAnimal() and removeAnimal() methods.
     */
    private static void runUnitTests() {
        System.out.println("\n===== Running Unit Tests =====");
        int passed = 0;
        int failed = 0;

        // Unit Test 1 - addAnimal() increases count
        Zoo testZoo = new Zoo("Test Zoo");
        Parrot testParrot = new Parrot("Polly", 4, "Green", 0.9, 50);
        testZoo.addAnimal(testParrot);
        if (testZoo.getCount() == 1) {
            System.out.println("PASSED - Test 1: addAnimal() increases count correctly");
            passed++;
        } else {
            System.out.println("FAILED - Test 1: addAnimal() did not increase count");
            failed++;
        }

        // Unit Test 2 - removeAnimal() returns false when not found
        boolean result = testZoo.removeAnimal("Rex");
        if (!result && testZoo.getCount() == 1) {
            System.out.println("PASSED - Test 2: removeAnimal() returns false when animal not found");
            passed++;
        } else {
            System.out.println("FAILED - Test 2: removeAnimal() behaved incorrectly");
            failed++;
        }

        System.out.println("===== Unit Tests Complete: " + passed + " passed, " + failed + " failed =====\n");
    } // end runUnitTests
} // end class ZooApp
