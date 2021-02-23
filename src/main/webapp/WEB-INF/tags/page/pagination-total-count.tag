<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>


<p class="top-desc"><em id="total_count">${empty pagination.totalItems ? '0' : pagination.totalItems}개</em>건</p>