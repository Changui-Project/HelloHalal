package cu.dev.halal.service.impl;


import cu.dev.halal.dto.StoreDTO;
import cu.dev.halal.service.AddressService;
import cu.dev.halal.service.DataService;
import cu.dev.halal.service.ImageService;
import cu.dev.halal.service.StoreService;
import org.apache.commons.httpclient.URIException;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataServiceImpl implements DataService {
    private final static Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);

    private final AddressService addressService;
    private final StoreService storeService;
    private final ImageService imageService;

    public DataServiceImpl(
            @Autowired ImageService imageService,
            @Autowired StoreService storeService,
            @Autowired AddressService addressService
    ){
        this.addressService = addressService;
        this.storeService = storeService;
        this.imageService = imageService;
    }


    @Override
    public Map getData(String x, String y) throws IOException, URISyntaxException {
        String url = "https://apis.data.go.kr/B551011/ForFriTourService/locationBasedList";
        LinkedHashMap returnValue = new LinkedHashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("https://apis.data.go.kr/B551011/ForFriTourService/locationBasedList?serviceKey=INPDOyrXXImnGdmHVQ0dbbSxVooQk7kDVX7dYfzS%2BNLdsx%2BR" +
                "wnE5Z7zHLdCIIB2B7i4CrOVGm2j3z4F2%2FIT1bg%3D%3D&numOfRows=1000" +
                "&pageNo=1&MobileOS=ETC&MobileApp=APP&arrange=C&mapX="+x+"&mapY="+y+"&radius=20000&_type=json");
//        URI serviceKey = new URI("?serviceKey=INPDOyrXXImnGdmHVQ0dbbSxVooQk7kDVX7dYfzS%2BNLdsx%2BRwnE5Z7zHLdCIIB2B7i4CrOVGm2j3z4F2%2FIT1bg%3D%3D");
//        String numOfRow = "&numOfRows=1000";
//        String pageNo = "&pageNo=1";
//        String MobileOS = "&MobileOS=ETC";
//        String MobileApp = "&MobileApp=APP";
//        String arrange = "&arrange=C";
//        String mapX = "&mapX=128.4988125";
//        String mapY = "&mapY=35.8589654";
//        String radius = "&radius=20000";
//        String _type = "&_type=json";
//        url = url + serviceKey + numOfRow + pageNo + MobileOS + MobileApp + arrange + mapX + mapY + radius + _type;
        logger.info(url);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<JSONObject> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, JSONObject.class);
        JSONObject jsonObject = (JSONObject) response.getBody();
        returnValue = (LinkedHashMap) jsonObject.get("response");
        returnValue = (LinkedHashMap) returnValue.get("body");
        returnValue = (LinkedHashMap) returnValue.get("items");
        List<LinkedHashMap> result = (ArrayList) returnValue.get("item");
        logger.info(result.toString());
        List<JSONObject> results = new ArrayList<>();

        for(LinkedHashMap linkedHashMap : result){
            List<byte[]> images = new ArrayList<>();
            URI imageUri1 = new URI(linkedHashMap.get("firstimage").toString());
            URI imageUri2 = new URI(linkedHashMap.get("firstimage2").toString());
            try {
                images.add(restTemplate.exchange(imageUri1, HttpMethod.GET, httpEntity, byte[].class).getBody());
                images.add(restTemplate.exchange(imageUri2, HttpMethod.GET, httpEntity, byte[].class).getBody());
            }catch (IllegalArgumentException e){
                images.add(restTemplate.exchange("https://loremflickr.com/320/240/dog", HttpMethod.GET, httpEntity, byte[].class).getBody());
                images.add(restTemplate.exchange("https://loremflickr.com/320/240/dog", HttpMethod.GET, httpEntity, byte[].class).getBody());
            }

            StoreDTO storeDTO = StoreDTO.builder()
                    .name(linkedHashMap.get("title").toString())
                    .address(this.addressService.toAddress(
                            Double.valueOf(linkedHashMap.get("mapx").toString()),
                            Double.valueOf(linkedHashMap.get("mapy").toString())
                    ).toString())
                    .coordinateX(Double.valueOf(linkedHashMap.get("mapx").toString()))
                    .coordinateY(Double.valueOf(linkedHashMap.get("mapy").toString()))
                    .operatingTime("9to18")
                    .storePhoneNumber("010-1234-5678")
                    .menu("카레").build();
            JSONObject value = new JSONObject();
            value = this.storeService.createStore(storeDTO, images);
            if(!value.containsValue("exists")){
                results.add(value);
            }
        }
        JSONObject json = new JSONObject();
        json.put("results", results);
        return json;
    }
}
