package cu.dev.halal.dto;


import lombok.*;

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
