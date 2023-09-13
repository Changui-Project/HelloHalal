package cu.dev.halal.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class StoreDTO {
    private Long id;
    private String name;

    private String address;

    private String coordinateX;
    private String coordinateY;

    private String operatingTime;

    private String storePhoneNumber;

    private String menu;
}
