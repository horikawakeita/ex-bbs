package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

/**
 * 記事関連機能の処理の制御を行うコントローラ.
 * 
 * @author keita.horikawa
 *
 */
@Controller
@RequestMapping("/bbs")
public class ArticleController {

	/** リポジトリ */
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	/**
	 * ArticleFormオブジェクトをrequestスコープに格納.
	 * 
	 * @return ArticleFormオブジェクト
	 */
	@Autowired
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	/**
	 * CommentFormオブジェクトをrequestスコープに格納.
	 * 
	 * @return CommentFormオブジェクト
	 */
	@Autowired
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	
	/**
	 * 記事全件を表示するメソッド.
	 * 
	 * @param model requestスコープに格納するためのオブジェクト
	 * @return 掲示板
	 */
	@RequestMapping("")
	public String showBbs(Model model) {
		List<Article> articleList = articleRepository.findAll();
		for(Article article : articleList) {
			article.setCommentList(commentRepository.findByArticleId(article.getId()));
		}
		model.addAttribute("articleList", articleList);		
		return "bbs";
	}
	
	/**
	 * 新規記事をDBに登録するメソッド.
	 * 
	 * @param form 入力された名前と内容を持ったフォーム
	 * @param model requestスコープに格納するためのオブジェクト
	 * @return 掲示板
	 */
	@RequestMapping("/insert-article")
	public String insertArticle(ArticleForm form, Model model) {
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleRepository.insert(article);
		return "redirect:";
	}
	
	/**
	 * 新規コメントをDBに登録するメソッド.
	 * 
	 * @param form 入力された名前と内容を持ったフォーム
	 * @param model requestスコープに格納するためのオブジェクト
	 * @return 掲示板
	 */
	@RequestMapping("/insert-comment")
	public String insertComment(CommentForm form, Model model) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		comment.setArticleId(Integer.parseInt(form.getArticleId()));
		commentRepository.insert(comment);
		return "redirect:";
	}

}
