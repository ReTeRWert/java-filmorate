package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Qualifier
@Component
@RequiredArgsConstructor
public class ReviewDbStorage implements ReviewStorage {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public Review createReview(Review review) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reviews")
                .usingGeneratedKeyColumns("review_id");

       Integer reviewId = simpleJdbcInsert.executeAndReturnKey(toMap(review)).intValue();
       review.setReviewId(reviewId);

       return review;
    }



    @Override
    public Review updateReview(Review review) {
        String sql = "UPDATE reviews " +
                     "SET content =?, isPositive =?, user_id =?, film_id =?, useful =? " +
                     "WHERE review_id =?";
        jdbcTemplate.update(sql, review.getContent(), review.getIsPositive(), review.getUserId(), review.getFilmId(),
                review.getUseful(), review.getReviewId());

        return review;
    }

    @Override
    public void deleteReview(Review review) {
        String sql = "DELETE " +
                     "FROM reviews " +
                     "WHERE review_id =?";

        jdbcTemplate.update(sql, review.getReviewId());
    }

    @Override
    public Review getReviewById(Integer reviewId) {
        String sql = "SELECT * " +
                     "FROM reviews " +
                     "WHERE review_id =?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeReview(rs), reviewId);
    }

    @Override
    public Iterable<Review> getAllReviews() {
        String sql = "SELECT * " +
                     "FROM reviews";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeReview(rs));
    }

    public Iterable<Review> getReviewsByFilmId(Integer filmId, Integer count) {
        String sql = "SELECT * " +
                     "FROM reviews " +
                     "WHERE film_id =? " +
                     "ORDER BY useful DESC " +
                     "LIMIT ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeReview(rs), filmId, count);
    }


    private Map<String, Object> toMap(Review review) {
        Map<String, Object> reviewParams = new HashMap<>();
        reviewParams.put("review_id", review.getReviewId());
        reviewParams.put("content", review.getContent());
        reviewParams.put("isPositive", review.getIsPositive());
        reviewParams.put("user_id", review.getUserId());
        reviewParams.put("film_id", review.getFilmId());
        reviewParams.put("useful", review.getUseful());

        return reviewParams;
    }

    private Review makeReview(ResultSet rs) throws SQLException {
        Review review = new Review();

        review.setReviewId(rs.getInt("review_id"));
        review.setContent(rs.getString("content"));
        review.setIsPositive(rs.getBoolean("isPositive"));
        review.setUserId(rs.getInt("user_id"));
        review.setFilmId(rs.getInt("film_id"));
        review.setUseful(rs.getInt("useful"));

        return review;
    }
}
