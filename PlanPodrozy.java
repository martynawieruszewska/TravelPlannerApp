import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class PlanPodrozy {
    private ArrayList<Podroz> listaPodrozy;

    public PlanPodrozy() {
        listaPodrozy = new ArrayList<>();
    }

    public void dodajPodroz(Podroz p) { 
        // sprawdzenie czy podróż już jest na liście pomaga uniknąć błędów, np. przy chęci usunięcia podróży
        for (Podroz podroz : listaPodrozy) {
            if (podroz.getMiejsce().equalsIgnoreCase(p.getMiejsce())) {
                System.out.println("Podróż do tego miejsca już istnieje!");
                return;
            }
        }
        listaPodrozy.add(p);
    }

    public boolean usunPodroz(String miejsce) { 
        // usuwamy po nazwie miejsca a nie indeksie – wygodniejszy i bardziej czytelny interfejs
        for (int i = 0; i < listaPodrozy.size(); i++) {
            if (listaPodrozy.get(i).getMiejsce().equalsIgnoreCase(miejsce)) {
                listaPodrozy.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean zmienDate(String miejsce, String nowaData) { 
        // tak samo dla wygody szukamy po nazwie miejsca
        for (Podroz p : listaPodrozy) {
            if (p.getMiejsce().equalsIgnoreCase(miejsce)) {
                p.setData(nowaData);
                return true;
            }
        }
        return false;
    }

    public void wyswietlPodroze() {
        if (listaPodrozy.isEmpty()) {
            System.out.println("Brak zapisanych podróży.");
        } else {
            System.out.println("Lista podróży:");
            for (int i = 0; i < listaPodrozy.size(); i++) {
                System.out.println((i + 1) + ". " + listaPodrozy.get(i));
            }
        }
    }

    public void posortujPoDacie() {
        Collections.sort(listaPodrozy);
    }

    public int podliczKoszt() { // koszt całkowity wszystkich podrózy
        int suma = 0;
        for (Podroz p : listaPodrozy) {
            suma += p.getKoszt();
        }
        return suma;
    }
    

   public void saveToFile(File f) throws IOException {
        // Zapis listy podróży do pliku
        BufferedWriter bfw = new BufferedWriter(new FileWriter(f));
        for (Podroz u : listaPodrozy) {
            bfw.write(u.serialize()); // Zapis pojedynczej linii
            bfw.newLine();
        }
        bfw.close();
    }

    public static PlanPodrozy readFromFile(File f) throws IOException {
        // Odczyt listy podróży z pliku
        BufferedReader bfr = new BufferedReader(new FileReader(f));
        String line;
        PlanPodrozy p = new PlanPodrozy();
        while ((line = bfr.readLine()) != null) {
            p.dodajPodroz(Podroz.deserialize(line)); // Odtworzenie obiektu
        }
        bfr.close();
        return p;
    }
}