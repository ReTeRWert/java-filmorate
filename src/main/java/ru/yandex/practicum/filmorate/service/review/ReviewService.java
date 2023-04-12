package ru.yandex.practicum.filmorate.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.ReviewDbStorage;
import ru.yandex.practicum.filmorate.dao.ReviewLikeDbStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewDbStorage reviewDbStorage;
    private final ReviewLikeDbStorage reviewLikeDbStorage;

    public Review createReview(Review review) {
        if (review.getReviewId() != null) {
            throw new ValidationException("Ревью уже существует");
        }

        return reviewDbStorage.createReview(review);
    }

    public Review updateReview(Review review) {
        if (reviewDbStorage.getReviewById(review.getReviewId()) == null) {
            throw new ValidationException("Ревью не существует.");
        }
        return reviewDbStorage.updateReview(review);
    }

    public void deleteReview(Integer reviewId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidationException("Ревью не существует");
        }

        reviewDbStorage.deleteReview(reviewId);
    }

    public Review getReviewById(Integer reviewId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidationException("Ревью не существует");
        }
        return reviewDbStorage.getReviewById(reviewId);
    }

    public List<Review> getAllReviews() {
        return reviewDbStorage.getAllReviews();
    }

    public List<Review> getReviewsByFilmId(Integer filmId, Integer count) {
        return reviewDbStorage.getReviewsByFilmId(filmId, count);
    }

    public void addLikeReview(Integer reviewId, Integer userId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidationException("Ревью не существует");
        }

        reviewLikeDbStorage.addLike(reviewId, userId);
    }

    public void addDislikeReview(Integer reviewId, Integer userId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidationException("Ревью не существует");
        }

        reviewLikeDbStorage.addDislike(reviewId, userId);
    }

    public void deleteLikeReview(Integer reviewId, Integer userId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidationException("Ревью не существует");
        }

        reviewLikeDbStorage.deleteLike(reviewId, userId);
    }

    public void deleteDislikeReview(Integer reviewId, Integer userId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidationException("Ревью не существует");
        }

        reviewLikeDbStorage.deleteDislike(reviewId, userId);
    }
}
