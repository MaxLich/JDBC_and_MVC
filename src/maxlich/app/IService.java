package maxlich.app;

import java.util.Map;
import java.util.NoSuchElementException;

public interface IService {
    Map<String, String> getDictionary();

    boolean add(String key, String value) throws SuchKeyAlreadyExistsException;

    String getValue(String word);

    String getKey(String definition);

    boolean updateRecord(String word, String newDefinition) throws NoSuchElementException;

    boolean removeRecord(String word) throws NoSuchElementException;
}
