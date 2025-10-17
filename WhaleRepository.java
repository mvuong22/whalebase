import java.util.ArrayList;
import java.util.List;

// Repository to store and manage whale objects
public class WhaleRepository {
    private List<Whale> whales = new ArrayList<>();

    // Add whale (check duplicates)
    public boolean addWhale(Whale whale) {
        for (Whale w : whales) {
            if (w.getSpeciesId().equalsIgnoreCase(whale.getSpeciesId())) {
                return false; // Duplicate ID
            }
        }
        whales.add(whale);
        return true;
    }

    // Remove whale by ID
    public boolean removeWhale(String id) {
        return whales.removeIf(w -> w.getSpeciesId().equalsIgnoreCase(id));
    }

    // Update whale (replace old with new)
    public boolean updateWhale(String id, Whale updated) {
        for (int i = 0; i < whales.size(); i++) {
            if (whales.get(i).getSpeciesId().equalsIgnoreCase(id)) {
                whales.set(i, updated);
                return true;
            }
        }
        return false;
    }

    // Get all whales
    public List<Whale> getAllWhales() {
        return whales;
    }

    // Get whale by ID
    public Whale getWhaleById(String id) {
        for (Whale w : whales) {
            if (w.getSpeciesId().equalsIgnoreCase(id)) return w;
        }
        return null;
    }
}
