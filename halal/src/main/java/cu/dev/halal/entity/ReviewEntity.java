package cu.dev.halal.entity;


import lombok.*;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review")
public class ReviewEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Float score;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name="store_id", nullable = false)
    private StoreEntity store;

}
