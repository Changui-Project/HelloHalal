package cu.dev.halal.service.impl;

import cu.dev.halal.service.AddressService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class AddressServiceImpl implements AddressService {

    private final static Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);


    // naver api 사용  주소 -> 좌표 변환
    @Override
    public LinkedHashMap toCoordinate(String address) {
        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
        url = url + "?query="+address;
        LinkedHashMap returnValue = new LinkedHashMap<>();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-NCP-APIGW-API-KEY-ID", "rnl7q9733x");
        httpHeaders.add("X-NCP-APIGW-API-KEY", "iiK2zg20DDqp6yoVsdnryVn7DG4SkYz7ehaxL1aO");

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, JSONObject.class);
        if(response.getStatusCode() == HttpStatus.OK){
            JSONObject jsonObject = (JSONObject) response.getBody();
            ArrayList addresses = (ArrayList) jsonObject.get("addresses");
            try{
                LinkedHashMap coordinate = (LinkedHashMap) addresses.get(0);
                return coordinate;
            }catch (IndexOutOfBoundsException e){
                returnValue.put("result", "invalid address");
                return returnValue;
            }
        }
        else {

            returnValue.put("result", "naver api error");
            return returnValue;
        }

    }
}
