window.onload = function () {
    document.getElementById("sendBtn").addEventListener("click", () => {
        sendMessage();
    });
}

const socketOpenListener = (event) => {
    
};

function sendMessage() {
    let channelID = document.getElementById("channelID").value;
    let message = document.getElementById("message").value;
    socket.send("SEND|" + btoa(channelID) + "*" + btoa(message));
}