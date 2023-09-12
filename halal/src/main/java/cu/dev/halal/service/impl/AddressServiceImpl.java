package cu.dev.halal.service.impl;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.*;
import cu.dev.halal.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class AddressServiceImpl implements AddressService {

    private final static Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Override
    public String toCoordinate(String address) {
        GeocoderRequest geocoderRequest = new GeocoderRequest(address, "ko");
        logger.info(address);
        Geocoder geocoder = new Geocoder();
        try {
            GeocodeResponse geocodeResponse = geocoder.geocode(geocoderRequest);
            logger.info(geocodeResponse.getStatus().toString());
            if(geocodeResponse.getStatus() == GeocoderStatus.OK & !geocodeResponse.getResults().isEmpty()){
                GeocoderResult geocoderResult = geocodeResponse.getResults().iterator().next();
                String coordinate = geocoderResult.getGeometry().toString();
                logger.info("coordinate"+coordinate);
                return coordinate;
            }

        } catch (IOException e) {
            logger.info("runtime exception");
            throw new RuntimeException(e);
        }
        logger.info("asd");
        return null;
    }
}
