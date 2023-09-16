package cu.dev.halal.service.impl;


import cu.dev.halal.service.DataService;
import cu.dev.halal.service.StoreService;
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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DataServiceImpl implements DataService {
    private final static Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);


    private final StoreService storeService;

    public DataServiceImpl(
            @Autowired StoreService storeService
    ){
        this.storeService = storeService;
    }


    @Override
    public Map getData() {
        String url = "https://apis.data.go.kr/B551011/ForFriTourService/locationBasedList?serviceKey=74cTW%2BCvk1IoEbR5u%2B8BcDxwqay0DlWbnebYQV2JSBZXHblA76x0PLsPgROdVfDkf0rXd7dccWI72J70MOEnhA%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=APP&arrange=C&contentTypeId=1145&mapX=127.7276298330&mapY=37.8792343699&radius=10000&_type=json";
        LinkedHashMap returnValue = new LinkedHashMap<>();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Cookie", Collections.singletonList("NCPVPCLB=53dc2963a8054bd57870a8b2355dc148919c5a02851f15d4ffafa945a766b4a1"));
        httpHeaders.put("Accept", Collections.singletonList("*/*"));
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(httpHeaders);


        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        logger.info(response.toString());
        Map jsonObject = (JSONObject) response.getBody();
        logger.info(jsonObject.toString());

        return jsonObject;
    }
}
