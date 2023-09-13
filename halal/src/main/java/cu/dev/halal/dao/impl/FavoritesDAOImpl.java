package cu.dev.halal.dao.impl;

import cu.dev.halal.dao.FavoritesDAO;
import cu.dev.halal.entity.FavoriteEntity;
import cu.dev.halal.entity.StoreEntity;
import cu.dev.halal.entity.UserEntity;
import cu.dev.halal.repository.FavoriteRepository;
import cu.dev.halal.repository.StoreRepository;
import cu.dev.halal.repository.UserRepository;
import org.hibernate.PropertyAccessException;
import org.hibernate.PropertyValueException;
import org.hibernate.TransientObjectException;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FavoritesDAOImpl implements FavoritesDAO {
    private final static Logger logger = LoggerFactory.getLogger(FavoritesDAOImpl.class);
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public FavoritesDAOImpl(
            @Autowired FavoriteRepository favoriteRepository,
            @Autowired UserRepository userRepository,
            @Autowired StoreRepository storeRepository

            ){
        this.favoriteRepository = favoriteRepository;
        this.userRepository  = userRepository;
        this.storeRepository = storeRepository;
    }

    @Override
    @Transactional
    public JSONObject addFavorite(FavoriteEntity favoriteEntity) {
        JSONObject jsonObject = new JSONObject();
        UserEntity userEntity = this.userRepository.getByEmail(favoriteEntity.getUser().getEmail());
        StoreEntity storeEntity = this.storeRepository.getById(favoriteEntity.getStore().getId());
        if(this.userRepository.existsByEmail(favoriteEntity.getUser().getEmail()) &&
            this.storeRepository.existsById(favoriteEntity.getStore().getId())){
            if (!this.favoriteRepository.existsByUserAndStore(userEntity, storeEntity)) {
                favoriteEntity.setUser(userEntity);
                favoriteEntity.setStore(storeEntity);
                this.favoriteRepository.save(favoriteEntity);

                jsonObject.put("result", "success");
                return jsonObject;
            } else {
                jsonObject.put("result", "already exists");
                return jsonObject;
            }
        }else if(!this.userRepository.existsByEmail(favoriteEntity.getUser().getEmail())){
            jsonObject.put("result", "User is not exists");
            return jsonObject;
        }else if(!this.storeRepository.existsById(favoriteEntity.getStore().getId())){
            jsonObject.put("result", "Store is not exists");
            return jsonObject;
        }else {
            jsonObject.put("result", "error");
            return jsonObject;
        }

    }

    @Override
    @Transactional
    public JSONObject deleteFavorite(FavoriteEntity favoriteEntity) {
        JSONObject jsonObject = new JSONObject();
        try {
            UserEntity userEntity = this.userRepository.getByEmail(favoriteEntity.getUser().getEmail());
            StoreEntity storeEntity = this.storeRepository.getById(favoriteEntity.getStore().getId());

            Boolean result = this.favoriteRepository.existsByUserAndStore(
                    userEntity,
                    storeEntity);

            if (result) {
                this.favoriteRepository.deleteByUserAndStore(userEntity, storeEntity);
                jsonObject.put("result", "success");
                return jsonObject;
            }
            jsonObject.put("result", "not exists");
            return jsonObject;
        }catch (TransientObjectException e) {
            jsonObject.put("result", "not exists");
            return jsonObject;
        }
    }

    @Override
    public List<FavoriteEntity> getFavorites(String email) {
        try{
            return this.userRepository.getByEmail(email).getFavorites();
        }catch (NullPointerException e){
            return new ArrayList<>();
        }

    }
}
