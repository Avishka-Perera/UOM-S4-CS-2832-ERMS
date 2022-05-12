const updateVote = (row, partyId, locationId) => {
    const votes = row.querySelector("#votes-amount").value;

    const endpoint = BASE_URL+VOTES_ENDPOINT;
    const data = JSON.stringify({partyId, locationId, votes})
    const successFunction = (data) => {
        console.log(data);
    }
    const errorFunction = (data) => console.log("error:", data);
    updateEntry(endpoint, data, successFunction, errorFunction);
}