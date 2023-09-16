package cu.dev.halal.service;

import java.util.LinkedHashMap;

public interface AddressService {
    public LinkedHashMap toCoordinate(String address);

    public Double dis(Double x1, Double y1, Double x2, Double y2);


}
