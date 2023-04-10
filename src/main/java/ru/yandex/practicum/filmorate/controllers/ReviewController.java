package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;

@RestController
@RequestMapping("/reviews")
@Slf4j
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        reviewService.createReview(review);
        log.info("Создано ревью: {}", review);
        return review;
    }

    @PutMapping
    public Review updateReview(@RequestBody Review review) {
        reviewService.updateReview(review);
        log.info("Обновлено ревью: {}", review);
        return review;
    }

    @DeleteMapping
    public void deleteReview(@RequestBody Review review) {
        reviewService.deleteReview(review);
        log.info("Удалено ревью: {}", review);
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Integer reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @GetMapping()
    public Iterable<Review> getReviewsByFilmId(@RequestParam(required = false) Integer filmId,
                                               @RequestParam(defaultValue = "10", required = false) int count) {
        if (filmId == null) {
            return reviewService.getAllReviews();
        }
        if (count < 0) {
            throw new IllegalArgumentException("Количество искомых отзывов не может быть отрицательным");
        }

        return reviewService.getReviewsByFilmId(filmId, count);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikeReview(@PathVariable Integer reviewId,
                              @PathVariable Integer userId) {

        reviewService.addLikeReview(reviewId, userId);
    }

    @PutMapping("/{id}/dislike/{userId}")
    public void addDislikeReview(@PathVariable Integer reviewId,
                                 @PathVariable Integer userId) {

        reviewService.addDislikeReview(reviewId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikeReview(@PathVariable Integer reviewId,
                                 @PathVariable Integer userId) {

        reviewService.deleteLikeReview(reviewId, userId);
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public void deleteDislikeReview(@PathVariable Integer reviewId,
                                    @PathVariable Integer userId) {

        reviewService.deleteDislikeReview(reviewId, userId);
    }
}
