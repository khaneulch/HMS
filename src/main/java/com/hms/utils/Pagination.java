package com.hms.utils;

import java.io.Serializable;
import java.util.Map;

/**
 * 페이징 
 */
public class Pagination implements Serializable {
	private static final long serialVersionUID = 2668835103426978778L;
	private int totalItems = 0;
	private int itemsPerPage = 15;
	private int currentPage = 1;
	private int totalPages;
	private int endRownum;
	private int startRownum;
	
	public Pagination(Map<String, Object> param, int totalItems) {
		itemsPerPage = Integer.valueOf(param.getOrDefault("itemsPerPage", "10") + "");
		currentPage = Integer.valueOf(param.getOrDefault("currentPage", "1") + "");
		this.totalItems = totalItems;
		calculateRownum();
	}

	public static Pagination getInstance(Map<String, Object> param, int totalItems) {
		return new Pagination(param, totalItems);
	}
	
	public int getTotalItems() {
		return totalItems;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPages() {
		return totalPages = (int) Math.ceil(totalItems / (itemsPerPage * 1.0));
	}

	public int getFirstPage() {
		return currentPage == 1 ? 0 : 1;
	}

	public int getLastPage() {
		return currentPage >= totalPages ? 0 : totalPages;
	}

	public int getPreviousPage() {
		return currentPage > 1 ? currentPage - 1 : 0;
	}

	public int getNextPage() {
		return currentPage < totalPages ? currentPage + 1 : 0;
	}

	public int getEndRownum() {
		return endRownum;
	}

	public void setEndRownum(int endRownum) {
		this.endRownum = endRownum;
	}

	public int getStartRownum() {
		return startRownum;
	}

	public void setStartRownum(int startRownum) {
		this.startRownum = startRownum;
	}
	
	private void calculateRownum() {
		startRownum = ((currentPage - 1) * itemsPerPage)+1;
		endRownum = itemsPerPage * currentPage;
	}
}