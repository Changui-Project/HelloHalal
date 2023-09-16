package cu.dev.halal.dto;


import lombok.*;


// 범위내 Store를 가져오기 위해 좌표 값들을 가져오는 DTO
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RangeDTO {

    private Double minX;
    private Double minY;
    private Double maxX;
    private Double maxY;


}
