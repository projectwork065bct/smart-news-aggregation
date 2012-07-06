<%-- 
    Document   : SubCategorize
    Created on : Oct 19, 2011, 9:38:51 AM
    Author     : robik
--%>

<jsp:include page="header.jsp" />
<jsp:include page="leftMenu.jsp" />
<div class="right_content" />

<h4>The articles have been assigned their respective sub categories. You may want to re check the articles under "Extra News" and "Other Sports" to manually define the sub categories.</h4>
<jsp:useBean class="ArticleCollection.Controller" id="c" />
<br/><br/>
<% c.automaticallySubCategorizeArticles();%>

<jsp:useBean class="DbInformation.StatisticsProvider" id="s" />
<jsp:useBean class="DbInformation.QueriesForOutput" id="o" />
<%//s.connect();
    out.println(s.subCategory());

    out.println(o.getSubCategorizedArticles());
%>
<jsp:include page="footer.jsp" />