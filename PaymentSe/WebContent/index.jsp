<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="model.Payment"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Details</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/main.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-8">
				<h1 class="m-3">Payment Details</h1>
				<form id="formpayment">

					<!-- NAME -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblName">Name on card:
							</span>
						</div>
						<input type="text" id="NameOnCard" name="NameOnCard">
					</div>

					<!-- CardNo -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblName">card no: </span>
						</div>
						<input type="text" id="CardNo" name="CardNo">
					</div>

					<!-- Card Type -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblName">Card Type: </span>
						</div>
						<input type="text" id="cardType" name="cardType">
					</div>

					<!-- BANK NAME -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblName">Bank: </span>
						</div>
						<input type="text" id="bank" name="bank">
					</div>

					<!-- Total Amount -->
					<div class="input-group input-group-sm mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="lblName">Total Amount:
							</span>
						</div>
						<input type="text" id="totAmount" name="totAmount">
					</div>


					<input type="button" id="btnSave" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidItemIDSave" name="hidItemIDSave" value="">
				</form>
				<br>

				<div id="alertSuccess" class="alert alert-success"></div>

				<div id="alertError" class="alert alert-danger"></div>

				<div class='col-12'>
					<div id="divItemsGrid">
						<%
							Payment payObj = new Payment();
							out.print(payObj.readPayments());
						%>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>