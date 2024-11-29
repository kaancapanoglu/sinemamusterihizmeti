import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// BaseEntity: Ortak özellikler için temel sınıf.
abstract class BaseEntity {
    private int id;
    private String name;

    public BaseEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract void bilgiGoster();
}

// Musteri Sınıfı
class Musteri extends BaseEntity {
    private String soyisim;
    private String telefonNumarasi;
    private String filmAdi;
    private int salonNumarasi;
    private int koltukNumarasi;
    private char siraNumarasi;
    private String seans;

    public Musteri(int id, String name, String soyisim, String telefonNumarasi, String filmAdi, int salonNumarasi, int koltukNumarasi, char siraNumarasi, String seans) {
        super(id, name);
        this.soyisim = soyisim;
        this.telefonNumarasi = telefonNumarasi;
        this.filmAdi = filmAdi;
        this.salonNumarasi = salonNumarasi;
        this.koltukNumarasi = koltukNumarasi;
        this.siraNumarasi = siraNumarasi;
        this.seans = seans;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public String getTelefonNumarasi() {
        return telefonNumarasi;
    }

    public String getFilmAdi() {
        return filmAdi;
    }

    public int getSalonNumarasi() {
        return salonNumarasi;
    }

    public int getKoltukNumarasi() {
        return koltukNumarasi;
    }

    public char getSiraNumarasi() {
        return siraNumarasi;
    }

    public String getSeans() {
        return seans;
    }

    @Override
    public void bilgiGoster() {
        System.out.println("Müşteri ID: " + getId() + ", İsim: " + getName() + " " + soyisim +
                ", Telefon: " + telefonNumarasi + ", Film: " + filmAdi +
                ", Salon Numarası: " + salonNumarasi +
                ", Koltuk No: " + koltukNumarasi + ", Sıra No: " + siraNumarasi +
                ", Seans: " + seans);
    }
}

// Film Sınıfı
class Film {
    private String name;
    private int duration; // dakika cinsinden
    private String genre;
    private List<String> seanslar; // Seans listesi

    public Film(String name, int duration, String genre) {
        this.name = name;
        this.duration = duration;
        this.genre = genre;
        this.seanslar = new ArrayList<>();
        this.seanslar.add("10:30");
        this.seanslar.add("13:00");
        this.seanslar.add("20:00");
    }

    public String getName() {
        return name;
    }

    public List<String> getSeanslar() {
        return seanslar;
    }

    public void bilgiGoster() {
        System.out.println("Film Adı: " + name + ", Süre: " + duration + " dakika, Tür: " + genre);
        System.out.println("Seanslar: " + String.join(", ", seanslar));
    }
}

// Salon Sınıfı
class Salon extends BaseEntity {
    private Film film;

    public Salon(int id, String name, Film film) {
        super(id, name);
        this.film = film;
    }

    public Film getFilm() {
        return film;
    }

    @Override
    public void bilgiGoster() {
        System.out.println("Salon Numarası: " + getId() + ", İsim: " + getName());
        if (film != null) {
            System.out.println("Gösterimdeki Film: ");
            film.bilgiGoster();
        } else {
            System.out.println("Gösterimdeki Film Yok");
        }
    }
}

// MusteriYonetimi Interface'i
interface MusteriYonetimi {
    void musteriEkle(Musteri musteri);

    void musteriListele();

    void musteriSil(int musteriId);
}

// SalonYonetimi Interface'i
interface SalonYonetimi {
    void salonBilgisiGoster(List<Salon> salonlar);
}

// MusteriYonetimi'ni uygulayan sınıf
class MusteriIslemleri implements MusteriYonetimi {
    private List<Musteri> musteriler;

    public MusteriIslemleri() {
        this.musteriler = new ArrayList<>();
    }

    @Override
    public void musteriEkle(Musteri musteri) {
        musteriler.add(musteri);
        System.out.println("Müşteri sisteme eklendi: " + musteri.getName() + " " + musteri.getSoyisim());
    }

    @Override
    public void musteriListele() {
        if (musteriler.isEmpty()) {
            System.out.println("Henüz kayıtlı müşteri yok.");
        } else {
            for (Musteri musteri : musteriler) {
                musteri.bilgiGoster();
            }
        }
    }

    @Override
    public void musteriSil(int musteriId) {
        Musteri silinecekMusteri = null;
        for (Musteri musteri : musteriler) {
            if (musteri.getId() == musteriId) {
                silinecekMusteri = musteri;
                break;
            }
        }
        if (silinecekMusteri != null) {
            musteriler.remove(silinecekMusteri);
            System.out.println("Müşteri silindi: " + silinecekMusteri.getName() + " " + silinecekMusteri.getSoyisim());
        } else {
            System.out.println("Girilen ID ile eşleşen müşteri bulunamadı.");
        }
    }
}

// SalonYonetimi'ni uygulayan sınıf
class SalonIslemleri implements SalonYonetimi {
    @Override
    public void salonBilgisiGoster(List<Salon> salonlar) {
        for (Salon salon : salonlar) {
            salon.bilgiGoster();
            System.out.println("------------------");
        }
    }
}

// Ana sınıf
public class MusteriKayitSistemi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Hazır film ve salon listeleri
        Film film1 = new Film("Inception", 148, "Bilim Kurgu");
        Film film2 = new Film("The Dark Knight", 152, "Aksiyon");
        Film film3 = new Film("Interstellar", 169, "Macera");

        List<Salon> salonlar = new ArrayList<>();
        salonlar.add(new Salon(1, "Salon A", film1));
        salonlar.add(new Salon(2, "Salon B", film2));
        salonlar.add(new Salon(3, "Salon C", film3));

        MusteriIslemleri musteriIslemleri = new MusteriIslemleri();
        SalonIslemleri salonIslemleri = new SalonIslemleri();

        while (true) {
            System.out.println("\n1. Yeni Müşteri Ekle");
            System.out.println("2. Müşteri Listesini Göster");
            System.out.println("3. Salon ve Film Bilgilerini Göster");
            System.out.println("4. Müşteri Sil");
            System.out.println("5. Çıkış");
            System.out.print("Seçiminiz: ");
            int secim = scanner.nextInt();
            scanner.nextLine(); // satır sonu temizleme

            switch (secim) {
                case 1:
                    System.out.print("Müşteri İsmi: ");
                    String musteriIsmi = scanner.nextLine();
                    System.out.print("Müşteri Soyismi: ");
                    String musteriSoyismi = scanner.nextLine();
                    System.out.print("Müşteri Telefon Numarası: ");
                    String telefonNumarasi = scanner.nextLine();

                    // Mevcut filmleri listele
                    System.out.println("Mevcut Filmler:");
                    for (int i = 0; i < salonlar.size(); i++) {
                        System.out.println((i + 1) + ". " + salonlar.get(i).getFilm().getName());
                    }
                    System.out.print("Hangi filmi izlemek istiyorsunuz (1-3): ");
                    int filmSecim = scanner.nextInt();
                    scanner.nextLine(); // satır sonu temizleme

                    if (filmSecim < 1 || filmSecim > salonlar.size()) {
                        System.out.println("Geçersiz film seçimi, işlem iptal edildi.");
                        continue;
                    }
                    Salon secilenSalon = salonlar.get(filmSecim - 1);
                    String secilenFilmAdi = secilenSalon.getFilm().getName();
                    int salonNumarasi = secilenSalon.getId();

                    // Mevcut seansları listele
                    System.out.println("Mevcut Seanslar:");
                    List<String> seanslar = secilenSalon.getFilm().getSeanslar();
                    for (int i = 0; i < seanslar.size(); i++) {
                        System.out.println((i + 1) + ". " + seanslar.get(i));
                    }
                    System.out.print("Hangi seansı izlemek istiyorsunuz (1-3): ");
                    int seansSecim = scanner.nextInt();
                    scanner.nextLine(); // satır sonu temizleme

                    if (seansSecim < 1 || seansSecim > seanslar.size()) {
                        System.out.println("Geçersiz seans seçimi, işlem iptal edildi.");
                        continue;
                    }
                    String secilenSeans = seanslar.get(seansSecim - 1);

                    // Koltuk numarası al
                    int koltukNumarasi;
                    while (true) {
                        System.out.print("Koltuk Numarası (1-20): ");
                        koltukNumarasi = scanner.nextInt();
                        if (koltukNumarasi >= 1 && koltukNumarasi <= 20) {
                            break;
                        } else {
                            System.out.println("Geçersiz koltuk numarası! Lütfen 1-20 arasında bir değer giriniz.");
                        }
                    }

                    // Sıra numarası al
                    char siraNumarasi;
                    while (true) {
                        System.out.print("Sıra Numarası (A-Z): ");
                        siraNumarasi = scanner.next().toUpperCase().charAt(0);
                        if (siraNumarasi >= 'A' && siraNumarasi <= 'Z') {
                            break;
                        } else {
                            System.out.println("Geçersiz sıra numarası! Lütfen A-Z arasında bir harf giriniz.");
                        }
                    }
                    scanner.nextLine(); // satır sonu temizleme

                    // Müşteri oluştur ve listeye ekle
                    int musteriId = (int) (Math.random() * 1000);
                    Musteri musteri = new Musteri(musteriId, musteriIsmi, musteriSoyismi, telefonNumarasi, secilenFilmAdi, salonNumarasi, koltukNumarasi, siraNumarasi, secilenSeans);
                    musteriIslemleri.musteriEkle(musteri);
                    break;

                case 2:
                    musteriIslemleri.musteriListele();
                    break;

                case 3:
                    salonIslemleri.salonBilgisiGoster(salonlar);
                    break;

                case 4:
                    System.out.print("Silmek istediğiniz müşterinin ID'sini girin: ");
                    int musteriIdSil = scanner.nextInt();
                    musteriIslemleri.musteriSil(musteriIdSil);
                    break;

                case 5:
                    System.out.println("Sistemden çıkılıyor...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Geçersiz seçim.");
            }
        }
    }
}
