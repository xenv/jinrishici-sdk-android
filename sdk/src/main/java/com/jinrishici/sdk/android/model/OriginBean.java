package com.jinrishici.sdk.android.model;

import java.io.Serializable;
import java.util.List;

public class OriginBean implements Serializable {
	private String title;
	private String dynasty;
	private String author;
	private List<String> content;
	private List<String> translate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDynasty() {
		return dynasty;
	}

	public void setDynasty(String dynasty) {
		this.dynasty = dynasty;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<String> getContent() {
		return content;
	}

	public void setContent(List<String> content) {
		this.content = content;
	}

	public List<String> getTranslate() {
		return translate;
	}

	public void setTranslate(List<String> translate) {
		this.translate = translate;
	}
}