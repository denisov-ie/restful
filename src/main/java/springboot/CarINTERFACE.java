package springboot;

import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarINTERFACE {

    public CarDAO carDAO = new CarDAO();
    public CarDTO carDTO = new CarDTO();

    public int dbInfo() throws SQLException {
        ResultSet resultSet = carDAO.dbCount("CARS");
        int i = 0;
        while (resultSet.next()){
            i = resultSet.getInt(1);
        }
        return i;
    }

    public ArrayList<CarDTO> dbShowCars(String method, String search){
        ResultSet resultSet = carDAO.dbShowCars(method, search);
        while (resultSet.next()){
            carDTO.setCarManufacturer(resultSet.);
        }


    }


}
