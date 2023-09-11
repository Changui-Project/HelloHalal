package cu.dev.halal.dao.impl;

import cu.dev.halal.dao.FavoritesDAO;
import cu.dev.halal.entity.UserEntity;
import cu.dev.halal.repository.UserRepository;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FavoritesDAOImpl implements FavoritesDAO {
    private final static Logger logger = LoggerFactory.getLogger(FavoritesDAOImpl.class);
    private final UserRepository userRepository;

    public FavoritesDAOImpl(
            @Autowired UserRepository userRepository
    ){
        this.userRepository = userRepository;
    }

    @Override
    public JSONObject addFavorite(UserEntity targetEntity) {
        JSONObject jsonObject = new JSONObject();
        UserEntity userEntity = this.userRepository.getByEmail(targetEntity.getEmail());
        if(!userEntity.getFavorites().contains(targetEntity.getFavorites().get(0))){
            List<Long> targetList = userEntity.getFavorites();
            targetList.addAll(targetEntity.getFavorites());
            userEntity.setFavorites(targetList);
            this.userRepository.save(userEntity);

            jsonObject.put("result", "success");
            return jsonObject;
        }else{
            jsonObject.put("result", "already exists");
            return jsonObject;
        }
    }

    @Override
    public JSONObject deleteFavorite(UserEntity targetEntity) {
        UserEntity userEntity = this.userRepository.getByEmail(targetEntity.getEmail());
        JSONObject jsonObject = new JSONObject();
        List<Long> targetList = userEntity.getFavorites();
        boolean result = targetList.remove(targetEntity.getFavorites().get(0));
        if(result){
            userEntity.setFavorites(targetList);
            this.userRepository.save(userEntity);
            jsonObject.put("result", "success");
            return jsonObject;
        }
        jsonObject.put("result", "not exists");
        return jsonObject;


    }

    @Override
    public List<Long> getFavorites(String email) {
        try{
            return this.userRepository.getByEmail(email).getFavorites();
        }catch (NullPointerException e){
            return new ArrayList<>();
        }

    }
}
