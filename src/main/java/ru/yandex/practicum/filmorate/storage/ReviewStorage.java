package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Review;

public interface ReviewStorage {
    Review createReview(Review review) throws Exception;

    Review updateReview(Review review);

    void deleteReview(Integer reviewId);

    Review getReviewById(Integer reviewId);

    Iterable<Review> getAllReviews();
}
