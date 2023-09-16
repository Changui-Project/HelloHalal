package cu.dev.halal.service;

import cu.dev.halal.dto.RangeDTO;
import cu.dev.halal.dto.StoreDTO;
import cu.dev.halal.entity.StoreEntity;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StoreService {

    public JSONObject createStore(StoreDTO storeDTO, List<MultipartFile> multipartFiles) throws IOException;
    public StoreDTO readStore(Long id);
    public JSONObject readAllRoundStore(RangeDTO rangeDTO);
    public JSONObject readAllCoordinateAroundStore(Double x, Double y);
    public JSONObject deleteStore(Long id);



}
