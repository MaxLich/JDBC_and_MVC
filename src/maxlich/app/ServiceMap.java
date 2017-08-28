package maxlich.app;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by maxlich on 18.07.17.
 */
public class ServiceMap implements IService {
    private Map<String, String> dictionary;

    public ServiceMap() {
        dictionary = new HashMap<>();
        dictionary.put("vehicle","транспортное средство, автомобиль");
        dictionary.put("execute","осуществлять, выполнять, делать; реализовать; доводить до конца");
        dictionary.put("run","бежать, бегать, передвигаться свободно, без ограничений; быстро уходить, убегать; спасаться бегством, дезертировать");
        dictionary.put("function","назначение, функция");
        dictionary.put("catch","ловить; поймать; схватить");
    }

    @Override
    public Map<String, String> getDictionary() {
        return dictionary;
    }

    @Override
    public boolean add(String key, String value) throws SuchKeyAlreadyExistsException {
        if (dictionary.containsKey(key))
            throw new SuchKeyAlreadyExistsException();

        try {
            dictionary.put(key,value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String getValue(String word) {
        return dictionary.get(word.toLowerCase());
    }

    @Override
    public String getKey(String definition) {
        for (Map.Entry<String,String> entry: dictionary.entrySet()) {
            String value = entry.getValue().toLowerCase();
            if (value.contains(definition.toLowerCase()))
                return entry.getKey();
        }
        return null;
    }

    @Override
    public boolean updateRecord(String word, String newDefinition) throws NoSuchElementException {
        if (!dictionary.containsKey(word.toLowerCase()))
            throw new NoSuchElementException();

        try {
            dictionary.put(word, newDefinition);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean removeRecord(String word) throws NoSuchElementException {
        String delEntry = dictionary.remove(word);
        if (delEntry == null)
            throw new NoSuchElementException();

        return true;
    }
}
