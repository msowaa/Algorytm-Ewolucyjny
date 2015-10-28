package app;

import app.utils.AlgorithmUtil;

/**
 * Klasa pozwalająca na wykonywanie podstawowych działań na liczbie całkowitej
 * zarówno w systemie dziesiętnym jak i binarnym.
 */
public class Specimen {
    
    /**
     * Fenotyp
     */
    private int number;
    
    /**
     * Metoda pozwalająca na pobranie informacji fenotypu
     * @return fenotyp
     */
    public int getValue() {
        return number;
    }
    
    /**
     * Konstruktor pozwalający na utworzenie obiektu MyNumber w oparciu
     * o liczbę zapisaną w systemie dziesiętnym.
     * @param number liczba zapisana w systemie dziesiętnym
     */
    public Specimen(int number) {
        this.number = number;
    }
    
    /**
     * Konstruktor pozwalający na utworzenie obiektu MyNumber w oparciu o
     * liczbę zapisną w systemie binarnym.
     * @param number liczba zapisana w systemie binarnym
     */
    public Specimen(String number) {
        this.number = AlgorithmUtil.binToDec(number);
    }
    
    /**
     * Metoda pozwalająca na zmianę wartości fenotypu
     * @param number fenotyp zapisany w systemie dziesiętnym
     */
    public void setNumber(int number) {
        this.number = number;
    }
    
    /**
     * Metoda pozwalająca na zmianę wartości fenotypu
     * @param number fenotyp zapisany w systemie binarnym 
     */
    public void setNumber(String number) {
        this.number = AlgorithmUtil.binToDec(number);
    }

    @Override
    public String toString() {
        return "Specimen{" + "dec=" + number + ", bin=" + AlgorithmUtil.decToBin(number) + "}";
    }
    
}