import java.sql.Date;
import java.sql.*;
public class DataBaseHandler extends Configs {
    Connection dbConnection;
    public Connection getDbConnection() throws SQLException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");
        dbConnection = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
        System.out.println("Успешно подключено");
        return dbConnection;
    }
    public void signUpUser(User user){
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_USERNAME + "," +
                Const.USERS_PASSWORD + "," +
                Const.USERS_Email + ")" +
                "VALUES(?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, user.getEmail());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getUser (User user){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void signUpObject(Thing thing, String user){
        String insert = "INSERT INTO " + Const.OBJECTS_TABLE + "(" +
                Const.OBJECTS_NAME + "," +
                Const.OBJECTS_DIRECTION + "," +
                Const.OBJECTS_WEIGHT + "," +
                Const.OBJECTS_CREATOR + ")" +
                "VALUES(?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, thing.getName());
            prSt.setString(2, thing.getDirection());
            prSt.setInt(3, thing.getWeight());
            prSt.setString(4,user);

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void newSighUpObject(Thing thing,String user, Date date){
        String insert = "INSERT INTO " + Const.OBJECTS_TABLE + "(" +
                Const.OBJECTS_NAME + "," +
                Const.OBJECTS_DIRECTION + "," +
                Const.OBJECTS_WEIGHT + "," +
                Const.OBJECTS_CREATOR + "," +
                Const.OBJECTS_DATE +
                ")" +
                "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, thing.getName());
            prSt.setString(2, thing.getDirection());
            prSt.setInt(3, thing.getWeight());
            prSt.setString(4,user);
            prSt.setDate(5, date);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getAllUsers(){
        ResultSet resultSet = null;
        String select = "SELECT " + Const.USERS_USERNAME + " FROM " + Const.USER_TABLE;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException ex){
            ex.getErrorCode();
        }catch (ClassNotFoundException ex){
            ex.getException();
        }
        return resultSet;
    }
    public ResultSet getObject(){
        ResultSet resultSet = null;

        String select = "SELECT " + Const.OBJECTS_NAME + "," +
                Const.OBJECTS_DIRECTION + "," + Const.OBJECTS_WEIGHT + "," + Const.OBJECTS_CREATOR +
                " FROM " + Const.OBJECTS_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(resultSet);

        return resultSet;
    }
    public ResultSet checkUser (User user){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? OR " + Const.USERS_PASSWORD + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public void removeAllUserObjects(String thatUser){
        String delete = "DELETE FROM " + Const.OBJECTS_TABLE + " WHERE " + Const.OBJECTS_CREATOR + " =? ";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(delete);
            preparedStatement.setString(1,thatUser);
            preparedStatement.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public ResultSet getAllUserObjects(String thatUser){
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.OBJECTS_TABLE + " WHERE " + Const.OBJECTS_CREATOR + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1,thatUser);
            resultSet = preparedStatement.executeQuery();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return resultSet;

    }
    public ResultSet getAllObjects(){
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + Const.OBJECTS_TABLE;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);

            resultSet = preparedStatement.executeQuery();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return resultSet;

    }
}

