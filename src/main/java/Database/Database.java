package Database;

import java.io.*;
import java.sql.*;
import java.util.SortedSet;
import java.util.TreeSet;

public class Database {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "123456";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase"
            + "?autoReconnect=true&verifyServerCertificate=false&useSSL=true";
    private Connection conn = null;
    private PreparedStatement ps = null;
    public Database() throws SQLException {
        conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
//        initTable();
    }
/*    private void initTable() throws SQLException
//    {
//        Statement st = conn.createStatement();
//        st.executeUpdate("CREATE TABLE IF NOT EXISTS `test` (`id` INT UNSIGNED NOT NULL AUTO_INCREMENT, `key` VARCHAR(20), `definition` VARCHAR(20));");
//    }
*/

    public boolean isExist(String word) throws SQLException {
        String sql = "SELECT COUNT(*) FROM mydatabase.test WHERE word = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, word.toLowerCase());
        ResultSet rs = ps.executeQuery();
        return rs.next() && rs.getInt(1) > 0;
    }

    public void insertWord(Word word) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM mydatabase.dictionary WHERE word = ? AND detail = ?";
        ps = conn.prepareStatement(selectSql);
        ps.setString(1, word.getWord_target().toLowerCase());
        ps.setString(2, word.getWord_explain().toLowerCase());
        ResultSet rs = ps.executeQuery();
        if (rs.next() && rs.getInt(1) == 0) {
            String sql = "INSERT INTO mydatabase.dictionary(word, detail) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, word.getWord_target().toLowerCase());
            ps.setString(2, word.getWord_explain().toLowerCase());
            ps.executeUpdate();
        }
    }

    public String searchWord(String s) throws SQLException {
        String sql = "SELECT * FROM mydatabase.dictionary WHERE word = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, s.toLowerCase());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("detail");
        }
        return null;
    }

    public String deleteWord(String s) throws SQLException {
        s = s.toLowerCase();
        String sql = "DELETE FROM mydatabase.dictionary WHERE word = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, s.toLowerCase());
        ps.executeUpdate();
        return "Delete successfully";
    }

    public void updateWord(String s, String s1) throws SQLException {
        s = s.toLowerCase();
        String sql = "UPDATE mydatabase.dictionary SET detail = ? WHERE word = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, s1.toLowerCase());
        ps.setString(2, s.toLowerCase());
        ps.executeUpdate();
    }

    public void exportDataToCsv() throws SQLException, FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File("dictionary.csv"));
        StringBuilder sb = new StringBuilder();
        ResultSet rs = selectAllWord();
        while (rs.next()) {
            sb.append(rs.getString("id"));
            sb.append("\t");
            sb.append(rs.getString("word"));
            sb.append("\t");
            sb.append(rs.getString("detail"));
            sb.append("\r\n");
        }
        pw.write(sb.toString());
        pw.close();
    }

    public SortedSet<String> getListWord() throws SQLException {
        String querySql = "SELECT word FROM mydatabase.dictionary limit 59,150";

        PreparedStatement preparedStatement = conn.prepareStatement(querySql);
        ResultSet resultSet = preparedStatement.executeQuery();

        SortedSet<String> list = new TreeSet<>();

        while (resultSet.next()) {
            list.add(resultSet.getString("word"));
        }

        return list;
    }

    public ResultSet selectAllWord() throws SQLException {
        final String selectAllData = "select * from mydatabase.dictionary";
        ps = conn.prepareStatement(selectAllData);
        return ps.executeQuery();
    }


    public void closeConnection() throws SQLException {
        conn.close();
    }
}
