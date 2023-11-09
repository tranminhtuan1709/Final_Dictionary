package Database;


import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class DataLite {
    private static final String DB_URL_Lite = "jdbc:sqlite:src/main/java/Database/dict_t.db";
    private final HikariDataSource dataSource;
    public DataLite() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL_Lite);
        config.setMaximumPoolSize(20);
        dataSource = new HikariDataSource(config);
    }
    
    /*
    ********************************************************************************************************************
     author: anh tuan
     * Dictionary
     */
    public String searchWord(String s) throws SQLException {
        String sql = "SELECT * FROM av WHERE word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.toLowerCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("html");
                }
            }
        }
        return null;
    }

    public ArrayList<String> suggestWords(String input) throws SQLException {
        input = input.toLowerCase();
        String sql = "SELECT word FROM av WHERE word LIKE ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, input + "%");
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<String> wordList = new ArrayList<>();
                while (rs.next()) {
                    wordList.add(rs.getString("word"));
                }
                return wordList;
            }
        }
    }

    public ArrayList<String> getListWord() throws SQLException {
        String querySql = "SELECT word FROM av limit 58171";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql);
             ResultSet resultSet = ps.executeQuery()) {
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("word"));
            }
            return list;
        }
    }

    /*
     ********************************************************************************************************************
     *Add Word, DeleteWord, Update Word
     */
    public boolean isExist(String word) throws SQLException {
        String sql = "SELECT COUNT(*) FROM av WHERE word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word.toLowerCase());
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }
    public String convertToHTML(String addWord, String addPronounce, String addDescription) {
        return "<h1 style= \"color:#951D05; font-family : Segoe UI\">" + addWord + "</h1>" +
                "<h3><i>/" + addPronounce + "/</i></h3>" +
                "<h2>" + addDescription + "</h2>";
    }

    public void addWord(String word, String pos, String breIpa, String nameIpa, String meaning) throws SQLException {
        word = word.toLowerCase();
        String sql = "INSERT INTO av(word, description, html, pronounce) VALUES(?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word);
            ps.setString(2, pos + "; " + meaning);
            ps.setString(3, convertToHTML(word + "<br>"+ breIpa, pos, meaning));
            ps.setString(4, nameIpa);
            ps.executeUpdate();
        }
    }

    public void deleteWord(String s) throws SQLException {
        s = s.toLowerCase();
        String sql = "DELETE FROM av WHERE word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.toLowerCase());
            ps.executeUpdate();
        }
    }

    public void updateWord(String word, String pos, String breIpa, String nameIpa, String meaning, String old_word) throws SQLException {
        String html = convertToHTML(word + "<br>" +breIpa, pos, meaning);
        word = word.toLowerCase();
        String sql = "UPDATE av SET word = ?, html = ?, description = ?, pronounce = ? WHERE word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word);
            ps.setString(2, html);
            ps.setString(3, nameIpa);
            ps.setString(4, pos + "; " +meaning);
            ps.setString(5, old_word);
            ps.executeUpdate();
        }
    }

    /*
     ********************************************************************************************************************
     *Login
     */
    public boolean checkLogin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    /*
     ********************************************************************************************************************
     *SignUp
     */
    public boolean isExistAccount(String email,String username, String password) {
        String sql = "SELECT COUNT(*) FROM account WHERE email = ? AND username = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, username);
            ps.setString(3, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(new PrintStream(System.out));
        }
        return false;
    }

    public void signUp(String username, String password, String email) throws SQLException {
        String sql = "INSERT INTO account(username, password, email) VALUES(?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.executeUpdate();
        }
    }

    /*
     ********************************************************************************************************************
     *Delete Account
     */
    public boolean checkAccount(String username, String password, String email) throws SQLException {
        String sql = "SELECT * FROM account WHERE username = ? AND password = ? AND email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void deleteAccount(String username, String password, String email) throws SQLException {
        String sql = "SELECT * FROM account WHERE username = ? AND password = ? AND email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) != 0) {
                    String sql1 = "DELETE FROM account WHERE username = ? AND password = ? AND email = ?";
                    try (PreparedStatement deleteStatement = connection.prepareStatement(sql1)) {
                        deleteStatement.setString(1, username);
                        deleteStatement.setString(2, password);
                        deleteStatement.setString(3, email);
                        deleteStatement.executeUpdate();
                    }
                }
            }
        }
    }

    /*
     ********************************************************************************************************************
     *Change Password
     */

    public boolean checkPassword(String username, String password) throws SQLException {
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void changePassword(String username, String password, String new_password) throws SQLException {
        String sql = "UPDATE account SET password = ? WHERE username = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, new_password);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.executeUpdate();
        }
    }
    /*
     ********************************************************************************************************************
     *Favorite Word
     */
    public boolean isExistFavorite(String word) throws SQLException {
        String sql = "SELECT COUNT(*) FROM avfavorite WHERE word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word.toLowerCase());
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public void addFavorite(String word) throws SQLException {
        String sql = "INSERT INTO avfavorite(word, html, description, pronounce) SELECT word, html, description, pronounce FROM av WHERE word=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word);
            ps.executeUpdate();
        }
    }

    /**
     * Delete a favourite word from favourite table.
     * @param word the word to be excluded from the table
     * @throws SQLException SQL Exception
     */
    public void deleteFavorite(String word) throws SQLException {
        String sql = "DELETE FROM avfavorite WHERE word=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word);
            ps.executeUpdate();
        }
    }

    public String searchWordFa(String s) throws SQLException {
        String sql = "SELECT * FROM avfavorite WHERE word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.toLowerCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("html");
                }
            }
        }
        return null;
    }

    public ArrayList<String> getFavorite() throws SQLException {
        String querySql = "SELECT word FROM avfavorite group by word";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql);
             ResultSet resultSet = ps.executeQuery()) {
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("word"));
            }
            return list;
        }
    }

    public ArrayList<String> suggestWordsFa(String input) throws SQLException {
        input = input.toLowerCase();
        String sql = "SELECT word FROM avfavorite WHERE word LIKE ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, input + "%");
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<String> wordList = new ArrayList<>();
                while (rs.next()) {
                    wordList.add(rs.getString("word"));
                }
                return wordList;
            }
        }
    }

    public ArrayList<String> deleteFavorite() throws SQLException {
        String deleteSql = "DELETE FROM avfavorite where true";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(deleteSql)) {
            ps.executeUpdate();
            return new ArrayList<>();
        }
    }
    /*
     ********************************************************************************************************************
     *History Word
     */
    public boolean isExistHistory(String word) throws SQLException {
        String sql = "SELECT COUNT(*) FROM avhistory WHERE word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word.toLowerCase());
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public void addHistory(String word) throws SQLException {
        word = word.toLowerCase();
        String sql = "INSERT INTO avhistory(word, html, description, pronounce) SELECT word, html, description, pronounce FROM av WHERE word=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word);
            ps.executeUpdate();
        }
    }

    public ArrayList<String> getHistory() throws SQLException {
        String querySql = "SELECT word FROM avhistory";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql);
             ResultSet resultSet = ps.executeQuery()) {
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("word"));
            }
            return list;
        }
    }

    public ArrayList<String> deleteHistory() throws SQLException {
        String deleteSql = "DELETE FROM avhistory where true";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(deleteSql)) {
            ps.executeUpdate();
            return new ArrayList<>();
        }
    }
    /*
     ********************************************************************************************************************
     *Learning
     */
    public ArrayList<String> getFlashcardWord() throws SQLException {
        String querySql = "SELECT word, description FROM avfavorite";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql);
             ResultSet resultSet = ps.executeQuery()) {
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("word"));
            }
            return list;
        }
    }

    public ArrayList<String> getMultipleChoice() throws SQLException {
        String querySql = "SELECT * FROM practice";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql);
             ResultSet resultSet = ps.executeQuery()) {
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("question"));
                list.add(resultSet.getString("caseA"));
                list.add(resultSet.getString("caseB"));
                list.add(resultSet.getString("caseC"));
                list.add(resultSet.getString("true_case"));
            }
            return list;
        }
    }
}
