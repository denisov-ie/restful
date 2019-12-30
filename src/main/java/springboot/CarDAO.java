package springboot;

import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.ResultSet;

import java.sql.*;

public class CarDAO {

    private static final String DB_URL = "jdbc:h2:mem:test";
    private static final String DB_Driver = "org.h2.Driver";
    private static final String DB_User = "sa";
    private static final String DB_Password = "";
    private Connection connection = null;
    private PreparedStatement statement = null;
    private static final String DB_Create = "CREATE TABLE MANUFACTURERS (MAN_ID BIGINT AUTO_INCREMENT PRIMARY KEY, MAN_NAME VARCHAR(255));" +
            "CREATE TABLE MODELS (MOD_ID BIGINT AUTO_INCREMENT PRIMARY KEY, MOD_NAME VARCHAR(255), MAN_ID BIGINT, FOREIGN KEY (MAN_ID) REFERENCES MANUFACTURERS(MAN_ID));" +
            "CREATE TABLE CARS (CAR_ID BIGINT AUTO_INCREMENT PRIMARY KEY, CAR_VIN VARCHAR(255), CAR_PRICE INT, MOD_ID BIGINT, FOREIGN KEY (MOD_ID) REFERENCES MODELS(MOD_ID));";
    private static final String DB_ExampleData = "INSERT INTO MANUFACTURERS(MAN_NAME) VALUES('Toyota');" +
            "INSERT INTO MANUFACTURERS(MAN_NAME) VALUES('Mazda');" +
            "INSERT INTO MANUFACTURERS(MAN_NAME) VALUES('Lada');" +
            "INSERT INTO MANUFACTURERS(MAN_NAME) VALUES('Land Rover');" +
            "INSERT INTO MANUFACTURERS(MAN_NAME) VALUES('Huyndai');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('Camry', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Toyota');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('Corolla', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Toyota');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('Land Cruiser', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Toyota');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('Highlander', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Toyota');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('CX-5', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Mazda');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('CX-9', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Mazda');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('BT-50', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Mazda');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('Granta', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Lada');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('Vesta', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Lada');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('Vesta SW', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Lada');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('Kalina', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Lada');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('Evoque', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Land Rover');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('Defender', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Land Rover');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('Creta', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Huyndai');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('Getz', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Huyndai');" +
            "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('Solaris', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE 'Huyndai');" +
            "INSERT INTO CARS(CAR_VIN,CAR_PRICE,MOD_ID) VALUES('TESTVIN1',10000,SELECT MOD_ID FROM MODELS WHERE MOD_NAME LIKE 'Camry');" +
            "INSERT INTO CARS(CAR_VIN,CAR_PRICE,MOD_ID) VALUES('TESTVIN2',8000,SELECT MOD_ID FROM MODELS WHERE MOD_NAME LIKE 'Creta');" +
            "INSERT INTO CARS(CAR_VIN,CAR_PRICE,MOD_ID) VALUES('TESTVIN3',12000,SELECT MOD_ID FROM MODELS WHERE MOD_NAME LIKE 'CX-5');" +
            "INSERT INTO CARS(CAR_VIN,CAR_PRICE,MOD_ID) VALUES('TESTVIN4',19000,SELECT MOD_ID FROM MODELS WHERE MOD_NAME LIKE 'Land Cruiser');" +
            "INSERT INTO CARS(CAR_VIN,CAR_PRICE,MOD_ID) VALUES('TESTVIN5',23000,SELECT MOD_ID FROM MODELS WHERE MOD_NAME LIKE 'Evoque');" +
            "INSERT INTO CARS(CAR_VIN,CAR_PRICE,MOD_ID) VALUES('TESTVIN6',4000,SELECT MOD_ID FROM MODELS WHERE MOD_NAME LIKE 'Granta');" +
            "INSERT INTO CARS(CAR_VIN,CAR_PRICE,MOD_ID) VALUES('TESTVIN7',9000,SELECT MOD_ID FROM MODELS WHERE MOD_NAME LIKE 'Corolla');" +
            "INSERT INTO CARS(CAR_VIN,CAR_PRICE,MOD_ID) VALUES('TESTVIN8',11000,SELECT MOD_ID FROM MODELS WHERE MOD_NAME LIKE 'Defender');";

    public void dbConnect(){
        try{
            Class.forName(DB_Driver);
            connection = DriverManager.getConnection(DB_URL, DB_User, DB_Password);
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public ResultSet dbExecuteQuery(String query, String type){
        dbConnect();
        try{
            statement = connection.prepareStatement(query);
            if (type.equals("import")) {
                ResultSet resultSet = statement.executeQuery();
                return resultSet;
                /*JSONArray jsonArray = new JSONArray();
                while (resultSet.next()) {
                    int total_rows = resultSet.getMetaData().getColumnCount();
                    for (int i = 0; i < total_rows; i++) {
                        JSONObject obj = new JSONObject();
                        obj.put(resultSet.getMetaData().getColumnLabel(i + 1),
                                resultSet.getObject(i + 1));
                        jsonArray.put(obj);
                    }
                }
                return jsonArray;*/
            }
            else {
                statement.executeUpdate();
                return null;
            }
        }
        catch (SQLException e) {
            return null;
        }
    }

    public ResultSet dbCount(String tableName) {
        String query = "SELECT COUNT (*) FROM " + tableName;
        return dbExecuteQuery(query , "import");
    }

    public ResultSet dbShowCars() {
        String query = "SELECT CAR_ID, CAR_VIN, CAR_PRICE, MOD_NAME, MAN_NAME FROM CARS JOIN MODELS ON" +
                " (CARS.MOD_ID = MODELS.MOD_ID) JOIN MANUFACTURERS ON (MODELS.MAN_ID = MANUFACTURERS.MAN_ID)";
        return dbExecuteQuery(query, "import");
    }

    public ResultSet dbShowCars(String method, String search) {
        String query = "SELECT CAR_ID, CAR_VIN, CAR_PRICE, MOD_NAME, MAN_NAME FROM CARS JOIN MODELS ON" +
                " (CARS.MOD_ID = MODELS.MOD_ID) JOIN MANUFACTURERS ON (MODELS.MAN_ID = MANUFACTURERS.MAN_ID) WHERE " +
                method + " LIKE '" + search + "'";
        return dbExecuteQuery(query, "import");
    }

    public String dbAdd(CarDTO carPOJO, String method) {
        String query = "";
        switch (method) {
            case "CARS":
                query = "INSERT INTO CARS(CAR_VIN,CAR_PRICE,MOD_ID) VALUES('" + carPOJO.getCarVIN() + "'," + carPOJO.getCarPrice() + ",SELECT MOD_ID FROM MODELS WHERE MOD_NAME LIKE '" + carPOJO.getCarModel() + "');";
                break;
            case "MODELS":
                query = "INSERT INTO MODELS(MOD_NAME,MAN_ID) VALUES('" + carPOJO.getCarModel() + "', SELECT MAN_ID FROM MANUFACTURERS WHERE MAN_NAME LIKE '" + carPOJO.getCarManufacturer() + "');";
                break;
            case "MANUFACTURERS":
                query = "INSERT INTO MANUFACTURERS(MAN_NAME) VALUES('" + carPOJO.getCarManufacturer() + "');";
                break;
        }
        dbExecuteQuery(query, "export");
        return "Added";
    }

    public void dbCreate(){
        dbExecuteQuery(DB_Create, "export");
        dbExecuteQuery(DB_ExampleData, "export");
    }


}
