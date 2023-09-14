package cu.dev.halal.controller;


import cu.dev.halal.dto.FavoriteDTO;
import cu.dev.halal.service.FavoritesService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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


    // User의 즐겨찾기(찜)한 Store의 Id를 가진 정보를 저장
    @PostMapping
    public ResponseEntity<JSONObject> addFavorite(
            @RequestBody FavoriteDTO favoriteDTO
            ){
        JSONObject jsonObject = this.favoritesService.addFavorite(favoriteDTO);
        // result가 success가 아니라면 Bad Request 및 실패 사유를 응답
        if(!jsonObject.get("result").equals("success")){
            return ResponseEntity.status(400).body(jsonObject);
        }
        // result가 success라면 Created 응답
        return ResponseEntity.status(201).body(jsonObject);
    }


    // User의 즐겨찾기를 삭제한다.
    @DeleteMapping
    public ResponseEntity<JSONObject> deleteFavorite(
            @RequestBody FavoriteDTO favoriteDTO
    ){
        JSONObject jsonObject = this.favoritesService.deleteFavorite(favoriteDTO);
        // result가 success가 아니라면 Bad Request 및 실패 사유 응답
        if(!jsonObject.get("result").equals("success")){
            return ResponseEntity.status(400).body(jsonObject);
        }
        // result가 success라면 Ok 응답
        return ResponseEntity.status(200).body(jsonObject);
    }

    // User의 모든 즐겨찾기를 응답한다.
    @GetMapping()
    public ResponseEntity<JSONObject> getFavorites(
            @RequestParam String email
    ){
        JSONObject jsonObject = this.favoritesService.getFavorites(email);
        if(jsonObject.containsKey("result")){
            return ResponseEntity.status(400).body(jsonObject);
        }

        return ResponseEntity.status(200).body(jsonObject);
    }


}

