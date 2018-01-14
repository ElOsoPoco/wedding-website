/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*
 * This is a JavaScript Scratchpad.
 *
 * Enter some JavaScript, then Right Click or choose from the Execute Menu:
 * 1. Run to evaluate the selected text (Cmd-R),
 * 2. Inspect to bring up an Object Inspector on the result (Cmd-I), or,
 * 3. Display to insert the result in a comment after the selection. (Cmd-L)
 */

function createTable(data){  
  var table = document.createElement("table");
  table.id = "dashboard-table";
    
  row = table.insertRow(-1);
  
  cell = row.insertCell(-1);
  cell.innerHTML = "Party Name";
  
  cell = row.insertCell(-1);
  cell.innerHTML = "Party Number";
  
  cell = row.insertCell(-1);
  cell.innerHTML = "Attending";
  
  cell = row.insertCell(-1);
  cell.innerHTML = "Guest Name";
  
  cell = row.insertCell(-1);
  cell.innerHTML = "Entree";
  
  cell = row.insertCell(-1);
  cell.innerHTML = "Salad";
  
  cell = row.insertCell(-1);
  cell.innerHTML = "Over 21";
  
  cell = row.insertCell(-1);
  cell.innerHTML = "Comments";
  
  for(x in data){
    row = table.insertRow(-1);
    
    cell = row.insertCell(-1);
    cell.innerHTML = data[x].party_name;
    
    cell = row.insertCell(-1);
    cell.innerHTML = data[x].party_number;
    
    cell = row.insertCell(-1);
    cell.innerHTML = data[x].attending;
    
    cell = row.insertCell(-1);
    cell.innerHTML = data[x].guest_name;
    
    cell = row.insertCell(-1);
    cell.innerHTML = data[x].entree;
    
    cell = row.insertCell(-1);
    cell.innerHTML = data[x].salad;
    
    cell = row.insertCell(-1);
    cell.innerHTML = data[x].over21;
    
    cell = row.insertCell(-1);
    cell.innerHTML = data[x].comments;    
  }
  var dashboard = document.getElementById("dashboard");
  dashboard.innerHTML = "";
  dashboard.appendChild(table);
}

function getDashboard(){    
    // get form parameters and translate them to a JSON object
    // that is mapped to a JSON/Java object in service
    var viewArr = $("#Authentication").serializeArray();
    var view = {};
    // need to convert { name:"VarName", value:"ValName"} to {VarName:"ValName"}
    for (var i in viewArr) {
        view[viewArr[i].name] = viewArr[i].value;
    }
    console.log(view);

    // call back to service with post containing JSON data and on
    // return process results back into page          
    $.ajax({
      type:"POST",
      contentType: 'application/json',
      dataType: "json",
      data: JSON.stringify(view),        
      url: 'http://localhost:8080/KeepingUpWithTheKeims/webresources/dashboard/post_dashboard',        
      // you will want to update success function to get specific
      // JSON data fields for output back to the original HTML page
      success: function(data) {
        // this sets the html content of the html object
        // with id of reponseArea.
        console.log(data);
        //$("#rsvp-main").html(data.response);
        var resultData = JSON.parse(data.response);
        console.log(resultData);
        createTable(resultData);
      },
      error: function() {
        //$("#rsvp-main").html("<b>An error has occurred!</b><br \> \n\
        //                 <img src='images/frowny-face.gif'>");
      }
    });
}