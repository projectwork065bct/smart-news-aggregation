<%-- 
    Document   : SubCategorize
    Created on : Oct 19, 2011, 9:38:51 AM
    Author     : robik
--%>

<jsp:include page="header.jsp" />

        <h1>Assign suitable sub category to articles:</h1>
        <a href="SubCategorize.jsp" ><input onclick="return showMessage('Assigning sub categories......')" class="blueButton" type="button"  value="Click on this button to assign sub categories to the articles." /></a><br/><br/><hr/>
        <jsp:useBean class="DbInformation.HTMLConverter" id="Informer" />
        <h6>All the articles collected in the database will be grouped into the following categories and        sub-Categories automatically. You may use <a href="http://localhost/phpmyadmin">phpmyadmin</a> to assign the sub-categories manually.<br/></h6><br/><br/>
        <%  
                out.println("<table>"+Informer.getCategorySubCategoryTable()+"</table>");
        %>
        <br/><br/>
        <hr/>
        <h6>You may use <a href="http://localhost/phpmyadmin">phpmyadmin</a> to assign the sub-categories manually.<br/></h6>
        
        
        <a href="SubCategorize.jsp" ><input onclick="return showMessage('Assigning sub categories......')" type="button" class="blueButton" value="Click on this button to assign sub categories to the articles." /></a><br/><br/>
<a href="index.jsp">Return To The Home Page</a>


<jsp:include page="rightColumn.jsp" />
<jsp:include page="footer.jsp" />