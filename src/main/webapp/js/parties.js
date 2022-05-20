const validateParty = (name) => {
    let validity = "Valid", message = "";
    if (name === "") {
        validity = "Name Error";
        message = "The party name should be not empty";
    }
    return [validity, message];
}

const addParty = () => {

    const name = document.querySelector("#addPartyForm #partyName").value;

    const [validityTitle, message] = validateParty(name);

    const endpoint = BASE_URL + PARTIES_ENDPOINT;
    const data = $('#addPartyForm').serialize();
    const successFunction = (data) => {
        // data.length = 2
        // data[0]: query status
        //          0: Query didn't execute
        //          1,2 : Query did successfully execute
        //          3: Party name already taken
        // data[1]: id of the created user
        if (data.result) {
            if (data.result === 3) {
                displayModal("Party already exists", "A party with the same name already exists. Please enter another name.")
            } else {
                const partyId = data.id;

                // creates the elements except for the dropdown options
                const trDOM = document.createElement("TR");
                const idTdDOM = document.createElement("TD");
                const nameTdDOM = document.createElement("TD");
                const votesTdDOM = document.createElement("TD");
                const actionTdDOM = document.createElement("TD");
                const editBtnDOM = document.createElement("BUTTON");
                const deleteBtnDOM = document.createElement("BUTTON");

                // assigns required ids and names
                idTdDOM.setAttribute("id", "tdIdParty");
                nameTdDOM.setAttribute("id","tdNameParty");

                // assigns classes
                editBtnDOM.setAttribute("class", "btn primary-outlined x-small-btn m-1")
                deleteBtnDOM.setAttribute("class", "btn secondary-outlined x-small-btn m-1")

                // set texts of the DOMs
                idTdDOM.innerText = partyId;
                nameTdDOM.innerText = name;
                votesTdDOM.innerText = "0";
                editBtnDOM.innerText = "Edit";
                deleteBtnDOM.innerText = "Delete";

                // bind button functions
                editBtnDOM.onclick = () => openEditPartyModal(trDOM);
                deleteBtnDOM.onclick = () => safeDeleteParty(trDOM);

                // append the rest
                actionTdDOM.append(editBtnDOM, deleteBtnDOM);
                trDOM.append(idTdDOM, nameTdDOM, votesTdDOM, actionTdDOM);

                const tBody = document.querySelector("#availableParties #partiesTBody");
                tBody.appendChild(trDOM);
            }
        } else {
            displayModal("Server Error", "The serve faced an unexpected error. Please resubmit the registration data.")
        }
    }
    const errorFunction = (data) => displayModal("Error", data);

    if (validityTitle === "Valid") {
        postEntry(endpoint, data, successFunction, errorFunction);
    } else {
        displayModal(validityTitle, message);
    }
}

const updateParty = (id, modal, row) => {
    const name = modal.querySelector("#partyName").value;
    const data = JSON.stringify({id, name});
    const endpoint = BASE_URL + PARTIES_ENDPOINT;
    const successFunction = (data) => {
        toggleModal("#add-party-modal");
        if (data.status === 0) displayModal("Server error", "An unidentified error occurred in the server. Please try again");
        else if (data.status in [1,2]) {
            const nameTdDOM = row.querySelector("#tdNameParty");
            nameTdDOM.innerHTML = name;
        }
    }
    const errorFunction = (data) => displayModal("Error", data);

    const [validityTitle, message] = validateParty(name);
    if (validityTitle === "Valid") updateEntry(endpoint, data, successFunction, errorFunction);
    else displayModal(validityTitle, message);
}

const openAddPartyModal = () => {
    const partyModalRoot = document.querySelector("#add-party-modal");
    const heading = partyModalRoot.querySelector("#partyModalHeading");
    const nameInput = partyModalRoot.querySelector("#partyName");
    const actionBtn = partyModalRoot.querySelector("#party-action-btn");

    heading.innerHTML = "Add new party";
    nameInput.setAttribute("value", "");
    actionBtn.onclick = () => addParty();
    actionBtn.innerHTML = "Add";

    toggleModal('#add-party-modal');
}

const openEditPartyModal = (row) => {
    const partyModalRoot = document.querySelector("#add-party-modal");
    const heading = partyModalRoot.querySelector("#partyModalHeading");
    const nameInput = partyModalRoot.querySelector("#partyName");
    const actionBtn = partyModalRoot.querySelector("#party-action-btn");

    const partyName = row.querySelector("#tdNameParty").innerText;
    const partyId = row.querySelector("#tdIdParty").innerText;

    heading.innerHTML = "Edit party";
    nameInput.setAttribute("value", partyName);
    actionBtn.onclick = () => updateParty(partyId, partyModalRoot, row);
    actionBtn.innerHTML = "Edit";

    toggleModal('#add-party-modal');
}

const safeDeleteParty = (row) => {
    displayConfirmationModal("DELETE PARTY", "Are you sure you want to delete this party?", ()=>deleteParty(row));
}

const deleteParty = (row) => {
    const id = row.querySelector("#tdIdParty").innerHTML;
    const endpoint = BASE_URL + PARTIES_ENDPOINT;
    const successFunction = (data) => {
        if (data.status) {
            row.remove();
        }
        else displayModal("Error", "Party not deleted. Please try again");
    }
    const errorFunction = (data) => displayModal("Error", "Party not deleted. Please try again");
    deleteEntry(endpoint, id, successFunction, errorFunction);
}