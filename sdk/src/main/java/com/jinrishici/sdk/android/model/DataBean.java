package com.jinrishici.sdk.android.model;

import java.io.Serializable;
import java.util.List;

public class DataBean implements Serializable {
	private String id;
	private String content;
	private int popularity;
	private OriginBean origin;
	private String recommendedReason;
	private String cacheAt;
	private List<String> matchTags;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}

	public OriginBean getOrigin() {
		return origin;
	}

	public void setOrigin(OriginBean origin) {
		this.origin = origin;
	}

	public String getRecommendedReason() {
		return recommendedReason;
	}

	public void setRecommendedReason(String recommendedReason) {
		this.recommendedReason = recommendedReason;
	}

	public String getCacheAt() {
		return cacheAt;
	}

	public void setCacheAt(String cacheAt) {
		this.cacheAt = cacheAt;
	}

	public List<String> getMatchTags() {
		return matchTags;
	}

	public void setMatchTags(List<String> matchTags) {
		this.matchTags = matchTags;
	}
}