package cu.dev.halal.dto;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginDTO {
    private String email;
    private String password;

}
