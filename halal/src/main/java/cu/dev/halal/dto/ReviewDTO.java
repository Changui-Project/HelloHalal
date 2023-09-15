package cu.dev.halal.dto;


import lombok.*;

import java.util.List;

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
    private List<String> images;

}
