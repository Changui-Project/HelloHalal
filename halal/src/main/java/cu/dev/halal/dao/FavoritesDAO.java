package cu.dev.halal.dao;

import cu.dev.halal.dto.FavoriteDTO;
import cu.dev.halal.entity.FavoriteEntity;
import cu.dev.halal.entity.StoreEntity;
import cu.dev.halal.entity.UserEntity;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


public interface FavoritesDAO {
    public JSONObject addFavorite(FavoriteEntity favoriteEntity);
    public JSONObject deleteFavorite(FavoriteEntity favoriteEntity);
    public List<FavoriteEntity> getFavorites(String email);

}
