<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>redirect</title>
    </head>
    <body>
        <script type="text/javascript">
            top.window.location = "<%=request.getContextPath() %>/";
        </script>
        <div>退出系统...</div>
    </body>
</html>