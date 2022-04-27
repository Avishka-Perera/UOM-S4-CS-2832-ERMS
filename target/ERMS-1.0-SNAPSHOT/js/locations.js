const addLocation = (e) => {
    e.preventDefault();

    const name = document.querySelector("#addLocationForm #name").value;

    let validity = "Valid", message = "";
    if (name === "") {
        validity = "Name Error";
        message = "The location name should be not empty";
    }

    const endpoint = BASE_URL + LOCATIONS_ENDPOINT;
    const data = $('#addLocationForm').serialize();
    const successFunction = (data) => {
        // data.length = 2
        // data[0]: query status
        //          0: Query didn't execute
        //          1,2 : Query did successfully execute
        //          3: Email address already taken
        // data[1]: id of the created user
        if (data.result) {
            if (data.result === 3) {
                displayModal("Location already exists", "A location with the same name already exists. Please enter another name.")
            } else {
                console.log("Success", data);
        //         const userId = data.id;
        //
        //         const idDOM = document.createElement("TD");
        //         const nameDOM = document.createElement("TD");
        //         const emailDOM = document.createElement("TD");
        //         const contactNumberDOM = document.createElement("TD");
        //         const roleTDDOM = document.createElement("TD");
        //         const roleSELECTDOM = document.createElement("SELECT");
        //         const stationOfficerDOM = document.createElement("OPTION");
        //         const districtCenterOfficerDOM = document.createElement("OPTION");
        //         const secretariatOfficeDOM = document.createElement("OPTION");
        //         const adminDOM = document.createElement("OPTION");
        //         const locationDOM = document.createElement("TD");
        //         const actionTDDOM = document.createElement("TD");
        //         const updateBtnDOM = document.createElement("BUTTON");
        //         const deleteBtnDOM = document.createElement("BUTTON");
        //         const trDOM = document.createElement("TR");
        //         const tBody = document.querySelector("#assignRoles #usersTBody");
        //
        //         idDOM.innerText = userId;
        //         nameDOM.innerText = name;
        //         emailDOM.innerText = email;
        //         contactNumberDOM.innerText = contactNumber;
        //         stationOfficerDOM.innerText = "Station officer";
        //         districtCenterOfficerDOM.innerText = "District center officer";
        //         secretariatOfficeDOM.innerText = "Secretariat office";
        //         adminDOM.innerText = "Admin";
        //         locationDOM.innerText = "Location select";
        //         updateBtnDOM.innerText = "Update";
        //         deleteBtnDOM.innerText = "Delete";
        //
        //         idDOM.setAttribute("id", "trId");
        //         nameDOM.setAttribute("id", "trName");
        //         emailDOM.setAttribute("id", "trEmail");
        //         contactNumberDOM.setAttribute("id", "trContactNumber");
        //         roleTDDOM.setAttribute("id", "trUserLevel");
        //         roleSELECTDOM.setAttribute("id", "userLevel");
        //         locationDOM.setAttribute("id", "trLocation");
        //
        //         stationOfficerDOM.setAttribute("value", "0")
        //         districtCenterOfficerDOM.setAttribute("value", "1")
        //         secretariatOfficeDOM.setAttribute("value", "2")
        //         adminDOM.setAttribute("value", "3")
        //
        //         if (userLevel === "0") stationOfficerDOM.selected = true;
        //         if (userLevel === "1") districtCenterOfficerDOM.selected = true;
        //         if (userLevel === "2") secretariatOfficeDOM.selected = true;
        //         if (userLevel === "3") adminDOM.selected = true;
        //
        //         updateBtnDOM.onclick = () => {
        //             updateUser(trDOM);
        //         };
        //         deleteBtnDOM.onclick = () => {
        //             deleteUser(trDOM);
        //         }
        //
        //         trDOM.append(idDOM, nameDOM, emailDOM, contactNumberDOM, roleTDDOM, locationDOM, actionTDDOM);
        //         roleTDDOM.appendChild(roleSELECTDOM);
        //         roleSELECTDOM.append(stationOfficerDOM, districtCenterOfficerDOM, secretariatOfficeDOM, adminDOM);
        //         actionTDDOM.append(updateBtnDOM, deleteBtnDOM);
        //
        //         tBody.appendChild(trDOM);
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

const deleteLocation = (row) => {
    const id = row.querySelector("#tdId").innerHTML;
    const endpoint = BASE_URL + LOCATIONS_ENDPOINT;
    const successFunction = (data) => {
        if (data.status) row.remove();
        else console.log("user not deleted");
    }
    const errorFunction = (data) => console.log("error:", data);
    deleteEntry(endpoint, id, successFunction, errorFunction);
}

const updateLocation = (row) => {
    const locationId = row.querySelector("#trId").innerHTML;
    const userLevel = $(row).children("td#trUserLevel").children("select").children("option").filter(":selected")[0].value;
    const endpoint = BASE_URL+LOCATIONS_ENDPOINT;
    const data = JSON.stringify({locationId, userLevel})
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