const validateLocation = (name) => {
    let validity = "Valid", message = "";
    if (name === "") {
        validity = "Name Error";
        message = "The location name should be not empty";
    }
    return [validity, message];
}

const openAddLocationModal = () => {
    const locationModalRoot = document.querySelector('#add-location-modal');
    const nameInput = locationModalRoot.querySelector("#name");
    const typeOption = locationModalRoot.querySelector('input[name="type"]');
    const actionBtn = locationModalRoot.querySelector("#location-action-btn");

    nameInput.innerHTML = "";
    typeOption.checked = true;
    actionBtn.innerHTML = "Add";
    actionBtn.onclick = () => addLocation();
}

const addLocation = () => {

    const name = document.querySelector("#addLocationForm #name").value;
    const type = parseInt(document.querySelector("#addLocationForm input[name='type']:checked").value);

    const [validityTitle, message] = validateLocation(name);

    const endpoint = BASE_URL + LOCATIONS_ENDPOINT;
    const data = $('#addLocationForm').serialize();
    const successFunction = (data) => {
        // data.length = 2
        // data[0]: query status
        //          0: Query didn't execute
        //          1,2 : Query did successfully execute
        //          3: Email address already taken
        // data[1]: id of the created location
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
                const editBtnDOM = document.createElement("BUTTON");
                const deleteBtnDOM = document.createElement("BUTTON");

                // assigns required ids and names
                idTdDOM.setAttribute("id", "tdId");
                nameTdDOM.setAttribute("id","tdName");
                stationUserTdDOM.setAttribute("id", "stationUserId");
                districtUserTdDOM.setAttribute("id", "districtUserId");
                typeTdDOM.setAttribute("id", "type");
                stationUserSelectDOM.setAttribute("name", "stationUser");
                districtUserSelectDOM.setAttribute("name", "districtUser");
                typeSelectDOM.setAttribute("name", "type");

                // adds the classes
                updateBtnDOM.setAttribute("class",  "btn primary-outlined x-small-btn m-1");
                editBtnDOM.setAttribute("class",  "btn primary-outlined x-small-btn m-1");
                deleteBtnDOM.setAttribute("class",  "btn secondary-outlined x-small-btn m-1");

                // creates the dropdowns
                let option = document.createElement("OPTION");
                option.setAttribute("selected", "true");
                option.innerText = "None";
                stationUserSelectDOM.appendChild(option);
                option = document.createElement("OPTION");
                option.setAttribute("selected", "true");
                option.innerText = "None";
                districtUserSelectDOM.appendChild(option);
                for (let i = 0; i < stationUsers.length; i++) {
                    const user = stationUsers[i];
                    const option = document.createElement("OPTION");
                    option.setAttribute("value", user.id);
                    option.innerText = user.id + " " + user.name;
                    stationUserSelectDOM.appendChild(option);
                }
                for (let i = 0; i < districtUsers.length; i++) {
                    const user = districtUsers[i];
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
                editBtnDOM.innerText = "Edit";
                deleteBtnDOM.innerText = "Delete";

                // bind button functions
                updateBtnDOM.onclick = () => updateLocationDetails(locationId, trDOM);
                editBtnDOM.onclick = () => openEditLocationModal(trDOM);
                deleteBtnDOM.onclick = () => safeDeleteLocation(trDOM);

                // append the rest
                stationUserTdDOM.appendChild(stationUserSelectDOM);
                districtUserTdDOM.appendChild(districtUserSelectDOM);
                typeTdDOM.appendChild(typeSelectDOM);
                actionTdDOM.append(updateBtnDOM, editBtnDOM, deleteBtnDOM);
                trDOM.append(idTdDOM, nameTdDOM, stationUserTdDOM, districtUserTdDOM, typeTdDOM, actionTdDOM);

                const tBody = document.querySelector("#availableLocations #locationsTBody");
                tBody.appendChild(trDOM);
            }
        } else {
            displayModal("Server Error", "The serve faced an unexpected error. Please resubmit the registration data.")
        }
    }

    // TODO: Write this function correctly to display the error message
    const errorFunction = (data) => displayModal("Error", data);

    if (validityTitle === "Valid") postEntry(endpoint, data, successFunction, errorFunction);
    else displayModal(validityTitle, message);
}

const safeDeleteLocation = (row) => {
    displayConfirmationModal(
        "DELETE LOCATION",
        "Are you sure you want to delete this location?",
        ()=>deleteLocation(row)
    );
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

const openEditLocationModal = (row) => {
    const locationModalRoot = document.querySelector('#add-location-modal');
    const nameInput = locationModalRoot.querySelector("#name");
    const typeOptions = locationModalRoot.querySelectorAll('input[name="type"]');
    const actionBtn = locationModalRoot.querySelector("#location-action-btn");

    const nameText = row.querySelector("#tdName").innerText;
    const type = row.querySelector("#type select").value;
    const id = row.querySelector("#tdId").innerText;

    nameInput.setAttribute("value", nameText);
    for (let i = 0; i < 2; i++) {
        const option = typeOptions[i];
        if (option.value === type) option.checked = true;
    }
    actionBtn.innerHTML = "Update";
    actionBtn.onclick = () => updateLocationDetails(id, row,locationModalRoot);

    toggleModal("#add-location-modal");
}

const updateLocationDetails = (id, row, locationModalRoot) => {
    //required DOMs
    const nameTdDOM = row.querySelector("#tdName");
    const typeSelectDOM = $(row).children("td#type").children("select")[0];
    const typeOptionDOMs = $(typeSelectDOM).children("option");

    // reads the data in the row
    let stationUserId = $(row).children("td#stationUserId").children("select").children("option").filter(":selected")[0].value;
    if (stationUserId === "None") stationUserId = null;
    let districtCenterUserId = $(row).children("td#districtUserId").children("select").children("option").filter(":selected")[0].value;
    if (districtCenterUserId === "None") districtCenterUserId = null;

    let name = null;
    let type = null;
    if (locationModalRoot) {
        // reads the data in the modal
        const nameInput = locationModalRoot.querySelector("#name");
        const typeOptions = locationModalRoot.querySelectorAll('input[name="type"]');
        name = nameInput.value;
        type = 0;
        for (let i = 0; i < typeOptions.length; i++) {
            const option = typeOptions[i];
            if (option.checked === true) type = option.value;
        }
    } else {
        // reads the data in the row
        type = typeOptionDOMs.filter(":selected")[0].value;
        name = nameTdDOM.innerHTML;
    }

    // values for the put request
    const data = JSON.stringify({id, name, type, stationUserId, districtCenterUserId});
    const endpoint = BASE_URL+LOCATIONS_ENDPOINT;
    const successFunction = (data) => {
        if (data.status in [1,2]) {
            nameTdDOM.innerHTML = name;
            typeSelectDOM.value = type;
            console.log(data.status);
        } else if (data.status === 0){
            displayModal("Location not Updated", "The server faced an unexpected error. Please update again.");
        } else if (data.status === 3) {
            displayModal("Location not Updated", "The selected station user is already assigned for another location. Please select another user.");
        } else if (data.status === 4) {
            displayModal("Location not Updated", "The selected district user is already assigned for another location. Please select another user.");
        } else if (data.status === 5) {
            displayModal("Location not Updated", "Both selected users are already assigned for other locations. Please select different users.");
        }
    }
    const errorFunction = (data) => console.log("error:", data);

    // checks the validity and send the data
    const [validityTitle, message] = validateLocation(name);
    if (validityTitle === "Valid") updateEntry(endpoint, data, successFunction, errorFunction);
    else displayModal(validityTitle, message);
}