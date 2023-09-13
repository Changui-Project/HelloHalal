package cu.dev.halal.dao;

import cu.dev.halal.entity.StoreEntity;
import org.json.simple.JSONObject;

public interface StoreDAO {

    public JSONObject createStore(StoreEntity storeEntity);
    public StoreEntity readStore(Long id);
    public JSONObject deleteStore(Long id);

}
