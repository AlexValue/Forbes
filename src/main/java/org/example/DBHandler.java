package org.example;

import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;

public class DBHandler {

    private Connection connection;
    private final String DB = "database.db";


    public void connect() throws SQLException {
        String url = "jdbc:sqlite:" + this.DB;
        connection = DriverManager.getConnection(url);
        System.out.println("Connected!\n");
    }


    public void dirmonnect() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("\nDirmonnected!");
        }
    }


    public void createRichManTable() throws SQLException {
        Statement statement = connection.createStatement();
        String createRichManTableQuery = "CREATE TABLE IF NOT EXISTS ForbesDB (" +
                "rank INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "networth REAL, " +
                "age INTEGER, " +
                "country TEXT, " +
                "source TEXT, " +
                "industry TEXT)";
        statement.execute(createRichManTableQuery);
        System.out.println("Таблица создана");
    }

    public void insertData(List<RichMan> richMen) throws SQLException {
        String insertDataQuery = "INSERT INTO ForbesDB (rank, name, networth, age, country, source, industry) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertDataQuery);
        for (RichMan rm : richMen) {
            preparedStatement.setInt(1, rm.getRank());
            preparedStatement.setString(2, rm.getName());
            preparedStatement.setDouble(3, rm.getNetworth());
            preparedStatement.setInt(4, rm.getAge());
            preparedStatement.setString(5, rm.getCountry());
            preparedStatement.setString(6, rm.getSource());
            preparedStatement.setString(7, rm.getIndustry());
            preparedStatement.execute();
        }
        System.out.println("Поля добавлены");
    }


    //Метод для выполнения запросов
    public ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }


    //Вывод столбцов и строк после выполнения
    public void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Вывод заголовков столбцов
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(metaData.getColumnName(i) + "\t");
        }
        System.out.println();

        // Вывод данных строк
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i) + "\t");
            }
            System.out.println();
        }
    }

    /*
    ЗАДАЧА 1
     */

    // Метод для получения общего капитала участников Forbes по странам
    public double getTotalNetWorthByCountry(String country) throws SQLException {
        String query = "SELECT SUM(networth) FROM ForbesDB WHERE country = '" + country + "'";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        double totalNetWorth = 0.0;
        if (resultSet.next()) {
            totalNetWorth = resultSet.getDouble(1);
        }

        resultSet.close();
        statement.close();

        return totalNetWorth;
    }

    // Получает список уникальных стран из базы данных ForbesDB
    public List<String> getUniqueCountries() throws SQLException {
        List<String> countries = new ArrayList<>();

        String query = "SELECT DISTINCT country FROM ForbesDB";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String country = resultSet.getString("country");
            countries.add(country);
        }

        resultSet.close();
        statement.close();

        return countries;
    }


    //Метод для получения среднего количества студентов в конкретной стране
    public double getAverageStudents(String county) throws SQLException {
        String query = "SELECT AVG(students) FROM rmhool WHERE county = '" + county + "'";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        double averageStudents = 0.0;
        if (resultSet.next()) {
            averageStudents = resultSet.getDouble(1);
        }

        resultSet.close();
        statement.close();

        return averageStudents;
    }
}