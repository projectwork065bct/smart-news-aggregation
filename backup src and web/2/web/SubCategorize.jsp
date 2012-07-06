<%-- 
    Document   : SubCategorize
    Created on : Oct 19, 2011, 9:38:51 AM
    Author     : robik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="header.jsp" />

<h4>The articles have been assigned their respective sub categories. You may want to re check the articles under "Extra News" and "Other Sports" to manually define the sub categories.</h4>
<jsp:useBean class="ArticleCollection.Controller" id="c" />
<% c.automaticallySubCategorizeArticles();%>

<jsp:useBean class="DbInformation.StatisticsProvider" id="s" />
<jsp:useBean class="DbInformation.QueriesForOutput" id="o" />
<%//s.connect();
            out.println(o.getSubCategorizedArticles());
            out.println(s.subCategory());%>
<a href="index.jsp">Return To The Home Page</a

</body>
</html>
