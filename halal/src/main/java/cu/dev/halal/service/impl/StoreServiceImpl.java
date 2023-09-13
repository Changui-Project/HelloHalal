package cu.dev.halal.service.impl;

import cu.dev.halal.dao.StoreDAO;
import cu.dev.halal.dto.StoreDTO;
import cu.dev.halal.entity.StoreEntity;
import cu.dev.halal.service.AddressService;
import cu.dev.halal.service.StoreService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;


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


    @Override
    public JSONObject createStore(StoreDTO storeDTO) {
        LinkedHashMap coordinate = this.addressService.toCoordinate(storeDTO.getAddress());
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
                .coordinateX(coordinate.get("x").toString())
                .coordinateY(coordinate.get("y").toString())
                .build();


        return this.storeDAO.createStore(storeEntity);
    }


    // store Entity를 DTO로 변환할 것
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
    public JSONObject deleteStore(Long id) {
        return this.storeDAO.deleteStore(id);
    }
}
