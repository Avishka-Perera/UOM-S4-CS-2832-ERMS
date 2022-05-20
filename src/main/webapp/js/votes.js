const updateVote = (row, partyId, locationId) => {
    const votes = row.querySelector("#votes-amount").value;

    const endpoint = BASE_URL+VOTES_ENDPOINT;
    const data = JSON.stringify({partyId, locationId, votes})
    const successFunction = (data) => {
        notification("Votes updated successfully");
    }
    const errorFunction = (data) => displayModal("Error","Votes not updated. Please try again.");
    updateEntry(endpoint, data, successFunction, errorFunction);
}