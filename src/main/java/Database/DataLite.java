package Database;


import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;

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
 author: anh tu
 * Word of the day
 */
    public String searchWordbyID(int num) throws SQLException {
        String sql = "SELECT word FROM av WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(num));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("word");
                }
            }
        }
        return null;
    }

    public String searchPOSbyID(int num) throws SQLException {
        String sql = "SELECT html FROM av WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(num));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String s = rs.getString("html");
                    if (s.contains("danh từ")) return "noun";
                    else if (s.contains("động từ")) return "verb";
                    else if (s.contains("tính từ")) return "adjective";
                    else if (s.contains("giới từ")) return "preposition";
                    else if (s.contains("trạng từ") || s.contains("phó từ")) return "adverb";
                    else if (s.contains("đại từ")) return "pronoun";
                    else return "";
                }
            }
        }
        return null;
    }

    public String searchIPAbyID(int num) throws SQLException {
        String sql = "SELECT * FROM av WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(num));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("pronounce");
                }
            }
        }
        return null;
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

    public void addWord(String word, String pos, String breIpa, String meaning) throws SQLException {
        String html = convertToHTML(word + "<br>" +breIpa, pos, meaning);
        String sql = "INSERT INTO av(word, description, html, pronounce) VALUES(?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word);
            ps.setString(2, pos + "; " + meaning);
            ps.setString(3, html);
            ps.setString(4, breIpa);
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

//    public void addFavorite(String word) throws SQLException {
//        String sql = "INSERT INTO avFavorite(word_id, account_id, word, html, description, pronounce)\n" +
//                "SELECT id, active_id, word, html, description, pronounce\n" +
//                "FROM av, activeAccount\n" +
//                "WHERE word = ?";
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, word);
//            ps.executeUpdate();
//        }
//    }

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

    public ArrayList<String> getFavoritePOS() throws SQLException {
        String querySql = "SELECT pronounce FROM avfavorite group by word";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql);
             ResultSet resultSet = ps.executeQuery()) {
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("pronounce"));
            }
            return list;
        }
    }

    public ArrayList<String> getFavoriteDetail() throws SQLException {
        String querySql = "SELECT description FROM avfavorite group by word";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql);
             ResultSet resultSet = ps.executeQuery()) {
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("description"));
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

    public ArrayList<String> suggestPronounceFa(String input) throws SQLException {
        input = input.toLowerCase();
        String sql = "SELECT pronounce FROM avfavorite WHERE word LIKE ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, input + "%");
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<String> wordList = new ArrayList<>();
                while (rs.next()) {
                    wordList.add(rs.getString("pronounce"));
                }
                return wordList;
            }
        }
    }

    public ArrayList<String> suggestDetailFa(String input) throws SQLException {
        input = input.toLowerCase();
        String sql = "SELECT description FROM avfavorite WHERE word LIKE ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, input + "%");
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<String> wordList = new ArrayList<>();
                while (rs.next()) {
                    wordList.add(rs.getString("description"));
                }
                return wordList;
            }
        }
    }

    public ArrayList<String> deleteAllFavorite() throws SQLException {
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
    public void addHistory(String word) throws SQLException {
        String sql = "INSERT INTO avhistory(word) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word);
            ps.executeUpdate();
        }
    }

    public ArrayList<String> getHistory() throws SQLException {
        String querySql = "SELECT word FROM avhistory order by id desc limit 20";
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

    public void deleteHistory() throws SQLException {
        String deleteSql = "DELETE FROM avhistory where true";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(deleteSql)) {
            ps.executeUpdate();
        }
    }
    /*
     ********************************************************************************************************************
     *Learning
     */

    public void insertActiveAccount(String username, String password) throws SQLException {
        deleteActiveAccount();
        String sql = "INSERT INTO activeAccount(active_id, username, password, email)\n" +
                "SELECT id, username, password, email\n" +
                "FROM account\n" +
                "WHERE username = ? AND password = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
//            try (ResultSet rs = ps.executeQuery()) {
//
//            }
            ps.executeUpdate();
        }
    }

    public void deleteActiveAccount() throws SQLException {
        String sql = "DELETE FROM activeAccount";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }
    /*
        ********************************************************************************************************************
        * Flashcard
        */
    public ArrayList<String> getFlashcard() throws SQLException {
        String querySql = "SELECT word, description FROM av where id = (select id_av from practice)";;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(querySql);
             ResultSet resultSet = ps.executeQuery()) {
            ArrayList<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("word"));
                list.add(resultSet.getString("description"));
            }
            return list;
        }
    }

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
