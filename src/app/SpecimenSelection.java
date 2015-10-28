package app;

/**
 * Klasa pozwalająca na przechowywanie informacji co do selekcji osobnika
 * i uzyskanego wyniku.
 */
public class SpecimenSelection {
    
    /**
     * Adaptacja osobnika
     */
    private final SpecimenAdaption adaption;
    
    /**
     * Uzyskany wynik
     */
    private final double result;

    public double getResult() {
        return result;
    }

    /**
     * Konstruktor selekcji
     * @param adaption adaptacja osobnika
     * @param result uzyskany wynik
     */
    public SpecimenSelection(SpecimenAdaption adaption, double result) {
        this.adaption = adaption;
        this.result = result;
    }

    /**
     * Metoda zwracająca adaptację osobnika
     * @return adaptacja
     */
    public SpecimenAdaption getAdaption() {
        return adaption;
    }

    @Override
    public String toString() {
        return "SpecimenSelection{" + "adaption=" + adaption + ", result=" + result + '}';
    }
    
}
