package maxlich.app;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by maxlich on 18.07.17.
 */
public class Model {
    private IService service;

    public Model(IService service) {
        this.service = service;
    }

    public void setService(IService service) {
        this.service = service;
    }

    public boolean addNewRecordInDictionary(String word, String definition) throws SuchKeyAlreadyExistsException {
        return service.add(word,definition);
    }

    public String getDefinitionByWord(String word) {
        return service.getValue(word);
    }

    public String getWordByDefinition(String definition) {
        return service.getKey(definition);
    }

    public boolean replaceDefinitionOfWord(String word, String newDefinition) throws NoSuchElementException {
        return service.updateRecord(word,newDefinition);
    }

    public boolean removeRecordFromDictionary(String word) throws NoSuchElementException {
        return service.removeRecord(word);
    }


    public Map<String, String> getDictionary() {
        return service.getDictionary();
    }
}
