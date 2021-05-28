package com.example.domain;

/**
 * コメントを表すドメインを作成.
 * 
 * @author keita.horikawa
 *
 */
public class Comment {

	/** ID */
	private Integer id;

	/** 名前 */
	private String name;

	/** コメント内容 */
	private String content;

	/** 記事ID */
	private Integer articled;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getArticled() {
		return articled;
	}

	public void setArticled(Integer articled) {
		this.articled = articled;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", name=" + name + ", content=" + content + ", articled=" + articled + "]";
	}

}
