package cu.dev.halal.dto;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReportDTO {
    private Long id;
    private String title;
    private String email;
    private String content;
}
