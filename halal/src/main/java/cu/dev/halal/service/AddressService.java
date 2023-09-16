package cu.dev.halal.service;

import org.json.simple.JSONObject;

import java.util.LinkedHashMap;

public interface AddressService {
    public LinkedHashMap toCoordinate(String address);
    public String toAddress(Double x, Double y);

    public Double dis(Double x1, Double y1, Double x2, Double y2);


}
