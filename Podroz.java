public class Podroz implements Comparable<Podroz> {
    private String miejsce;
    private String kraj;
    private int koszt;
    private String data;

    public Podroz(String miejsce, String kraj, int koszt, String data) {
        this.miejsce = miejsce;
        this.kraj = kraj;
        this.koszt = koszt;
        this.data = data;
    }

    public String getMiejsce() {
        return miejsce;
    }
    public String getKraj() {
        return kraj;
    }
    public int getKoszt() {
        return koszt;
    }
    public void setKoszt(int koszt) {
        this.koszt = koszt;
    }
    public String getData() {
        return data;
    }
    public void setData(String nowaData) {
        this.data = nowaData;
    }

    @Override // nadpisanie - edytujemy coś istniejącego 
    public String toString() {
        return miejsce + " (" + kraj + ") [" + koszt + " zł, " + data + "]";
    }
    public String serialize() { // zapisuje w jednej spójnej linii aby łatwo było to zapisać do pliku
        return miejsce + "|" + kraj + "|" + koszt + "|" + data;
    }
    public static Podroz deserialize(String line) { // odczytuje z jednej linii aby odczytywać przy kazdym uruchomieniu (zapapmiętywanie danych)
        String[]tb = line.split("\\|");
        return new Podroz(tb[0], tb[1], Integer.parseInt(tb[2]), tb[3]); // utworzenie obiektu, parseInt - zamiana z String na int
    }

    @Override
    public int compareTo(Podroz o) {
        return this.data.compareTo(o.data); // porównanie dat (do sortowania podrózy chronologicznie)
    }

}