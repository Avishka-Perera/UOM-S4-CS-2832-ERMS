const updateAllCheckboxes = (checked) => {
    const checkboxes = document.querySelectorAll("#mediaTBody input[type=checkbox]");
    for (let i = 0; i < checkboxes.length; i++) {
        checkboxes[i].checked = checked;
    }
}

const sendMail = (row) => {
    const mail = row.querySelector("#tdMailMedia").innerHTML;
    const data = {mails:[mail]};
    sendMailRequest(data);
}

const sendMailsToSelected = () => {
    const form = document.querySelector("#mediaTable");
    const formData = $(form).serializeArray();
    const data = {mails:[]};
    for (let i = 0; i < formData.length; i++) data.mails.push(formData[i].name);
    sendMailRequest(data);
}

const sendMailRequest = (data) => {
    if (data.mails.length > 0) {
        const endpoint = SENDMAIL_ENDPOINT;
        const errorFunction = (error) => displayModal("Error", "Email not sent. PLease try again.");
        const successFunction = (data) => notification(data.successCount + " mails sent, " + data.failCount + " failed.");
        const strData = JSON.stringify(data);
        postEntry(endpoint, strData, successFunction, errorFunction);
    }
}