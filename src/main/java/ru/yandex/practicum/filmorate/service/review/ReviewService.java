package ru.yandex.practicum.filmorate.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.ReviewDbStorage;
import ru.yandex.practicum.filmorate.dao.ReviewLikeDbStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.model.FeedEventType;
import ru.yandex.practicum.filmorate.model.FeedOperation;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.user.FeedStorage;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewDbStorage reviewDbStorage;
    private final ReviewLikeDbStorage reviewLikeDbStorage;
    private final FeedStorage feedStorage;

    public Review createReview(Review review) {
        if (review.getReviewId() != null) {
            throw new ValidationException("Ревью уже существует");
        }
        feedStorage.addFeed(Feed.builder().operation(FeedOperation.ADD).eventType(FeedEventType.REVIEW).entityId(review.getFilmId()).userId(review.getUserId()).build());
        return reviewDbStorage.createReview(review);
    }

    public Review updateReview(Review review) {
        if (reviewDbStorage.getReviewById(review.getReviewId()) == null) {
            throw new ValidationException("Ревью не существует.");
        }
        feedStorage.addFeed(Feed.builder().operation(FeedOperation.UPDATE).eventType(FeedEventType.REVIEW).entityId(review.getFilmId()).userId(review.getUserId()).build());
        return reviewDbStorage.updateReview(review);
    }

    public void deleteReview(Long reviewId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidationException("Ревью не существует");
        }
        feedStorage.addFeed(Feed.builder().operation(FeedOperation.REMOVE).eventType(FeedEventType.REVIEW).entityId(getReviewById(reviewId).getFilmId()).userId(getReviewById(reviewId).getUserId()).build());
        reviewDbStorage.deleteReview(reviewId);
    }

    public Review getReviewById(Long reviewId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidationException("Ревью не существует");
        }
        return reviewDbStorage.getReviewById(reviewId);
    }

    public List<Review> getAllReviews() {
        return reviewDbStorage.getAllReviews();
    }

    public List<Review> getReviewsByFilmId(Long filmId, Integer count) {
        return reviewDbStorage.getReviewsByFilmId(filmId, count);
    }

    public void addLikeReview(Long reviewId, Long userId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidationException("Ревью не существует");
        }

        reviewLikeDbStorage.addLike(reviewId, userId);
    }

    public void addDislikeReview(Long reviewId, Long userId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidationException("Ревью не существует");
        }

        reviewLikeDbStorage.addDislike(reviewId, userId);
    }

    public void deleteLikeReview(Long reviewId, Long userId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidationException("Ревью не существует");
        }

        reviewLikeDbStorage.deleteLike(reviewId, userId);
    }

    public void deleteDislikeReview(Long reviewId, Long userId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidationException("Ревью не существует");
        }

        reviewLikeDbStorage.deleteDislike(reviewId, userId);
    }
}
