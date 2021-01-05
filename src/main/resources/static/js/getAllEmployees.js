$(document).ready(function () {
    getAllEmployeesFromDB();
})


function getAllEmployeesFromDB() {
    var urlAddress = window.location.protocol + "//" + window.location.host + "/admin/getAllEmployees";

    $.ajax({method: "GET", url: urlAddress})
        .done(function(responseJson){
            customersDropDown = $("#employees");
            if(responseJson.length == 0){
                $('<option>').text("You don't have any employees in system.").appendTo(customersDropDown);
            }else{
                $.each(responseJson, function (index, employee) {
                    //alert(index + ": " + customer.id + ": " + customer.firstName +": " + customer.lastName);
                    $('<option>').val(employee.id).text("ID: " + employee.id + " - " + employee.lastName +" "+ employee.firstName).appendTo(customersDropDown);
                })
            }

        })
        .fail(function(){
            alert("Error connecting to the server");
        })
        .always(function (){
            //  alert("Always executed");
        });
}
