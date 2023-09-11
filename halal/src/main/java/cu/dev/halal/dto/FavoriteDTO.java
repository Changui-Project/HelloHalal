package cu.dev.halal.dto;


import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FavoriteDTO {
    private String email;
    private Long storeId;
}
