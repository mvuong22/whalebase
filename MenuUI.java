import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Handles user input via console
public class MenuUI {
    private AppController controller = new AppController();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\nWelcome to WhaleBase Explorer! Let's add some whales to your WhaleLog!\n");
            System.out.println("<====== WB MENU ======>");
            System.out.println("\n1. Add Whale");
            System.out.println("2. Remove Whale");
            System.out.println("3. Update Whale");
            System.out.println("4. Display All Whales");
            System.out.println("5. Conservation Overview");
            System.out.println("6. Exit");
            System.out.print("\nSelect an option: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> addWhale();
                case "2" -> removeWhale();
                case "3" -> updateWhale();
                case "4" -> controller.displayAll();
                case "5" -> controller.conservationOverview();
                case "6" -> {
                    running = false;
                    System.out.println("\nExiting Whale Base... Wave you later!\n");
                }
                default -> System.out.println("\nInvalid input. Try again!\n");
            }
        }
    }

    private void addWhale() {
        System.out.println("\nAdd Whale:");
        System.out.println("1. Manual Entry");
        System.out.println("2. Upload from Text File");
        System.out.print("\nChoose an option: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> addWhaleManually(); // manual entry
            case "2" -> uploadWhalesFromFile(); // file upload
            default -> System.out.println("\nNice try, that's an invalid choice... Returning to menu!\n");
        }
    }

    // manual entry method
    private void addWhaleManually() {
        String id;
        while (true) {
            System.out.print("Enter species ID (6 digits, no letters or spaces): ");
            id = scanner.nextLine().trim();
            if (id.matches("\\d{6}")) break;
            System.out.println("\nAlmost! Let's try doing only 6 digits.\n");
        }

        String sciName;
        while (true) {
            System.out.print("Enter scientific name (letters only): ");
            sciName = scanner.nextLine().trim();
            if (!sciName.isEmpty() && sciName.matches("[a-zA-Z ]+")) break;
            System.out.println("\nNice Try! Only letters and spaces are allowed.\n");
        }

        List<String> commonNames;
        while (true) {
            System.out.print("Enter common names (letters only): ");
            String input = scanner.nextLine().trim();
            String[] names = input.split(",");
            boolean valid = true;
            for (String name : names) {
                if (!name.trim().matches("[a-zA-Z ]+")) {
                    valid = false;
                    break;
                }
            }
            if (valid && names.length > 0) {
                commonNames = List.of(names);
                break;
            }
            System.out.println("\nSneaky Sneaky! Remember only letters and spaces allowed!\n");
        }

        double length;
        while (true) {
            System.out.print("Enter average length in meters (positive number): ");
            try {
                length = Double.parseDouble(scanner.nextLine().trim());
                if (length > 0) break;
                System.out.println("\nSilly, the whale's length can't be 0 or less.\n");
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Try again!");
            }
        }

        double weight;
        while (true) {
            System.out.print("Enter average weight in tons (positive number): ");
            try {
                weight = Double.parseDouble(scanner.nextLine().trim());
                if (weight > 0) break;
                System.out.println("\nHaha, the whale's weight must be greater than zero.\n");
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Try again!\n");
            }
        }

        String status;
        while (true) {
            System.out.println("Select conservation status:");
            System.out.println("1. Least Concern");
            System.out.println("2. Vulnerable");
            System.out.println("3. Endangered");
            System.out.println("4. Critically Endangered");
            System.out.print("Enter number (1-4): ");

            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> status = "Least Concern";
                case "2" -> status = "Vulnerable";
                case "3" -> status = "Endangered";
                case "4" -> status = "Critically Endangered";
                default -> {
                    System.out.println("\nThat's an invalid choice. Please enter 1, 2, 3, or 4.");
                    continue;
                }
            }
            break;
        }

        List<String> habitats;
        while (true) {
            System.out.print("Enter habitat regions (letters only): ");
            String input = scanner.nextLine().trim();
            String[] regions = input.split(",");
            boolean valid = true;
            for (String region : regions) {
                if (!region.trim().matches("[a-zA-Z ]+")) {
                    valid = false;
                    break;
                }
            }
            if (valid && regions.length > 0) {
                habitats = List.of(regions);
                break;
            }
            System.out.println("\nOh no, invalid input. Only letters and spaces allowed!\n");
        }

        boolean success = controller.addWhale(id, sciName, commonNames, length, weight, status, habitats);
        if (success) System.out.println("Awesome, your whale has been added. See if you can add some more!");
        else System.out.println("\nOh fish sticks! It failed to add a whale. Let's try again!\n");
    }

    //Upload from file method
    private void uploadWhalesFromFile() {
        System.out.print("Enter file path to upload whales: ");
        String filePath = scanner.nextLine().trim();

        try (Scanner fileScanner = new Scanner(new java.io.File(filePath))) {
            int count = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                // In order for users to upload their whale file correctly: no spaces around commas, and the lines are used for multiple names/habitats.
                // Expected format: id,sciName,common1|common2,length,weight,status,habitat1|habitat2
                String[] parts = line.split(",");
                if (parts.length != 7) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                String id = parts[0].trim();
                String sciName = parts[1].trim();
                List<String> commonNames = List.of(parts[2].trim().split("\\|"));
                double length = Double.parseDouble(parts[3].trim());
                double weight = Double.parseDouble(parts[4].trim());
                String status = parts[5].trim();
                List<String> habitats = List.of(parts[6].trim().split("\\|"));

                // user upload whale file validation
                if (!WhaleValidator.isValidSpeciesId(id)) {
                    System.out.println("Skipping invalid ID: " + id);
                    continue;
                }

                if (!WhaleValidator.isValidScientificName(sciName)) {
                    System.out.println("Skipping invalid scientific name: " + sciName);
                    continue;
                }

                if (!WhaleValidator.isValidList(commonNames)) {
                    System.out.println("Skipping invalid common names: " + parts[2]);
                    continue;
                }

                try {
                    length = Double.parseDouble(parts[3].trim());
                    if (!WhaleValidator.isValidPositiveDouble(length)) {
                        System.out.println("Skipping invalid length: " + parts[3]);
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid length (not a number): " + parts[3]);
                    continue;
                }

                try {
                    weight = Double.parseDouble(parts[4].trim());
                    if (!WhaleValidator.isValidPositiveDouble(weight)) {
                        System.out.println("Skipping invalid weight: " + parts[4]);
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid weight (not a number): " + parts[4]);
                    continue;
                }

                if (!WhaleValidator.isValidConservationStatus(status)) {
                    System.out.println("Skipping invalid conservation status: " + status);
                    continue;
                }

                if (!WhaleValidator.isValidList(habitats)) {
                    System.out.println("Skipping invalid habitats: " + parts[6]);
                    continue;
                }

                boolean success = controller.addWhale(id, sciName, commonNames, length, weight, status, habitats);
                if (success) count++;
                else System.out.println("\nOh no! Failed to add whale from line: " + line);
            }
            System.out.println(count + " whales successfully uploaded!");
        } catch (Exception e) {
            System.out.println("\nFailed to discover your whale file. Make sure the path is correct and file is formatted properly!\n");
        }
    }

    private void removeWhale() {
        if (controller.getAllWhales().isEmpty()) {
            System.out.println("\nNo whales in Whale Base yet... Maybe try adding some whales first!\n");
            return;
        }

        System.out.print("Enter species ID to remove: ");
        String id = scanner.nextLine().trim();
        Whale whale = controller.getWhaleById(id);
        if (whale == null) {
            System.out.println("\nHuh, Whale not found?! Try again!\n");
            return;
        }

        System.out.print("Are you sure you want to delete this whale? (yes/no): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("yes")) {
            boolean success = controller.removeWhale(id);
            if (success) System.out.println("\nAw, the whale has been released, goodbye!\n");
        } else {
            System.out.println("\nWhale entry cancelled. What a relief, let's keep collecting more! \n");
        }
    }

    private void updateWhale() {
        if (controller.getAllWhales().isEmpty()) {
            System.out.println("\nLooks like there's no whales in your log, nothing to update yet!\n");
            return;
        }

        System.out.print("Enter species ID to update: ");
        String id = scanner.nextLine().trim();
        Whale whale = controller.getWhaleById(id);
        if (whale == null) {
            System.out.println("\nScanning...Whale not found! Check the species ID and try again.\n");
            return;
        }

        boolean updating = true;
        while (updating) {
            System.out.println("\n--- Update Whale ---");
            System.out.println("1. Scientific Name");
            System.out.println("2. Common Names");
            System.out.println("3. Average Length");
            System.out.println("4. Average Weight");
            System.out.println("5. Conservation Status");
            System.out.println("6. Habitat Regions");
            System.out.println("0. Finish Updating");

            System.out.print("Select field to update: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.print("Enter new Scientific Name: ");
                    String newSci = scanner.nextLine().trim();
                    if (newSci.matches("[a-zA-Z ]+")) whale.setScientificName(newSci);
                    else System.out.println("\nInvalid input! Letters only.\n");
                }
                case "2" -> {
                    System.out.print("Enter new common name(s): ");
                    String[] names = scanner.nextLine().trim().split(",");
                    boolean valid = true;
                    for (String n : names) if (!n.trim().matches("[a-zA-Z ]+")) valid = false;
                    if (valid) whale.setCommonNames(List.of(names));
                    else System.out.println("\nInvalid input! Letters only.\n");
                }
                case "3" -> {
                    System.out.print("Enter new average length: ");
                    try {
                        double len = Double.parseDouble(scanner.nextLine().trim());
                        if (len > 0) whale.setAvgLengthMeters(len);
                        else System.out.println("\nRemember, the length must be over 0!\n");
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid number!\n");
                    }
                }
                case "4" -> {
                    System.out.print("Enter new average weight: ");
                    try {
                        double w = Double.parseDouble(scanner.nextLine().trim());
                        if (w > 0) whale.setAvgWeightTons(w);
                        else System.out.println("\nWeight can't be 0 or less!\n");
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid number!\n");
                    }
                }
                case "5" -> {
                    System.out.println("Select Conservation Status:");
                    System.out.println("1. Least Concern");
                    System.out.println("2. Vulnerable");
                    System.out.println("3. Endangered");
                    System.out.println("4. Critically Endangered");
                    String st = scanner.nextLine().trim();
                    switch (st) {
                        case "1" -> whale.setConservationStatus(ConservationStatus.LEAST_CONCERN);
                        case "2" -> whale.setConservationStatus(ConservationStatus.VULNERABLE);
                        case "3" -> whale.setConservationStatus(ConservationStatus.ENDANGERED);
                        case "4" -> whale.setConservationStatus(ConservationStatus.CRITICALLY_ENDANGERED);
                        default -> System.out.println("\nInvalid input! Choose 1-4.\n");
                    }
                }
                case "6" -> {
                    System.out.print("Enter new Habitat Regions: ");
                    String[] habs = scanner.nextLine().trim().split(",");
                    boolean valid = true;
                    for (String h : habs) if (!h.trim().matches("[a-zA-Z ]+")) valid = false;
                    if (valid) whale.setHabitatRegions(List.of(habs));
                    else System.out.println("\nInvalid input! Letters only.\n");
                }
                case "0" -> {
                    updating = false;
                    System.out.println("\nAll changes saved!\n");
                }
                default -> System.out.println("\nInvalid choice!\n");
            }
        }
    }
}
