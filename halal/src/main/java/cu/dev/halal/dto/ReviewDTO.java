package cu.dev.halal.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {

    private Long id;
    private String content;
    private Float score;
    private String email;
    private Long storeId;

}
