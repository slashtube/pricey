const ManualFiles = document.getElementById("upload-manual");
const List = document.getElementById("file-list");
const originLink = document.getElementById("link-path");
const download_button = document.getElementById("download-button");
const notification_success = document.getElementById("notification-success");
const notification_error = document.getElementById("notification-error");
const notification_warning = document.getElementById("notification-warning");
const submit_button = document.getElementById("submit");

const message_success = document.getElementById("message-success");
const message_error = document.getElementById("message-error");
const message_warning = document.getElementById("message-warning");
const dismiss_button = document.getElementById("dismiss");


dismiss_button.addEventListener("click", () => {
    notification_success.style.display = "none";
    notification_error.style.display = "none";
})

ManualFiles.addEventListener('change', handle_send);
download_button.addEventListener('click', handle_download);
submit_button.addEventListener('click', handle_origin);

async function handle_origin() {
    const origin_url = 'http://localhost:8080/sendOrigin';
    const origin = document.getElementById("link-path").value;

    const response = await fetch(origin_url, {
        method: 'POST',
        header: {
            'Content-Type': 'text/plain',
        },
        body: origin
    });

    if(response.status == 200) {
        message_success.innerText = "Origine aggiornata";
        notification_success.style.display = "block";
    }
}

async function handle_send(event) {
    const url = 'http://localhost:8080/send';
    const formData = new FormData();
    for(const file of event.target.files) {
        formData.append('file', file);
        addListItem(file);
    }
    
    const response = await fetch(url, {
        method: 'POST',
        header: {
            'Content-Type': 'multipart/form-data',
        },
        body: formData
    });

    if(response.status == 200) {
        message_success.innerText = "File caricati con successo";
        notification_success.style.display = "block";
    } else {
        message_error.innerText = "Errore caricamento file";
        notification_error.style.display = "block";
    }

}

async function handle_sort() {
    const sort_url = 'http://localhost:8080/sort';

    const response = await fetch(sort_url, {
        method: 'GET'
    })

}

async function handle_download() {
    const download_url = 'http://localhost:8080/catalog';

    message_warning.innerText = "Operazione in corso, attendere...";
    notification_warning.style.display = "block";
    await handle_sort();
    notification_warning.style.display = "none";

    const response = await fetch(download_url, {
        method: 'GET',
    })

    if(response.status == 200) {
        message_success.innerText = "Download in Corso...";
        notification_success.style.display = "block";
        const blob = await response.blob();
        download(blob, "Listino.zip");
        notification_success.style.display = "none";
        while(List.hasChildNodes()) {
            List.removeChild(List.lastChild);
        }
    }
}

function addListItem(file) {
    item = document.getElementById("item").cloneNode(true);

    filename = item.children[1];
    filename.innerText = file.name;

    item.classList.toggle("d-none");

    List.appendChild(item);

}