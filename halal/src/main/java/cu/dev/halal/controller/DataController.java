package cu.dev.halal.controller;


import cu.dev.halal.service.DataService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/openapi/data")
public class DataController {

    private final DataService dataService;

    public DataController(
            @Autowired DataService dataService
    ){
        this.dataService = dataService;
    }


    @GetMapping
    public ResponseEntity<Map> test(
            @RequestBody JSONObject json
            ) throws IOException, URISyntaxException {
        Map jsonObject = this.dataService.getData(json.get("x").toString(), json.get("y").toString());

        return ResponseEntity.status(200).body(jsonObject);
    }


}
