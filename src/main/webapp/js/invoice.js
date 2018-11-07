/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Root Url

//Returns a table with the customers from the same state

var customerRefs = [];
var invoiceRootUrl = "http://localhost:8080/soen487-w18-team07/webresources/invoice";


function loadInvoices() { 
    $.ajax
        ({
            type: 'GET',                        //Request Data Type
            contentType: 'application/json',    //Request Content Type
            url: invoiceRootUrl,                           //RESTful Service URL
            dataType: "json",                   //Response Data Type
            success: function(data)
            {   
                displayInvoices(data);
            }
        });
}

function displayInvoices(data){
    var invoiceRows = document.getElementById("invoiceTableBody");
    while (invoiceRows.firstChild) {
        invoiceRows.removeChild(invoiceRows.firstChild);
    }
    
    customerRefs = [];
    
    for (var i = 0; i < data.length; i++) {
        var row = document.createElement("tr");
        
        var tableDoc = document.createElement("td");
        tableDoc.textContent = data[i].customerRef;
        customerRefs.push(data[i].customerRef);
        row.appendChild(tableDoc);
        
        tableDoc = document.createElement("td");
        tableDoc.textContent = data[i].amount_due;
        row.appendChild(tableDoc);
        
        tableDoc = document.createElement("td");
        tableDoc.textContent = data[i].payment_status;
        row.appendChild(tableDoc);
        
        tableDoc = document.createElement("td");
        tableDoc.textContent = new Date(data[i].last_update);
        row.appendChild(tableDoc);
        
        invoiceRows.appendChild(row);
    }
    
}

function pay(){
    var customerRef = document.getElementById("customerRef").value;
    var payment_amt = document.getElementById("amount").value;
    if(!validateCusRef(customerRef)){
        alert("Please enter a valid customer reference");
        return false;
    }
        $.ajax
            ({
                type: 'POST',                        //Request Data Type
                contentType: 'application/json',    //Request Content Type
                url: invoiceRootUrl + '/pay?'
                        +'customerRef='+ customerRef +'&'
                        +'payment='+ payment_amt,    //RESTful Service URL
                dataType: "json",                   //Response Data Type
                success: function(data, textStatus, jqXHR){
                    alert('Payment received.');
                    location.reload();
                },
                error: function(jqXHR, textStatus, errorThrown){
                    alert('error: ' + textStatus);
                }
            });
}

function validateCusRef(data){
    for(i=0; i<customerRefs.length; i++){
        if(String(data) === String(customerRefs[i])){
            return true;
        }
    }
    return false;
}