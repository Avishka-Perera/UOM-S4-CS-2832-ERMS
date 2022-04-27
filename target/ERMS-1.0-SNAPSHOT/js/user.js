
const addUser = (e) => {
    e.preventDefault();
    console.log("addinng user");
    const [validity, message] = validateUserFormData();
    if (validity === "Valid") {
        $.ajax({
            url: 'http://localhost/ERMS/users',
            type: 'post',
            dataType: 'json',
            data: $('#addUserForm').serialize(),
            success: (data) => {
                console.log(data)
            },
            error: (data) => {
                console.log(data);
            }
        });
    } else {
        displayModel(validity, message);
    }
}

const updateUser = (userId) => {
    console.log("Updating", userId);
}

const deleteUser = (userId, row) => {
    console.log("Deleting", userId);
    $.ajax( {
        url : 'http://localhost/ERMS/users',
        data : userId.toString(),
        success : function(data){
            if (data.status) {
                row.remove();
            } else {
                console.log("user not deleted");
            }
        },
        error : function(data) {
            console.log("error:", data);
        },
        dataType : "json",
        timeout : 30000,
        type : "delete"
    });
}