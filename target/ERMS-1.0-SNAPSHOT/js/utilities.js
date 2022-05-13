const displayModal = (title_text, message_text) => {
    const root = document.createElement("DIV");
    const modal = document.createElement("DIV");
    const title = document.createElement("H3");
    const message = document.createElement("P");
    const button = document.createElement("BUTTON");

    title.innerText = title_text;
    message.innerText = message_text;
    button.innerText = "CLOSE";

    title.setAttribute("class", "mb-3");
    root.setAttribute("class", "modalBG")
    modal.setAttribute("class", "modalContent")
    button.setAttribute("class", "m-2 btn primary-outlined");

    button.onclick = () => root.remove();
    root.onclick = () => root.remove();
    modal.onclick = (event) => event.stopPropagation();

    modal.appendChild(title);
    modal.appendChild(message);
    modal.appendChild(button);
    root.appendChild(modal);

    document.body.appendChild(root);
}

const displayConfirmationModal = (title_text, message_text, confirmFunction) => {
    const root = document.createElement("DIV");
    const modal = document.createElement("DIV");
    const title = document.createElement("H3");
    const message = document.createElement("P");
    const buttonPanel = document.createElement("DIV");
    const confirmButton = document.createElement("BUTTON");
    const closeButton = document.createElement("BUTTON");

    title.innerText = title_text;
    message.innerText = message_text;
    confirmButton.innerText = "Yes";
    closeButton.innerText = "No";

    title.setAttribute("class", "mb-3");
    root.setAttribute("class", "modalBG")
    modal.setAttribute("class", "modalContent")
    closeButton.setAttribute("class", "m-2 btn primary-outlined");
    confirmButton.setAttribute("class", "m-2 btn secondary-contained");

    confirmButton.onclick = () => {
        confirmFunction();
        root.remove();
    };
    closeButton.onclick = () => root.remove();
    root.onclick = () => root.remove();
    modal.onclick = (event) => event.stopPropagation();

    modal.appendChild(title);
    modal.appendChild(message);
    buttonPanel.append(confirmButton, closeButton);
    modal.appendChild(buttonPanel);
    root.appendChild(modal);

    document.body.appendChild(root);
}

const toggleModal = (modalRootId) => {
    const modalRoot = document.querySelector(modalRootId);
    if (modalRoot.classList.contains("hidden")) modalRoot.classList.remove("hidden");
    else modalRoot.classList.add("hidden");
}

const validateEmail = (mail) => {
    return /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(mail);
}

const validateUserFormData = (name, email, password, confirmPassword, contactNumber) => {

    if (password !== confirmPassword) {
        document.querySelector("#addUserForm #confirm-password").value = "";
        return ["Password Error", "Passwords do not match each other. Please re-enter"];
    }
    if (password.length < 1) return ["Password Error", "Weak password. Please enter a password with 8 or more charachters"];

    if (!validateEmail(email)) return ["Email error", "Invalid email. Please enter a valid email"];

    if (name === "") return ["Name error", "Name should be none empty"];

    if (contactNumber === "") return ["Contact number error", "Contact number should be none empty"];

    return ["Valid", ""];
}