package techeer.restaurant.domain.review.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import techeer.restaurant.domain.restaurant.dto.RestaurantInfo;
import techeer.restaurant.domain.restaurant.entity.Restaurant;
import techeer.restaurant.domain.restaurant.repository.RestaurantRepository;
import techeer.restaurant.domain.review.dto.ReviewInfo;
import techeer.restaurant.domain.review.dto.ReviewRequest;
import techeer.restaurant.domain.review.dto.UpdateReviewRequest;
import techeer.restaurant.domain.review.entity.Review;
import techeer.restaurant.domain.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    public ReviewInfo createReview(ReviewRequest request) {
        Review newReview = convertReviewFromRequest(request);
        Review savedReview = reviewRepository.save(newReview);
        return mapReviewEntityToReviewInfoResponse(savedReview);
    }

    private Review convertReviewFromRequest(ReviewRequest request) {
        Restaurant foundRestaurant = getRestaurantById(request.getRestaurantId());

        return Review.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .restaurant(foundRestaurant)
                .build();
    }

    private Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.findRestaurantById(restaurantId);
    }

    private ReviewInfo mapReviewEntityToReviewInfoResponse(Review review) {
        return ReviewInfo.builder()
                .title(review.getTitle())
                .content(review.getContent())
                .restaurant(review.getRestaurant().getName())
                .build();
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public void updateReviewInfo(Long id, UpdateReviewRequest request) {
        Review review = reviewRepository.findReviewById(id);
        review.updateTitle(request.getTitle());
        review.updateContent(request.getContent());
        reviewRepository.save(review);
    }

    public ReviewInfo findReviewInfoById(Long id) {
        Review review = findReviewById(id);
        reviewRepository.save(review);
        return mapReviewEntityToReviewInfoResponse(review);
    }

    public Review findReviewById(Long id) {
        return reviewRepository.findReviewById(id);
    }
}
