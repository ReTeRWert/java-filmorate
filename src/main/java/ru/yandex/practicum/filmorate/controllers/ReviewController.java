package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.review.ReviewService;
import ru.yandex.practicum.filmorate.validator.ReviewValidator;


import java.util.List;

@RestController
@RequestMapping("/reviews")
@Slf4j
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewValidator reviewValidator;

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        reviewValidator.validate(review);
        reviewService.createReview(review);
        log.info("Создано ревью: {}", review);
        return review;
    }

    @PutMapping
    public Review updateReview(@RequestBody Review review) {
        reviewValidator.validate(review);
        log.info("Обновлено ревью: {}", review);
        return reviewService.updateReview(review);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Integer reviewId) {
        reviewService.deleteReview(reviewId);
        log.info("Ревью удалено.");
    }

    @GetMapping("/{reviewId}")
    public Review getReviewById(@PathVariable Integer reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @GetMapping()
    public List<Review> getReviewsByFilmId(@RequestParam(required = false) Integer filmId,
                                           @RequestParam(defaultValue = "10", required = false) int count) {
        if (filmId == null) {
            return reviewService.getAllReviews();
        }
        if (count < 0) {
            throw new IllegalArgumentException("Количество искомых отзывов не может быть отрицательным");
        }

        return reviewService.getReviewsByFilmId(filmId, count);
    }

    @PutMapping("/{reviewId}/like/{userId}")
    public void addLikeReview(@PathVariable Integer reviewId,
                              @PathVariable Integer userId) {

        reviewService.addLikeReview(reviewId, userId);
    }

    @PutMapping("/{reviewId}/dislike/{userId}")
    public void addDislikeReview(@PathVariable Integer reviewId,
                                 @PathVariable Integer userId) {

        reviewService.addDislikeReview(reviewId, userId);
    }

    @DeleteMapping("/{reviewId}/like/{userId}")
    public void deleteLikeReview(@PathVariable Integer reviewId,
                                 @PathVariable Integer userId) {

        reviewService.deleteLikeReview(reviewId, userId);
    }

    @DeleteMapping("/{reviewId}/dislike/{userId}")
    public void deleteDislikeReview(@PathVariable Integer reviewId,
                                    @PathVariable Integer userId) {

        reviewService.deleteDislikeReview(reviewId, userId);
    }
}
