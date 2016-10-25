package cn.noteblog.pagination.domain;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

public class PageBounds<E> extends RowBounds implements Serializable{
	private static final long serialVersionUID = -770904754454048860L;
	
	private int offset = 1;
	private int limit = 0;
	/*页数*/
	private int pageCount = 1;
	/*总记录数*/
	private int totalCount = 1;
	/*当前页数*/
	private int pageSize =1;
	
	/*总页数*/
	private String orderBy = null;
	
	private static String ASC = "asc";
	private static String DESC = "desc";
	
	private List<E> result = null;
//	public PageBounds(RowBounds rowBounds){
//		if(rowBounds instanceof PageBounds){
//			
//		}
//	}


	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = (totalCount % offset)==0?(totalCount % offset):(totalCount % offset)+1;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public List<E> getResult() {
		return result;
	}

	public void setResult(List<E> result) {
		this.result = result;
	}

	
	
}
