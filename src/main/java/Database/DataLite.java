package Database;


import java.sql.*;
import java.util.ArrayList;


public class DataLite {
    private static final String DB_URL_Lite = "jdbc:sqlite:src/main/java/Database/dict_hh.db";
    private final Connection conn;
    private PreparedStatement ps = null;
    public DataLite() throws SQLException {
        conn = DriverManager.getConnection(DB_URL_Lite);
    }
    public void close() throws SQLException {
        conn.close();
    }
    public String searchWord(String s) throws SQLException {
        String sql = "SELECT * FROM av WHERE word = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, s.toLowerCase());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("html");
        }
        close();
        return null;
    }
        public ArrayList<String> getListWord() throws SQLException {
        String querySql = "SELECT word FROM av limit 58171";

        PreparedStatement preparedStatement = conn.prepareStatement(querySql);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<String> list = new ArrayList<>();

        while (resultSet.next()) {
            list.add(resultSet.getString("word"));
        }
        close();
        return list;
    }
}
