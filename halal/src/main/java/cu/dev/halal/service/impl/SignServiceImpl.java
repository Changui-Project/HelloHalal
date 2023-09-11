package cu.dev.halal.service.impl;

import cu.dev.halal.config.security.JwtTokenProvider;
import cu.dev.halal.dto.LoginDTO;
import cu.dev.halal.entity.UserEntity;
import cu.dev.halal.repository.UserRepository;
import cu.dev.halal.service.SignService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;



@Service
public class SignServiceImpl implements SignService {

    private static final Logger logger = LoggerFactory.getLogger(SignServiceImpl.class);

    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;
    @Autowired
    public SignServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailDuplicateCheck(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public ResponseEntity<JSONObject> signUp(LoginDTO loginDTO, String role) {
        logger.info("[getSignUpResult] 회원가입 정보 전달");
        UserEntity userEntity;
        // 규칙 설정
        if(role.equalsIgnoreCase("admin")){
            userEntity = UserEntity.builder()
                    .email(loginDTO.getEmail())
                    .password(passwordEncoder.encode(loginDTO.getPassword()))
                    .favorites(new ArrayList<>())
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        }
        else {
            userEntity = UserEntity.builder()
                    .email(loginDTO.getEmail())
                    .password(passwordEncoder.encode(loginDTO.getPassword()))
                    .favorites(new ArrayList<>())
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }

        // 생성한 유저 엔티티를 DB에 저장
        UserEntity savedUser = userRepository.save(userEntity);
        JSONObject json = new JSONObject();

        logger.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과 값 주입");
        if(!savedUser.getEmail().isEmpty()){
            logger.info("[getSignUpResult] 정상 처리 완료");
            json.put("result", "success");
            return ResponseEntity.status(201).body(json);
        } else{
            logger.info("[getSignUpResult]  실패 처리 완료");
            json.put("result", "email Null");
            return ResponseEntity.badRequest().body(json);

        }

    }

    @Override
    public ResponseEntity<JSONObject> signIn(LoginDTO loginDTO) {
        logger.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        UserEntity userEntity = userRepository.getByEmail(loginDTO.getEmail());
        logger.info("[getSignInResult] e-mail : {}", loginDTO.getEmail());

        logger.info("[getSignInResult] 패스워드 비교 수행");
        try{
            try{// 패스워드 불일치
                if (!passwordEncoder.matches(loginDTO.getPassword(), userEntity.getPassword())) {
                    logger.info("패스워드 불일치");

                    JSONObject json = new JSONObject();
                    json.put("access_token", "Password mismatch");
                    return ResponseEntity.badRequest().body(json);
                }
            }catch (IllegalArgumentException e){
                JSONObject json = new JSONObject();
                json.put("access_token", "Email mismatch");
                return ResponseEntity.badRequest().body(json);
            }
        }catch (NullPointerException e){
            JSONObject json = new JSONObject();
            json.put("access_token", "존재하지 않는 이메일입니다.");
            return ResponseEntity.badRequest().body(json);
        }


        logger.info("[getSignInResult] 패스워드 일치");
        JSONObject json = new JSONObject();
        json.put("access_token", jwtTokenProvider.createToken(String.valueOf(userEntity.getEmail()), userEntity.getRoles()));

        return ResponseEntity.status(200).body(json);

    }

}
