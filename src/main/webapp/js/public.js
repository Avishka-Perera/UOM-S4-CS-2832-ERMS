const getTableData = (partyId) => {
    const party = parties[partyId];

    let returnData = {name: party.name};
    const votes = [];
    for (let i = 0; i < party.votes.length; i++) {
        const vote = party.votes[i];
        votes.push({...vote, locationName: locations[vote.locationId]})
    }
    returnData = {...returnData, votes};

    return returnData;
}

const displayBreakDown = (partyId) => {
    const breakDownData = getTableData(partyId);

    const tbody = document.querySelector("#breakDownTbody");
    tbody.innerHTML = null;
    const partyNameHeading = document.querySelector("#partyNameHeading");
    partyNameHeading.innerHTML = breakDownData.name;

    let totalVotes = 0;
    for (let i = 0; i < breakDownData.votes.length; i++) {
        const vote = breakDownData.votes[i];

        const trDOM = document.createElement("TR");
        const locationTdDOM = document.createElement("TD");
        const voteTdDOM = document.createElement("TD");

        locationTdDOM.innerText = vote.locationName;
        voteTdDOM.innerText = vote.votes;
        totalVotes += vote.votes;

        trDOM.append(locationTdDOM, voteTdDOM);
        tbody.appendChild(trDOM);
    }

    const trDOM = document.createElement("TR");
    const locationTdDOM = document.createElement("TD");
    const voteTdDOM = document.createElement("TD");

    locationTdDOM.innerText = "Total";
    voteTdDOM.innerText = totalVotes.toString();

    trDOM.append(locationTdDOM, voteTdDOM);
    tbody.appendChild(trDOM);

}

