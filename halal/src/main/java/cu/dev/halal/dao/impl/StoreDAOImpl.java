package cu.dev.halal.dao.impl;

import cu.dev.halal.dao.StoreDAO;
import cu.dev.halal.dto.RangeDTO;
import cu.dev.halal.entity.StoreEntity;
import cu.dev.halal.repository.StoreRepository;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class StoreDAOImpl implements StoreDAO {
    private final static Logger logger = LoggerFactory.getLogger(StoreDAOImpl.class);
    private final StoreRepository storeRepository;

    public StoreDAOImpl(
            @Autowired StoreRepository storeRepository
    ){
        this.storeRepository = storeRepository;
    }


    // 같은 Store 중복처리 필요.
    @Override
    public JSONObject createStore(StoreEntity storeEntity) {
        this.storeRepository.save(storeEntity);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        return jsonObject;
    }

    @Override
    public StoreEntity readStore(Long id) {
        StoreEntity storeEntity = new StoreEntity();
        // 해당 Store가 존재하지 않는다면
        if(!this.storeRepository.existsById(id)){
            storeEntity.setName("not exists");
            return storeEntity;
        }

        storeEntity =  this.storeRepository.getById(id);
        return storeEntity;
    }

    @Override
    public List<StoreEntity> readAllRoundStore(RangeDTO rangeDTO) {
        List<StoreEntity> stores= this.storeRepository.getByCoordinateXBetweenAndCoordinateYBetween(
                rangeDTO.getMinX(), rangeDTO.getMaxX(),
                rangeDTO.getMinY(), rangeDTO.getMaxY()
        );

        return stores;
    }

    @Override
    public JSONObject deleteStore(Long id) {
        JSONObject jsonObject = new JSONObject();
        try{
            this.storeRepository.deleteById(id);
            // 해당 Store가 존재하지 않는다면
        }catch (EmptyResultDataAccessException e){
            jsonObject.put("result", "not exists");
            return jsonObject;
        }
        jsonObject.put("result", "success");
        return jsonObject;
    }
}
