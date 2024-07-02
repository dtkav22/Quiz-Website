<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>OOPS</title>
    <style>
        body {
            font-size: large;
            font-family: 'Lato', sans-serif;
            background-color: dodgerblue;
            display: flex;
            align-items: center; /* center x axis*/
            justify-content: center; /*center y axis*/
        }
        .image-container {
            text-align: center;
            background-color: red;
            padding: 20px;
            width: 50%;
            border-radius: 6px;
            box-shadow: 5px 10px;
        }
        .image-description {
            color: lightblue;
            font-size: 30px;
            margin-bottom: 10px;
            align-items: center;
        }
        .image-container img {
            width: 100%;
            height: auto;
        }
    </style>
</head>
<body>

<div class="image-container">
    <div class="image-description">ERROR 404</div>
    <img src="../../images/miqa.png" alt="ERROR 404" />
</div>

</body>
</html>