package springboot;

import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarINTERFACE {

    public CarDAO carDAO = new CarDAO();
    public CarDTO carDTO = new CarDTO();

    public int dbInfo() throws SQLException {
        ResultSet resultSet = carDAO.dbCount("CARS");
        carDTO.setCount(0);
        while (resultSet.next()){
            carDTO.setCount(resultSet.getInt(1));
        }
        return carDTO.getCount();
    }

    public CarDTO dbShowCars(String method, String search){
        ResultSet resultSet = carDAO.dbShowCars(method, search);

    }


}
