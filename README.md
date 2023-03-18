# java-filmorate
Template repository for Filmorate project.


Запросы:
films
 getFilms: SELECT *
          FROM films AS f
          JOIN rating_mpa AS r ON f.rating_id = r.rating_id;

 getFilmById: SELECT *
             FROM films AS f, rating_mpa AS r
             WHERE f.rating_id = r.rating_id AND f.film_id = ?;
 
users
 getUsers: SELECT * 
           FROM users;  
           
 getUserById: SELECT *
              FROM users
              WHERE user_id =?;
              
genres
  getGenres: SELECT *
             FROM genres;
             
  getGenreById: SELECT *
                FROM genres
                WHERE genre_id = ?;

friends
 addFriend: INSERT INTO friends(user_id, friend_id)
            VALUES (?,?);
            
 removeFriend: DELETE FROM friends
               WHERE user_id = ? AND friend_id = ?;
              
 getFriendsUser: SELECT *
                 FROM users AS u, friends AS f
                 WHERE u.user_id = f.friend_id AND f.user_id = ?;
                 
 getCommonFriends: SELECT * 
                   FROM users AS u, friends AS f, friends AS o
                   WHERE u.user_id = f.friend_id 
                   AND u.user_id = o.friend_id
                   AND f.user_id= ?
                   AND o.user_id=?";
                   
rating_mpa
 getRatings: SELECT *
             FROM rating_mpa;
             
 getRatingById: SELECT *
                FROM rating_mpa
                WHERE rating_id = ?;