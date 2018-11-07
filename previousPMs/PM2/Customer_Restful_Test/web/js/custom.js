/*
 * @Marco
 * @Mohammed
 */

//arrays that will contain the ids of customer for a given discount code
var codeH = [];
var codeM = [];
var codeL = [];
var codeN = [];

getCustomers();

//Getting the the data from the customer table through an ajax call
                function getCustomers()
                {
                    var url = "http://localhost:8080/Customer_Restful/webresources/entities.customer";
                    //console.log('Start!');
                    $.ajax
                        ({
                            type: 'GET',                        //Request Data Type
                            contentType: 'application/json',    //Request Content Type
                            url: url,                       //RESTful Service URL
                            dataType: "json",                   //Response Data Type
                
                            success: function(data)
                            {
                                document.write("<a href='search.html'>Search Criteria</a>");
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
                                outputCustRowsHtml(data);
                                document.write("</table>");
                                discountwithCustId(data);
                                getDiscountInfo();

                            }
                        });
                }
                
                //Function to see which customer id is with which discount code
                function discountwithCustId(data){
                    
                    for(var i = 0; i < data.length; i++){
                        if(data[i].discountCode.discountCode === "H"){

                                codeH.push(data[i].customerId);
                        }else if(data[i].discountCode.discountCode === "M"){
                                codeM.push(data[i].customerId);
                        }else if(data[i].discountCode.discountCode === "L"){
                                codeL.push(data[i].customerId);
                        }else if(data[i].discountCode.discountCode === "N"){
                                codeN.push(data[i].customerId);
                        }
                    }
                    
                }
                
                
                
                //Helper function to go throught all the rows in the customer table and output it to the browser
                function outputCustRowsHtml(data){
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
                
                
                
                

                function getDiscountInfo(){
                    var url = "http://localhost:8080/Customer_Restful/webresources/entities.discountcode";
                    console.log('Start!');
                    $.ajax
                        ({
                            type: 'GET',                        //Request Data Type
                            contentType: 'application/json',    //Request Content Type
                            url: url,                       //RESTful Service URL
                            dataType: "json",                   //Response Data Type
                
                            success: function(data)
                            {
                                document.writeln("<h4>Discount Code</h4> <br>");
                                document.writeln("<table style='border: 1px solid black;'>");
                                document.writeln("<tr>");
                                document.write("<th style='border: 1px solid black;'>Dis-code</th>");
                                document.write("<th style='border: 1px solid black;'>Rate</th>");
                                document.write("<th style='border: 1px solid black;'>Customer IDs</th>");
                                document.write("</tr>");
                                outputDiscRowsHtml(data);
                                document.write("</table>");
                            }
                        });
                }
                
                function outputDiscRowsHtml(data){
                    for(var i = 0; i < data.length; i++){
                        document.write("<tr>");
                        document.write("<td style='border: 1px solid black;'>" + data[i].discountCode +"</td>");
                        document.write("<td style='border: 1px solid black;'>" + data[i].rate.toFixed(2) + "</td>");
                        if(i === 0){
                            document.write("<td style='border: 1px solid black;'>" + codeH + "</td>");
                        }else if(i === 1){
                            document.write("<td style='border: 1px solid black;'>" + codeM + "</td>");
                        }else if(i === 2){
                            document.write("<td style='border: 1px solid black;'>" + codeL + "</td>");
                        }else if(i === 3){
                            document.write("<td style='border: 1px solid black;'>" + codeN + "</td>");
                        }
                        document.write("</tr>");
                    }
                }