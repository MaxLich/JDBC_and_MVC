package maxlich.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by maxlich on 18.07.17.
 */
public class View {
    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void run() throws IOException {
        printHeader();
        printMenu();
        inputMenuItem();
    }

    public void printHeader() {
        System.out.println("Словарь 1.0");
        System.out.println();
    }

    public void printMenu() {
        System.out.println("Выберите пункт меню:");
        System.out.println("1) Добавить слово в словарь");
        System.out.println("2) Найти слово в словаре");
        System.out.println("3) Найти по значению  слово в словаре");
        System.out.println("4) Изменить значение слова из словаря");
        System.out.println("5) Удалить слово из словаря");
        System.out.println("6) Отобразить все слова из словаря");
        System.out.println("7) Выход");
    }

    public void inputMenuItem() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int indexMenuItem;
        while (true) {
            try {
                indexMenuItem = Integer.parseInt(reader.readLine());
                if (indexMenuItem >= 1 && indexMenuItem <= 7) break;
            } catch (NumberFormatException e) {
            }
        }
        chooseAction(indexMenuItem);

    }

    private void chooseAction(int indexOfAction) throws IOException {
        switch (indexOfAction) {
            case 1: //добавить слово в словарь
                inputWordAndDefinition();
                break;
            case 2: //искать слово в словаре
                inputSoughtWord();
                break;
            case 3: //искать слово по определению
                inputDefinitionOfSoughtWord();
                break;
            case 4: //изменить слово в словаре
                inputNewDefinitionForWord();
                break;
            case 5: //удалить слово из словаря
                inputRemovingWord();
                break;
            case 6: //отобразить все слова из словаря
                controller.getAllWords();
                break;
            case 7: //выход из программы
                System.exit(0);

        }
    }




    private void inputWordAndDefinition() throws IOException {
        System.out.println("Введите слово, которое вы хотите добавить в словарь, на одной строке,");
        System.out.println("а значение слова - на другой:");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String word = reader.readLine();
        String definition = reader.readLine();

        controller.addNewWord(word, definition);
    }

    private void inputSoughtWord() throws IOException {
        System.out.println("Введите слово, которое вы хотите найти в словаре:");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String word = reader.readLine();

        controller.findWord(word);
    }

    private void inputDefinitionOfSoughtWord() throws IOException {
        System.out.println("Введите значение слова, которое вы хотите найти в словаре:");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String def = reader.readLine();

        controller.findWordByDefinition(def);

    }

    private void inputNewDefinitionForWord() throws IOException {
        System.out.println("Введите слово, значение которого Вы хотите изменить: ");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String word = reader.readLine();

        System.out.println("Введите новое значение для этого слова: ");
        String definition = reader.readLine();

        controller.changeDefinitionOfWord(word, definition);
    }


    private void inputRemovingWord() throws IOException {
        System.out.println("Введите слово, которое вы хотите удалить из словаря:");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String word = reader.readLine();

        controller.deleteWord(word);
    }


    public void showText(String text) {
        System.out.println(text);

        try {
            inputEmptyLine();
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showAllWordsFromDict(Map<String, String> dict) {
        for (Map.Entry<String, String> entry: dict.entrySet()) {
            String word = entry.getKey().substring(0,1).toUpperCase() + entry.getKey().substring(1);
            String def = entry.getValue();
            System.out.println(word + " - "  + def);
            System.out.println();
        }

        try {
            inputEmptyLine();
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void inputEmptyLine() throws IOException {
        new BufferedReader(new InputStreamReader(System.in)).readLine();
    }


}
