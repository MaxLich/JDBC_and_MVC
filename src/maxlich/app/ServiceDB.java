package maxlich.app;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

public class ServiceDB implements IService {

    public static final String user = "root";
    public static final String pswd = "root";
    public static final String url = "jdbc:mysql://localhost/dictionary_db";
    public static final String driver = "com.mysql.jdbc.Driver";

    private final Properties connInfo;

    public ServiceDB() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        connInfo = new Properties();
        initConnInfo();

        createTable();
    }

    private void initConnInfo() {
        if (connInfo == null)
            return;

        connInfo.put("user",user);
        connInfo.put("password",pswd);

        connInfo.put("useUnicode","true");
        connInfo.put("characterEncoding","UTF8");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, connInfo);
    }

    public void createTable() throws SQLException {
        try (Connection conn = getConnection()) {
            conn.prepareStatement("CREATE TABLE IF NOT EXISTS dictionary(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "word VARCHAR(50) NOT NULL, " +
                    "definition VARCHAR(255)" +
                    ")").execute();
        }
    }

    @Override
    public Map<String, String> getDictionary() {
        String sql = "SELECT word, definition FROM dictionary";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            Map<String, String> dict = new HashMap<>();

            while (rs.next()) {
                dict.put(rs.getString(1), rs.getString(2));
            }

            return dict;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean add(String key, String value) throws SuchKeyAlreadyExistsException {
        if (contains(key))
            throw new SuchKeyAlreadyExistsException();

        String sql = "INSERT INTO dictionary(word,definition) VALUES (?,?)";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, key);
            st.setString(2, value);

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    private boolean contains(String key) {

        String sql = "SELECT word FROM dictionary WHERE word = ?";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, key);

            ResultSet rs = st.executeQuery();
            boolean isNextExists = rs.next();
            rs.close();

            return isNextExists;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public String getValue(String word) {

        String sql = "SELECT definition FROM dictionary WHERE word = ?";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, word);

            ResultSet rs = st.executeQuery();
            rs.next();
            String value = rs.getString(1);
            rs.close();

            return value;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public String getKey(String definition) {

        String sql = "SELECT word FROM dictionary WHERE definition LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, "%" + definition + "%");

            ResultSet rs = st.executeQuery();
            rs.next();
            String key = rs.getString(1);
            rs.close();

            return key;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean updateRecord(String word, String newDefinition) throws NoSuchElementException {
        if (!contains(word))
            throw new NoSuchElementException();

        String sql = "UPDATE dictionary SET definition = ? WHERE word = ?";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, newDefinition);
            st.setString(2, word);

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }

    }

    @Override
    public boolean removeRecord(String word) throws NoSuchElementException {
        if (!contains(word))
            throw new NoSuchElementException();

        String sql = "DELETE FROM dictionary WHERE word = ?";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, word);

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
