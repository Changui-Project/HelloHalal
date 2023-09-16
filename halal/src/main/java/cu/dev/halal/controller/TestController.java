package cu.dev.halal.controller;


import com.google.gson.JsonObject;
import cu.dev.halal.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    private final AddressService addressService;


    public TestController(
            @Autowired AddressService addressService
    ){
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<String> toCoordinate(
            @RequestParam String address
    ){
        return ResponseEntity.status(200).body(this.addressService.toCoordinate(address).toString());
    }

    @GetMapping
    public ResponseEntity<?> distance(
            @RequestParam("x1") double x1,
            @RequestParam("y1") double y1,
            @RequestParam("x2") double x2,
            @RequestParam("y2") double y2
            ){

        Double distance = this.addressService.dis(x1, y1, x2, y2);
        return ResponseEntity.status(200).body(distance);
    }

}
