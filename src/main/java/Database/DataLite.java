package Database;


import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class DataLite {
    private static final String DB_URL_Lite = "jdbc:sqlite:src/main/java/Database/dict_hh.db";
    private final HikariDataSource dataSource;
    public DataLite() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL_Lite);
        config.setMaximumPoolSize(20);
        dataSource = new HikariDataSource(config);
    }

    public void close() {
        dataSource.close();
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
     *Add Word
     */
    public void addWord(String word, String pos, String breIpa, String nameIpa, String meaning) throws SQLException {
    }

    public boolean isExist(String word) throws SQLException {
        String sql = "SELECT COUNT(*) FROM av WHERE word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, word.toLowerCase());
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
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

    public void updateWord(String s, String s1) throws SQLException {
        s = s.toLowerCase();
        String sql = "UPDATE av SET description = ? WHERE word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s1.toLowerCase());
            ps.setString(2, s.toLowerCase());
            ps.executeUpdate();
        }
    }
}
