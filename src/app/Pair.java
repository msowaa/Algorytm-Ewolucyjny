package app;

/**
 * Klasa pomocnicza pozwalająca na generowanie par osobników i utrzymywanie
 * ich referencji w jednym obiekcie.
 */
public class Pair {
    /**
     * Skojarzone ze sobą osobniki
     */
    private final Specimen a, b;

    /**
     * Konstruktor pary
     * @param a pierwszy osobnik
     * @param b drugi osobnik
     */
    public Pair(Specimen a, Specimen b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Metoda pozwalająca na pobranie referencji na pierwszego osobnika z pary
     * @return pierwszy osobnik
     */
    public Specimen getA() {
        return a;
    }

    /**
     * Metoda pozwalająca na pobranie referencji na drugiego osobnika z pary
     * @return drugi osobnik
     */
    public Specimen getB() {
        return b;
    }

    @Override
    public String toString() {
        return "Pair{" + "a=" + a + ", b=" + b + '}';
    }
    
}
