$('#admincustomermanage').css({display:'none'})
loadpendingBookingsToAdminPanel();
let currentDriver='';
let altDriver='';

function loadCustomersStatus() {
    $('#admincustomermanage').css({display: ''})
    loadAllCustomer();
}

function loadAllCustomer() {
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/customer/searchallcustomer',
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            $('#admincustomertbody').children().remove();
            for(const data of res.data){
                $('#admincustomertbody').append(`<tr>
                                    <th scope="row">${data.name}</th>
                                    <td>${data.address}</td>
                                    <td>${data.email}</td>
                                    <td><button type="button" class="btn btn-success" onclick="loadToImage(this)">Confirm</button></td>
                                </tr>`)
            }

        }
    });
}


function searchCustomer() {
    const customer = $('#customerName').val();
    const sortProp = $('#customerSearchCriteria').val();
    const typedVal = customer.trim();

    console.log(customer,sortProp);
    if(typedVal === ''){
        loadAllCustomer();
    }else {
        $.ajax({
            url: 'http://localhost:8080/demo/api/v1/customer/searchcustomer/'+typedVal+'/'+sortProp,
            type: 'get',
            contentType: 'application/json',
            success: function (res) {
                $('#admincustomertbody').children().remove();
                for(const data of res.data){
                    $('#admincustomertbody').append(`<tr>
                                    <th scope="row">${data.name}</th>
                                    <td>${data.address}</td>
                                    <td>${data.email}</td>
                                    <td><button type="button" class="btn btn-success" onclick="loadToImage(this)">Confirm</button></td>
                                </tr>`)
                }
            }
        });

    }

}

function loadToImage(path) {
    $('#custDocImg').attr('src','');
    const email=path.parentNode.parentNode.children[2].textContent;
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/customer/searchonecustomer/'+email,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            const imgSrc=res.data.document.slice(90,res.data.document.length);
            console.log(imgSrc)
            $('#custDocImg').attr("src",imgSrc);
        }
    });
}




function loadpendingBookingsToAdminPanel() {
    $('#pendingbookingtbody').children().remove();

    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking/getpending',
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            console.log(res);
            for(const data of res.data) {
                console.log(data);
                $('#pendingbookingtbody').append(`<tr>
                            <th scope="row">${data.bookingId}</th>
                            <td>${data.customerName}</td>
                            <td>${data.rqrdLocation}</td>
                            <td>${data.bookingDate}</td>
                            <td>${data.dateOfReturn}</td>
                            <td>${data.driverId}</td>
                            <td><span class="badge badge-warning">${data.status}</span></td>
                            <td><button type="button" class="btn btn-success btn-sm" onclick="acceptBooking(this)">Accept</button></td>
                            <td><button type="button" class="btn btn-danger btn-sm" onclick="denyBooking(this)">Deny</button></td>
                            
                        </tr>`)

            }
        }
    });
}



function acceptBooking(val) {
    const bid = val.parentNode.parentNode.children[0].textContent;
    const did = val.parentNode.parentNode.children[5].textContent;
    currentDriver=did;
    $('#currentDriver').children().remove();
    $('#currentDriver').append(`<h5>${did}</h5>`)
    $('#currentDriver').append(`<h5 id="currentBookingId">${bid}</h5>`)
    if(did!="N/A") {
        $('#acceptModel').modal('show');

    }

}

function denyBooking(val) {

}



function loadDriverSchedules() {
    const dteFrom = $('#driveScheduleFrom').val();
    const dteTo = $('#driveScheduleTo').val();

    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking/getdriverchedule/' + currentDriver+'/'+ dteFrom+'/'+dteTo,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            $('#dScheduleTbdody').children().remove();
            for (const data of res.data) {
                $('#dScheduleTbdody').append(
                    ` <tr>
      <th scope="row">${data.location}</th>
      <td>${data.fromDate}</td>
      <td>${data.dateTo}</td>
    </tr>`)
            }
        }
    })
}


function loadAlterNativeDriver() {
    const dteFrom = $('#driveScheduleFrom').val();
    const dteTo = $('#driveScheduleTo').val();

    $('#viewAlternativeDriver').css({display:''})
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/driver/getdrivernames/'+currentDriver,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            $('#selectAlternativeDriver').children().remove();
            for(const data of res.data){
                $('#selectAlternativeDriver').append(`
                <option>${data.did}</option>
            `)
            }

        }
    })
}



function viewAlterNavtiveSchedule(val) {
    const selected = $('#selectAlternativeDriver').val();
    const dteFrom = $('#driveScheduleFrom').val();
    const dteTo = $('#driveScheduleTo').val();
    altDriver=selected;

    $('#driverRadioView').children().remove();
    $('#driverRadioView').append(`<div class="form-check">
                                <input class="form-check-input" type="radio" name="exampleRadios" value="currentDriver" id="radioCurrent" checked>
                                <label class="form-check-label" for="exampleRadios1">
                                    Select Current Driver
                                </label>                            
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios2" value="${selected}" id="radioAlt">
                                <label class="form-check-label" for="exampleRadios2">
                                    Second Driver : ${selected}
                                </label>
                            </div>`)

    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking/getdriverchedule/' + selected+'/'+ dteFrom+'/'+dteTo,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            $('#dScheduleTbdody').children().remove();
            for (const data of res.data) {
                $('#dScheduleTbdody').append(
                    ` <tr>
      <th scope="row">${data.location}</th>
      <td>${data.fromDate}</td>
      <td>${data.dateTo}</td>
    </tr>`)
            }
        }
    })

}


function completeBooking() {
    const bid=$('#currentDriver').children()[1].innerHTML;
    let driver='';
    if($('#radioCurrent').is(':checked')) {
        console.log(currentDriver,'current driver is')
        driver=currentDriver;
    }else{
        console.log('Inside')
        driver=altDriver;
    }
    dataset = {
        "bid":bid,
        "did":driver
    }

    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking/finalizebooking',
        type: 'post',
        data:JSON.stringify(dataset),
        contentType: 'application/json',
        success: function (res) {
            console.log(res);
        }
    })
}
