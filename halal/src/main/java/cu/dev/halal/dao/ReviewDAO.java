package cu.dev.halal.dao;

import cu.dev.halal.entity.ReviewEntity;
import org.json.simple.JSONObject;

import java.util.List;

public interface ReviewDAO {

    public JSONObject createReview(ReviewEntity reviewEntity);
    public List<ReviewEntity> readReviewByUser(String email);
    public List<ReviewEntity> readReviewByStore(Long storeId);
    public JSONObject deleteReview(Long reviewId);
}
