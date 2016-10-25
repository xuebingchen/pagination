
package cn.noteblog.pagination.domain;

import java.util.Date;

/**
 * author chenxuebing
 * 2016-3-1
 */
public class Blog {
	
	private Integer id;
	private Integer userId;
	private String  title;
	private String  outline;
	private String  content;
	private Integer prise;   //称赞
	private Integer disdain; //鄙视
	private Integer browse;  //查看量
	private Integer commentNum;//评论量 
	private String  keyword;
	private  Date   createAt;
	private String  orderBy;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOutline() {
		return outline;
	}
	public void setOutline(String outline) {
		this.outline = outline;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getPrise() {
		return prise;
	}
	public void setPrise(Integer prise) {
		this.prise = prise;
	}
	public Integer getDisdain() {
		return disdain;
	}
	public void setDisdain(Integer disdain) {
		this.disdain = disdain;
	}
	public Integer getBrowse() {
		return browse;
	}
	public void setBrowse(Integer browse) {
		this.browse = browse;
	}
	
	public String getKeyword() {
		return keyword;
	}
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	

}
