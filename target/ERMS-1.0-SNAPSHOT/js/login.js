const login = () => {
    const endpoint = BASE_URL + LOGIN_ENDPOINT;

    const formData = $('#login-form').serializeArray();
    const formDataObj = {};
    formData.forEach((dataObj) => formDataObj[dataObj.name]=dataObj.value);
    const data = JSON.stringify(formDataObj);

    const successFunction = (data) => {
        // loginStatus --> 0: logged in, 1: user doesn't exist, 2: incorrect password, 3: other error
        if (data.loginStatus === 0) window.location.replace(data.redirectTo);
        else if (data.loginStatus === 1) displayModal("Invalid email", "A user with the given email does not exist. Please re-check your email");
        else if (data.loginStatus === 2) displayModal("Invalid password", "The password you entered is incorrect. Please try again");
        else if (data.loginStatus === 3) displayModal("Server error", "The server faced an un expected error. Please try again");
    }
    const errorFunction = (data) => {
        displayModal("Server error", "The server faced an un expected error. Please try again");
        console.log(data);
    }
    postEntry(endpoint, data, successFunction, errorFunction);
}