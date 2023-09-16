package cu.dev.halal.service;

import org.apache.commons.httpclient.URIException;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface DataService {
    public Map getData(String x, String y) throws IOException, URISyntaxException;

}
