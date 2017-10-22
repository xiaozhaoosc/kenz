package site.kenz.db.api.kenzdbapi.bean;

import java.util.List;

/**
 * mongodb 分页查询实体类
 * 返回的分页数据为一个list集合
 * @author es_ai
 *
 */
public class PageEntity<T extends BaseEntity> {
	/** 开始行数 **/
	private int start;
	/** 查询行数 **/
	private int pageSize;
	/** 总记录数 **/
	private int totalResults;
	/** 总页数 **/
	private int totalPages;
	/** 返回数据集合 **/
	private List<T> results;
	/** 当前页面记录数 **/
	private int currentPageLength;
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public int getCurrentPageLength() {
		return currentPageLength;
	}

	public void setCurrentPageLength(int currentPageLength) {
		this.currentPageLength = currentPageLength;
	}

	public PageEntity(int start, int pageSize) {
		super();
		this.start = start;
		this.pageSize = pageSize;
	}

	
}
