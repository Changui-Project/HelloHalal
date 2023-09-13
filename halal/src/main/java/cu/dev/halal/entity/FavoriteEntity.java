package cu.dev.halal.entity;


import lombok.*;

import javax.persistence.*;

// DB 테이블과 매핑되는 객체
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class FavoriteEntity {

    // Favorite Table Primary Key, Auto Generate
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    // Store 테이블과 다대일 관계
    // Join store_id = this.store_id
    @ManyToOne()
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;

    // User 테이블과 다대일 관계
    // Join user_id = this.user_id
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;


}
