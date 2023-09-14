package cu.dev.halal.dao.impl;

import cu.dev.halal.dao.FavoritesDAO;
import cu.dev.halal.entity.FavoriteEntity;
import cu.dev.halal.entity.StoreEntity;
import cu.dev.halal.entity.UserEntity;
import cu.dev.halal.repository.FavoriteRepository;
import cu.dev.halal.repository.StoreRepository;
import cu.dev.halal.repository.UserRepository;
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

    // @Transactional
    // 트랜잭션 단위로 연산하기 때문에 ACID 속성이 보장된다.
    @Override
    @Transactional
    public JSONObject addFavorite(FavoriteEntity favoriteEntity) {
        JSONObject jsonObject = new JSONObject();
        // FavoriteEntity에 User와 Store의 다대일 연관관계에 의해 생긴 Foreign Key를 만들어준다.
        UserEntity userEntity = this.userRepository.getByEmail(favoriteEntity.getUser().getEmail());
        StoreEntity storeEntity = this.storeRepository.getById(favoriteEntity.getStore().getId());

        // Store Id와 email이 Store와 User에 존재하는지 확인
        if(this.userRepository.existsByEmail(favoriteEntity.getUser().getEmail()) &&
            this.storeRepository.existsById(favoriteEntity.getStore().getId())){
            // 해당 즐겨찾기 값이 이미 존재하지 않는 경우
            if (!this.favoriteRepository.existsByUserAndStore(userEntity, storeEntity)) {
                favoriteEntity.setUser(userEntity);
                favoriteEntity.setStore(storeEntity);
                this.favoriteRepository.save(favoriteEntity);

                jsonObject.put("result", "success");
                return jsonObject;
                // 이미 값이 존재할 경우
            } else {
                jsonObject.put("result", "already exists");
                return jsonObject;
            }
            // email이 User테이블에 존재하지 않을 경우
        }else if(!this.userRepository.existsByEmail(favoriteEntity.getUser().getEmail())){
            jsonObject.put("result", "User is not exists");
            return jsonObject;
            // Store id가 Store테이블에 존재하지 않을 경우
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

            // 지우고자 하는 값이 존재하는지 체크
            Boolean result = this.favoriteRepository.existsByUserAndStore(
                    userEntity,
                    storeEntity);
            // 만약 존재한다면
            if (result) {
                this.favoriteRepository.deleteByUserAndStore(userEntity, storeEntity);
                jsonObject.put("result", "success");
                return jsonObject;
            }
            jsonObject.put("result", "not exists");
            return jsonObject;
            // user 또는 store의 값이 존재하지 않을 경우 발생하는 예외 처리
        }catch (TransientObjectException e) {
            jsonObject.put("result", "not exists");
            return jsonObject;
        }
    }

    @Override
    public List<FavoriteEntity> getFavorites(String email) {
        // email을 기반으로 모든 즐겨찾기 값을 가져온다.
        try{
            return this.userRepository.getByEmail(email).getFavorites();
            // null 값이 저장되어 있을 경우 빈 배열 반환
        }catch (NullPointerException e){
            List<FavoriteEntity> error = new ArrayList<>();
            error.add(null);
            return error;
        }

    }
}
