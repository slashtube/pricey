const ManualFiles = document.getElementById("upload-manual");
const List = document.getElementById("file-list");

ManualFiles.addEventListener('change', async (event) => {
    const url = 'http://localhost:8080/send';
    for(file of event.target.files) {
    const response = await fetch(url, {
            method: 'POST',
            header: {
                'Content-Type': 'multipart/form-data',
            },
            body: {'file': file},
        });


        console.log(response);
    }
    
});
