<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>

<input type="hidden" id="itemsPerPage" name="itemsPerPage" value="${pagination.itemsPerPage eq null ? '10' : pagination.itemsPerPage}"/>
<input type="hidden" id="currentPage" name="currentPage" value="${pagination.currentPage eq null ? '1' : pagination.currentPage}"/>