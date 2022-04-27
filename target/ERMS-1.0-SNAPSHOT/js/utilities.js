const displayModal = (title_text, message_text) => {
    const root = document.createElement("DIV");
    const modal = document.createElement("DIV");
    const title = document.createElement("H3");
    const message = document.createElement("P");
    const button = document.createElement("BUTTON");

    title.innerText = title_text;
    message.innerText = message_text;
    button.innerText = "CLOSE";

    root.className = "backdrop-overlay";
    modal.className = "modal-body";
    title.className = "modal-heading";
    message.className = "modal-message";
    button.className = "modal-button";

    button.onclick = () => {
        root.remove();
    };

    modal.appendChild(title);
    modal.appendChild(message);
    modal.appendChild(button);
    root.appendChild(modal);

    document.body.appendChild(root);
}

const validateEmail = (mail) => {
    return /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(mail);
}

const validateUserFormData = (name, email, password, confirmPassword, contactNumber) => {

    if (password !== confirmPassword) {
        document.querySelector("#addUserForm #confirm-password").value = "";
        return ["Password Error", "Passwords do not match each other. Please re-enter"];
    }
    if (password.length < 8) return ["Password Error", "Weak password. Please enter a password with 8 or more charachters"];

    if (!validateEmail(email)) return ["Email error", "Invalid email. Please enter a valid email"];

    if (name === "") return ["Name error", "Name should be none empty"];

    if (contactNumber === "") return ["Contact number error", "Contact number should be none empty"];

    return ["Valid", ""];
}