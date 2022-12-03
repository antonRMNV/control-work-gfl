package org.example;

import org.example.model.Manufacturer;
import org.example.model.Souvenir;
import org.example.serialization.Serialization;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Controller {

    private String directory;
    private Serialization<Manufacturer> manufacturerSerialization;
    private Serialization<Souvenir> souvenirSerialization;
    private List<Manufacturer> manufacturers;
    private List<Souvenir> souvenirs;

    {
        manufacturers = new ArrayList<>();
        souvenirs = new ArrayList<>();
    }

    public void run() {
        findDirectory();

        manufacturerSerialization = new Serialization<>(directory + "/" + "manufacturer.dat");
        souvenirSerialization = new Serialization<>(directory + "/" + "souvenir.dat");

        manufacturers = manufacturerSerialization.read();
        souvenirs = souvenirSerialization.read();

        /*Manufacturer roshen = new Manufacturer(1124, "Roshen", "Ukraine");
        Manufacturer comfy = new Manufacturer(2216, "Comfy", "Ukraine");
        Manufacturer kfc = new Manufacturer(1259, "KFC", "United States");

        manufacturers.add(roshen);
        manufacturers.add(comfy);
        manufacturers.add(kfc);
        manufacturerSerialization.write(manufacturers);

        Souvenir cupRoshen = new Souvenir(10214, "Cup", roshen.getId(), LocalDate.of(2022,12,1), 15);
        Souvenir trinketComfy = new Souvenir(11236, "Trinket", comfy.getId(), LocalDate.of(2021, 5, 15), 5);
        Souvenir cupComfy = new Souvenir(11237, "Cup", comfy.getId(), LocalDate.of(2021, 8, 26), 18);
        Souvenir tshirtComfy = new Souvenir(11238, "T-Shirt", comfy.getId(), LocalDate.of(2022, 12, 3), 10);
        Souvenir tshirtKFC = new Souvenir(15366, "T-Shirt", kfc.getId(), LocalDate.of(2019, 10, 15), 12);
        Souvenir socksKFC = new Souvenir(15367, "Socks", kfc.getId(), LocalDate.of(2018, 1, 1), 3);

        souvenirs.add(cupRoshen);
        souvenirs.add(trinketComfy);
        souvenirs.add(cupComfy);
        souvenirs.add(tshirtComfy);
        souvenirs.add(tshirtKFC);
        souvenirs.add(socksKFC);
        souvenirSerialization.write(souvenirs);*/

        Scanner scanner = new Scanner(System.in);
        int userChoice;
        System.out.print("\nWelcome to admin panel!\n");
        do {
            System.out.print("""
                    \u001B[32m--------------------\u001B[0m
                    1. Add new manufacturer
                    2. Add new souvenir
                    3. Edit manufacturer
                    4. Edit souviner
                    5. View all manufacturers and souvenirs
                    6. View souvenirs of a specific manufacturer
                    7. View souvenirs by country of issue
                    8. View manufacturers whose prices for souvenirs are less than the selected one
                    9. View information about the manufacturers and their souvenirs
                    10. View information about the manufacturers of a given souvenir produced in a given year
                    11. View souvenirs by year of issue
                    12. Delete manufacturer and his souvenirs
                    Your choice:\040""");
            userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1 -> addManufacturer();
                case 2 -> addSouvenir();
                case 3 -> editManufacturer();
                case 4 -> editSouvenir();
                case 5 -> viewAllManufacturersAndSouvenirs();
                case 6 -> viewSouvenirsByManufacturer();
                case 7 -> viewSouvenirsByCountry();
                case 8 -> viewSouvenirByPrice();
                case 9 -> viewManufacturersAndTheirSouvenirs();
                case 10 -> viewGivenSouvenirByYear();
                case 11 -> viewSouvenirsByYear();
                case 12 -> deleteManufacturer();
                default -> { }
            }

            System.out.print("\n⇢ For continue enter any number / for exit enter '0': ");
            userChoice = scanner.nextInt();
        } while (userChoice != 0);
        System.out.println("\n✓ Work with the admin panel is completed!");

    }

    private void findDirectory() {
        Properties properties = new Properties();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("config.properties"))) {
            properties.load(bufferedReader);
            directory = properties.getProperty("data");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void addManufacturer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("⇢ To add a new manufacturer, enter next fields:\n\tName: ");
        String name = scanner.nextLine();
        System.out.print("\tCountry: ");
        String country = scanner.nextLine();
        System.out.print("\tID: ");
        int id = scanner.nextInt();

        Manufacturer manufacturer = new Manufacturer(id, name, country);
        if(!isManufacturerExist(id)) {
            manufacturers.add(manufacturer);
            manufacturerSerialization.write(manufacturers);
            System.out.println("\t✓ Manufacturer successfully added!");
        } else {
            System.out.println("\u001B[31mError! A manufacturer with this ID already exists.\u001B[0m");
        }
    }

    private void addSouvenir() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("⇢ To add a new souvenir, enter next fields:\n\tName: ");
        String name = scanner.nextLine();
        System.out.print("\t↱ Available manufacturers: ");
        manufacturers.stream()
                .sorted(Comparator.comparing(Manufacturer::getId))
                .forEach(m -> System.out.print(m.getId() + "-" + m.getName() + "; "));
        System.out.print("\n\tManufacturer's id: ");
        int manufacturerId = scanner.nextInt();
        System.out.print("\tYear of issue: ");
        int year = scanner.nextInt();
        System.out.print("\tMonth of issue: ");
        int month = scanner.nextInt();
        System.out.print("\tDay of issue: ");
        int day = scanner.nextInt();
        System.out.print("\tPrice: ");
        int price = scanner.nextInt();
        System.out.print("\tID: ");
        int id = scanner.nextInt();

        if(isManufacturerExist(manufacturerId)) {
            Souvenir souvenir = new Souvenir(id, name, manufacturerId, LocalDate.of(year, month, day), price);
            if(!isSouvenirExist(id)) {
                souvenirs.add(souvenir);
                souvenirSerialization.write(souvenirs);
                System.out.println("\t✓ Souvenir successfully added!");
            } else {
                System.out.println("\u001B[31mError! A souviner with this ID already exists.\u001B[0m");
            }
        } else {
            System.out.println("\u001B[31mError! There is no manufacturer with this ID.\u001B[0m");
        }
    }

    private void editManufacturer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("↱ Available manufacturers: ");
        manufacturers.stream()
                .sorted(Comparator.comparing(Manufacturer::getId))
                .forEach(m -> System.out.print(m.getId() + "-" + m.getName() + "; "));
        System.out.print("\n⇢ Enter ID of manufacturer you want to change: ");
        int id = scanner.nextInt();
        if(isManufacturerExist(id)) {
            System.out.print("⇢ Enter new information about manufacturer:\n\tName: ");
            scanner.nextLine();
            String newName = scanner.nextLine();
            System.out.print("\tCountry: ");
            String newCountry = scanner.nextLine();
            Manufacturer manufacturer = getManufacturerById(id);
            manufacturer.setName(newName);
            manufacturer.setCountry(newCountry);
            manufacturerSerialization.write(manufacturers);
            System.out.println("\t✓ Manufacturer successfully changed!");
        }
    }

    private void editSouvenir() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("↱ Available souvenirs: ");
        souvenirs.stream()
                .sorted(Comparator.comparing(Souvenir::getId))
                .forEach(s -> System.out.print(s.getId() + "-" + s.getName() + "; "));
        System.out.print("\n⇢ Enter ID of souvenir you want to change: ");
        int id = scanner.nextInt();
        if(isSouvenirExist(id)) {
            System.out.print("⇢ Enter new information about souvenir:\n\tName: ");
            scanner.nextLine();
            String newName = scanner.nextLine();
            System.out.print("\t↱ Available manufacturers: ");
            manufacturers.stream()
                    .sorted(Comparator.comparing(Manufacturer::getId))
                    .forEach(m -> System.out.print(m.getId() + "-" + m.getName() + "; "));
            System.out.print("\n\tManufacturer's id: ");
            int newManufacturerId = scanner.nextInt();
            System.out.print("\tYear of issue: ");
            int newYear = scanner.nextInt();
            System.out.print("\tMonth of issue: ");
            int newMonth = scanner.nextInt();
            System.out.print("\tDay of issue: ");
            int newDay = scanner.nextInt();
            System.out.print("\tPrice: ");
            int newPrice = scanner.nextInt();
            Souvenir souvenir = getSouvenirById(id);
            souvenir.setName(newName);
            souvenir.setManufacturerId(newManufacturerId);
            souvenir.setDate(LocalDate.of(newYear, newMonth, newDay));
            souvenir.setPrice(newPrice);
            souvenirSerialization.write(souvenirs);
            System.out.println("\t✓ Souvenir successfully changed!");
        }
    }

    private void viewAllManufacturersAndSouvenirs() {
        System.out.println("⇢ Manufacturer list: ");
        manufacturers.stream()
                .sorted(Comparator.comparing(Manufacturer::getId))
                .forEach(System.out::println);

        System.out.println("⇢ Souvenir list: ");
        souvenirs.stream()
                .sorted(Comparator.comparing(Souvenir::getId))
                .forEach(System.out::println);
    }

    private void viewSouvenirsByManufacturer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("↱ Available manufacturers: ");
        manufacturers.stream()
                .sorted(Comparator.comparing(Manufacturer::getId))
                .forEach(m -> System.out.print(m.getId() + "-" + m.getName() + "; "));
        System.out.print("\n⇢ To find souvenirs by manufacturer enter his id: ");
        int id = scanner.nextInt();
        if(isManufacturerExist(id)) {
            List<Souvenir> souvenirsByManufacturer = getSouvenirsByManufacturer(id);
            souvenirsByManufacturer.forEach(System.out::println);
        } else {
            System.out.println("\u001B[31mError! There is no manufacturer with this ID.\u001B[0m");
        }
    }

    private void viewSouvenirsByCountry() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("↱ Available countries: ");
        Set<String> countryName = new HashSet<>();
        for(Manufacturer m : manufacturers) {
            countryName.add(m.getCountry());
        }
        countryName.stream()
                .sorted()
                .forEach(cn -> System.out.print(cn + "; "));

        System.out.print("\n⇢ To find souvenirs by country enter country's name: ");
        String country = scanner.nextLine();
        getSouvenirsByCountry(country).forEach(System.out::println);
    }

    private void viewSouvenirByPrice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the price below which you want to find souvenirs: ");
        int price = scanner.nextInt();

        List<Souvenir> souvenirsLessPrice = souvenirs.stream().filter(s -> s.getPrice() < price).toList();
        for(Manufacturer m: manufacturers) {
            for(Souvenir s: souvenirsLessPrice) {
                if(s.getManufacturerId() == m.getId()) {
                    System.out.println(m);
                    System.out.println("\t" + s);
                }
            }
        }


    }


    private void viewManufacturersAndTheirSouvenirs() {
        for(Manufacturer m : manufacturers) {
            System.out.println(m);
            for(Souvenir s : souvenirs) {
                if(s.getManufacturerId() == m.getId()) {
                    System.out.println("\t" + s);
                }
            }
        }
    }

    private void viewGivenSouvenirByYear() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("↱ Available souvenirs: ");
        Set<String> souvenirNames = new HashSet<>();
        for(Souvenir s : souvenirs) {
            souvenirNames.add(s.getName());
        }
        souvenirNames.stream()
                .sorted()
                .forEach(sn -> System.out.print(sn + "; "));
        System.out.print("\nEnter the name of souvenir: ");
        String name = scanner.nextLine();

        List<Souvenir> givenSouvenirsByName = souvenirs.stream()
                .filter(x -> x.getName().equalsIgnoreCase(name))
                .toList();
        System.out.print("↱ Available years: ");
        Set<Integer> availableYears = new HashSet<>();
        for(Souvenir s : givenSouvenirsByName) {
            availableYears.add(s.getDate().getYear());
        }
        availableYears.stream()
                .sorted()
                .forEach(ay -> System.out.print(ay + "; "));

        System.out.print("\n⇢ Enter the year: ");
        int year = scanner.nextInt();
        List<Souvenir> givenSouvenirByNameAndYear = givenSouvenirsByName.stream()
                .filter(x -> x.getDate().getYear() == year)
                .toList();

        // View manufacturer + souvenir
        for(Manufacturer m : manufacturers) {
            for(Souvenir s : givenSouvenirByNameAndYear) {
                if(s.getManufacturerId() == m.getId()) {
                    System.out.println(m);
                    System.out.println("\t" + s);
                }
            }
        }
    }

    private void viewSouvenirsByYear() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("↱ Available years: ");
        Set<Integer> availableYears = new HashSet<>();
        for(Souvenir s : souvenirs) {
            availableYears.add(s.getDate().getYear());
        }
        availableYears.stream()
                .sorted()
                .forEach(ay -> System.out.print(ay + "; "));

        System.out.print("\n⇢ To find souvenirs by year enter the year: ");
        int year = scanner.nextInt();
        getSouvenirsByYear(year).forEach(System.out::println);
    }

    private void deleteManufacturer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("↱ Available manufacturers: ");
        manufacturers.stream()
                .sorted(Comparator.comparing(Manufacturer::getId))
                .forEach(m -> System.out.print(m.getId() + "-" + m.getName() + "; "));
        System.out.print("\n⇢ To delete the manufacturer enter his id: ");
        int id = scanner.nextInt();
        if(isManufacturerExist(id)) {
            manufacturers.remove(getManufacturerById(id));
            manufacturerSerialization.write(manufacturers);
        } else {
            System.out.println("\u001B[31mError! There is no manufacturer with this ID.\u001B[0m");
        }

        souvenirs.removeIf(s -> s.getManufacturerId() == id);
        souvenirSerialization.write(souvenirs);
        System.out.println("✓ The manufacturer and his souvenirs have been removed!");
    }

    private Manufacturer getManufacturerById(int id) {
        for(Manufacturer m : manufacturers) {
            if(m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    private Souvenir getSouvenirById(int id) {
        for(Souvenir s : souvenirs) {
            if(s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    private List<Souvenir> getSouvenirsByManufacturer(int id) {
        return souvenirs.stream()
                .filter(s -> s.getManufacturerId() == id)
                .toList();
    }

    private List<Souvenir> getSouvenirsByCountry(String country) {
        List<Souvenir> souvenirsByCountry = new ArrayList<>();
        for(Souvenir s : souvenirs) {
            if(getManufacturerById(s.getManufacturerId()).getCountry().equalsIgnoreCase(country)) {
                souvenirsByCountry.add(s);
            }
        }
        return souvenirsByCountry;
    }

    private List<Souvenir> getSouvenirsByYear(int year) {
        return souvenirs.stream()
                .filter(s -> s.getDate().getYear() == year)
                .toList();
    }

    private boolean isManufacturerExist(int id) {
        for(Manufacturer m : manufacturers) {
            if(m.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private boolean isSouvenirExist(int id) {
        for(Souvenir s : souvenirs) {
            if(s.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
