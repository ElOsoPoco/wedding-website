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
    console.log(JSON.stringify(view));

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
            console.log(data);
            $("#rsvp-main").html(data.response);
        },
        error: function() {
            $("#rsvp-main").html("<b>An error has occurred!</b><br \> \n\
                             <img src='images/frowny-face.jpg'>");
        }
    });
};

//TODO: Need to move HTML-esq elements to index.html
function partyDetails(){
    rsvpGuestDetails = document.getElementById('rsvp-guest-details');
    rsvpGuestDetails.innerHTML = "";
 
    var partyNumber = parseInt(document.forms["rsvp-intro-form"]["partyNumber"].value);  

    for(i=1; i<= partyNumber; i++){
        rsvpGuestDetailsForm = document.createElement("form");
        rsvpGuestDetailsForm.id = "rsvp-guest-details-form-"+i;
        rsvpGuestDetailsForm.classList.add("rsvp-guest-details-form");
        
        guestNumberLabel = document.createElement("p");
        guestNumberLabel.innerHTML = "Guest Number "+i;
        guestNumberLabel.style.fontWeight = "bold";
        rsvpGuestDetailsForm.appendChild(guestNumberLabel);
        
        rsvpGuestDetailsTable = document.createElement("table");
        rsvpGuestDetailsTable.id = "rsvp-guest-details-table-"+i;
        rsvpGuestDetailsTable.style.tableLayout = "fixed";

        addPartyDetail(rsvpGuestDetailsTable, "Guest Name: ", "text", "guestName");        
        addRadioButton(rsvpGuestDetailsTable, "Entree: ", "entree", 
            ["<img src='images/steak.png' alt='' class='food'> NY Strip Steak - gold potato puree, red wine and shallot compound butter, sauteed crimini mushroom and spinach ", 
             "<img src='images/salmon.png' alt='' class='food'> Cedar Salmon - peas and carrots, roasted cauliflower wild rice, creme fraiche, dill ", 
             "<img src='images/vegetarian.png' alt='' class='food'> Vegetarian - Chef's choice "],
            ["Steak", "Salmon", "Vegetarian"]);            
        addRadioButton(rsvpGuestDetailsTable, "Salad: ", "salad", 
            ["<img src='images/heirloom.png' alt='' class='food'> Signature Heirloom (Spicy) - heirloom tomato, lemon cucumber, chile-cornbread croutons, parmesean cheese, and preserved meyer lemon vinaigrette  "
            , "<img src='images/house-salad.png' alt='' class='food'> House - Oragnic greens with a champagne vinaigrette, shaved parmesan, and a seasonal garnishment "],
            ["Heirloom", "House"]);
        addRadioButton(rsvpGuestDetailsTable, "Over 21?: ", "over21", ["Yes: ", "No: "],
            ["True", "False"]);
        //addPartyDetail(rsvpGuestDetailsTable, "Age: ", "text", "age");
        addTextArea(rsvpGuestDetailsTable, "Comments: ", 
            "rsvp-guest-details-form", "comments");
        
        rsvpGuestDetailsForm.appendChild(rsvpGuestDetailsTable);
        rsvpGuestDetails.appendChild(rsvpGuestDetailsForm);
        
    }
    sendButtonDiv = document.getElementById("rsvp-submit");
    sendButtonDiv.innerHTML = "";
    
    sendButton = document.createElement("button");
    sendButton.classList.add("hover-button");    
    sendButton.innerHTML = "Send!";
    sendButton.onclick = sendRSVPRequest;    
    
    sendButtonDiv.appendChild(sendButton);
}

function addPartyDetail(tableName, label, inputType, inputName){
        
    row = tableName.insertRow(-1);

    cell = row.insertCell(-1);
    guestName = document.createElement("p");    
    guestName.innerHTML = label;
    guestName.style.textAlign = "left";
    cell.appendChild(guestName);

    cell = row.insertCell(-1);
    guestNameInput = document.createElement("input");
    guestNameInput.type = inputType;
    guestNameInput.name = inputName;    
    cell.appendChild(guestNameInput);
}

function addRadioButton(tableName, label, radioButtonGroupName, html, values){
        
    row = tableName.insertRow(-1);

    cell = row.insertCell(-1);
    guestName = document.createElement("p");   
    guestName.innerHTML = label;
    cell.style.textAlign = "left";
    cell.appendChild(guestName);
    
    cell = row.insertCell(-1);
    blank = document.createElement("br");    
    cell.appendChild(blank);

    for(j=0; j<values.length; j++){        
        console.log("j: "+j);
        
        row = tableName.insertRow(-1);
        
        cell = row.insertCell(-1);
        //blank = document.createElement("br");
        cell.innerHTML = html[j];
        cell.classList.add("rsvp-detail-label");
        cell.appendChild(blank);
        
        cell = row.insertCell(-1);
        guestNameInput = document.createElement("input");
        guestNameInput.type = "RADIO";
        guestNameInput.name = radioButtonGroupName;
        guestNameInput.value = values[j];
        cell.classList.add("rsvp-radio-button");
        cell.appendChild(guestNameInput);
    }
}

function addTextArea(table, label, form, id){
    row = table.insertRow(-1);
    
    cell = row.insertCell(-1);
    labelElement = document.createElement("p");
    labelElement.innerHTML = label;
    cell.style.textAlign = "left";
    cell.append(labelElement);
    
    cell = row.insertCell(-1);
    textArea = document.createElement("textarea");
    textArea.form = form;
    textArea.cols = 25;
    textArea.rows = 5;    
    textArea.name = id;
    cell.style.alignContent = "left";
    cell.appendChild(textArea);
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

function isAttending(){
  rsvpIntroTable = document.getElementById("rsvp-intro-table");
  rsvpGuestDetails = document.getElementById("rsvp-guest-details");
  sendButtonDiv = document.getElementById("rsvp-submit");
  
  document.getElementById("partyNumRow").innerHTML = "";
  sendButtonDiv.innerHTML = "";
  
    if(document.getElementById("is-attending").checked){    
      row = document.getElementById("partyNumRow");      

      cell = row.insertCell(-1);
      partyNumLabel = document.createElement("p");
      partyNumLabel.innerHTML = "Number of Guests in Party: ";
      cell.appendChild(partyNumLabel);

      cell = row.insertCell(-1);
      partyNum = document.createElement("input");
      partyNum.type = "text";
      partyNum.name = "partyNumber";
      partyNum.size = 1;
      partyNum.maxlength = 1;
      partyNum.addEventListener('input', partyDetails);
      cell.appendChild(partyNum);


    } else {    
      sendButton = document.createElement("button");
      sendButton.classList.add("hover-button");      
      sendButton.innerHTML = "Send!";
      sendButton.onclick = sendRSVPRequest;

      sendButtonDiv.appendChild(sendButton);
    }
}