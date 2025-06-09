import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Organizer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlanPodrozy plan = new PlanPodrozy();
        File plik = new File("podroze.txt");

        // Automatyczne wczytanie z pliku
        if (plik.exists()) {
            try {
                plan = PlanPodrozy.readFromFile(plik);
                System.out.println("Dane zostały wczytane z pliku.");
            } catch (IOException e) {
                System.out.println("Nie udało się wczytać danych: " + e.getMessage());
            }
        } else {
            System.out.println("Brak zapisanych podróży – zaczynasz od zera.");
        }

        int wybor;
        do {
            System.out.println("\n=== MENU ORGANIZATORA PODRÓŻY ===");
            System.out.println("1. Wyświetl wszystkie podróże");
            System.out.println("2. Dodaj podróż");
            System.out.println("3. Usuń podróż");
            System.out.println("4. Wyczyść wszystkie podróże");
            System.out.println("5. Zmień datę podróży");
            System.out.println("6. Zmień koszt podróży");
            System.out.println("7. Pokaż całkowity koszt");
            System.out.println("8. Posortuj podróże chronologicznie");
            System.out.println("9. Pokaż statystyki (średni koszt, najdroższa i najtańsza podróż, liczba podróży)");
            System.out.println("10. Zapisz do pliku");
            System.out.println("11. Zakończ program");
            System.out.print("Twój wybór: ");

            wybor = Integer.parseInt(scanner.nextLine());

            switch (wybor) {
                case 1:
                    plan.wyswietlPodroze();
                    break;

                case 2:
                    System.out.print("Miejsce: ");
                    String miejsce = scanner.nextLine();
                    System.out.print("Kraj: ");
                    String kraj = scanner.nextLine();
                    System.out.print("Koszt: ");
                    int koszt = Integer.parseInt(scanner.nextLine());
                    if (koszt < 0) {
                        System.out.println("Koszt nie może być ujemny.");
                        break;
                    }
                    System.out.print("Data (rrrr-mm-dd): ");
                    String data = scanner.nextLine();
                    if (!data.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        System.out.println("Niepoprawny format daty. Użyj rrrr-mm-dd.");
                        break;
                    }
                    plan.dodajPodroz(new Podroz(miejsce, kraj, koszt, data));
                    break;

                case 3:
                    System.out.print("Podaj nazwę miejsca do usunięcia: ");
                    String doUsuniecia = scanner.nextLine();
                    if (plan.usunPodroz(doUsuniecia)) {
                        System.out.println("Usunięto podróż.");
                    } else {
                        System.out.println("Nie znaleziono podróży.");
                    }
                    break;

                case 4:
                    System.out.print("Czy na pewno chcesz usunąć wszystkie podróże? (tak/nie): ");
                    String potwierdzenie = scanner.nextLine();
                    if (potwierdzenie.equalsIgnoreCase("tak")) {
                        plan.wyczyscPodroze();
                        System.out.println("Wyczyszczono wszystkie podróże.");
                    } else {
                        System.out.println("Anulowano.");
                    }
                    break;

                case 5:
                    System.out.print("Podaj miejsce, dla którego chcesz zmienić datę: ");
                    String zmienianeMiejsce = scanner.nextLine();
                    System.out.print("Nowa data (rrrr-mm-dd): ");
                    String nowaData = scanner.nextLine();
                    if (!nowaData.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        System.out.println("Niepoprawny format daty. Użyj rrrr-mm-dd.");
                        break;
                    }
                    if (plan.zmienDate(zmienianeMiejsce, nowaData)) {
                        System.out.println("Zmieniono datę.");
                    } else {
                        System.out.println("Nie znaleziono takiej podróży.");
                    }
                    break;

                case 6:
                    System.out.print("Podaj miejsce, dla którego chcesz zmienić koszt: ");
                    String miejsceKoszt = scanner.nextLine();
                    System.out.print("Nowy koszt: ");
                    int nowyKoszt = Integer.parseInt(scanner.nextLine());
                    if (nowyKoszt < 0) {
                        System.out.println("Koszt nie może być ujemny.");
                        break;
                    }
                    if (plan.zmienKoszt(miejsceKoszt, nowyKoszt)) {
                        System.out.println("Zmieniono koszt.");
                    } else {
                        System.out.println("Nie znaleziono takiej podróży.");
                    }
                    break;

                case 7:
                    System.out.println("Całkowity koszt podróży: " + plan.podliczKoszt() + " zł");
                    break;

                case 8:
                    plan.posortujPoDacie();
                    System.out.println("Posortowano podróże chronologicznie.");
                    break;

                case 9:
                    System.out.println("\n--- STATYSTYKI ---");
                    System.out.println("Liczba podróży: " + plan.getLiczbaPodrozy());
                    System.out.println("Średni koszt: " + plan.sredniKoszt() + " zł");
                    Podroz najdrozsza = plan.najdrozszaPodroz();
                    Podroz najtansza = plan.najtanszaPodroz();
                    if (najdrozsza != null) {
                        System.out.println("Najdroższa podróż : " + najdrozsza.getMiejsce());
                    } else {
                        System.out.println("Brak zapisanych podróży.");
                    }

                    if (najtansza != null) {
                        System.out.println("Najtańsza podróż : " + najtansza.getMiejsce());
                    } else {
                        System.out.println("Brak zapisanych podróży.");
                    }
                    break;

                case 10:
                    try {
                        plan.saveToFile(plik);
                        System.out.println("Zapisano dane do pliku.");
                    } catch (IOException e) {
                        System.out.println("Błąd zapisu: " + e.getMessage());
                    }
                    break;

                case 11:
                    System.out.println("Zakończono program.");
                    break;

                default:
                    System.out.println("Niepoprawna opcja, spróbuj ponownie.");
            }

        } while (wybor != 11);

        scanner.close();
    }
}