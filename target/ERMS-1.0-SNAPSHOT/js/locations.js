const addLocation = () => {

    const name = document.querySelector("#addLocationForm #name").value;
    const type = parseInt(document.querySelector("#addLocationForm input[name='type']:checked").value);

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
                const locationId = data.id;

                // creates the elements except for the dropdown options
                const trDOM = document.createElement("TR");
                const idTdDOM = document.createElement("TD");
                const nameTdDOM = document.createElement("TD");
                const stationUserTdDOM = document.createElement("TD");
                const districtUserTdDOM = document.createElement("TD");
                const typeTdDOM = document.createElement("TD");
                const actionTdDOM = document.createElement("TD");
                const stationUserSelectDOM = document.createElement("SELECT");
                const districtUserSelectDOM = document.createElement("SELECT");
                const typeSelectDOM = document.createElement("SELECT");
                const updateBtnDOM = document.createElement("BUTTON");
                const deleteBtnDOM = document.createElement("BUTTON");

                // assigns required ids and names
                idTdDOM.setAttribute("id", "tdId");
                stationUserTdDOM.setAttribute("id", "stationUserId");
                districtUserTdDOM.setAttribute("id", "districtUserId");
                typeTdDOM.setAttribute("id", "type");
                stationUserSelectDOM.setAttribute("name", "stationUser");
                districtUserSelectDOM.setAttribute("name", "districtUser");
                typeSelectDOM.setAttribute("name", "type");

                // creates the dropdowns
                let option = document.createElement("OPTION");
                option.setAttribute("selected", "true");
                option.innerText = "None";
                stationUserSelectDOM.appendChild(option);
                option = document.createElement("OPTION");
                option.setAttribute("selected", "true");
                option.innerText = "None";
                districtUserSelectDOM.appendChild(option);
                for (const user in stationUsers) {
                    const option = document.createElement("OPTION");
                    option.setAttribute("value", user.id);
                    option.innerText = user.id + " " + user.name;
                    stationUserSelectDOM.appendChild(option);
                }
                for (const user in districtUsers) {
                    const option = document.createElement("OPTION");
                    option.setAttribute("value", user.id);
                    option.innerText = user.id + " " + user.name;
                    districtUserSelectDOM.appendChild(option);
                }
                option = document.createElement("OPTION");
                option.setAttribute("value", "0");
                option.innerText = "Polling Station";
                if (type === 0) option.setAttribute("selected", "true")
                typeSelectDOM.appendChild(option);
                option = document.createElement("OPTION");
                option.setAttribute("value", "1");
                option.innerText = "District Center";
                if (type === 1) option.setAttribute("selected", "true")
                typeSelectDOM.appendChild(option);

                // set texts of the DOMs
                idTdDOM.innerText = locationId;
                nameTdDOM.innerText = name;
                updateBtnDOM.innerText = "Update";
                deleteBtnDOM.innerText = "Delete";

                // bind button functions
                updateBtnDOM.onclick = () => updateLocation(trDOM);
                deleteBtnDOM.onclick = () => deleteLocation(trDOM);

                // append the rest
                stationUserTdDOM.appendChild(stationUserSelectDOM);
                districtUserTdDOM.appendChild(districtUserSelectDOM);
                typeTdDOM.appendChild(typeSelectDOM);
                actionTdDOM.append(updateBtnDOM,deleteBtnDOM);
                trDOM.append(idTdDOM, nameTdDOM, stationUserTdDOM, districtUserTdDOM, typeTdDOM, actionTdDOM);

                const tBody = document.querySelector("#availableLocations #locationsTBody");
                tBody.appendChild(trDOM);
            }
        } else {
            displayModal("Server Error", "The serve faced an unexpected error. Please resubmit the registration data.")
        }
    }
    const errorFunction = (data) => console.log("Error", data);

    if (validity === "Valid") {
        console.log("valid");
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
    const locationId = row.querySelector("#tdId").innerHTML;
    let stationUserId = $(row).children("td#stationUserId").children("select").children("option").filter(":selected")[0].value;
    if (stationUserId === "None") stationUserId = null;
    let districtUserId = $(row).children("td#districtUserId").children("select").children("option").filter(":selected")[0].value;
    if (districtUserId === "None") districtUserId = null;
    const type = $(row).children("td#type").children("select").children("option").filter(":selected")[0].value;

    const endpoint = BASE_URL+LOCATIONS_ENDPOINT;
    const data = JSON.stringify({locationId, stationUserId, districtUserId, type})
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