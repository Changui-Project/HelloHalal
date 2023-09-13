package cu.dev.halal.service;

import cu.dev.halal.dto.StoreDTO;
import cu.dev.halal.entity.StoreEntity;
import org.json.simple.JSONObject;

public interface StoreService {

    public JSONObject createStore(StoreDTO storeDTO);
    public StoreDTO readStore(Long id);
    public JSONObject deleteStore(Long id);

}
