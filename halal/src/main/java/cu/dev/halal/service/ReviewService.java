package cu.dev.halal.service;

import cu.dev.halal.dto.ReviewDTO;
import org.json.simple.JSONObject;

import java.util.List;

public interface ReviewService {

    public JSONObject createReview(ReviewDTO reviewDTO);
    public JSONObject readReviewByUser(String email);
    public JSONObject readReviewByStore(Long storeId);
    public JSONObject deleteReview(Long reviewId);
}
