package cu.dev.halal.controller;


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
        return ResponseEntity.status(200).body(this.addressService.toCoordinate(address));
    }


}
