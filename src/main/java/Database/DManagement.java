package Database;

import java.io.*;
import java.sql.*;
import java.util.SortedSet;
import java.util.TreeSet;

public class DManagement {
    private static Connection conn;
    private static PreparedStatement ps = null;
    public DManagement() throws SQLException {
        conn = Database.getConnection();
        initTable();
    }
    private void initTable() throws SQLException
    {
        Statement st = conn.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS `test` (`id` INT UNSIGNED NOT NULL AUTO_INCREMENT, `key` VARCHAR(20), `definition` VARCHAR(20));");
    }
    public static void insertWord(Word word) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM mydatabase.test WHERE word = ? AND detail = ?";
        ps = conn.prepareStatement(selectSql);
        ps.setString(1, word.getWord_target().toLowerCase());
        ps.setString(2, word.getWord_explain().toLowerCase());
        ResultSet rs = ps.executeQuery();
        if (rs.next() && rs.getInt(1) == 0) {
            String sql = "INSERT INTO mydatabase.test(word, detail) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, word.getWord_target().toLowerCase());
            ps.setString(2, word.getWord_explain().toLowerCase());
            ps.executeUpdate();
        }
    }

    public String searchWord(String s) throws SQLException {
        String sql = "SELECT * FROM mydatabase.test WHERE word = ?";
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
        String sql = "DELETE FROM mydatabase.test WHERE word = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, s.toLowerCase());
        ps.executeUpdate();
        return "Delete successfully";
    }

    public void updateWord(String s, String s1) throws SQLException {
        s = s.toLowerCase();
        String sql = "UPDATE mydatabase.test SET detail = ? WHERE word = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, s1.toLowerCase());
        ps.setString(2, s.toLowerCase());
        ps.executeUpdate();
    }

    public void exportDataToCsv() throws SQLException, FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File("dictionary.csv"));
        StringBuilder sb = new StringBuilder();
        ResultSet rs = selectAllWord();
        /*
         * NOTE: For your computer safety, please don't iterate through all the database. The database
         * have 200000 rows, it might crash the app ¯\_(ツ)_/¯
         */
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
        String querySql = "SELECT word FROM mydatabase.test";

        Statement statement = conn.createStatement();
        statement.executeUpdate(querySql);
        ResultSet resultSet = statement.executeQuery(querySql);

        SortedSet<String> list = new TreeSet<String>();

        while (resultSet.next()) {
            list.add(resultSet.getString("word"));
        }
        return list;
    }
    public ResultSet selectAllWord() throws SQLException {
        final String selectAllData = "select * from mydatabase.test";
        ps = conn.prepareStatement(selectAllData);
        return ps.executeQuery();
    }

//    public static void main(String[] args) {
//        try {
//            DManagement dm = new DManagement();
//            Word word = new Word();
//            word.setWord_target("Hello");
//            word.setWord_explain("xin chao");
//            insertWord(word);
//
//            //System.out.println(dm.deleteWord("hello"));
//            //System.out.println(dm.searchWord("hello"));
//            dm.updateWord("hello", "xin chaos");
//            //System.out.println(dm.searchWord("hello"));
//            ResultSet rs = dm.selectAllWord();
//            while (rs.next()) {
//                System.out.println(rs.getString("id") + " " + rs.getString("word") + " " + rs.getString("detail"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
