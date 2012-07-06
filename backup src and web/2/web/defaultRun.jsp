<%-- 
    Document   : defaultRun
    Created on : Oct 23, 2011, 1:40:16 PM
    Author     : robik
--%>
<jsp:include page="header.jsp" />
<jsp:useBean class="ArticleCollection.Controller" id="c" />
<jsp:useBean class="DbInformation.StatisticsProvider"  id="s" />
<%
    c.automaticallyInsertArticles();
    //s.connect();
    out.println(s.linkAndError());
    
    c.automaticallySubCategorizeArticles();
    out.println(s.subCategory());
    
    int days=1;
    c.automaticIndexing(days);
    
    c.automaticallyFindBM25Score(days);
    
%>


<jsp:include page="rightColumn.jsp" />
<jsp:include page="footer.jsp" />