import java.util.List;

// Validates user input to prevent crashes or invalid data
public class WhaleValidator {

    // Validate species ID is not empty
    public static boolean isValidSpeciesId(String id) {
        return id != null && !id.trim().isEmpty();
    }

    // Validate scientific name
    public static boolean isValidScientificName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    // Validate list of names or regions
    public static boolean isValidList(List<String> list) {
        return list != null && !list.isEmpty();
    }

    // Validate positive numbers for length and weight
    public static boolean isValidPositiveDouble(double value) {
        return value > 0;
    }

    // Validate conservation status
    public static boolean isValidConservationStatus(String status) {
        try {
            ConservationStatus.valueOf(status.toUpperCase().replace(" ", "_"));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

