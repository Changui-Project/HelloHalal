package cu.dev.halal.controller;


import cu.dev.halal.dto.FavoriteDTO;
import cu.dev.halal.service.FavoritesService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        logger.info(favoriteDTO.toString());
        return ResponseEntity.ok().body(this.favoritesService.addFavorite(favoriteDTO));
    }


    @DeleteMapping()
    public ResponseEntity<JSONObject> deleteFavorite(
            @RequestBody FavoriteDTO favoriteDTO
    ){
        logger.info(favoriteDTO.toString());
        return ResponseEntity.ok().body(this.favoritesService.deleteFavorite(favoriteDTO));
    }

    @GetMapping()
    public ResponseEntity<JSONObject> getFavorites(
            @RequestParam String email
    ){
        List<Long> target = this.favoritesService.getFavorites(email);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("favorites", target);

        return ResponseEntity.ok().body(jsonObject);
    }


}

