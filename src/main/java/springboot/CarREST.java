package springboot;

import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/info")

public class CarREST {

    //public CarDAO dao = new CarDAO();
    public CarDTO dto = new CarDTO();
    public CarINTERFACE carInterface = new CarINTERFACE();

    @GetMapping
    public int showStatus() throws SQLException {
        return carInterface.dbInfo();
    }

    @GetMapping("/showcar/{method}/{search}")
    public ArrayList<CarDTO> carShow(@PathVariable String method, @PathVariable String search) {
        return carInterface.dbShowCars(method, search);
    }

 /*   @GetMapping("/showcar")
    public List<Object> carShow() {
        return dao.dbShowCars().toList();
    }

    @GetMapping("/showcar/{method}/{search}")
    public List<Object> carShow(@PathVariable String method, @PathVariable String search) {
        return dao.dbShowCars(method, search).toList();
    }

    @PostMapping("/addcar")
    public String add(@RequestBody CarDTO request) {
        return dao.dbAdd(request, "CARS");
    }*/
}