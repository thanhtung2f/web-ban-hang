
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
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
    <c:choose>
            <c:when test="${empty adminAccount}">
                <c:redirect url="/home"><c:param name="req" value="home"/></c:redirect>
            </c:when>
            <c:when test="${not empty adminAccount}">
                <c:if test="${empty admin}">
                    <c:redirect url="/DashBoard"><c:param name="req" value="dashBoard"/></c:redirect>
                </c:if>
            </c:when>
    </c:choose>
    <div id="container">
        <div id="header">
            <a id="logo-admin" style="text-decoration: none;color: black;" href="<c:url value="/Admin"><c:param name="req" value="listProduct"/></c:url>">
                    Ecommerce Admin
            </a>
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
                    <a href="<c:url value="/DashBoard"><c:param name="req" value="dashBoard"/></c:url>"><li>Dashboard</li></a>
                    <a href="<c:url value="/Admin"><c:param name="req" value="orderList"/></c:url>"><li>Orders</li></a>
                    <a href="<c:url value="/Admin"><c:param name="req" value="listProduct"/></c:url>"><li>Products</li></a>
                    <a href="<c:url value="/Admin"><c:param name="req" value="listCustomer"/></c:url>"><li>Customers</li></a>
                </ul>
            </div>
            <div id="content-right">
                <div class="path-admin">DASHBOARD</b></div>
                <div class="content-main">
                    <div id="content-main-dashboard">
                        <div id="dashboard-1">
                            <div id="dashboard-1-container">
                                <div class="dashboard-item">
                                    <div class="dashboard-item-title">Weekly Sales</div>
                                    <div class="dashboard-item-content">$${weeklySales}</div>
                                </div>
                                <div class="dashboard-item">
                                    <div class="dashboard-item-title">Total Orders</div>
                                    <div class="dashboard-item-content">$${totalTOrders}</div>
                                </div>
                                <div class="dashboard-item">
                                    <div class="dashboard-item-title">Total Customers</div>
                                    <div class="dashboard-item-content">${totalCustomer}</div>
                                </div>
                                <div class="dashboard-item">
                                    <div class="dashboard-item-title">Total Guest</div>
                                    <div class="dashboard-item-content">${totalGuest}</div>
                                </div>
                            </div>
                        </div>
                        <div id="dashboard-2">
                            <div id="chart" style="text-align: center;">
                                <div id="chart1">
                                    <h3>Statistic Orders (Day)</h3>
                                    <canvas id="myChart1" style="width: 100%;"></canvas>
                                </div>
                                <div id="chart2">
                                    <canvas id="myChart2" style="width: 80%;"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="footer-admin">footer</div>
        <c:set var="getlistRevenuePerMonth" value="${listRevenuePerMonth}"/>
    </div>
</body>
</html>
<script>
    function OrdersChart(){
        var xValues = [12,11,10,9,8,7,6,5,4,3,2,1];
        const data = ${getlistRevenuePerMonth};
        
        new Chart("myChart1", {
        type: "line",
        data: {
            labels: xValues,
            datasets: [{
                    
            data: ${getlistRevenuePerMonth},
            borderColor: "sienna",
            fill: true
            }]
        },
        options: {
            legend: {display: false}
        }
        });
    }

    function CustomersChart(){
        var xValues = ["Total", "Customer", "Guest"];
        var yValues = [${totalCustomer} , ${totalCustomer} - ${totalGuest}, ${totalGuest}];
        var barColors = ["green", "red", "grey"];

        new Chart("myChart2", {     
        type: "bar",
        data: {
            labels: xValues,
            datasets: [{
            backgroundColor: barColors,
            data: yValues
            }]
        },
        options: {
            legend: {display: false},
            title: {
            display: true,
            text: "New Customers (30 daily Avg)"
            }
        }
        });
    }
    
    OrdersChart();
    CustomersChart();
    </script>
