package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

/**
 * articleテーブルを操作するリポジトリ.
 * 
 * @author keita.horikawa
 *
 */
@Repository
public class ArticleRepository {

//	/** articlesオブジェクトを生成するRowMapper */
//	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
//		Article article = new Article();
//		article.setId(rs.getInt("id"));
//		article.setName(rs.getString("name"));
//		article.setContent(rs.getString("content"));
//		return article;
//	};

	/** resultsetにテーブル全てをセットして処理する変数 */
	private static final ResultSetExtractor<List<Article>> ARTICLE_RSE = (rs) -> {
		List<Article> articleList = new ArrayList<>();
		List<Comment> commentList = null;
		int beforeId = 0;
		while (rs.next()) {
			if (beforeId != rs.getInt("id")) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				commentList = new ArrayList<>();
				article.setCommentList(commentList);
				articleList.add(article);
			} 
			if(rs.getInt("com_id") != 0){
				Comment comment = new Comment();
				comment.setId(rs.getInt("com_id"));
				comment.setName(rs.getString("com_name"));
				comment.setContent(rs.getString("com_content"));
				comment.setArticleId(rs.getInt("article_id"));
				commentList.add(comment);
			}
			beforeId = rs.getInt("id");
		}
		return articleList;
	};

	/** テンプレート */
	@Autowired
	private NamedParameterJdbcTemplate template;

	/** 記事情報が入ったテーブル名 */
	private final String TABLE = "articles";

	/**
	 * 記事一覧を表示するメソッド.
	 * 
	 * @return 全記事
	 */
	public List<Article> findAll() {
//		String sql = "select id,name,content from " + TABLE + " order by id desc";		
		String sql = "select a.id,a.name,a.content,c.id as com_id,c.name as com_name,c.content as com_content"
				+ ",c.article_id from articles as a left outer join comments as c on a.id = c.article_id "
				+ "order by a.id desc,c.id";
//		return template.query(sql, ARTICLE_ROW_MAPPER);
		return template.query(sql, ARTICLE_RSE);
	}

	/**
	 * 新規記事を挿入するメソッド.
	 * 
	 * @param article 新規記事
	 */
	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String sql = "insert into " + TABLE + "(name,content) values(:name,:content);";
		template.update(sql, param);
	}

	/**
	 * 指定されたIDの記事を削除するメソッド.
	 * 
	 * @param id 記事ID
	 */
	public void deleteById(int id) {
//		String sql = "delete from " + TABLE + " where id=:id";
		String sql = "delete from articles as a left outer join comments as c on a.id = c.article_id "
				+ "where a.id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
