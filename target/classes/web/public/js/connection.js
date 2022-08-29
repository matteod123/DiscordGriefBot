const read = fetch("/websocket", { method: 'GET' }).then(response => response.text("Result"));
init();

function init() {
    read.then((result) => {
        startSocket(result.toString());
    });
}