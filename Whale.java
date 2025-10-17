import java.util.List;

// Whale object to store all attributes
public class Whale {
    private String speciesId;               // Unique ID
    private String scientificName;          // Scientific name
    private List<String> commonNames;       // List of common names
    private double avgLengthMeters;         // Average length
    private double avgWeightTons;           // Average weight
    private ConservationStatus conservationStatus; // Conservation status
    private List<String> habitatRegions;    // List of habitat regions

    // Constructor
    public Whale(String speciesId, String scientificName, List<String> commonNames,
                 double avgLengthMeters, double avgWeightTons,
                 ConservationStatus conservationStatus, List<String> habitatRegions) {
        this.speciesId = speciesId;
        this.scientificName = scientificName;
        this.commonNames = commonNames;
        this.avgLengthMeters = avgLengthMeters;
        this.avgWeightTons = avgWeightTons;
        this.conservationStatus = conservationStatus;
        this.habitatRegions = habitatRegions;
    }

    // Getters and Setters
    public String getSpeciesId() { return speciesId; }
    public void setSpeciesId(String speciesId) { this.speciesId = speciesId; }

    public String getScientificName() { return scientificName; }
    public void setScientificName(String scientificName) { this.scientificName = scientificName; }

    public List<String> getCommonNames() { return commonNames; }
    public void setCommonNames(List<String> commonNames) { this.commonNames = commonNames; }

    public double getAvgLengthMeters() { return avgLengthMeters; }
    public void setAvgLengthMeters(double avgLengthMeters) { this.avgLengthMeters = avgLengthMeters; }

    public double getAvgWeightTons() { return avgWeightTons; }
    public void setAvgWeightTons(double avgWeightTons) { this.avgWeightTons = avgWeightTons; }

    public ConservationStatus getConservationStatus() { return conservationStatus; }
    public void setConservationStatus(ConservationStatus conservationStatus) { this.conservationStatus = conservationStatus; }

    public List<String> getHabitatRegions() { return habitatRegions; }
    public void setHabitatRegions(List<String> habitatRegions) { this.habitatRegions = habitatRegions; }

    // Display whale info
    public void display() {
        System.out.println("\nID: " + speciesId);
        System.out.println("Scientific Name: " + scientificName);
        System.out.println("Common Names: " + String.join(", ", commonNames));
        System.out.println("Average Length (m): " + avgLengthMeters);
        System.out.println("Average Weight (tons): " + avgWeightTons);
        System.out.println("Conservation Status: " + conservationStatus);
        System.out.println("Habitat Regions: " + String.join(", ", habitatRegions));
        System.out.println("-------------------------------");
    }
}
