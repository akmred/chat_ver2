package server;

import java.sql.*;

public class BasesUsers {

    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement psInsert;

    public BasesUsers() {
        try {
            connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            prepareAllStatements();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:access.db");
        stmt = connection.createStatement();
    }

    /* Процедура по пользователю и паролю возвращает ник пользователя
    * Параметры:
    * login - логин
    * password - пароль
    *
    * Возвращаемое значение:
    * String - ник или пустая строка, если логин и пароль не подошел
    */
    public String CheckingLoginPassword(String login, String password){

        // Запросим таблицу базы данных
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM USERS WHERE Login = '" + login
                    + "' AND Password='" + password + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (rs.next()){
                return  rs.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    /* Процедура проверяет наличие пользоватяля
     * Параметры:
     * login - логин
     *
     * Возвращаемое значение:
     * Boolean - тайок пользователь есть
     */
    public Boolean CheckingLogin(String login){

        // Запросим таблицу базы данных
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT * FROM USERS WHERE Login = '" + login + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (rs.next()){
                return  true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Процедура добавляет пользователя в базу данных
    public void addUser(String nick, String login, String password) throws SQLException {

        psInsert.setString(1, nick);
        psInsert.setString(2, login);
        psInsert.setString(3, password);
        psInsert.executeUpdate();

    }

    // Процедура готовит шаблон добавления пользователя
    private void prepareAllStatements() throws SQLException {
        psInsert = connection.prepareStatement("INSERT INTO USERS (Nick,Login,Password) " +
                "VALUES(?,?,?)");
    }

}
