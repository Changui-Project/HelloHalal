package cu.dev.halal.controller;


import cu.dev.halal.service.DataService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

// 공공데이터 Open API 에서 데이터를 가져와 파싱 후 디비에 값 저장
@RestController
@RequestMapping("/openapi/data")
public class DataController {

    private final DataService dataService;

    public DataController(
            @Autowired DataService dataService
    ){
        this.dataService = dataService;
    }

    // 위도 경도 기준 20KM 내의 데이터를 가져와서 중복없이 Database에 저장
    @GetMapping
    public ResponseEntity<Map> test(
            @RequestBody JSONObject json
            ) throws IOException, URISyntaxException {
        Map jsonObject = this.dataService.getData(json.get("x").toString(), json.get("y").toString());

        return ResponseEntity.status(200).body(jsonObject);
    }


}
