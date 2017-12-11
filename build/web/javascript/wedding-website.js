/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function sendRSVPRequest() {
    // get form parameters and translate them to a JSON object
    // that is mapped to a JSON/Java object in service
    var guests = serializeRSVP();
    console.log(guests);
    
    var viewArr = $("#rsvp-intro-form").serializeArray();
    var view = {};
        // need to convert { name:"VarName", value:"ValName"} to {VarName:"ValName"}
    for (var i in viewArr) {
      view[viewArr[i].name] = viewArr[i].value;
    }
    view["guests"] = guests;
    console.log(view);
    
    view = "Hello";

    // call back to service with post containing JSON data and on
    // return process results back into page          
    $.ajax({
        type:"POST",
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify(view),
        url: 'http://localhost:8080/KeepingUpWithTheKeims/webresources/rsvp/post_rsvp',
        // you will want to update success function to get specific
        // JSON data fields for output back to the original HTML page
        success: function(data) {
            // this sets the html content of the html object
            // with id of reponseArea.
            console.log("Event Success!");
            console.log(data);
            var response = "<b>Thank You!</b>";            
            $("#rsvp").html(response);
        },
        error: function(data) {
            console.log("Event Error!"); 
            console.log(data);
            $("#rsvp").html("<b>An error has occurred!</b>");
        }
    });
};

function partyDetails(locationId){
    rsvpGuestDetails = document.getElementById(locationId);
    rsvpGuestDetails.innerHTML = "";        
 
    var partyNumber = parseInt(document.forms["rsvp-intro-form"]["partyNumber"].value);  

    for(i=1; i<= partyNumber; i++){
        rsvpGuestDetailsForm = document.createElement("form");
        rsvpGuestDetailsForm.id = "rsvp-guest-details-form-"+i;
        rsvpGuestDetailsForm.classList.add("rsvp-guest-details-form");
        rsvpGuestDetailsTable = document.createElement("table");
        rsvpGuestDetailsTable.id = "rsvp-guest-details-table-"+i;

        addPartyDetail(rsvpGuestDetailsTable, "Guest Name", "text", "guestName", i);
        addPartyDetail(rsvpGuestDetailsTable, "Age", "text", "age", i);
        
        rsvpGuestDetailsForm.appendChild(rsvpGuestDetailsTable);
        rsvpGuestDetails.appendChild(rsvpGuestDetailsForm);
        
    }
    
    sendButton = document.createElement("button");
    sendButton.classList.add("hover-button");
    sendButton.id = "rsvp-submit";    
    sendButton.innerHTML = "Send!";
    sendButton.onclick = sendRSVPRequest;
    
    rsvpGuestDetails.appendChild(sendButton);
}

function addPartyDetail(tableName, label, inputType, inputName, i){
    
    
    row = rsvpGuestDetailsTable.insertRow(-1);

    cell = row.insertCell(-1);
    guestName = document.createElement("p");
    guestName.innerHTML = label;
    cell.appendChild(guestName);

    cell = row.insertCell(-1);
    guestNameInput = document.createElement("input");
    guestNameInput.type = inputType;
    guestNameInput.name = inputName;
    cell.appendChild(guestNameInput);
}

function serializeRSVP(){
    var guests = [];
    $(".rsvp-guest-details-form").each(function(i) {
        var viewArr = $(this).serializeArray();        
        var view = {};
        // need to convert { name:"VarName", value:"ValName"} to {VarName:"ValName"}
        for (var i in viewArr) {
          view[viewArr[i].name] = viewArr[i].value;
        }
        guests.push(view);
    });
    
    return guests;
}