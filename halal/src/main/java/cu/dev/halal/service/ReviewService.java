package cu.dev.halal.service;

import cu.dev.halal.dto.ReviewDTO;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReviewService {

    public JSONObject createReview(ReviewDTO reviewDTO, List<MultipartFile> multipartFiles) throws IOException;
    public JSONObject readReviewByUser(String email);
    public JSONObject readReviewByStore(Long storeId);
    public JSONObject deleteReview(Long reviewId);
}
