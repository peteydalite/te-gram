package com.techelevator.dao;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.Comment;

public class CommentSqlDAOTest extends DAOIntegrationTest {

	 private static SingleConnectionDataSource dataSource;

	
	    @BeforeClass
	    public static void setupDataSource() {
	        dataSource = new SingleConnectionDataSource();
	        dataSource.setUrl("jdbc:postgresql://testdb.cqp384uauftt.us-east-2.rds.amazonaws.com/final_capstone");
	        dataSource.setUsername("postgres");
	        dataSource.setPassword("postgres1");
	       
	        dataSource.setAutoCommit(false);
	    }
	    private CommentSqlDAO commentSqlDAO;

	    @Before
	    public void setup() {
	        DataSource dataSource = this.getDataSource();
	        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	        commentSqlDAO = new CommentSqlDAO(jdbcTemplate);
	    }

	    @AfterClass
	    public static void closeDataSource() throws SQLException {
	        dataSource.destroy();
	    }

	   
	    @After
	    public void rollback() throws SQLException {
	        dataSource.getConnection().rollback();
	    }

	  
	    protected DataSource getDataSource() {
	        return dataSource;
	    }
	

	@Test
	public void findAll() {
		List<Comment> allComBefore = commentSqlDAO.findAll();
		
		Date date = new Date();
		Comment temp = new Comment(1, 0, "test", 1, 1);
		commentSqlDAO.addComment(temp);
		List<Comment> allComAfter = commentSqlDAO.findAll();

		assertEquals(allComBefore.size() + 1, allComAfter.size());
	}

	@Test
	public void getAllCommentsByPhotoId() {
		List<Comment> allComByPhotoBefore = commentSqlDAO.getAllCommentsByPhotoId(4);
		Date date = new Date();
		Comment temp = new Comment(4, 4, "test", 4, 4);

		commentSqlDAO.addComment(temp);
		List<Comment> allComByPhotoAfter = commentSqlDAO.getAllCommentsByPhotoId(4);

		assertEquals(allComByPhotoBefore.size() + 1, allComByPhotoAfter.size());
	}

	@Test
	public void addComment() {
		List<Comment> allComBefore = commentSqlDAO.findAll();

		Date date = new Date();
		Comment temp = new Comment(1, 1, "test", 1, 1);
		commentSqlDAO.addComment(temp);

		List<Comment> allComAfter = commentSqlDAO.findAll();

		assertEquals(allComBefore.size() + 1, allComAfter.size());
	}

	@Test
	public void deleteComment() {
		List<Comment> allComBefore = commentSqlDAO.findAll();

		Comment temp = new Comment(1, 0, null, 0, 0);
		commentSqlDAO.deleteComment(1);

		List<Comment> allComAfter = commentSqlDAO.findAll();

		assertEquals(allComBefore.size() - 1, allComAfter.size());
	}
}