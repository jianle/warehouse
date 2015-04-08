package com.wms.model;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class Pagination<T> extends JdbcDaoSupport {

    public static final int NUMBERS_PER_PAGE = 10;
    // 一页显示的记录数
    private int numPerPage;
    // 记录总数
    private int totalRows;
    // 总页数
    private int totalPages;
    // 当前页码
    private int currentPage;
    // 起始行数
    private int startIndex;
    // 结束行数
    private int lastIndex;
    // 结果集存放List
    private List<T> resultList;


    /**
     * 分页构造函数
     * 
     * @param sql
     *            根据传入的sql语句得到一些基本分页信息
     * @param currentPage
     *            当前页
     * @param numPerPage
     *            每页记录数
     */
    
    public Pagination (int totalRows, int currentPage) {
        // 设置每页显示记录数
        this.setNumPerPage(NUMBERS_PER_PAGE);
        // 设置要显示的页数
        this.setCurrentPage(currentPage);
        // 总记录数
        this.setTotalRows(totalRows);
        // 计算总页数
        this.setTotalPages();
        // 计算起始行数
        this.setStartIndex();
        // 计算结束行数
        this.setLastIndex();
    }

    /**
     * 构造MySQL数据分页SQL
     * 
     * @param queryString
     * @param startIndex
     * @param pageSize
     * @return
     */
    public String getMySQLPageSQL(String queryString, int currentPage) {
        String result = "";
        if (currentPage > 0) {
            result = queryString + " limit " + (currentPage - 1) * numPerPage + "," + NUMBERS_PER_PAGE;
        } else {
            result = queryString;
        }
        return result;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNumPerPage() {
        return this.numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public List<T> getResultList() {
        return this.resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    // 计算总页数
    public void setTotalPages() {
        if (this.totalRows % this.numPerPage == 0) {
            this.totalPages = this.totalRows / this.numPerPage;
        } else {
            this.totalPages = (this.totalRows / this.numPerPage) + 1;
        }
    }

    public int getTotalRows() {
        return this.totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public void setStartIndex() {
        this.startIndex = (currentPage - 1) * numPerPage;
    }

    public int getLastIndex() {
        return this.lastIndex;
    }

    // 计算结束时候的索引
    public void setLastIndex() {
        if (totalRows < numPerPage) {
            this.lastIndex = totalRows;
        } else if ((totalRows % numPerPage == 0)
                || (totalRows % numPerPage != 0 && currentPage < totalPages)) {
            this.lastIndex = currentPage * numPerPage;
        } else if (totalRows % numPerPage != 0 && currentPage == totalPages) {// 最后一页
            this.lastIndex = totalRows;
        }
    }

    @Override
    public String toString() {
        return "Pagination [numPerPage=" + numPerPage + ", totalRows="
                + totalRows + ", totalPages=" + totalPages + ", currentPage="
                + currentPage + ", startIndex=" + startIndex + ", lastIndex="
                + lastIndex + "]";
    }

}