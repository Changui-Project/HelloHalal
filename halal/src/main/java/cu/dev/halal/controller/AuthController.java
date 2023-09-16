package cu.dev.halal.controller;


import cu.dev.halal.dto.LoginDTO;
import cu.dev.halal.service.SignService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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




    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<JSONObject> signup(
            @RequestBody LoginDTO loginDTO
            ){
        logger.info("[AuthController] 회원가입 요청 {}", loginDTO.toString());
        return this.signService.signUp(loginDTO, "USER");
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<JSONObject> signin(
            @RequestBody LoginDTO loginDTO
    ){
        logger.info("[AuthController] 로그인 요청 {}", loginDTO.toString());
        return this.signService.signIn(loginDTO);
    }

    // 토큰의 유효성 검사 (현재 토큰이 유효한가)
    @PostMapping("/valid")
    public ResponseEntity<?> tokenValidCheck(
            @RequestParam("token") String token
    ){
        boolean result = this.signService.tokenValidCheck(token);
        if(result){
            return ResponseEntity.status(200).body(result);
        }else{
            return ResponseEntity.status(400).body(result);
        }
    }

}
