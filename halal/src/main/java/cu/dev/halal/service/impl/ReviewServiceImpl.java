package cu.dev.halal.service.impl;

import cu.dev.halal.dao.ReviewDAO;
import cu.dev.halal.dto.ReviewDTO;
import cu.dev.halal.entity.ReviewEntity;
import cu.dev.halal.entity.StoreEntity;
import cu.dev.halal.entity.UserEntity;
import cu.dev.halal.service.ReviewService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ReviewServiceImpl implements ReviewService {
    private final static Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final ReviewDAO reviewDAO;

    public ReviewServiceImpl(
            @Autowired ReviewDAO reviewDAO
    ){
        this.reviewDAO = reviewDAO;
    }


    @Override
    public JSONObject createReview(ReviewDTO reviewDTO) {

        ReviewEntity reviewEntity = ReviewEntity.builder()
                .content(reviewDTO.getContent())
                .score(reviewDTO.getScore())
                .store(StoreEntity.builder().id(reviewDTO.getStoreId()).build())
                .user(UserEntity.builder().email(reviewDTO.getEmail()).build())
                .build();

        return this.reviewDAO.createReview(reviewEntity);
    }

    @Override
    public JSONObject readReviewByUser(String email) {
        JSONObject jsonObject = new JSONObject();
        List<ReviewEntity> reviewEntities = this.reviewDAO.readReviewByUser(email);
        try{
            List<ReviewDTO> reviews = new ArrayList<>();
            if (!reviewEntities.isEmpty()) {
                for (ReviewEntity reviewEntity : reviewEntities) {
                    ReviewDTO reviewDTO = ReviewDTO.builder()
                            .id(reviewEntity.getId())
                            .content(reviewEntity.getContent())
                            .score(reviewEntity.getScore())
                            .storeId(reviewEntity.getStore().getId())
                            .email(reviewEntity.getUser().getEmail())
                            .build();
                    reviews.add(reviewDTO);
                }
                jsonObject.put("reviews", reviews);
                return jsonObject;
            }
        }catch (NullPointerException e){
            jsonObject.put("result", "user not exists");
            return jsonObject;
        }
        jsonObject.put("reviews", reviewEntities);
        return jsonObject;
    }

    @Override
    public JSONObject readReviewByStore(Long storeId) {
        JSONObject jsonObject = new JSONObject();
        List<ReviewEntity> reviewEntities = this.reviewDAO.readReviewByStore(storeId);
        List<ReviewDTO> reviews = new ArrayList<>();
        Float scoreAvg = 0F;

        try{
            if (!reviewEntities.isEmpty()) {
                for (ReviewEntity reviewEntity : reviewEntities) {
                    ReviewDTO reviewDTO = ReviewDTO.builder()
                            .id(reviewEntity.getId())
                            .content(reviewEntity.getContent())
                            .score(reviewEntity.getScore())
                            .storeId(storeId)
                            .email(reviewEntity.getUser().getEmail())
                            .build();
                    reviews.add(reviewDTO);
                    scoreAvg += reviewEntity.getScore();
                }
                scoreAvg /= reviewEntities.size();
                jsonObject.put("scoreAvg", scoreAvg);
                jsonObject.put("reviews", reviews);
                return jsonObject;
            }
        }catch (NullPointerException e){
            jsonObject.put("result", "store not exists");
            return jsonObject;
        }
        jsonObject.put("reviews", reviewEntities);
        return jsonObject;
    }

    @Override
    public JSONObject deleteReview(Long reviewId) {
        return this.reviewDAO.deleteReview(reviewId);
    }
}
