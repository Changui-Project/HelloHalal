package cu.dev.halal.service.impl;

import cu.dev.halal.dao.FavoritesDAO;
import cu.dev.halal.dto.FavoriteDTO;
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

    @Override
    public JSONObject addFavorite(FavoriteDTO dto) {
        List<Long> target = new ArrayList<>();
        logger.info(dto.getStoreId().toString());
        target.add(dto.getStoreId());
        logger.info(target.toString());
        JSONObject jsonObject = this.favoritesDAO.addFavorite(
                UserEntity.builder()
                .email(dto.getEmail())
                .favorites(target)
                .build());
        return jsonObject;
    }

    @Override
    public JSONObject deleteFavorite(FavoriteDTO dto) {
        List<Long> target = new ArrayList<>();
        target.add(dto.getStoreId());
        JSONObject jsonObject = this.favoritesDAO.deleteFavorite(
                UserEntity.builder()
                        .email(dto.getEmail())
                        .favorites(target)
                        .build());
        return jsonObject;
    }

    @Override
    public JSONObject getFavorites(String email) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("favorites", (ArrayList)this.favoritesDAO.getFavorites(email));

        return jsonObject;
    }
}
