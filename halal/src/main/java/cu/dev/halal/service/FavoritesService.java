package cu.dev.halal.service;

import cu.dev.halal.dto.FavoriteDTO;
import org.json.simple.JSONObject;

public interface FavoritesService {

    public JSONObject addFavorite(FavoriteDTO dto);
    public JSONObject deleteFavorite(FavoriteDTO dto);
    public JSONObject getFavorites(String email);

}
