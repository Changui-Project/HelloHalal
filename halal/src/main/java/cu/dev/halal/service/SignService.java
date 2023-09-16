package cu.dev.halal.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import cu.dev.halal.dto.LoginDTO;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;


public interface SignService {

    ResponseEntity<JSONObject>  signUp(LoginDTO loginDTO, String role);
    ResponseEntity<JSONObject> signIn(LoginDTO loginDTO);
    public boolean emailDuplicateCheck(String email);
    public boolean tokenValidCheck(String token);
}
