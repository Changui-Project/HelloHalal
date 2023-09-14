package cu.dev.halal.service;

import cu.dev.halal.dto.RangeDTO;
import cu.dev.halal.dto.StoreDTO;
import cu.dev.halal.entity.StoreEntity;
import org.json.simple.JSONObject;

import java.util.List;

public interface StoreService {

    public JSONObject createStore(StoreDTO storeDTO);
    public StoreDTO readStore(Long id);
    public JSONObject readAllRoundStore(RangeDTO rangeDTO);
    public JSONObject deleteStore(Long id);


}
