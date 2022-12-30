package app.stores;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class QuestionCSVLoader {

    public static ArrayList<Question> parseCSV(File url) {
        ArrayList<Question> questions = new ArrayList<Question>();
        try {
            Scanner inputStream = new Scanner(url);
            while(inputStream.hasNext()) {
                String line = inputStream.nextLine();
                String[] vals = line.split(";");
                questions.add(new Question(vals[0], vals[1], Double.parseDouble(vals[2]), Double.parseDouble(vals[3])));
            }
        } catch(FileNotFoundException e) {
            System.out.println("Soubor neexistuje !!");
        }
        return questions;
    }
    public static Question pickRandomQuestion(ArrayList<Question> questions,String category) {
        List<Question> outputList = questions.stream().filter(question -> question.getCategory().equals(category)).collect(Collectors.toList());
        return outputList.get(new Random().nextInt(outputList.size() - 1 + 1) + 0);
    }
}
