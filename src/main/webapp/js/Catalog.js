//Root Url

//Returns a table with the customers from the same state
function loadProducts() { 
    var url = "http://localhost:8080/soen487-w18-team07/webresources/retailer";
        $.ajax
            ({
                type: 'GET',                        //Request Data Type
                contentType: 'application/json',    //Request Content Type
                url: url,                           //RESTful Service URL
                dataType: "json",                   //Response Data Type
                success: function(data)
                {   
                    catalogProducts(data);
                }
            }); 
}

 // Rows for Catalog table
function catalogProducts(data){
    var productRows = document.getElementById("productTableBody");
    while (productRows.firstChild) {
            productRows.removeChild(productRows.firstChild);
        }
    for (var i = 0; i < data.length; i++) {
        var row = document.createElement("tr");
        
        var tableDoc = document.createElement("td");
        tableDoc.textContent = data[i].productId;
        row.appendChild(tableDoc);
        
        tableDoc = document.createElement("td");
        tableDoc.textContent = data[i].warehouseId;
        row.appendChild(tableDoc);
        
        tableDoc = document.createElement("td");
        tableDoc.textContent = data[i].manufacturerName;
        row.appendChild(tableDoc);
        
        tableDoc = document.createElement("td");
        tableDoc.textContent = data[i].productType;
        row.appendChild(tableDoc);
        
        tableDoc = document.createElement("td");
        tableDoc.textContent = data[i].unitPrice;
        row.appendChild(tableDoc); 
        
        tableDoc = document.createElement("td");
        tableDoc.textContent = data[i].quantity;
        row.appendChild(tableDoc);
        
        productRows.appendChild(row);
    } 
    orderForm(data);
}

function shuffle(data) {
  var currentIndex = data.length, temporaryValue, randomIndex ;

  // While there remain elements to shuffle...
  while (0 !== currentIndex) {

    // Pick a remaining element...
    randomIndex = Math.floor(Math.random() * currentIndex);
    currentIndex -= 1;

    // And swap it with the current element.
    temporaryValue = data[currentIndex];
    data[currentIndex] = data[randomIndex];
    data[randomIndex] = temporaryValue;
  }

  // collect unique products
  var uniqueProductCollection = [];
    for (var i =0; i < data.length; i++) {
        if (uniqueProductCollection === null) {
            uniqueProductCollection.push(data[i]);
        }
        else {
            var inArray = false;
            for (var j = 0; j < uniqueProductCollection.length; j++) {
                if (uniqueProductCollection[j].productType === data[i].productType ) {
                    inArray = true;
                    break;
                }
            }
            if (inArray === false) {
                uniqueProductCollection.push(data[i]);
            }
        }
    }
  return uniqueProductCollection;
}

// order form 
function orderForm(data) {

    var productData = shuffle(data);   
    var orderForm = document.getElementById("orderForm");
    
    // creating product fields
    for (var i = 0; i < productData.length; i++) {
        var product = "product_" + i;
        var productType = document.getElementById(product);

        while (productType.firstChild) {
            productType.removeChild(productType.firstChild);
        }
        
        var productLabel = document.createElement("label");
        productLabel.setAttribute("class","w3-text-brown");
        
        var b = document.createElement("b");
        b.textContent = productData[i].productType;
        
        productLabel.appendChild(b);
        
        var productInputField = document.createElement('input');
        productInputField.setAttribute("type", "number");
        productInputField.setAttribute("id", "products");
        productInputField.setAttribute("min", "0");
        productInputField.setAttribute("max", "100");
        productInputField.setAttribute('name', 'product-' + productData[i].productId);
        productInputField.setAttribute('class', "w3-input w3-border w3-sand");
        productInputField.setAttribute('placeholder', "Qty");
        
        productType.appendChild(productLabel);
        productType.appendChild(productInputField);
    }

    
    // Create Random Customer IDs
    var customerReferenceNumber = Math.floor(Math.random() * 100000);
    // customer id
    var customerId = document.createElement('input');
    customerId.setAttribute("type", "text");
    customerId.setAttribute("name", "customerReferenceNumber");
    customerId.setAttribute("value", customerReferenceNumber);
    customerId.setAttribute("hidden", "hidden");
    orderForm.appendChild(customerId);
    
    $('*[id*=products]').each(function() {
        $(this).addClass("prod");
    });
       
    $("#orderForm").submit(function (e) {
        var atleastOneInput = false;
        $('*[id*=products]').each(function() {
            if ($(this).val()) {
                if (parseInt($(this).val()) < 0 || parseInt($(this).val()) > 100) {
                    alert('Please enter a valid quantity amount');
                    e.preventDefault();
                    return false;
                }
                else {
                    atleastOneInput = true;
                }
            }
        });
        if (!atleastOneInput) {
            alert('Please enter product quantity');
            e.preventDefault();
            return false;
        }
    }); 
}