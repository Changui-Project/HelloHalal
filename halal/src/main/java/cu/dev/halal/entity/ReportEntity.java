package cu.dev.halal.entity;


import lombok.*;
import javax.persistence.*;


// DB 테이블과 매핑되는 객체
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReportEntity {

    // Report Table Primary Key, Auto Generated
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 신고 제목
    @Column(nullable = false)
    private String title;

    // User 테이블과 다대일 관계
    // Join user_id = this.user_id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;

    // 신고 본문
    @Column(nullable = false)
    private String content;



}
