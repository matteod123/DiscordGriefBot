window.onload = function () {

    document.getElementById("login").addEventListener("click", () => {
        let username = document.getElementById("username").value;
        let password = document.getElementById("password").value;

        socket.send("LOGIN|" + btoa(btoa(username) + ":" + md5(password)));
    });
}

function unauthorized() {
    document.getElementById("alert-error").hidden = false;
}

const socketOpenListener = (event) => { };