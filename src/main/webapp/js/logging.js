/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var logRootUrl = "http://localhost:8080/soen487-w18-team07/webresources/logging";
var displayAll = true;

function loadLogs() { 
    customerRefs = [];
        $.ajax
            ({
                type: 'GET',                        //Request Data Type
                contentType: 'application/json',    //Request Content Type
                url: logRootUrl,                           //RESTful Service URL
                dataType: "json",                   //Response Data Type
                success: function(data)
                {   
                    displayLogs(data);
                }
            });
}

function displayLogs(data){
    var logRows = document.getElementById("logTableBody");
    while (logRows.firstChild) {
        logRows.removeChild(logRows.firstChild);
    }
    for (var i = 0; i < data.length; i++) {
        var row = document.createElement("tr");
        
        var tableDoc = document.createElement("td");
        tableDoc.textContent = data[i].logClass;
        customerRefs.push(data[i].customerRef);
        row.appendChild(tableDoc);
        
        tableDoc = document.createElement("td");
        tableDoc.textContent = data[i].logLevel;
        row.appendChild(tableDoc);
        
        tableDoc = document.createElement("td");
        tableDoc.textContent = data[i].logMsg;
        row.appendChild(tableDoc);
        
        tableDoc = document.createElement("td");
        tableDoc.textContent = new Date(data[i].log_timestamp);
        row.appendChild(tableDoc);
        
        logRows.appendChild(row);
    }
    
}

function displayIntevalLogs(){
    var lower_limit = document.getElementById("logFrom").value;
    var upper_limit = document.getElementById("logTo").value;
    
    if(lower_limit === "" || upper_limit===""){
        alert("Date and time cannot be empty.");
        return false;
    }
    
    lower_limit = new Date(lower_limit).getTime();
    upper_limit = new Date(upper_limit).getTime();
    
     $.ajax
            ({
                type: 'GET',                        //Request Data Type
                contentType: 'application/json',    //Request Content Type
                url: logRootUrl + '/inteval?'
                        +'lower_limit='+ lower_limit +'&'
                        +'upper_limit='+ upper_limit,    //RESTful Service URL
                dataType: "json",                   //Response Data Type
                success: function(data, textStatus, jqXHR){
                    displayLogs(data);
                },
                error: function(jqXHR, textStatus, errorThrown){
                    alert('error: ' + textStatus);
                }
            });
}
