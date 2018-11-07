/*
 * @Marco
 * @Mohammed
 */

//Root Url
var url = "http://localhost:8080/Customer_Restful/webresources/entities.customer/state/";
//Returns a table with the customers from the same state
function stateSearch(){        
    var state = document.getElementById("state").value;
    console.log(state);
        if(state !== null){            
            $.ajax
                ({
                    type: 'GET',                        //Request Data Type
                    contentType: 'application/json',    //Request Content Type
                    url: url + state,          //RESTful Service URL
                    dataType: "json",                   //Response Data Type

                    success: function(data)
                    {
                        tableHeader();
                        outputCustRowsHtml(data);
                        document.write("</table>");
                        document.write("<br/><a href='search.html'>Search Criteria</a>");
                        document.write("<br/><a href='test.html'>Show All Customers</a>");
                    }
                });
    }   
}//end of state search


//Headers for Customer table
function tableHeader(){
    document.writeln("<h2>Javascript client for CustomerDB Restful Web Service</h2><br>");
    document.writeln("<h4>Resources for CustoermDBRest:</h4>");
    document.writeln("<h5>Customers</h5>");
    document.writeln("<table style='border: 1px solid black;'>");
    document.writeln("<tr>");
    document.write("<th style='border: 1px solid black;'>custID</th>");
    document.write("<th style='border: 1px solid black;'>dis-code</th>");
    document.write("<th style='border: 1px solid black;'>name</th>");
    document.write("<th style='border: 1px solid black;'>address</th>");
    document.write("<th style='border: 1px solid black;'>city</th>");
    document.write("<th style='border: 1px solid black;'>state</th>");
    document.write("<th style='border: 1px solid black;'>zip</th>");
    document.write("<th style='border: 1px solid black;'>phone</th>");
    document.write("<th style='border: 1px solid black;'>fax</th>");
    document.write("<th style='border: 1px solid black;'>email</th>");
    document.write("</tr>");
 }//end function tableHeader


//Helper function to go throught all the rows in the customer table and output it to the browser
function outputCustRowsHtml(data){
    console.log(data)
    for(var i = 0; i < data.length; i++){
        document.write("<tr>");
        document.write("<td style='border: 1px solid black;'>" + data[i].customerId + "</td>");
        document.write("<td style='border: 1px solid black;'>" + data[i].discountCode.discountCode +"</td>");
        document.write("<td style='border: 1px solid black;'>" + data[i].name + "</td>");
        document.write("<td style='border: 1px solid black;'>" + data[i].addressline1 + " " + data[i].addressline2 + "</td>");
        document.write("<td style='border: 1px solid black;'>" + data[i].city + "</td>");
        document.write("<td style='border: 1px solid black;'>" + data[i].state + "</td>");
        document.write("<td style='border: 1px solid black;'>" + data[i].zip.zipCode + "</td>");
        document.write("<td style='border: 1px solid black;'>" + data[i].phone + "</td>");
        document.write("<td style='border: 1px solid black;'>" + data[i].fax + "</td>");
        document.write("<td style='border: 1px solid black;'>" + data[i].email + "</td>");
        document.write("</tr>");
    }
}