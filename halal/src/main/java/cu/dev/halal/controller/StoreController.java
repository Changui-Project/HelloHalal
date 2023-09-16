package cu.dev.halal.controller;


import cu.dev.halal.dto.RangeDTO;
import cu.dev.halal.dto.StoreDTO;
import cu.dev.halal.service.StoreService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



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


    // Store 정보 등록
    @PostMapping
    public ResponseEntity<JSONObject> createStore(
            @RequestPart("body") StoreDTO storeDTO,
            @RequestPart("images") List<MultipartFile> images
            ) throws IOException {
        logger.info("[StoreController] create store : {}", storeDTO.toString());
        List<byte[]> imageList = new ArrayList<>();
        for(MultipartFile multipartFile : images){
            imageList.add(multipartFile.getBytes());
        }


        JSONObject jsonObject = this.storeService.createStore(storeDTO, imageList);
        // 정보 등록이 success가 아니라면 400 Bad Request
        if(!jsonObject.containsValue("success")){
            return ResponseEntity.status(400).body(jsonObject);
        }
        logger.info("[StoreController] create store : {}", jsonObject.toString());
        return ResponseEntity.status(201).body(jsonObject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> readStore(
            @PathVariable("id") Long id
    ){
        logger.info("[StoreController] read store : {}", id);
        StoreDTO storeDTO = this.storeService.readStore(id);
        // Store가 존재하지 않는다면 400 Bad Request
        if(storeDTO.getName().equals("not exists")){
            return ResponseEntity.status(400).body(storeDTO);
        }
        logger.info("[StoreController] read store : {}", storeDTO.toString());
        return ResponseEntity.status(200).body(storeDTO);
    }

    @GetMapping
    public ResponseEntity<JSONObject> readAllAroundStore(
            @RequestBody RangeDTO rangeDTO
            ){
        logger.info("[StoreController] readAllBy Around store : {}", rangeDTO.toString());
        JSONObject jsonObject = this.storeService.readAllRoundStore(rangeDTO);
        if(jsonObject.containsKey("result")){
            return ResponseEntity.status(400).body(jsonObject);
        }
        logger.info("[StoreController] readAllBy Around store : {}", jsonObject.toString());
        return ResponseEntity.status(200).body(jsonObject);
    }

    @GetMapping("/coordinate")
    public ResponseEntity<JSONObject> readAllByCoordinateAroundStore(
            @RequestParam("x") Double x,
            @RequestParam("y") Double y
    ){
        JSONObject jsonObject = this.storeService.readAllCoordinateAroundStore(x, y);
        return ResponseEntity.status(200).body(jsonObject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JSONObject> deleteStore(
            @PathVariable("id") Long id
    ){
        logger.info("[StoreController] delete store : {}", id);
        JSONObject jsonObject = this.storeService.deleteStore(id);
        if(!jsonObject.containsValue("success")){
            return ResponseEntity.status(400).body(jsonObject);
        }
        logger.info("[StoreController] delete store : {}", jsonObject.toString());
        return ResponseEntity.status(200).body(jsonObject);

    }


}
