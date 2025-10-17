import java.util.ArrayList;
import java.util.List;

// Controls all actions between UI and Repository
public class AppController {
    private WhaleRepository repository = new WhaleRepository();

    // Add a whale
    public boolean addWhale(String id, String scientificName, List<String> commonNames,
                            double length, double weight, String status, List<String> habitats) {
        if (!WhaleValidator.isValidSpeciesId(id) ||
                !WhaleValidator.isValidScientificName(scientificName) ||
                !WhaleValidator.isValidList(commonNames) ||
                !WhaleValidator.isValidPositiveDouble(length) ||
                !WhaleValidator.isValidPositiveDouble(weight) ||
                !WhaleValidator.isValidConservationStatus(status) ||
                !WhaleValidator.isValidList(habitats)) {
            return false;
        }

        // Convert string status to enum
        ConservationStatus cs = ConservationStatus.valueOf(status.toUpperCase().replace(" ", "_"));
        Whale whale = new Whale(id, scientificName, commonNames, length, weight, cs, habitats);
        return repository.addWhale(whale);
    }

    // Remove whale
    public boolean removeWhale(String id) {
        if (repository.getAllWhales().isEmpty()) return false; // nothing to remove
        return repository.removeWhale(id);
    }

    // Update whale
    public boolean updateWhale(String id, String scientificName, List<String> commonNames,
                               double length, double weight, String status, List<String> habitats) {
        if (repository.getAllWhales().isEmpty()) return false; // nothing to update
        Whale existing = repository.getWhaleById(id);
        if (existing == null) return false; // ID not found

        // Convert string status to enum
        ConservationStatus cs = ConservationStatus.valueOf(status.toUpperCase().replace(" ", "_"));
        Whale updated = new Whale(id, scientificName, commonNames, length, weight, cs, habitats);
        return repository.updateWhale(id, updated);
    }

    // Display all whales
    public void displayAll() {
        List<Whale> whales = repository.getAllWhales();
        if (whales.isEmpty()) {
            System.out.println("\nHmmm.. Looks like your log doesn't have any whales yet. Let's try adding some!");
        } else {
            for (Whale w : whales) w.display();
        }
    }

    // Custom action: Conservation Overview
    public void conservationOverview() {
        List<Whale> whales = repository.getAllWhales();
        if (whales.isEmpty()) {
            System.out.println("\nHold your flippers Explorer! There's no whales yet, add some whales first.");
            return;
        }

        System.out.println("\n<====== Conservation Overview ======>\n");
        for (ConservationStatus cs : ConservationStatus.values()) {
            List<String> species = new ArrayList<>();
            for (Whale w : whales) {
                if (w.getConservationStatus() == cs) species.add(w.getScientificName());
            }
            System.out.println(cs + " (" + species.size() + "): " + String.join(", ", species));
        }
        System.out.println("\nSorted & saved!");
    }

    // Public getter for all whales (used by MenuUI)
    public List<Whale> getAllWhales() {
        return repository.getAllWhales();
    }

    // Public getter for single whale by ID (used by MenuUI)
    public Whale getWhaleById(String id) {
        return repository.getWhaleById(id);
    }
}
