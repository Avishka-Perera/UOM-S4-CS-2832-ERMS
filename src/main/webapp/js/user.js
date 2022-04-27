
const addUser = (e) => {
    e.preventDefault();

    const name = document.querySelector("#addUserForm #name").value;
    const email = document.querySelector("#addUserForm #email").value;
    const password = document.querySelector("#addUserForm #password").value;
    const confirmPassword = document.querySelector("#addUserForm #confirm-password").value;
    const contactNumber = document.querySelector("#addUserForm #contactNumber").value;
    const userLevel = document.querySelector("#addUserForm input[name='userLevel']:checked").value;

    const [validity, message] = validateUserFormData(name, email, password, confirmPassword, contactNumber);

    const endpoint = BASE_URL + USERS_ENDPOINT;
    const data = $('#addUserForm').serialize();
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

                idDOM.innerText = userId;
                nameDOM.innerText = name;
                emailDOM.innerText = email;
                contactNumberDOM.innerText = contactNumber;
                stationOfficerDOM.innerText = "Station officer";
                districtCenterOfficerDOM.innerText = "District center officer";
                secretariatOfficeDOM.innerText = "Secretariat office";
                adminDOM.innerText = "Admin";
                locationDOM.innerText = "Location select";
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
                    updateUser(trDOM);
                };
                deleteBtnDOM.onclick = () => {
                    deleteUser(trDOM);
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

const updateUser = (row) => {
    const userId = row.querySelector("#trId").innerHTML;
    const userLevel = $(row).children("td#trUserLevel").children("select").children("option").filter(":selected")[0].value;
    const endpoint = BASE_URL+USERS_ENDPOINT;
    const data = JSON.stringify({userId, userLevel})
    const successFunction = (data) => {
        if (data.status) {
            console.log(data.status);
        } else {
            console.log("user not updated");
        }
    }
    const errorFunction = (data) => console.log("error:", data);
    updateEntry(endpoint, data, successFunction, errorFunction);
}

const deleteUser = (row) => {
    const data = row.querySelector("#trId").innerHTML;
    const endpoint = BASE_URL + USERS_ENDPOINT;
    const successFunction = (data) => {
        if (data.status) row.remove();
        else console.log("user not deleted");
    }
    const errorFunction = () => console.log("error:", data);
    deleteEntry(endpoint, data, successFunction, errorFunction);
}