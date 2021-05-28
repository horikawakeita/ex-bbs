package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;


/**
 * articleテーブルを操作するリポジトリ.
 * 
 * @author keita.horikawa
 *
 */
@Repository
public class ArticleRepository {

	/** articlesオブジェクトを生成するRowMapper */
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
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
	public List<Article> findAll(){
		String sql = "select id,name,content from " + TABLE + " order by id desc";		
		return template.query(sql, ARTICLE_ROW_MAPPER);
	}
	
	/**
	 * 新規記事を挿入するメソッド.
	 * 
	 * @param article　新規記事
	 */
	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String sql = "insert into " + TABLE + "(name,content) values(:name,:content);";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("name", article.getName()).addValue("content", article.getContent());
		template.update(sql, param);
	}
	
	/**
	 * 指定されたIDの記事を削除するメソッド.
	 * 
	 * @param id 記事ID
	 */
	public void deleteById(int id) {
		String sql = "delete from " + TABLE + " where id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
