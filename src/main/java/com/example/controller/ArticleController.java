package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;

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
	private ArticleRepository repository;
	
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
	 * 記事全件を表示するメソッド.
	 * 
	 * @param model requestスコープに格納するためのオブジェクト
	 * @return 掲示板
	 */
	@RequestMapping("")
	public String showBbs(Model model) {
		model.addAttribute("articleList", repository.findAll());		
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
		repository.insert(article);
		return showBbs(model);
	}

}
