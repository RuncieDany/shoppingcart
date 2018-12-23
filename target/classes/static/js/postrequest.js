$( document ).ready(function() {
	
	// SUBMIT FORM
    $("#addCategoryForm").submit(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		ajaxPostCategory();
		$( '#addCategoryForm' ).each(function(){
		    this.reset();
		});
	});
    
    $("#addItemForm").submit(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		if ($.trim($("#getAllItemsId").data("categoryTitle")) != '')
		{
			ajaxPostItem();
			$( '#addItemForm' ).each(function(){
			    this.reset();
			});
		}
	else {
		
		alert ("Please a Select a Category");
	}
		
		
	});
    
    
    function ajaxPostCategory(){
    	
    	clearData();
    	
    	// PREPARE FORM DATA
    	var categoryInput = { 	categoryTitle : $.trim($("#categoryTitleInput").val())
    		
    	};  	
    	
    	
    	var apiurl=	gethomeurl(window.location.href) + "shopping/createcategory";
    	
    	// DO POST
    	$.ajax({
			type : "POST",
			contentType : "application/json",
			url : apiurl,
			result: "{}",
			data : JSON.stringify(categoryInput),
			dataType : 'json',
			success : function(result) {
				if(result.status == "Category was successfully inserted"){	
					
					var success="<p id='successMsg'><strong>Category is created </strong></p> ";
					$("#getCategoryResultDiv").append(success);
					
				}else{
					
					var failure="<p id='failMsg'><strong>Failed to create a Category</strong></p> ";
					$("#getCategoryResultDiv").append(failure);
				}
				console.log(result);
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
    	});
		}
    	
    	
    	function ajaxPostItem(){
        	
        	clearData();
        	
        	// PREPARE FORM DATA
        	var itemInput = { 	itemTitle : $.trim($("#itemTitleInput").val()),
        			text: $.trim($("#itemTextInput").val()),
        			price : $.trim($("#itemPriceInput").val()),
        			categoryId	:$("#getAllItemsId").data("categoryId")
        	       		
        	};  	
        	
        	
        	var apiurl=	gethomeurl(window.location.href) + "shopping/createItem";
        	console.log (apiurl);
        	console.log (itemInput);
        	// DO POST
        	$.ajax({
    			type : "POST",
    			contentType : "application/json",
    			url : apiurl,
    			result: "{}",
    			data : JSON.stringify(itemInput),
    			dataType : 'json',
    			success : function(result) {
    				if(result.status == "Item was successfully inserted"){	
    					
    					var success="<p id='successMsg'><strong>Item is created </strong></p> ";
    					$("#getCategoryResultDiv").append(success);
    					
    				}else{
    					
    					var failure="<p id='failMsg'><strong>Failed to create an Item</strong></p> ";
    					$("#getCategoryResultDiv").append(failure);
    				}
    				console.log(result);
    			},
    			error : function(e) {
    				alert("Error!")
    				console.log("ERROR: ", e);
    			}
    		});    
    	}
})