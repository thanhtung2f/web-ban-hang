<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Index</title>
    <link href="css/style.css" rel="stylesheet"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
</head>
<body>
    <div id="container">
        <div id="header">
            <div id="logo-admin">
                Ecommerce Admin
            </div>
            <div id="banner-admin">
                <ul>
                   <li><a href="<c:url value="/home"><c:param name="req" value="home"/></c:url>">Home</a></li>
                   <li><a href="<c:url value="/home"><c:param name="req" value="remove"/></c:url>">SignOut</a></li>
                </ul>
            </div>
        </div>
        <div id="content">
            <div id="content-left">
                <ul>
                    <a href="<c:url value="/Admin"><c:param name="req" value="dashBoard"/></c:url>"><li>Dashboard</li></a>
                    <a href="<c:url value="/Admin"><c:param name="req" value="orderDetail"/></c:url>"><li>Orders</li></a>
                    <a href="<c:url value="/Admin"><c:param name="req" value="listProduct"/></c:url>"><li>Products</li></a>
                    <a href="<c:url value="/Admin"><c:param name="req" value="listCustomer"/></c:url>"><li>Customers</li></a>
                </ul>
            </div>
            <div id="content-right">
            <c:if test="${not empty checkCreate}">
                <div class="path-admin">CREATE A NEW PRODUCT</b></div>
                <div class="content-main">
                    <form id="content-main-product" action="<c:url value="/adminCRUD"><c:param name="req" value="Create"/></c:url>" method="post">
                        <div class="content-main-1">
                            <label>Product name (*):</label><br/>
                            <input type="text" name="txtProductName" id=""><br/>
                            <c:if test="${not empty productNameMsg}"><span class="msg-error">${productNameMsg}</span><br/></c:if>
                            <label>Unit price:</label><br/>
                            <input type="text" name="txtUnitPrice" id=""><br/>
                            <c:if test="${not empty unitPriceMsg}"><span class="msg-error">${unitPriceMsg}</span><br/></c:if>
                            <label>Quantity per unit:</label><br/>
                            <input type="text" name="txtQuantityPerUnit" id=""><br/>
                            <label>Units in stock (*):</label><br/>
                            <input type="text" name="txtUnitsInStock" id=""><br/>
                            <c:if test="${not empty unitsInStockMsg}"><span class="msg-error">${unitsInStockMsg}</span><br/></c:if>
                        </div>
                        <div class="content-main-1">
                            <label>Category (*):</label><br/>
                            <select name="ddlCategory">
                                <c:forEach items="${category}" var="c">
                                    <option value="${c.categoryID}">${c.categoryName}</option>
                                </c:forEach>
                            </select>
                            <br/>
                            <label>Reorder level:</label><br/>
                            <input type="text" name="txtReorderLevel" id="" disabled><br/>
                            <label>Units on order:</label><br/>
                            <input type="text" name="txtUnitsOnOrder" id="" disabled><br/>
                            <label>Discontinued:</label><br/>
                            <input type="checkbox" name="checkDiscontinued" id=""><br/>
                            <input type="submit" value="Save"/>
                            <input type="submit" value="Back" name="txtBack"/>
                        </div>
                    </form>
                </div>
            </c:if>
            <c:if test="${not empty checkEdit}">
                <div class="path-admin">CREATE A NEW PRODUCT</b></div>
                <div class="content-main">
                    <form id="content-main-product" action="<c:url value="/adminCRUD"><c:param name="req" value="Edit"/></c:url>" method="post">
                        <div class="content-main-1">
                        <c:set var="product" value="${ProductCategory.getProduct()}"/>
                        <c:set var="cate" value="${ProductCategory.getCategory()}"/>
                            <label>Product ID:</label><br/>
                            <input type="text" name="txtProductID" id="" readonly value="${product.productID}"><br/>
                            <label>Product name:</label><br/>
                            <input type="text" name="txtProductName" id="" value="${product.productName}"><br/>
                            <c:if test="${not empty productNameMsg}"><span class="msg-error">${productNameMsg}</span><br/></c:if>
                            <label>Unit price:</label><br/>
                            <input type="text" name="txtUnitPrice" id="" value="${product.unitPrice}"><br/>
                            <c:if test="${not empty unitPriceMsg}"><span class="msg-error">${unitPriceMsg}</span><br/></c:if>
                            <label>Quantity per unit:</label><br/>
                            <input type="text" name="txtQuantityPerUnit" id="" value="${product.quantityPerUnit}"><br/>
                            <label>Units in stock:</label><br/>
                            <input type="text" name="txtUnitsInStock" id="" value="${product.unitsInStock}"><br/>
                            <c:if test="${not empty unitsInStockMsg}"><span class="msg-error">${unitsInStockMsg}</span><br/></c:if>
                        </div>
                        <div class="content-main-1">
                            <label>Category:</label><br/>
                            <select name="ddlCategory">
                                <c:forEach items="${category}" var="c">
                                    <c:if test="${c.categoryName == cate.categoryName}">
                                        <option value="${c.categoryID}" selected>${c.categoryName}</option>
                                    </c:if>
                                    <c:if test="${c.categoryName != cate.categoryName}">
                                        <option value="${c.categoryID}">${c.categoryName}</option>
                                    </c:if>     
                                </c:forEach>
                            </select>
                            <br/>
                            <label>Reorder level:</label><br/>
                            <input type="text" name="txtReorderLevel" id="" disabled><br/>
                            <label>Units on order:</label><br/>
                            <input type="text" name="txtUnitsOnOrder" id="" disabled><br/>
                            <label>Discontinued:</label><br/>
                            <c:if test="${product.discontinued == true}">
                                <input type="checkbox" name="checkDiscontinued" id="" checked><br/>
                            </c:if>
                            <c:if test="${product.discontinued == false}">
                                <input type="checkbox" name="checkDiscontinued" id=""><br/>
                            </c:if>
                            <input type="submit" value="Update"/>
                            <input type="submit" value="Back" name="txtBack"/>
                        </div>
                    </form>
                </div>
            </c:if>
                        
            </div>
        </div>
        <div id="footer-admin">footer</div>
    </div>
</body>
</html>