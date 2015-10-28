package app;

import app.utils.AlgorithmUtil;
import java.util.ArrayList;

public class Main {
    
    public static final boolean TEST = false;
    
    private static void startAlgorithm() {
        startAlgorithm(0);
    }
    
    /**
     * Metoda pozwalająca na wykonanie algorytmu.
     * @param maxIterations parametr umożliwiający iterowanie bez sprawdzania
     * kryterium stopu. Musi być większy od 0, aby uaktywnić funkcję
     */
    private static void startAlgorithm(int maxIterations) {
        ArrayList<Specimen> currentPopulation, previousPopulation = new ArrayList<>();
        if (TEST) {
            currentPopulation = new ArrayList<>();;
            currentPopulation.add(new Specimen("00110"));
            currentPopulation.add(new Specimen("00101"));
            currentPopulation.add(new Specimen("01101"));
            currentPopulation.add(new Specimen("10101"));
            currentPopulation.add(new Specimen("11010"));
            currentPopulation.add(new Specimen("10010"));
            currentPopulation.add(new Specimen("01000"));
            currentPopulation.add(new Specimen("00101"));
        }
        else {
            currentPopulation = AlgorithmUtil.generatePopulation();
        }
        
        int it = 0;
        
        long start = System.currentTimeMillis();
        
        if (maxIterations > 0) {
            for (it = 0; it < maxIterations; it++) {
                previousPopulation = currentPopulation;
                currentPopulation = AlgorithmUtil.iterate(it, currentPopulation);
            }
        }
        else {
            do {
                previousPopulation = currentPopulation;
                currentPopulation = AlgorithmUtil.iterate(it++, currentPopulation);
            } while (!AlgorithmUtil.finalStop(previousPopulation, currentPopulation));
        }
        
        long end = System.currentTimeMillis();
        
        System.out.println();
        System.out.println("--- WYNIK PRACY ALGORYTMU ---");
        System.out.println("Liczba wykonanych iteracji: " + it);
        System.out.println("Obliczenia wykonano w czasie " + (end - start) + " ms");
        // wynik pracy algorytmu (obecna populacja)
        System.out.println("Osobnik, dla którego wartość funkcji przystosowania JEST największa: " + AlgorithmUtil.findSolution(currentPopulation));
        // porównanie do wyniku uzyskanego w poprzedniej populacji
        System.out.println("Osobnik, dla którego wartość funkcji przystosowania BYŁA największa: " + AlgorithmUtil.findSolution(previousPopulation));
        if (TEST) {
            System.out.println("* Uruchomiono w trybie testowym");
        }
    }
    
    public static void main(String[] args) {
        startAlgorithm();
    }
    
}
