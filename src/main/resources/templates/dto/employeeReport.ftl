<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<div style="background-color: #dddede;">
    <div style="width: 100%;">
        <div style="width: 100%;">
            <p style="text-align: left; font-size: 1.25rem; margin-left: 2%;">Busman Application</p>
        </div>
        <div style="width: 100%; text-align: left; margin-top: 2%; margin-left: 2%;">
            <span>Date: ${date} &nbsp;</span>
            <span>Time: ${time}</span>
        </div>
    </div>
</div>

<div style="margin:2%;">
    <div style="display: flex; width: 100%; ">
        <div style="width: 100%; text-align: center;">
            <h3>Completed tasks in ${month} ${year} by ${fullName} </h3>
        </div>
    </div>

    <div style="width: 100%;">
        <#list projects as project>
            <div style="width: 100%; text-align: center;">
                <div><h4>Project title: ${project.projectTitle}</h4></div>
                <table style="font-family: Arial, Helvetica, sans-serif;

              width: 100%; text-align:left; border: 1px solid black;" >
                    <tr style="border: 1px solid black; background-color: #708090; color:white; ">
                        <th style="width: 50%; border: 1px solid black; padding: 12px 0px 12px 0px;">Task title</th>
                        <th  style="border: 1px solid black; padding: 12px 0px 12px 0px;">Start date</th>
                        <th  style="border: 1px solid black; padding: 12px 0px 12px 0px;">End date</th>
                        <th style="border: 1px solid black; padding: 12px 0px 12px 0px;">Time(h)</th>
                    </tr>
                    <#list project.projectTasks as task>
                    <tr style="border: 1px solid black;">
                        <td style="border: 1px solid black; padding: 8px 0px 8px 0px;">${task.title}</td>
                        <td style="border: 1px solid black; padding: 8px 0px 8px 0px;">${task.dateOfCreation}</td>
                        <td style="border: 1px solid black; padding: 8px 0px 8px 0px;">${task.dateOfEnd}</td>
                        <td style="border: 1px solid black; padding: 8px 0px 8px 0px;">${task.neededTime}</td>
                    </tr>
                    </#list>
                </table>
            </#list>
        </div>
    </div>

    <div style="display: flex; width: 100%; text-align: left;">
        <h4>Total hours in ${month} ${year} by ${fullName}: <span style="color: red;"> ${totalHours} H</span> </h4>
    </div>
</div>
<div style="background-color: #dddede;">
    <div style="display: flex; width: 100%;">
            <p style="text-align:left; margin-left: 2%; padding:1% 1% 1% 1%;">© All rights reserved Busman Application</p>
    </div>
    <div>

</body>
</html>
