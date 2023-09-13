package cu.dev.halal.dao.impl;

import cu.dev.halal.dao.StoreDAO;
import cu.dev.halal.entity.StoreEntity;
import cu.dev.halal.repository.StoreRepository;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;


@Repository
public class StoreDAOImpl implements StoreDAO {
    private final static Logger logger = LoggerFactory.getLogger(StoreDAOImpl.class);
    private final StoreRepository storeRepository;

    public StoreDAOImpl(
            @Autowired StoreRepository storeRepository
    ){
        this.storeRepository = storeRepository;
    }


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
        if(!this.storeRepository.existsById(id)){
            storeEntity.setName("not exists");
            return storeEntity;
        }

        storeEntity =  this.storeRepository.getById(id);
        return storeEntity;
    }

    @Override
    public JSONObject deleteStore(Long id) {
        JSONObject jsonObject = new JSONObject();
        try{
            this.storeRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            jsonObject.put("result", "not exists");
            return jsonObject;
        }
        jsonObject.put("result", "success");
        return jsonObject;
    }
}
