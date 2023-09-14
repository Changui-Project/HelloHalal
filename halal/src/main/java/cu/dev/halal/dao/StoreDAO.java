package cu.dev.halal.dao;

import cu.dev.halal.dto.RangeDTO;
import cu.dev.halal.entity.StoreEntity;
import org.json.simple.JSONObject;

import java.util.List;

public interface StoreDAO {

    public JSONObject createStore(StoreEntity storeEntity);
    public StoreEntity readStore(Long id);
    public List<StoreEntity> readAllRoundStore(RangeDTO rangeDTO);
    public JSONObject deleteStore(Long id);

}
