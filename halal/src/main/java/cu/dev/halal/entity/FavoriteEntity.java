package cu.dev.halal.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class FavoriteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;


}
