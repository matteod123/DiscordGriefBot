window.onload = function () {

    document.getElementById("submitBtn").addEventListener("click", () => {
        let botToken = document.getElementById("botToken").value;
        let userID = document.getElementById("userID").value;
        let username = document.getElementById("username").value;
        let password = document.getElementById("password").value;
        let activityDescription = document.getElementById("activityDescription").value;
        let griefMessage = document.getElementById("griefMessage").value;
        let spamMessage = document.getElementById("spamMessage").value;
        const disguiseCommandPrefix = "!";
        const griefPicture = "https://0cn.de/po21";

        if (botToken != "" && userID != "" && username != "" && password != "") {
            let encodedUsername = btoa(username);
            let hashedPassword = md5(password);
            activityDescription = (activityDescription == "") ? document.getElementById("activityDescription").placeholder : activityDescription;
            griefMessage = (griefMessage == "") ? document.getElementById("griefMessage").placeholder : griefMessage;
            spamMessage = (spamMessage == "") ? document.getElementById("spamMessage").placeholder : spamMessage;

            const jsonObj = { botToken: botToken, userID: userID, griefMessage: griefMessage, spamMessage: spamMessage, griefPicture: griefPicture, activityDescription: activityDescription, disguiseCommandPrefix: disguiseCommandPrefix };
            socket.send("SETUP|" + btoa(JSON.stringify(jsonObj)));
            socket.send("REGISTER|" + btoa(encodedUsername + ":" + hashedPassword));
        }
        location.reload();
    });

}