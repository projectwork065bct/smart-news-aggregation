<%-- 
    Document   : Statistics
    Created on : Oct 22, 2011, 11:08:36 AM
    Author     : ramsharan
--%>

<jsp:include page="header.jsp" />
<jsp:useBean class="DbInformation.StatisticsProvider" id="statistics"/>
<h5>The following is the statistical report.</h5>
<%
    //statistics.connect();
    out.println("<table><tr><td><h6>Date:<td><h2> " + statistics.getDate() + "</h2></tr>");
    out.println("<tr><td><h6>The number of articles collected today is: <td><h2><center>" + statistics.numberOfArticles() + "</center></h2></tr>");
    out.println(statistics.category());
    out.println(statistics.subCategory());
    out.println(statistics.statusOfChannel());
    out.println(statistics.channelAndArticle());
%>


<jsp:include page="rightColumn.jsp" />
<jsp:include page="footer.jsp" />