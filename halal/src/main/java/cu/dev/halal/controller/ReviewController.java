package cu.dev.halal.controller;


import cu.dev.halal.dto.ReviewDTO;
import cu.dev.halal.service.ReviewService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;

    public ReviewController(
            @Autowired ReviewService reviewService
    ){
        this.reviewService = reviewService;
    }


    @PostMapping()
    public ResponseEntity<JSONObject> createReview(
            @RequestBody ReviewDTO reviewDTO
            ){
        logger.info("[ReviewController] create review : {}", reviewDTO.toString() );
        JSONObject jsonObject = this.reviewService.createReview(reviewDTO);

        if(!jsonObject.containsValue("success")){
            return ResponseEntity.status(400).body(jsonObject);
        }
        return ResponseEntity.status(201).body(jsonObject);

    }


    @GetMapping
    public ResponseEntity<JSONObject> readReviewByUser(
            @RequestParam String email
    ){
        logger.info("[ReviewController] readByUser review : {}", email);
        JSONObject jsonObject = this.reviewService.readReviewByUser(email);
        if(jsonObject.containsKey("result")){
            return ResponseEntity.status(400).body(jsonObject);
        }
        logger.info("[ReviewController] readByUser review : {}", jsonObject.toString());
        return ResponseEntity.status(200).body(jsonObject);

    }

    @GetMapping("/{storeId}")
    public ResponseEntity<JSONObject> readReviewByStore(
        @PathVariable("storeId") Long storeId
    ){
        logger.info("[ReviewController] readByStore review : {}", storeId);
        JSONObject jsonObject = this.reviewService.readReviewByStore(storeId);
        if(jsonObject.containsKey("result")){
            return ResponseEntity.status(400).body(jsonObject);
        }
        logger.info("[ReviewController] readByStore review : {}", jsonObject.toString());
        return ResponseEntity.status(200).body(jsonObject);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<JSONObject> deleteReview(
            @PathVariable("reviewId") Long reviewId
    ){
        logger.info("[ReviewController] delete review : {}", reviewId);
        JSONObject jsonObject = this.reviewService.deleteReview(reviewId);
        if(!jsonObject.containsValue("success")){
            return ResponseEntity.status(400).body(jsonObject);
        }


        return ResponseEntity.status(200).body(jsonObject);
    }


}
