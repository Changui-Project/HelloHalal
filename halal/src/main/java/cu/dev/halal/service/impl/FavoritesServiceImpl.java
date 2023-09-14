package cu.dev.halal.service.impl;

import cu.dev.halal.dao.FavoritesDAO;
import cu.dev.halal.dto.FavoriteDTO;
import cu.dev.halal.entity.FavoriteEntity;
import cu.dev.halal.entity.StoreEntity;
import cu.dev.halal.entity.UserEntity;
import cu.dev.halal.service.FavoritesService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoritesServiceImpl implements FavoritesService {
    private final static Logger logger = LoggerFactory.getLogger(FavoritesServiceImpl.class);
    private final FavoritesDAO favoritesDAO;

    public FavoritesServiceImpl(
            @Autowired FavoritesDAO favoritesDAO
    ){
        this.favoritesDAO = favoritesDAO;
    }


    // FavoriteDTO -> FavoriteEntity
    @Override
    public JSONObject addFavorite(FavoriteDTO dto) {
        return this.favoritesDAO.addFavorite(
                FavoriteEntity.builder()
                        .user(UserEntity.builder().email(dto.getEmail()).build())
                        .store(StoreEntity.builder().id(dto.getStoreId()).build())
                        .build());
    }

    // FavoriteDTO -> favoriteEntity
    @Override
    public JSONObject deleteFavorite(FavoriteDTO dto) {
        return this.favoritesDAO.deleteFavorite(
                FavoriteEntity.builder()
                        .user(UserEntity.builder().email(dto.getEmail()).build())
                        .store(StoreEntity.builder().id(dto.getStoreId()).build())
                        .build());
    }

    // FavoriteEntity -> Store Id 배열로 변환 후 반환
    @Override
    public JSONObject getFavorites(String email) {
        JSONObject jsonObject = new JSONObject();
        List<FavoriteEntity> favorites = this.favoritesDAO.getFavorites(email);
        try{
            if (!favorites.isEmpty()) {
                ArrayList<Long> result = new ArrayList<>();
                for (FavoriteEntity favorite : favorites) {
                    result.add(favorite.getStore().getId());
                }
                jsonObject.put("favorites", result);
                return jsonObject;
            } else {
                jsonObject.put("favorites", favorites);
                return jsonObject;
            }
        }catch (NullPointerException e){
            jsonObject.put("result", "user or store not exists");
            return jsonObject;
        }
    }
}
