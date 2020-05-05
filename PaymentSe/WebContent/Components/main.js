$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

$(document).on("click", "#btnSave", function(event) {

	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "paymentAPI",
		type : type,
		data : $("#formpayment").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onItemSaveComplete(response.responseText, status);
		}
	});
});

function onItemSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();

			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidItemIDSave").val("");
	$("#formpayment")[0].reset();
}

// UPDATE==========================================
$(document).on("click",".btnUpdate",function(event) {
	$("#hidItemIDSave").val($(this).closest("tr").find('#hidItemIDUpdate').val());
	$("#NameOnCard").val($(this).closest("tr").find('td:eq(0)').text());
	$("#CardNo").val($(this).closest("tr").find('td:eq(1)').text());
	$("#cardType").val($(this).closest("tr").find('td:eq(2)').text());
    $("#bank").val($(this).closest("tr").find('td:eq(3)').text());
	$("#totAmount").val($(this).closest("tr").find('td:eq(4)').text());
	 
});

// Remove................................
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "paymentAPI",
		type : "DELETE",
		data : "p_id=" + $(this).data("p_id"),
		dataType : "text",
		complete : function(response, status) {
			onItemDeleteComplete(response.responseText, status);
		}
	});
});

function onItemDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();

			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENT-MODEL================================================================

// CODE
function validateItemForm() {

	// NAME
	if ($("#NameOnCard").val().trim() == "") {
		return "Insert the Name.";
	}

	// Card-------------------------------
	if ($("#CardNo").val().trim() == "") {
		return "Insert Card No.";
	}

	// is numerical value
	var tmpPrice = $("#CardNo").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for card number.";
	}

	// Type
	if ($("#cardType").val().trim() == "") {
		return "Insert the card type.";
	}

	// bank
	if ($("#bank").val().trim() == "") {
		return "Insert the bank.";
	}
	// Amount-------------------------------
	if ($("#totAmount").val().trim() == "") {
		return "Insert Total Amount.";
	}

	// is numerical value
	var totAmount = $("#totAmount").val().trim();
	if (!$.isNumeric(totAmount)) {
		return "Insert a numerical value for Amount.";
	}

	// convert to decimal price
	$("#totAmount").val(parseFloat(totAmount).toFixed(2));

	return true;
}