// const driverId = sessionStorage.getItem("driverId");

const driverId = "did-004";

loadDriverSchedule();

function loadDriverSchedule() {
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking//driverSch/' + driverId,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            $('#driverScheduleBody').children().remove();
            if (res.data != null) {
                for (const data of res.data) {
                    $('#driverScheduleBody').append(`  <tr>
                    <th scope="row">${data.driverId}</th>
                    <td>${data.location}</td>
                    <td>${data.fromDate}</td>
                    <td>${data.dateTo}</td>
                </tr>`)
                }
            }
        }
    });
}

