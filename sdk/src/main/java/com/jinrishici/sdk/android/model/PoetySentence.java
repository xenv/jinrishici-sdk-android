package com.jinrishici.sdk.android.model;

import java.util.List;

public class PoetySentence {
	/**
	 * status : success
	 * data : {"id":"5b8b9572e116fb3714e6faba","content":"君问归期未有期，巴山夜雨涨秋池。","popularity":1170000,"origin":{"title":"夜雨寄北","dynasty":"唐代","author":"李商隐","content":["君问归期未有期，巴山夜雨涨秋池。","何当共剪西窗烛，却话巴山夜雨时。"],"translate":["您问归期，归期实难说准，巴山连夜暴雨，涨满秋池。","何时归去，共剪西窗烛花，当面诉说，巴山夜雨况味。"]},"matchTags":["秋","晚上"],"recommendedReason":"","cacheAt":"2018-09-17T21:18:44.693645"}
	 * token : 6453911a-9ad7-457e-9b9d-c21011b85a0c
	 * ipAddress : 162.248.93.154
	 */

	private String status;
	private DataBean data;
	private String token;
	private String ipAddress;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public static class DataBean {
		/**
		 * id : 5b8b9572e116fb3714e6faba
		 * content : 君问归期未有期，巴山夜雨涨秋池。
		 * popularity : 1170000
		 * origin : {"title":"夜雨寄北","dynasty":"唐代","author":"李商隐","content":["君问归期未有期，巴山夜雨涨秋池。","何当共剪西窗烛，却话巴山夜雨时。"],"translate":["您问归期，归期实难说准，巴山连夜暴雨，涨满秋池。","何时归去，共剪西窗烛花，当面诉说，巴山夜雨况味。"]}
		 * matchTags : ["秋","晚上"]
		 * recommendedReason :
		 * cacheAt : 2018-09-17T21:18:44.693645
		 */

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

		public static class OriginBean {
			/**
			 * title : 夜雨寄北
			 * dynasty : 唐代
			 * author : 李商隐
			 * content : ["君问归期未有期，巴山夜雨涨秋池。","何当共剪西窗烛，却话巴山夜雨时。"]
			 * translate : ["您问归期，归期实难说准，巴山连夜暴雨，涨满秋池。","何时归去，共剪西窗烛花，当面诉说，巴山夜雨况味。"]
			 */

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
	}
}
