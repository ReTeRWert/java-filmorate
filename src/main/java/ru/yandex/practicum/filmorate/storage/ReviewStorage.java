package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Review;

public interface ReviewStorage {

    Review createReview(Review review);

    Review updateReview(Review review);

    void deleteReview(Review review);

    Review getReviewById(Integer reviewId);

    Iterable<Review> getAllReviews();
}
