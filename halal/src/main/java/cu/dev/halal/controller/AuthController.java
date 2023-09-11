package cu.dev.halal.controller;


import cu.dev.halal.dto.LoginDTO;
import cu.dev.halal.service.SignService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final SignService signService;

    public AuthController(
            @Autowired SignService signService
    ){
        this.signService = signService;
    }



    @PostMapping("/signup")
    public ResponseEntity<JSONObject> signup(
            @RequestBody LoginDTO loginDTO
            ){
        return this.signService.signUp(loginDTO, "USER");
    }

    @PostMapping("/signin")
    public ResponseEntity<JSONObject> signin(
            @RequestBody LoginDTO loginDTO
    ){
        return this.signService.signIn(loginDTO);
    }


}
