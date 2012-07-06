<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
        <title>Smart News Aggregation</title>
        <link rel="stylesheet" href="css/960.css" type="text/css" media="screen" charset="utf-8" />
        <link rel="stylesheet" href="css/template.css" type="text/css" media="screen" charset="utf-8" />
        <link rel="stylesheet" href="css/colour.css" type="text/css" media="screen" charset="utf-8" />
        <script type="text/javascript">
            
            function showMessage(message)
            {
                var b=document.getElementById("loading");
                b.innerHTML="<center>"+message+"</center>";
                return true;
            }
        </script>
    </head>
    <body>

        <h1 id="head">Smart News Aggregation</h1>

        <ul id="navigation">
            <li><a href="index.jsp">Home</a></li>
            <li><a href="CollectArticlesOptions.jsp">Collect Articles</a></li>

            <li><a href="SubCategorizeOptions.jsp">Assign Sub Category</a></li>

            <li><a href="IndexArticlesOptions.jsp">Index Articles</a></li>

            <li><a href="RelateArticlesOptions.jsp">Group Articles</a></li>

            <li><a href="IndexKeywords.jsp">Index Keywords</a></li>

            <li><a href="Statistics.jsp">Statistics</a></li>

            <li><a href="AboutUs.jsp">About Us</a></li>

            <li><a href="Help.jsp">Help</a></li>
        </ul>

        <div id="loading">
        </div>
        <div id="content" class="container_16 clearfix">
            <div class="grid_11">

