
const addUser = () => {

    const name = document.querySelector("#addUserForm #name").value;
    const email = document.querySelector("#addUserForm #email").value;
    const password = document.querySelector("#addUserForm #password").value;
    const confirmPassword = document.querySelector("#addUserForm #confirm-password").value;
    const contactNumber = document.querySelector("#addUserForm #contactNumber").value;
    const userLevel = document.querySelector("#addUserForm input[name='userLevel']:checked").value;

    const [validity, message] = validateUserFormData(name, email, password, confirmPassword, contactNumber);

    const endpoint = BASE_URL + USERS_ENDPOINT;
    const formData = $('#addUserForm').serializeArray();
    const formDataObj = {};
    formData.forEach((dataObj) => formDataObj[dataObj.name]=dataObj.value);
    const data = JSON.stringify(formDataObj);
    const successFunction = (data) => {
        // data.length = 2
        // data[0]: query status
        //          0: Query didn't execute
        //          1,2 : Query did successfully execute
        //          3: Email address already taken
        // data[1]: id of the created user

        if (data.result) {
            if (data.result === 3) {
                displayModal("Email unavailable", "The entered email has already been taken. Please enter another.")
            } else {
                const userId = data.id;

                const idDOM = document.createElement("TD");
                const nameDOM = document.createElement("TD");
                const emailDOM = document.createElement("TD");
                const contactNumberDOM = document.createElement("TD");
                const roleTDDOM = document.createElement("TD");
                const roleSELECTDOM = document.createElement("SELECT");
                const stationOfficerDOM = document.createElement("OPTION");
                const districtCenterOfficerDOM = document.createElement("OPTION");
                const secretariatOfficeDOM = document.createElement("OPTION");
                const adminDOM = document.createElement("OPTION");
                const locationDOM = document.createElement("TD");
                const actionTDDOM = document.createElement("TD");
                const updateBtnDOM = document.createElement("BUTTON");
                const deleteBtnDOM = document.createElement("BUTTON");
                const trDOM = document.createElement("TR");
                const tBody = document.querySelector("#assignRoles #usersTBody");

                updateBtnDOM.setAttribute("class",  "btn primary-outlined x-small-btn m-1")
                deleteBtnDOM.setAttribute("class",  "btn secondary-outlined x-small-btn m-1")

                idDOM.innerText = userId;
                nameDOM.innerText = name;
                emailDOM.innerText = email;
                contactNumberDOM.innerText = contactNumber;
                stationOfficerDOM.innerText = "Station officer";
                districtCenterOfficerDOM.innerText = "District center officer";
                secretariatOfficeDOM.innerText = "Secretariat office";
                adminDOM.innerText = "Admin";
                locationDOM.innerText = "Not assigned";
                updateBtnDOM.innerText = "Update";
                deleteBtnDOM.innerText = "Delete";

                idDOM.setAttribute("id", "trId");
                nameDOM.setAttribute("id", "trName");
                emailDOM.setAttribute("id", "trEmail");
                contactNumberDOM.setAttribute("id", "trContactNumber");
                roleTDDOM.setAttribute("id", "trUserLevel");
                roleSELECTDOM.setAttribute("id", "userLevel");
                locationDOM.setAttribute("id", "trLocation");

                stationOfficerDOM.setAttribute("value", "0")
                districtCenterOfficerDOM.setAttribute("value", "1")
                secretariatOfficeDOM.setAttribute("value", "2")
                adminDOM.setAttribute("value", "3")

                if (userLevel === "0") stationOfficerDOM.selected = true;
                if (userLevel === "1") districtCenterOfficerDOM.selected = true;
                if (userLevel === "2") secretariatOfficeDOM.selected = true;
                if (userLevel === "3") adminDOM.selected = true;

                updateBtnDOM.onclick = () => {
                    updateUserLevel(trDOM);
                };
                deleteBtnDOM.onclick = () => {
                    safeDeleteUser(trDOM);
                }

                trDOM.append(idDOM, nameDOM, emailDOM, contactNumberDOM, roleTDDOM, locationDOM, actionTDDOM);
                roleTDDOM.appendChild(roleSELECTDOM);
                roleSELECTDOM.append(stationOfficerDOM, districtCenterOfficerDOM, secretariatOfficeDOM, adminDOM);
                actionTDDOM.append(updateBtnDOM, deleteBtnDOM);

                tBody.appendChild(trDOM);
            }
        } else {
            displayModal("Server Error", "The serve faced an unexpected error. Please resubmit the registration data.")
        }
    }
    const errorFunction = (data) => console.log("Error", data);

    if (validity === "Valid") {
        postEntry(endpoint, data, successFunction, errorFunction);
    } else {
        displayModal(validity, message);
    }
}

const updateUserLevel = (row) => {
    const id = row.querySelector("#trId").innerHTML;
    const userLevel = $(row).children("td#trUserLevel").children("select").children("option").filter(":selected")[0].value;
    const endpoint = BASE_URL+USERS_ENDPOINT;
    const data = JSON.stringify({ task:TASK_USER_PUT_UPDATE_USER_LEVEL, user: { id, userLevel}})
    const successFunction = (data) => {
        if (data.status) {
            console.log(data.status);
        } else {
            console.log("user not updated");
        }
    }
    const errorFunction = (data) => displayModal("Error", data);
    updateEntry(endpoint, data, successFunction, errorFunction);
}

const updateUserData = (id, modal, row) => {
    const name = modal.querySelector("#name").value;
    const email = modal.querySelector("#email").value;
    const password = modal.querySelector("#password").value;
    const confirmPassword = modal.querySelector("#confirm-password").value;
    const contactNumber = modal.querySelector("#contactNumber").value;

    // endpoint
    const endpoint = BASE_URL + USERS_ENDPOINT;
    // data
    const formData = $('#addUserForm').serializeArray();
    const userObj = {};
    formData.forEach((data) => userObj[data.name]=data.value);
    const data = JSON.stringify({ task:TASK_USER_PUT_UPDATE_USER_DETAILS, user: {id,...userObj}});

    // success function
    const successFunction = (data) => {
        if (data.status === 0) displayModal("Server error","The server faced an unexpected error. Please try again");
        else if (data.status in [1,2]) {
            toggleModal("#add-user-modal");
            const nameDOM = row.querySelector("#trName");
            const emailDOM = row.querySelector("#trEmail");
            const contactNumberDOM = row.querySelector("#trContactNumber");
            const roleDOM = row.querySelector("#trUserLevel select");

            nameDOM.innerText = userObj.name;
            emailDOM.innerText = userObj.email;
            contactNumberDOM.innerText = userObj.contactNumber;
            roleDOM.value = userObj.userLevel;
        }
    }
    const errorFunction = (data) => {
        console.log(data);
    }

    const [validity, message] = validateUserFormData(name, email, password, confirmPassword, contactNumber);
    if (validity === "Valid") updateEntry(endpoint, data, successFunction, errorFunction);
    else displayModal(validity, message);
}

const getUserModalDOMs = () => {
    const userModalRoot = document.querySelector('#add-user-modal');
    const nameInput = userModalRoot.querySelector("#name");
    const emailInput = userModalRoot.querySelector("#email");
    const passwordInput = userModalRoot.querySelector("#password");
    const confirmPasswordInput = userModalRoot.querySelector("#confirm-password");
    const contactNumberInput = userModalRoot.querySelector("#contactNumber");
    const userLevelOptions = userModalRoot.querySelectorAll('input[name="userLevel"]');
    const actionBtn = userModalRoot.querySelector("#user-action-btn");

    return [userModalRoot, nameInput, emailInput, passwordInput, confirmPasswordInput, contactNumberInput, userLevelOptions, actionBtn];
}

const openAddUserModal = () => {
    const [_, nameInput, emailInput, passwordInput, confirmPasswordInput, contactNumberInput, userLevelOptions, actionBtn] = getUserModalDOMs();

    nameInput.value = "";
    emailInput.value = "";
    passwordInput.value = "";
    confirmPasswordInput.value = "";
    contactNumberInput.value = "";
    userLevelOptions[0].checked = true;
    actionBtn.innerHTML = "Add";
    actionBtn.onclick = () => addUser();

    toggleModal("#add-user-modal");
}

const openEditUserModal = (row) => {
    const [userModalRoot, nameInput, emailInput, passwordInput, confirmPasswordInput, contactNumberInput, userLevelOptions, actionBtn] = getUserModalDOMs();

    const id = row.querySelector("#trId").innerText;
    const nameText = row.querySelector("#trName").innerText;
    const emailText = row.querySelector("#trEmail").innerText;
    const contactNumberText = row.querySelector("#trContactNumber").innerText;
    const userLevel = row.querySelector("#trUserLevel select").value;

    nameInput.value = nameText;
    emailInput.value = emailText;
    passwordInput.value = "";
    confirmPasswordInput.value = "";
    contactNumberInput.value = contactNumberText;
    for (let i = 0; i < 4; i++) {
        const option = userLevelOptions[i];
        if (option.value === userLevel) option.checked = true;
    }
    actionBtn.innerHTML = "Update";
    actionBtn.onclick = () => updateUserData(id, userModalRoot, row);
    toggleModal("#add-user-modal");
}

const safeDeleteUser = (row) => {
    displayConfirmationModal("DELETE USER", "Are you sure you want to delete this user?", ()=>deleteUser(row));
}

const deleteUser = (row) => {
    const data = row.querySelector("#trId").innerHTML;
    const endpoint = BASE_URL + USERS_ENDPOINT;
    const successFunction = (data) => {
        if (data.status) row.remove();
        else console.log("user not deleted");
    }
    const errorFunction = (data) => console.log("error:", data);
    deleteEntry(endpoint, data, successFunction, errorFunction);
}