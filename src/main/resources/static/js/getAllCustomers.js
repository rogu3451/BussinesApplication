$(document).ready(function () {
    getAllCustomersFromDB();
})

function getAllCustomersFromDB() {
    var urlAddress = window.location.protocol + "//" + window.location.host + "/admin/getAllCustomers";
    $.ajax({method: "GET", url: urlAddress})
        .done(function(responseJson){
            customersDropDown = $("#customers");
            if(responseJson.length == 0){
                $('<option>').text("You don't have any customers in system.").appendTo(customersDropDown);
            }else{
                $.each(responseJson, function (index, customer) {
                    //alert(index + ": " + customer.id + ": " + customer.firstName +": " + customer.lastName);
                    $('<option>').val(customer.id).text("ID: " + customer.id + " - " + customer.lastName +" "+ customer.firstName).appendTo(customersDropDown);
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
