$( document ).ready(function() {
	
	$(document).on('click', '#getCategoryResultDiv .list-group-item', function(event){
	   
	    var $this = $(this);
	    var iCategoryTitle=$(this).html();
	   
	 $('.active').removeClass('active');
	  $(this).addClass('active');
	    $("#getAllItemsId").addClass('active');
	    $("#getAllItemsId").data("categoryTitle",$.trim(iCategoryTitle) );
	    $("#getAllItemsId").data("categoryId",$(this).val() );
	   	  $("#addItemId").addClass('active');
	});
	

	
	// GET REQUEST
	$("#getAllCategoryId").click(function(event){
		 $('.active').removeClass('active');
		 $(this).addClass('active');
		 $("#addCategoryId").addClass('active');
		 $("#getAllItemsId").removeData();
		 clearData();
		event.preventDefault();
		ajaxGetCategory();
	});
	
	$("#getAllItemsId").click(function(event){
		event.preventDefault();
		clearData();
		var categoryTitle=$.trim($("#getAllItemsId").data("categoryTitle"));
		if (categoryTitle != '')
			{   
				ajaxGetItem();
			}
		else {
			
			alert ("Please a Select a Category");
		}
		
	});
	
	$("#addCategoryId").click(function(event){
		event.preventDefault();
		clearData();
		$('#addCategoryForm').show();
		
	});
	
	$("#addItemId").click(function(event){
		event.preventDefault();
		clearData();
		var categoryTitle=$.trim($("#getAllItemsId").data("categoryTitle"));
		if (categoryTitle != '')
		{   
			var catTitle="<p id='catTitle'class='label label-default' >Category : "+categoryTitle+" </p> ";
			$("#categoryHead").append(catTitle);
			$('#addItemForm').show();
			
		}
	else {
		
		alert ("Please a Select a Category");
	}
		
		
	});
	// DO GET
	function ajaxGetCategory(){
	var apiurl=	gethomeurl(window.location.href) + "shopping/getcategory";
		$.ajax({
			type : "GET",
			url : apiurl,
			 result: "{}",
             contentType: "application/json; charset=utf-8",
             dataType: "json",
			success: function(result){
				if(result.status == "Done"){
					$('#getCategoryResultDiv ul').empty();
					var custList = "";
					$.each(result.data, function(i, category){
						var categoryTitle = category.categoryTitle;
						var categoryId=category.categoryId;
					$('#getCategoryResultDiv .list-group').append("<button type='button' class='list-group-item' value="+
							categoryId+ ">" +categoryTitle+ " </button>");
			        });
					$('#categoryList').show();
					console.log("Success: ", result);
				}else{
					$("#getCategoryResultDiv").html("<strong>No Output</strong>");
					console.log("Fail: ", result);
				}
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});	
	}	
	
	function ajaxGetItem(){
		var apiurl=	gethomeurl(window.location.href) + "shopping/getitem";
		var categorytitle=$("#getAllItemsId").data("categoryTitle");
		$('#categoryList').hide();	
		
			$.ajax({
				type : "GET",
				url : apiurl,
				data : {categoryTitle:categorytitle},
				 result: "{}",
	             contentType: "application/json; charset=utf-8",
	             dataType: "json",
				success: function(result){
					if(result.status == "Done"){
						$('#getCategoryResultDiv ul').empty();
						//var custList = "";
						$.each(result.data, function(i, item){
							var itemTitle = item.itemTitle;
				            var itemText = item.text;
				            var itemPrice = item.price;
				           
				            var tableRow = "<tr><td>"+ categorytitle+ "</td><td>" + itemTitle + "</td><td>"+ itemText + 
				            "</td><td>"+itemPrice +"</td></tr>";

				            $("#iTemTable > tbody").append(tableRow);	
						  });
						$("#iTemTable").show();
						console.log("Success: ", result);
					}else{
						var failure="<p id='failMsg'><strong>No Item exists for the current Category</strong></p> ";
						$("#getCategoryResultDiv").append(failure);						
						console.log("Fail: ", result);
					}
				},
				error : function(e) {
					alert("Error!")
					console.log("ERROR: ", e);
				}
			});	
		}
	
	function gethomeurl(url){
		return url.split('index')[0];
	}
	
	
	})
	
	function gethomeurl(url){
		return url.split('index')[0];
	}
	function clearData(){
		$("#successMsg").remove(); 
		$("#catTitle").remove();		
		$("#failMsg").remove(); 
		$('#iTemTable tbody').empty();
		 $("#iTemTable").hide();
		 $('#catTitle').hide();
		 $('#getCategoryResultDiv ul').empty();
		 $('#categoryList').hide();
		 $('#addCategoryForm').hide();
		 $('#addItemForm').hide();	
		 
		
	}