package app.utils;

import app.Main;
import app.Specimen;
import app.SpecimenAdaption;
import app.SpecimenSelection;
import app.Pair;
import java.util.ArrayList;
import java.util.Random;

public class AlgorithmUtil {
    
    /**
     * Liczba bitów na ilu zapisywana jest informacja
     */
    private final static int BIN_BITS = 5;

    /**
     * Instancja generatora liczb pseudolosowych
     */
    private final static Random random = new Random();

    /**
     * Rozmiar populacji
     */
    private static final int K = 4;
    
    /**
     * Przestrzeń poszukiwań
     */
    private static final int BOUND = 31;

    /**
     * Prawdopodobieństwo krzyżowania
     */
    private static final double P_K = 0.75;

    /**
     * Prawdopodobieństwo mutowania
     */
    private static final double P_M = 0.1;

    /**
     * Metoda obliczająca wartość funkcji przystosowania.
     *
     * @param specimen osobnik dla którego będzie wyliczana wartość funkcji
     * @return wartość funkcji przystosowania
     */
    private static int calculateAdapt(Specimen specimen) {
        return 2 * specimen.getValue() + 1;
    }

    /**
     * Krzyżowanie osobników
     * @param pair para osobników
     * @param crossOverPoint punkt krzyżowania
     * @return skrzyżowana para osobników
     */
    private static Pair crossOver(Pair pair, int crossOverPoint) {
        String a = decToBin(pair.getA().getValue());
        String b = decToBin(pair.getB().getValue());
        
        StringBuilder resultA = new StringBuilder();
        StringBuilder resultB = new StringBuilder();
        
        for (int index = 0; index < BIN_BITS; index++) {
            if (index >= crossOverPoint) {
                resultA.append(b.charAt(index));
                resultB.append(a.charAt(index));
            }
            else {
                resultA.append(a.charAt(index));
                resultB.append(b.charAt(index));
            }
        }
        
        return new Pair(new Specimen(resultA.toString()), new Specimen(resultB.toString()));
    }
    
    /**
     * Metoda obliczająca wycinek koła dla danego osobnika.
     * @param adaption uzyskany przez osobnika wynik adaptacji
     * @param results wszystkie wyniki adaptacji uzyskane przez populację
     * @return wycinek koła wyrażony w procentach (%)
     */
    private static double calculateClippingWheel(SpecimenAdaption adaption, ArrayList<SpecimenAdaption> results) {
        double total = 0.0;
        for (SpecimenAdaption result : results) {
            total += result.getResult();
        }
        return (double) ((adaption.getResult() * 100) / total);
    }
    
    /**
     * Metoda obliczająca adaptacje populacji
     * @param population populacja
     * @return adaptacje
     */
    private static ArrayList<SpecimenAdaption> calculateAdaptions(ArrayList<Specimen> population) {
        System.out.println("Ocena przystosowania osobników do populacji:");
        ArrayList<SpecimenAdaption> result = new ArrayList<>();
        population.stream().forEach((specimen) -> {
            SpecimenAdaption adaption = new SpecimenAdaption(specimen, calculateAdapt(specimen));
            result.add(adaption);
            System.out.println("\t- " + adaption);
        });
        return result;
    }
    
    /**
     * Metoda wyznaczająca punkty krzyżowania
     * @param populationSize wielkość populacji
     * @return punkty krzyżowania
     */
    private static int[] randomCrossOverPoints(int populationSize) {
        int[] crossOverPoints = new int[populationSize / 2];
        for (int index = 0; index < crossOverPoints.length; index++) {
            crossOverPoints[index] = randomCrossoverPoint();
        }
        return crossOverPoints;
    }
    
    /**
     * Metoda losująca liczby dla metody koła ruletki
     * @param adaptionsSize liczba adaptacji
     * @return wylosowane liczby
     */
    private static int[] generateClippingWheel(int adaptionsSize) {
        int[] drawnClippingWheel = new int[adaptionsSize];
        for (int index = 0; index < drawnClippingWheel.length; index++) {
            drawnClippingWheel[index] = randomClippingWheel();
        }
        return drawnClippingWheel;
    }
    
    /**
     * Metoda generująca losową populację
     * @return populacja
     */
    public static ArrayList<Specimen> generatePopulation() {
        ArrayList<Specimen> result = new ArrayList<>();
        for (int index = 0; index < K; index++) {
            result.add(randomSpecimen(BOUND));
        }
        return result;
    }
    
    /**
     * Metoda generująca populację rodzicielską za pomocą metody koła ruletki
     * @param drawnClippingWheel wyznaczone wycinki koła
     * @param selections wyselekcjonowane osobniki
     * @return populacja rodzicielska
     */
    private static ArrayList<Specimen> generateParentPopulation(int[] drawnClippingWheel, ArrayList<SpecimenSelection> selections) {
        ArrayList<Specimen> result = new ArrayList<>();
        for (int index = 0; index < drawnClippingWheel.length; index++) {
            double position = 0.0;
            for (SpecimenSelection selection : selections) {
                if (position <= drawnClippingWheel[index] && selection.getResult() + position > drawnClippingWheel[index]) {
                    result.add(selection.getAdaption().getSpecimen());
                    break;
                }
                position += selection.getResult();
            }
        }
        return result;
    }
    
    /**
     * Metoda losująca prawdopodobieństwo
     * @param populationSize wielkość populacji
     * @return prawdopodobieństwo wystąpienia zdarzenia dla populacji
     */
    private static double[] randomProbability(int populationSize) {
        double[] result = new double[populationSize / 2];
        for (int index = 0; index < result.length; index++) {
            result[index] = randomProbability();
        }
        return result;
    }
    
    /**
     * Metoda generująca kolejną populację
     * @param parentPopulation populacja rodzicielska
     * @param mutationPropability wylosowane prawdopodobieństwo mutacji dla każdej z par
     * @param crossOverProbability wylosowane prawdopodobieństwo krzyżowania dla każdej z par
     * @param crossOverPoints wylosowany punkt krzyżowania dla każdej z par
     * @return następna generacja populacji
     */
    private static ArrayList<Specimen> nextGeneration(ArrayList<Specimen> parentPopulation, double[] mutationPropability, double[] crossOverProbability, int[] crossOverPoints) {
        ArrayList<Specimen> result = new ArrayList<>();
        System.out.println("Pary:");
        for (int index = 0; index < parentPopulation.size() && index / 2 < crossOverProbability.length && index / 2 < crossOverPoints.length; index += 2) {
            Pair pair = new Pair(parentPopulation.get(index), parentPopulation.get(index + 1));
            System.out.print("\t- " + pair);
            if (crossOverProbability[index / 2] < P_K) {
                pair = crossOver(pair, crossOverPoints[index / 2]);
                System.out.print(" [KRZYŻOWANIE]");
            }
            if (!Main.TEST && mutationPropability[index / 2] < P_M) {
                pair = mutate(pair);
                System.out.print(" [MUTACJA]");
            }
            System.out.println();
            result.add(pair.getA());
            result.add(pair.getB());
        }
        return result;
    }
    
    /**
     * Selekcja populacji
     * @param adaptions wynik adaptacji uzyskany przez populację
     * @return wyselekcjonowane osobniki
     */
    private static ArrayList<SpecimenSelection> calculateSelections(ArrayList<SpecimenAdaption> adaptions) {
        ArrayList<SpecimenSelection> result = new ArrayList<>();
        System.out.println("Selekcja chromosomów (metoda koła ruletki):");
        for (SpecimenAdaption adaption : adaptions) {
            SpecimenSelection selection = new SpecimenSelection(adaption, calculateClippingWheel(adaption, adaptions));
            result.add(selection);
            System.out.println("\t- " + selection);
        }
        return result;
    }
    
    /**
     * Metoda losująca liczbę dla metody koła ruletki
     * @return liczba całkowita z przedziału od 0 do 100
     */
    private static int randomClippingWheel() {
        return random.nextInt(100);
    }
    
    /**
     * Metoda losująca prawdopodobieństwo wystąpienia zdarzenia.
     * @return prawdopodobieństwo wystąpienia zdrarzenia w postaci
     * liczby rzeczywistej z przedziału od 0 do 1
     */
    private static double randomProbability() {
        return random.nextDouble();
    }
    
    /**
     * Metoda losująca punkt krzyżowania
     * @return punkt krzyżowania
     */
    private static int randomCrossoverPoint() {
        return random.nextInt(BIN_BITS - 1) + 1;
    }
    
    /**
     * Metoda obliczająca średnia wyników uzyskanych w procesie adaptacji
     * @param adaptions wyniki adaptacji populacji
     * @return średnia wyniku adaptacji danej populacji
     */
    private static double calculateAverage(ArrayList<SpecimenAdaption> adaptions) {
        double result = 0.0;
        if (adaptions.isEmpty()) {
            return result;
        }
        for (SpecimenAdaption adaption : adaptions) {
            result += adaption.getResult();
        }
        return result / adaptions.size();
    }
    
    /**
     * Metoda obliczająca kryterium stopu
     * @param previousPopulation poprzednia populacja
     * @param currentPopulation obecna populacja
     * @return true w przypadku gdy osiągnięto kryterium stopu, false w przeciwnym wypadku
     */
    public static boolean finalStop(ArrayList<Specimen> previousPopulation, ArrayList<Specimen> currentPopulation) {
        System.out.print("[Poprzednia] ");
        double a = calculateAverage(calculateAdaptions(previousPopulation));
        System.out.println("Średnia adaptacji populacji: " + a);
        
        System.out.print("[Obecna] ");
        double b = calculateAverage(calculateAdaptions(currentPopulation));
        System.out.println("Średnia adaptacji populacji: " + b);
        return a > b;
    }
    
    /**
     * Metoda obliczająca jedną iterację pracy algorytmu
     * @param it numer iteracji (służy tylko do poprawnego wydrukowania informacji na ekran)
     * @param population obecna populacja
     * @return kolejną generację (populację potomków) 
     */
    public static ArrayList<Specimen> iterate(int it, ArrayList<Specimen> population) {
        System.out.println();
        System.out.println("--- POCZĄTEK ITERACJI " + (it + 1) + " ---");
        System.out.println();
        
        System.out.println("Populacja:");
        population.stream().forEach((specimen) -> {
            System.out.println("\t- " + specimen);
        });
        
        ArrayList<SpecimenAdaption> adaptions = calculateAdaptions(population);
        
        int[] drawnClippingWheel;
        
        if (Main.TEST) {
            drawnClippingWheel = new int[] { 79, 44, 9, 74, 45, 86, 48, 23 };
        } else {
            drawnClippingWheel = generateClippingWheel(adaptions.size());
        }
        
        ArrayList<SpecimenSelection> selections = calculateSelections(adaptions);
        ArrayList<Specimen> parentPopulation = generateParentPopulation(drawnClippingWheel, selections);
        
        System.out.println("Pula rodzicielska:");
        for (Specimen parent : parentPopulation) {
            System.out.println("\t- " + parent);
        }
        
        double[] crossOverProbability;
        if (Main.TEST) {
            crossOverProbability = new double[] { 0.12, 0.73, 0.65, 0.33 };
        }
        else {
            crossOverProbability = randomProbability(parentPopulation.size());
        }
        
        double[] mutationPropability = randomProbability(parentPopulation.size());
        
        int[] crossOverPoints;
        if (Main.TEST) {
            crossOverPoints = new int[] { 3, 4, 3, 2 };
        }
        else {
            crossOverPoints = randomCrossOverPoints(parentPopulation.size());
        }
        
        ArrayList<Specimen> result = nextGeneration(parentPopulation, mutationPropability, crossOverProbability, crossOverPoints);
        
        System.out.println("Potomkowie:");
        result.stream().forEach((specimen) -> {
            System.out.println("\t- " + specimen);
        });
        
        System.out.println();
        System.out.println("--- KONIEC ITERACJI " + (it + 1) + " ---");
        System.out.println();

        return result;
    }
    
    /**
     * Metoda szukająca osobnika dla którego wartość funkcji przystosowania
     * jest największa.
     * @param population populacja osobników branych pod uwagę
     * @return osobnik (reprezentowany przez pojedynczy chromosom) dla którego wartość
     * funkcji przystosowania jest największa
     */
    public static Specimen findSolution(ArrayList<Specimen> population) {
        Specimen result = null;
        for (Specimen specimen : population) {
            if (result == null || result.getValue() < specimen.getValue()) {
                result = specimen;
            }
        }
        return result;
    }

    /**
     * Metoda konwertująca liczbę zapisaną w systemie binarnym na jej
     * reprezentację w systemie dziesiętnym.
     *
     * @param specimen liczba zapisana w systemie binarnym
     * @return liczba zapisana w systemie dziesiętnym
     */
    public static int binToDec(String specimen) {
        return Integer.parseInt(specimen, 2);
    }
    
    /**
     * Metoda zamieniająca zapis dziesiętny na binarny
     * @param number liczba zapisana w systemie dziesiętnym
     * @return zapis binarny liczby
     */
    public static String decToBin(int number) {
        return String.format("%" + BIN_BITS + "s", Integer.toBinaryString(number)).replace(" ", "0");
    }

    /**
     * Metoda generująca obiekt Specimen na podstawie losowej liczby całkowitej.
     *
     * @param bound zakres (od 0 do wartości przekazanej przez parametr)
     * @return zainicjalizowana instancja obiektu klasy Specimen
     */
    public static Specimen randomSpecimen(int bound) {
        return new Specimen(random.nextInt(++bound));
    }
    
    /**
     * Metoda mutująca osobnika
     * @param specimen osobnik, który zostanie zmutowany
     * @return zmutowany osobnik
     */
    private static Specimen mutate(Specimen specimen) {
        String binaryValue = decToBin(specimen.getValue());
        if (binaryValue.charAt(0) == '0') {
            return new Specimen('1' + binaryValue.substring(1));
        }
        else {
            return new Specimen('0' + binaryValue.substring(1));
        }
    }

    /**
     * Metoda mutująca parę osobników
     * @param pair para osobników, która zostanie zmutowana
     * @return para zmutowanych osobników
     */
    private static Pair mutate(Pair pair) {
        return new Pair(mutate(pair.getA()), mutate(pair.getB()));
    }
}
