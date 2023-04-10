package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.ReviewLike;
import ru.yandex.practicum.filmorate.storage.ReviewLikeStorage;

import java.util.HashMap;
import java.util.Map;

@Component
@Qualifier
@RequiredArgsConstructor
public class ReviewLikeDbStorage implements ReviewLikeStorage {

    private final JdbcTemplate jdbcTemplate;
    private final ReviewDbStorage reviewDbStorage;


    @Override
    public void addLike(Integer reviewId, Integer userId) {
        ReviewLike like = ReviewLike.builder()
                .reviewId(reviewId)
                .userId(userId)
                .like(true)
                .build();

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("review_likes");
        insert.execute(toMap(like));
        reviewDbStorage.getReviewById(reviewId).setUseful(
                reviewDbStorage.getReviewById(reviewId).getUseful() + 1);
    }

    @Override
    public void deleteLike(Integer reviewId, Integer userId) {
        String sql = "DELETE FROM review_likes " +
                "WHERE review_id =? AND user_id =?";

        jdbcTemplate.update(sql, reviewId, userId);

        reviewDbStorage.getReviewById(reviewId).setUseful(
                reviewDbStorage.getReviewById(reviewId).getUseful() - 1);
    }

    @Override
    public void addDislike(Integer reviewId, Integer userId) {
        ReviewLike like = ReviewLike.builder()
                .reviewId(reviewId)
                .userId(userId)
                .like(false)
                .build();

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("review_likes");
        insert.execute(toMap(like));

        reviewDbStorage.getReviewById(reviewId).setUseful(
                reviewDbStorage.getReviewById(reviewId).getUseful() - 1);
    }

    @Override
    public void deleteDislike(Integer reviewId, Integer userId) {
        String sql = "DELETE FROM review_likes " +
                "WHERE review_id =? AND user_id =?";

        jdbcTemplate.update(sql, reviewId, userId);

        reviewDbStorage.getReviewById(reviewId).setUseful(
                reviewDbStorage.getReviewById(reviewId).getUseful() + 1);
    }

    private Map<String, Object> toMap(ReviewLike reviewLike) {
        Map<String, Object> map = new HashMap<>();
        map.put("review_id", reviewLike.getReviewId());
        map.put("user_id", reviewLike.getUserId());
        map.put("like", reviewLike.getLike());
        return map;
    }
}
