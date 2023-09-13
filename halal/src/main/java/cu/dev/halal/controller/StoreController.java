package cu.dev.halal.controller;


import cu.dev.halal.dto.StoreDTO;
import cu.dev.halal.entity.StoreEntity;
import cu.dev.halal.service.StoreService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
public class StoreController {
    private final static Logger logger= LoggerFactory.getLogger(StoreController.class);
    private final StoreService storeService;


    public StoreController(
            @Autowired StoreService storeService
    ){
        this.storeService = storeService;
    }


    @PostMapping
    public ResponseEntity<JSONObject> createStore(
            @RequestBody StoreDTO storeDTO
            ){
        JSONObject jsonObject = this.storeService.createStore(storeDTO);
        if(!jsonObject.containsValue("success")){
            return ResponseEntity.status(400).body(jsonObject);
        }

        return ResponseEntity.status(201).body(jsonObject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> readStore(
            @PathVariable("id") Long id
    ){
        logger.info("read");
        StoreDTO storeDTO = this.storeService.readStore(id);
        if(storeDTO.getName().equals("not exists")){
            return ResponseEntity.status(400).body(storeDTO);
        }

        return ResponseEntity.status(200).body(storeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JSONObject> deleteStore(
            @PathVariable("id") Long id
    ){
        JSONObject jsonObject = this.storeService.deleteStore(id);
        if(!jsonObject.containsValue("success")){
            return ResponseEntity.status(400).body(jsonObject);
        }

        return ResponseEntity.status(200).body(jsonObject);

    }


}
