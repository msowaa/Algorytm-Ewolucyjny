package app;

/**
 * Klasa pozwalająca na przechowywanie informacji jaki wynik funkcji przystosowania
 * uzyskał osobnik w procesie adaptacji.
 */
public class SpecimenAdaption {
    
    /**
     * Osobnik
     */
    private final Specimen specimen;
    
    /**
     * Uzyskany wynik adaptacji
     */
    private final int result;

    /**
     * Konstruktor adaptacji osobnika
     * @param specimen osobnik
     * @param result uzyskany wynik
     */
    public SpecimenAdaption(Specimen specimen, int result) {
        this.specimen = specimen;
        this.result = result;
    }

    @Override
    public String toString() {
        return "SpecimenAdaption{" + "specimen=" + specimen + ", result=" + result + '}';
    }

    public Specimen getSpecimen() {
        return specimen;
    }

    public int getResult() {
        return result;
    }

}
