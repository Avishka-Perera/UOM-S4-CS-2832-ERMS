const addParty = () => {

    const name = document.querySelector("#addPartyForm #partyName").value;

    let validity = "Valid", message = "";
    if (name === "") {
        validity = "Name Error";
        message = "The party name should be not empty";
    }

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
                const deleteBtnDOM = document.createElement("BUTTON");

                // assigns required ids and names
                idTdDOM.setAttribute("id", "tdIdParty");

                // assigns classes
                deleteBtnDOM.setAttribute("class", "btn secondary-outlined")

                // set texts of the DOMs
                idTdDOM.innerText = partyId;
                nameTdDOM.innerText = name;
                votesTdDOM.innerText = "0";
                deleteBtnDOM.innerText = "Delete";

                // bind button functions
                deleteBtnDOM.onclick = () => safeDeleteParty(trDOM);

                // append the rest
                actionTdDOM.appendChild(deleteBtnDOM);
                trDOM.append(idTdDOM, nameTdDOM, votesTdDOM, actionTdDOM);

                const tBody = document.querySelector("#availableParties #partiesTBody");
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

const safeDeleteParty = (row) => {
    displayConfirmationModal("DELETE PARTY", "Are you sure you want to delete this party?", ()=>deleteParty(row));
}

const deleteParty = (row) => {
    const id = row.querySelector("#tdIdParty").innerHTML;
    const endpoint = BASE_URL + PARTIES_ENDPOINT;
    const successFunction = (data) => {
        if (data.status) row.remove();
        else console.log("user not deleted");
    }
    const errorFunction = (data) => console.log("error:", data);
    deleteEntry(endpoint, id, successFunction, errorFunction);
}