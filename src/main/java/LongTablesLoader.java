import java.sql.*;
import java.util.Random;

public class LongTablesLoader {
    public static final String SQL_CREATE = "CREATE TABLE ";
    public static final String SQL_INSERT = "INSERT INTO ";

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/plagiarism", "postgres", "QAZedctgb123");
            //createFirstTable(connection);
            //insertIntoFirstTable(connection);
            //createSecondTable(connection);
            insertIntoSecondTable(connection);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void insertIntoSecondTable(Connection connection) {
        StringBuilder builder = new StringBuilder();
        builder.append(SQL_INSERT);
        builder.append("data1 values(?");
        for (int i = 0; i < 100; i++) {
            builder.append(", ?");
        }
        builder.append(");");
        String insert = builder.toString();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(insert);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        Random random = new Random();
        for (int j = 0; j < 1000000; j++) {
            String random_first = getRandomString();
            try {
                for (int i = 1; i <= 100; i++) {
                    statement.setLong(i, random.nextLong());
                }
                statement.setString(101, random_first);
                int a = statement.executeUpdate();
                if(a == 0) throw new IllegalStateException("INSERT не прошел");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createSecondTable(Connection connection) throws SQLException {
        StringBuilder builder = new StringBuilder();
        builder.append(SQL_CREATE);
        builder.append("data1 ( bigint_1 bigint");
        for (int i = 2; i <= 100; i++) {
            builder.append(", " + "bigint_" + i + " bigint");
        }
        builder.append(", last_var varchar);");
        Statement statement = connection.createStatement();
        statement.execute(builder.toString());
        System.out.println(builder.toString());
    }

    private static void insertIntoFirstTable(Connection connection) {
        StringBuilder builder = new StringBuilder();
        builder.append(SQL_INSERT);
        builder.append("data values(?");
        for (int i = 0; i < 100; i++) {
            builder.append(", ?");
        }
        builder.append(");");
        String insert = builder.toString();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(insert);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        Random random = new Random();
        for (int j = 0; j < 1000000; j++) {
            String random_first = getRandomString();
            try {
                statement.setString(1, random_first);
                for (int i = 2; i <= 101; i++) {
                    statement.setLong(i, random.nextLong());
                }
                int a = statement.executeUpdate();
                if(a == 0) throw new IllegalStateException("INSERT не прошел");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getRandomString() {
        String symbols = "abcdefghijklmnopqrst";
        StringBuilder randString = new StringBuilder();
        int count = (int) (Math.random() * 30);
        for (int i = 0; i < count; i++)
            randString.append(symbols.charAt((int) (Math.random() * symbols.length())));
        return randString.toString();
    }

    private static void createFirstTable(Connection connection) throws SQLException {
        StringBuilder builder = new StringBuilder();
        builder.append(SQL_CREATE);
        builder.append("data ( first_varchar varchar");
        for (int i = 1; i <= 100; i++) {
            builder.append(", " + "bigint_" + i + " bigint");
        }
        builder.append(" );");
        Statement statement = connection.createStatement();
        statement.execute(builder.toString());
        System.out.println(builder.toString());
    }
}
