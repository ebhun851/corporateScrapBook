package com.nisum.corporateSocial.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import lombok.Getter;
import lombok.Setter;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.nisum.corporateSocial.model.User;
import com.nisum.corporateSocial.model.WallPost;

@Getter
@Setter
@Repository
public class WallPostingsDao {
	@Autowired(required=true)
	private DataSource dataSource;
	@Autowired(required=true)
	private JdbcTemplate jdbcTemplate;
	@Autowired(required=true)
	private UserDao userDao;
	
	private static final String CREATE_WALL_POST = "insert into wall_post (loged_user_id, posting_user_id,post_content," +
			"time_stamp) values (?, ?,?,?)";
	private static final String WALL_POSTS_BY_USER_ID = "select * from WALL_POST where loged_user_id = ?";
	
	public List<WallPost> getWallPostsByUser(User user) throws IOException{
		List<WallPost> wallPosts =  jdbcTemplate.query(WALL_POSTS_BY_USER_ID,new Object[]{user.getUserId()}, new WallPostMapper());
		for(WallPost wallPost : wallPosts){
			User wallPostUser = userDao.getUserByUserId(wallPost.getWallPostUser().getUserId());
			wallPost.setWallOwner(wallPostUser);
		}
		return wallPosts;
	}
	
	public int insertWallPostForUsers(List<User> users, WallPost wallPost){
		int count = 0;
		for(User user:users){
			count += jdbcTemplate.update(CREATE_WALL_POST, new Object[]{user.getUserId(),wallPost.getWallPostUser(),
					wallPost.getPostContent(),new DateTime().toString() });
			
		}
		return count;
	}
	
	public int insertWallPostForUser(WallPost wallPost){
		return jdbcTemplate.update(CREATE_WALL_POST, new Object[]{wallPost.getWallOwner().getUserId(),wallPost.getWallPostUser(),
				wallPost.getPostContent(),new DateTime().toString() });
	}
	
	class WallPostMapper implements ParameterizedRowMapper<WallPost> {
		   public WallPost mapRow(ResultSet rs, int rowNum) throws SQLException {
			  WallPost wallPost = new WallPost();
		      wallPost.setPostId(rs.getString("post_id"));
		      User wallPostUser = new User();
		      wallPostUser.setUserId(rs.getString("posting_user_id"));
		      wallPost.setWallPostUser(wallPostUser);
		      wallPost.setPostContent(rs.getString("post_content"));
		      wallPost.setTimeStamp(new DateTime(rs.getString("time_stamp")));
		      return wallPost;
		   }
	}
}
