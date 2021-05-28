package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * commentsテーブルを操作するリポジトリ.
 * 
 * @author keita.horikawa
 *
 */
@Repository
public class CommentRepository {

	/** commentsオブジェクトを生成するRowMapper */
	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};

	/** テンプレート */
	@Autowired
	private NamedParameterJdbcTemplate template;

	/** 記事情報が入ったテーブル名 */
	private final String TABLE = "comments";
	
	/**
	 * 記事IDからコメントを検索するメソッド.
	 * 
	 * @param articleId 記事ID
	 * @return 検索されたコメントのリスト
	 */
	public List<Comment> findByArticleId(int articleId){
		String sql = "select id,name,content,article_id from " + TABLE + " where article_id=:articleId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		return template.query(sql, param, COMMENT_ROW_MAPPER);
	}
	
	/**
	 * 新規コメントをDBに登録するメソッド.
	 * 
	 * @param comment 新規コメント
	 */
	public void insert(Comment comment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		String sql = "insert into " + TABLE + "(name,content,article_id) values(:name,:content,:articleId);";
		template.update(sql, param);
	}

}
