import java.sql.*;
import java.util.Date;

public class DataServiceLoader {
    private Connection connection;
    //language=SQL
    private static String SQL_INSERT_INTO;
    public static DataServiceLoader create(String jdbcUsername, String jdbcPassword, String jdbcUrl, Integer order) {
        return new DataServiceLoader(jdbcUrl, jdbcUsername, jdbcPassword, order);
    }
    private DataServiceLoader(String jdbcUrl, String jdbcUsername, String jdbcPassword, Integer order) {
        try {
            connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        try {
            String SQl_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS plagiat" + order + "(base varchar (100))";
            Statement statement = connection.createStatement();
            statement.execute(SQl_CREATE_TABLE);
            statement.close();
            SQL_INSERT_INTO = "insert into plagiat" +order + " values (?)";
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    public void save(String parametr) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTO)) {
            statement.setString(1, parametr);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}
