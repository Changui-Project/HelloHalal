package cu.dev.halal.dao.impl;

import cu.dev.halal.dao.ReviewDAO;
import cu.dev.halal.entity.ReviewEntity;
import cu.dev.halal.repository.ReviewRepository;
import cu.dev.halal.repository.StoreRepository;
import cu.dev.halal.repository.UserRepository;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class ReviewDAOImpl implements ReviewDAO {
    private final static Logger logger = LoggerFactory.getLogger(ReviewDAOImpl.class);
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public ReviewDAOImpl(
            @Autowired ReviewRepository reviewRepository,
            @Autowired UserRepository userRepository,
            @Autowired StoreRepository storeRepository
    ){
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
    }

    @Override
    public JSONObject createReview(ReviewEntity reviewEntity) {
        JSONObject jsonObject = new JSONObject();
        if(this.userRepository.existsByEmail(reviewEntity.getUser().getEmail() )&&
                this.storeRepository.existsById(reviewEntity.getStore().getId())){
            reviewEntity.setUser(this.userRepository.getByEmail(reviewEntity.getUser().getEmail()));
            reviewEntity.setStore(this.storeRepository.getById(reviewEntity.getStore().getId()));
            this.reviewRepository.save(reviewEntity);
            jsonObject.put("result", "success");
            return jsonObject;
        }else{
         jsonObject.put("result", "user or store not exists");
         return jsonObject;
        }
    }

    @Override
    public List<ReviewEntity> readReviewByUser(String email) {

        if(this.userRepository.existsByEmail(email)) {
            return this.userRepository.getByEmail(email).getReviews();
        }else{
            List<ReviewEntity> error = new ArrayList<>();
            error.add(ReviewEntity.builder().score(999F).build());
            return error;
        }
    }

    @Override
    public List<ReviewEntity> readReviewByStore(Long storeId) {

        if(this.storeRepository.existsById(storeId)){
            return this.storeRepository.getById(storeId).getReviews();
        }else{
            List<ReviewEntity> error = new ArrayList<>();
            error.add(ReviewEntity.builder().score(999F).build());
            return error;
        }
    }

    @Override
    public JSONObject deleteReview(Long reviewId) {
        JSONObject jsonObject = new JSONObject();
        if(this.reviewRepository.existsById(reviewId)){
            this.reviewRepository.deleteById(reviewId);
            jsonObject.put("result", "success");
            return jsonObject;
        }else {
            jsonObject.put("result", "review not exists");
            return jsonObject;
        }
    }
}
