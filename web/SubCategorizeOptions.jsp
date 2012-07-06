<%-- 
    Document   : SubCategorize
    Created on : Oct 19, 2011, 9:38:51 AM
    Author     : robik
--%>

<jsp:include page="header.jsp" />
<jsp:include page="leftMenu.jsp" />
<div class="right_content" />

        
        <a href="SubCategorize.jsp" ><input class="NFButton" onclick="return showMessage('Assigning sub categories......')" class="our_button" type="button"  value="CLICK HERE TO ASSIGN SUB-CATEGORIES TO THE NEWS ARTICLES" /></a><br/><br/>
        <jsp:useBean class="DbInformation.HTMLConverter" id="Informer" />
        <h4>All the articles collected in the database will be grouped into the following categories and        sub-Categories automatically. You may use <a href="http://localhost/phpmyadmin">phpmyadmin</a> to assign the sub-categories manually.</h4><br/><br/><br/>
        <%  
                out.println("<table>"+Informer.getCategorySubCategoryTable()+"</table>");
        %>
        <br/><br/>
        You may use <a href="http://localhost/phpmyadmin">phpmyadmin</a> to assign the sub-categories manually.<br/></h4>
        
        <a href="SubCategorize.jsp" ><input class="NFButton" onclick="return showMessage('Assigning sub categories......')" type="button" class="our_button" value="Click on this button to assign sub categories to the articles." /></a><br/><br/>
<a href="index.jsp">Return To The Home Page</a>


<jsp:include page="footer.jsp" />