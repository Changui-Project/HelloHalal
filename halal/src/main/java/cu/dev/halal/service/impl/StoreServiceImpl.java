package cu.dev.halal.service.impl;

import cu.dev.halal.dao.StoreDAO;
import cu.dev.halal.dto.RangeDTO;
import cu.dev.halal.dto.StoreDTO;
import cu.dev.halal.entity.StoreEntity;
import cu.dev.halal.service.AddressService;
import cu.dev.halal.service.StoreService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@Service
public class StoreServiceImpl implements StoreService {
    private final static Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);
    private final StoreDAO storeDAO;
    private final AddressService addressService;

    public StoreServiceImpl(
            @Autowired AddressService addressService,
            @Autowired StoreDAO storeDAO
    ){
        this.addressService = addressService;
        this.storeDAO = storeDAO;
    }


    // StoreDTO -> StoreEntity
    @Override
    public JSONObject createStore(StoreDTO storeDTO) {
        // Store address를 좌표로 변환
        LinkedHashMap coordinate = this.addressService.toCoordinate(storeDTO.getAddress());
        // 변환에 실패했다면 실패 사유를 반환
        if(coordinate.containsKey("result")){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", coordinate.get("result"));
            return jsonObject;
        }
        StoreEntity storeEntity = StoreEntity.builder()
                .name(storeDTO.getName())
                .address(storeDTO.getAddress())
                .storePhoneNumber(storeDTO.getStorePhoneNumber())
                .menu(storeDTO.getMenu())
                .operatingTime(storeDTO.getOperatingTime())
                .coordinateX(Double.parseDouble( coordinate.get("x").toString()))
                .coordinateY(Double.parseDouble(coordinate.get("y").toString()))
                .build();


        return this.storeDAO.createStore(storeEntity);
    }


    // storeEntity -> StoreDTO
    @Override
    public StoreDTO readStore(Long id) {
        StoreEntity storeEntity = this.storeDAO.readStore(id);
        StoreDTO storeDTO = StoreDTO.builder()
                .name(storeEntity.getName())
                .address(storeEntity.getAddress())
                .coordinateX(storeEntity.getCoordinateX())
                .coordinateY(storeEntity.getCoordinateY())
                .operatingTime(storeEntity.getOperatingTime())
                .storePhoneNumber(storeEntity.getStorePhoneNumber())
                .menu(storeEntity.getMenu())
                .id(storeEntity.getId())
                .build();

        return storeDTO;
    }

    @Override
    public JSONObject readAllRoundStore(RangeDTO rangeDTO) {
        List<StoreEntity> storeEntities = this.storeDAO.readAllRoundStore(rangeDTO);
        JSONObject jsonObject = new JSONObject();
        List<StoreDTO> stores = new ArrayList<>();
        try {
            for (StoreEntity storeEntity : storeEntities) {
                StoreDTO storeDTO = StoreDTO.builder()
                        .name(storeEntity.getName())
                        .operatingTime(storeEntity.getOperatingTime())
                        .storePhoneNumber(storeEntity.getStorePhoneNumber())
                        .address(storeEntity.getAddress())
                        .menu(storeEntity.getMenu())
                        .id(storeEntity.getId())
                        .coordinateX(storeEntity.getCoordinateX())
                        .coordinateY(storeEntity.getCoordinateY())
                        .build();
                stores.add(storeDTO);

            }
            jsonObject.put("stores", stores);
            return jsonObject;
        }catch (NullPointerException e){
            jsonObject.put("result", "store not exists");
            return jsonObject;
        }
    }

    @Override
    public JSONObject deleteStore(Long id) {
        return this.storeDAO.deleteStore(id);
    }
}
