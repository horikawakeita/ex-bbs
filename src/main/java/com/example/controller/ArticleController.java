package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping("")
	public String showBbs(Model model) {
		model.addAttribute("articleList", repository.findAll());		
		return "bbs";
	}

}
