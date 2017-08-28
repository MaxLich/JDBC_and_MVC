package maxlich.app;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by maxlich on 18.07.17.
 */
public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    public Controller(View view) {
        this.view = view;
    }

    public Controller(Model model) {
        this.model = model;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }


    public void addNewWord(String word, String definition) throws IOException {
        try {
            boolean isSuccessfullyDone = model.addNewRecordInDictionary(word, definition);
            if (isSuccessfullyDone)
                view.showText("Слово успешно добавлено в словарь.");
            else
                view.showText("Не удалось добавить новое слово в словарь.");
        } catch (SuchKeyAlreadyExistsException e) {
            view.showText("Такое слово уже существует в словаре!");
        }
    }

    public void findWord(String word) throws IOException {
        String def = model.getDefinitionByWord(word);
        if (def == null)
            view.showText("Слово не найдено в словаре.");
        else
            view.showText(word + " - " + def);
    }


    public void findWordByDefinition(String def) {
        String word = model.getWordByDefinition(def);
        if (word == null)
            view.showText("Слово не найдено в словаре.");
        else
            view.showText(word);
    }

    public void changeDefinitionOfWord(String word, String definition) {
        try {
            boolean isSuccessfullyDone = model.replaceDefinitionOfWord(word, definition);
            if (isSuccessfullyDone)
                view.showText("Значение слова успешно изменено.");
            else
                view.showText("Не удалось изменить значение слова.");
        } catch (NoSuchElementException e) {
            view.showText("Слово не найдено в словаре.");
        }
    }

    public void deleteWord(String word) throws IOException {
        try {
            boolean isSuccessfullyDone = model.removeRecordFromDictionary(word);
            if (isSuccessfullyDone)
                view.showText("Слово успешно удалено из словаря.");
            else
                view.showText("Не удалось удалить слово из словаря.");
        } catch (NoSuchElementException e) {
            view.showText("Удаляемое слово не найдено!");
        }
    }


    public void getAllWords() {
        Map<String, String> map = model.getDictionary();

        if (map == null || map.isEmpty())
            view.showText("В словаре не содержится ещё ни одного слова.");
        else
            view.showAllWordsFromDict(map);

    }



}
