# java-filmorate

Template repository for Filmorate project.

![Ссылка на структуру базы данных для приложения Filmorate:](https://github.com/84R5/java-filmorate/blob/add-friends-likes/info/DB_SHEME.png)

SELECT *
FROM film;

SELECT *  
FROM user;

SELECT f.film_id, f.film_name, COUNT(u.user_id) AS likes_count  
FROM film f  
LEFT OUTER JOIN user_film_likes u ON f.film_id = u.film_id  
GROUP BY f.film_id  
ORDER BY likes_count DESC  
LIMIT N;

SELECT * 
FROM userRelationship
WHERE relating_user_id = user1_id AND related_user_id = user2_id;
