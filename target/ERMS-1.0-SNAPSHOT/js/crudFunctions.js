const postEntry = (endpoint, data, successFunction, errorFunction) => {
    $.ajax({
        url: endpoint,
        data: data,
        success: (data) => successFunction(data),
        error: (data) => errorFunction(data),
        type: 'post',
        dataType: 'json',
    });
}

const deleteEntry = (endpoint, data, successFunction, errorFunction) => {
    $.ajax( {
        url : endpoint,
        data : data,
        success : (data) => successFunction(data), // data.status is of type boolean. If successfully deleted, will return true
        error : (data) => errorFunction(data),
        dataType : "json",
        timeout : 30000,
        type : "delete"
    });
}

const updateEntry = (endpoint, data, successFunction, errorFunction) => {
    $.ajax( {
        url : endpoint,
        data : data,
        success : (data) => successFunction(data),
        error : (data) => errorFunction(data),
        dataType : "json",
        timeout : 30000,
        type : "put"
    });
}