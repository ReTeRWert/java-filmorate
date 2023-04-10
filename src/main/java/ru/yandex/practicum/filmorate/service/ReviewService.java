package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidateException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.db.ReviewDbStorage;
import ru.yandex.practicum.filmorate.storage.db.ReviewLikeDbStorage;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewDbStorage reviewDbStorage;
    private final ReviewLikeDbStorage reviewLikeDbStorage;

    public void createReview(Review review) {
        if (reviewDbStorage.getReviewById(review.getReviewId()) != null) {
            throw new ValidateException("Ревью уже существует");
        }

        reviewDbStorage.createReview(review);
    }

    public Review updateReview(Review review) {
        if (reviewDbStorage.getReviewById(review.getReviewId()) == null) {
            throw new ValidateException("Ревью не существует");
        }

        return reviewDbStorage.updateReview(review);
    }

    public void deleteReview(Review review) {
        if (reviewDbStorage.getReviewById(review.getReviewId()) == null) {
            throw new ValidateException("Ревью не существует");
        }

        reviewDbStorage.deleteReview(review);
    }

    public Review getReviewById(Integer reviewId) {
        return reviewDbStorage.getReviewById(reviewId);
    }

    public Iterable<Review> getAllReviews() {
        return reviewDbStorage.getAllReviews();
    }

    public Iterable<Review> getReviewsByFilmId(Integer filmId, Integer count) {
        return reviewDbStorage.getReviewsByFilmId(filmId, count);
    }

    public void addLikeReview(Integer reviewId, Integer userId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidateException("Ревью не существует");
        }

        reviewLikeDbStorage.addLike(reviewId, userId);
    }

    public void addDislikeReview(Integer reviewId, Integer userId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidateException("Ревью не существует");
        }

        reviewLikeDbStorage.addDislike(reviewId, userId);
    }

    public void deleteLikeReview(Integer reviewId, Integer userId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidateException("Ревью не существует");
        }

        reviewLikeDbStorage.deleteLike(reviewId, userId);
    }

    public void deleteDislikeReview(Integer reviewId, Integer userId) {
        if (reviewDbStorage.getReviewById(reviewId) == null) {
            throw new ValidateException("Ревью не существует");
        }

        reviewLikeDbStorage.deleteDislike(reviewId, userId);
    }
}
