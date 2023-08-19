package itmo.ser.commands.managers;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
//import description.*;
import itmo.common.description.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
     * Класс Converter отвечает за чтение и запись в файл.
     */
    public class Converter {
    public void writeCollection(ArrayList<Movie> movies) {
        String[] header = {"id", "name", "x", "y", "oscarsCount", "mpaaRating",
                "genre", "creationDate", "totalBoxOffice", "screenwriter_name",
                "screenwriter_weight", "screenwriter_eyeColor", "screenwriterHairColor", "screenwriterLocation_x", "screenwriterLocation_y"};
        String[][] records = new String[movies.size()][header.length];
        int i = 0;
        for (Movie movie : CollectionManager.movies) {
            records[i][0] = String.valueOf(movie.getId());
            records[i][1] = movie.getName();
            records[i][2] = String.valueOf(movie.getCoordinates().getX());
            records[i][3] = String.valueOf(movie.getCoordinates().getY());
            records[i][4] = String.valueOf(movie.getOscarsCount());
            records[i][5] = String.valueOf(movie.getMpaaRating());
            records[i][6] = String.valueOf(movie.getGenre());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            records[i][7] = movie.getCreationDate().format(formatter).trim();
            records[i][8] = String.valueOf(movie.getTotalBoxOffice());
            records[i][9] = movie.getScreenwriter().getNm();
            records[i][10] = String.valueOf(movie.getScreenwriter().getWeight());
            records[i][11] = String.valueOf(movie.getScreenwriter().getEyeColor());
            records[i][12] = String.valueOf(movie.getScreenwriter().getHairColor());
            records[i][13] = String.valueOf(movie.getScreenwriter().getLocation().getX());
            records[i][14] = String.valueOf(movie.getScreenwriter().getLocation().getY());
            i++;
        }
        try (CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter("filename.csv")))) {
            writer.writeNext(header);
            for (Object[] record : records) {
                writer.writeNext((String[]) record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readCollectionFromFile(String filename) {
        Scanner scanner = new Scanner(System.in);
        CollectionManager.movies = new ArrayList<>();
        while (true) {
            try (CSVReader reader = new CSVReader(new FileReader(filename))) {
                String[] header = reader.readNext();
                String[] record;
                while ((record = reader.readNext()) != null) {
                    try {
                        int id = Integer.parseInt(record[0]);
                        String name = record[1];
                        int x = Integer.parseInt(record[2]);
                        double y = Double.parseDouble(record[3]);
                        Coordinates coordinates = new Coordinates(x, y);
                        long oscarsCount = Long.parseLong(record[4]);
                        MpaaRating mpaaRating = MpaaRating.fromValue(record[5]);
                        record[5] = mpaaRating.toString();
                        MovieGenre genre = MovieGenre.valueOf(record[6]);
                        LocalDate creationDate = LocalDate.parse(record[7]);
                        long totalBoxOffice = Long.parseLong(record[8]);

                        String screenwriterName = record[9];
                        double screenwriterWeight = Double.parseDouble(record[10]);
                        Color screenwriterEyeColor = null;
                        if (record[11] != null && !record[11].isEmpty() && !record[11].equalsIgnoreCase("NULL")) {
                            screenwriterEyeColor = Color.valueOf(record[11].toUpperCase());
                        }
                        ColorHair screenwriterHairColor = ColorHair.valueOf(record[12]);
                        double screenwriterLocationX = Double.parseDouble(record[13]);
                        float screenwriterLocationY = Float.parseFloat(record[14]);
                        Location screenwriterLocation = new Location(screenwriterLocationX, screenwriterLocationY, name);
                        Person screenwriter = new Person(screenwriterName, screenwriterWeight, screenwriterEyeColor, screenwriterHairColor, screenwriterLocation);
                        Movie movie = new Movie(
                                id,
                                name,
                                coordinates,
                                creationDate,
                                totalBoxOffice,
                                oscarsCount,
                                genre,
                                mpaaRating,
                                screenwriter);
                        CollectionManager.movies.add(movie);
                    } catch (NumberFormatException e) {
                        System.out.println("В файле некорректные данные. Коллекцию не удалось сохранить в файл.");
                        CollectionManager.movies.clear();
                        break;
                    }
                }
                break;
            } catch (FileNotFoundException e) {
                System.out.println("Ошибка: Файл не найден.");
                createEmptyFile(filename);
                break;


            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла: ");
                e.printStackTrace();
            } catch (CsvValidationException e) {
                System.out.println("Ошибка при валидации CSV: ");
                throw new RuntimeException(e);
            }
        }
    }

    private void createEmptyFile(String filename) {
        File file = new File(filename);
        try {
            file.createNewFile();
            System.out.println("Новый пустой файл успешно создан.");
        } catch (IOException e) {
            System.out.println("Ошибка при создании нового файла: ");
        }
    }

    public void createEmptyCollectionFile(String filename) {
        File file = new File(filename);
        try {
            if (file.createNewFile()) {
                System.out.println("Новый пустой файл успешно создан.");
            } else {
                System.out.println("Ошибка при создании файла.");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла: " + e.getMessage());
        }
    }


}




