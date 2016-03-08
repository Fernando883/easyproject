/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//window.onload = init;

socket.onmessage = onMessage;

function onMessage(event) {
    var message = JSON.parse(event.data);
    if (message.action === "add") {
        printNewMessage(message);
    }
}

function addMessage(message, name, photoURL, email) {
    var MessageAction = {
        action: "add",
        message: message,
        name: name,
        photoURL: photoURL,
        email: email
        
    };
    
    socket.send(JSON.stringify(MessageAction));
}

function printNewMessage(message) {
   
    var content = document.getElementById("chat-content");
    
    var messageBody = document.createElement("div");
    messageBody.setAttribute("id", message.id);
    if (getCurrentUser() == message.email)
        messageBody.setAttribute("class", "direct-chat-msg right");
    else
        messageBody.setAttribute("class", "direct-chat-msg");
    content.appendChild(messageBody);

    var messageInfo = document.createElement("div");
    messageInfo.setAttribute("class", "direct-chat-info clearfix");
    messageBody.appendChild(messageInfo);

    var messageAuthor = document.createElement("span");
    if (getCurrentUser() == message.email)
        messageAuthor.setAttribute("class", "direct-chat-name pull-right");
    else
        messageAuthor.setAttribute("class", "direct-chat-name pull-left");
    messageAuthor.innerHTML = message.name;
    messageInfo.appendChild(messageAuthor);

    var messageTimestamp = document.createElement("span");
    if (getCurrentUser() == message.email)
        messageTimestamp.setAttribute("class", "direct-chat-timestamp pull-left");
    else
        messageTimestamp.setAttribute("class", "direct-chat-timestamp pull-right");
    messageTimestamp.innerHTML = message.timestamp;
    messageInfo.appendChild(messageTimestamp);
    
    var authorImage = document.createElement("img");
    authorImage.setAttribute("class", "direct-chat-img");
    authorImage.setAttribute("src", message.photoURL);
    messageBody.appendChild(authorImage);

    var messageText = document.createElement("div");
    messageText.setAttribute("class", "direct-chat-text");
    messageText.innerHTML = message.message;
    messageBody.appendChild(messageText);
    
    updateScroll();
}

function formSubmit() {
    var form = document.getElementById("sendMessage");
    var message = form.elements["message"].value;
    var name = form.elements["name"].value;
    var photoURL = form.elements["photoURL"].value;
    var email = form.elements["email"].value;
    
    document.getElementById("sendMessage").reset();
    if (message.length > 0)
        addMessage( message, name, photoURL, email);
    
    return false;
}

function updateScroll(){
    var element = document.getElementById("chat-content");
    element.scrollTop = element.scrollHeight;
}