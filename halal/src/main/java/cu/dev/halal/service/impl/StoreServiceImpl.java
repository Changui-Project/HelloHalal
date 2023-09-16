package cu.dev.halal.service.impl;

import com.google.gson.JsonObject;
import cu.dev.halal.dao.StoreDAO;
import cu.dev.halal.dto.RangeDTO;
import cu.dev.halal.dto.StoreDTO;
import cu.dev.halal.entity.StoreEntity;
import cu.dev.halal.service.AddressService;
import cu.dev.halal.service.ImageService;
import cu.dev.halal.service.StoreService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@Service
public class StoreServiceImpl implements StoreService {
    private final static Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);
    private final StoreDAO storeDAO;
    private final AddressService addressService;
    private final ImageService imageService;

    public StoreServiceImpl(
            @Autowired ImageService imageService,
            @Autowired AddressService addressService,
            @Autowired StoreDAO storeDAO
    ){
        this.imageService = imageService;
        this.addressService = addressService;
        this.storeDAO = storeDAO;
    }


    // StoreDTO -> StoreEntity
    @Override
    public JSONObject createStore(StoreDTO storeDTO, List<MultipartFile> multipartFiles) throws IOException {
        List<String> images = new ArrayList<>();
        for(MultipartFile multipartFile : multipartFiles){
            images.add(this.imageService.upload(multipartFile));
        }
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
                .images(images)
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
                .images(storeEntity.getImages())
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
                        .images(storeEntity.getImages())
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
    public JSONObject readAllCoordinateAroundStore(Double x, Double y) {
        List<StoreDTO> stores = new ArrayList<>();
        List<StoreEntity> storeEntities = this.storeDAO.readAllRoundStore(
                RangeDTO.builder()
                        // 약 1.1km
                        .maxX(x+1)
                        .minX(x-1)
                        // 약 1.1km
                        .minY(y-1)
                        .maxY(y+1)
                        .build()
        );
        for(StoreEntity storeEntity : storeEntities){
            if(1D>this.addressService.dis(x, y, storeEntity.getCoordinateX(), storeEntity.getCoordinateY())){
                StoreDTO storeDTO = StoreDTO.builder()
                        .name(storeEntity.getName())
                        .operatingTime(storeEntity.getOperatingTime())
                        .storePhoneNumber(storeEntity.getStorePhoneNumber())
                        .address(storeEntity.getAddress())
                        .menu(storeEntity.getMenu())
                        .id(storeEntity.getId())
                        .coordinateX(storeEntity.getCoordinateX())
                        .coordinateY(storeEntity.getCoordinateY())
                        .images(storeEntity.getImages())
                        .build();
                stores.add(storeDTO);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stores", stores);
        return jsonObject;
    }

    @Override
    public JSONObject deleteStore(Long id) {
        return this.storeDAO.deleteStore(id);
    }
}
