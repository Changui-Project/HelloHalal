package cu.dev.halal.controller;


import cu.dev.halal.dto.FavoriteDTO;
import cu.dev.halal.service.FavoritesService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    private final static Logger logger = LoggerFactory.getLogger(FavoriteController.class);
    private final FavoritesService favoritesService;

    public FavoriteController(
            @Autowired FavoritesService favoritesService
    ){
        this.favoritesService = favoritesService;
    }


    @PostMapping()
    public ResponseEntity<JSONObject> addFavorite(
            @RequestBody FavoriteDTO favoriteDTO
            ){
        JSONObject jsonObject = this.favoritesService.addFavorite(favoriteDTO);
        if(!jsonObject.get("result").equals("success")){
            return ResponseEntity.status(400).body(jsonObject);
        }

        return ResponseEntity.status(201).body(jsonObject);
    }


    @DeleteMapping()
    public ResponseEntity<JSONObject> deleteFavorite(
            @RequestBody FavoriteDTO favoriteDTO
    ){
        JSONObject jsonObject = this.favoritesService.deleteFavorite(favoriteDTO);
        if(jsonObject.get("result").equals("success")){
            return ResponseEntity.status(400).body(jsonObject);
        }

        return ResponseEntity.status(200).body(jsonObject);
    }

    @GetMapping()
    public ResponseEntity<JSONObject> getFavorites(
            @RequestParam String email
    ){
        JSONObject jsonObject = this.favoritesService.getFavorites(email);
        List targetList = (List) jsonObject.get("favorites");
        if(targetList.isEmpty()){
            return ResponseEntity.status(404).body(jsonObject);
        }

        return ResponseEntity.status(200).body(jsonObject);
    }


}

