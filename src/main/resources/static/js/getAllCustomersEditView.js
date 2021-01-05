$(document).ready(function () {
    getCurrentCustomer();
})

function getCurrentCustomer(){
    var currentUrlAddress = window.location.href;
    var projectIdFromUrl = currentUrlAddress.substring(currentUrlAddress.lastIndexOf('/') + 1);
    var requestUrlAddress = window.location.protocol + "//" + window.location.host + "/admin/getCustomerDetailsByProjectId/"+projectIdFromUrl;

    $.ajax({method: "GET", url: requestUrlAddress})
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

var onlyOneTimes = 0;
$('#customers').on('click',function getAllCustomersFromDB() {

    if(onlyOneTimes == 0){
        var urlAddress = window.location.protocol + "//" + window.location.host + "/admin/getAllCustomers";
        $('#customers').find('option').remove().end();
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

    onlyOneTimes++;

});




