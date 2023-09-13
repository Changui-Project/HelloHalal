package cu.dev.halal.dao;

import cu.dev.halal.entity.FavoriteEntity;
import org.json.simple.JSONObject;
import java.util.List;


public interface FavoritesDAO {
    // 즐겨찾기 추가
    public JSONObject addFavorite(FavoriteEntity favoriteEntity);
    // 즐겨찾기 삭제
    public JSONObject deleteFavorite(FavoriteEntity favoriteEntity);
    // 이메일 기반으로 모든 즐겨찾기를 가져온다.
    public List<FavoriteEntity> getFavorites(String email);

}
