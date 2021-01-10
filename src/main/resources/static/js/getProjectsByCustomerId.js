$(document).ready(function () {
    getProjectsForSpecificCustomerFromDB();
})

function getProjectsForSpecificCustomerFromDB() {
    var urlAddress = window.location.protocol + "//" + window.location.host + "/customer/getAllProjects";
    $.ajax({method: "GET", url: urlAddress})
        .done(function(responseJson){
            projectsDropDown = $("#projects");
            if(responseJson.length == 0){
                $('<option>').text("You don't have any projects in system.").appendTo(projectsDropDown);
            }else{
                $.each(responseJson, function (index, project) {
                    $('<option>').val(project.id).text("ID: " + project.id + " - " + project.name).appendTo(projectsDropDown);
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
