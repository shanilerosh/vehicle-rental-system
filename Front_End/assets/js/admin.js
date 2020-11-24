$('#admincustomermanage').css({display: 'none'})
let currentDriver = '';
let altDriver = '';
let selectedCarForSchdule = '';

// function loadCustomersStatus() {
//     $('#admincustomermanage').css({display: ''})
//     loadAllCustomer();
// }


function loadAllCustomer() {
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/customer/searchallcustomer',
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            $('#admincustomertbody').children().remove();
            for (const data of res.data) {
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


function saveCar() {
    let formData = new FormData($('#carAddForm')[0]);
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car/savecar',
        type: 'post',
        data: formData,
        contentType: false,
        processData: false,
        success: function (res) {
            if (res.msg == "Success") {
                displaySuccessToast("Car Successfully Created.")
            } else {
                displayErrorToast(res.data);
                clearAllCarSaveFields();
            }
        }
    });
}

function displayWarningToast(msg) {
    $.toast({
        heading: 'Warning',
        text: msg,
        icon: 'info',
        bgColor: 'rgb(255,193,7)',
        position: 'top-right',
        textColor: 'black',
        showHideTransition: 'slide'
    })
}

function displaySuccessToast(msg) {
    $.toast({
        heading: 'Success',
        text: msg,
        icon: 'success',
        bgColor: '#28A745',
        position: 'top-right'
    })
}


function displayErrorToast(msg) {
    $.toast({
        heading: 'Error',
        text: msg,
        icon: 'error',
        bgColor: '#DC3545',
        position: 'top-right'
    })
}


function displayInfoToast(msg) {
    $.toast({
        heading: 'Information',
        text: msg,
        icon: 'info',
        bgColor: '#007BFF',
        position: 'top-right'
    })
}


function clearAllCarSaveFields() {
    $('#carName').val('');
    $('#carBrand').val('');
    $('#carRegNumber').val('');
    $('#carMonthlyRate').val('');
    $('#carDailyRate').val('');
    $('#carFreeKmPerDay').val('');
    $('#carFreeKmPerMonth').val('');
    $('#carPricePerExtraKm').val('');
    $('#carNumberOfPassenger').val('');
    $('#carColor').val('');
    $('#carColor').val('');
    $('#carDeposit').val('');
    $('#carMilage').val('');

}

function loadAdminCustomerManager() {
    $('#admincustomermanage').css({display: ''});
    $('#admincarmanage').css({display: 'none'});
    $('#paymentAndReturn').css({display: 'none'});
    searchCustomer();
}

function loadAdminCarManager() {
    $('#admincustomermanage').css({display: 'none'});
    $('#admincarmanage').css({display: ''});
    $('#paymentAndReturn').css({display: 'none'})
}


function searchCustomer() {
    const customer = $('#customerName').val();
    const sortProp = $('#customerSearchCriteria').val();
    const typedVal = customer.trim();

    console.log(customer, sortProp);
    if (typedVal === '') {
        loadAllCustomer();
    } else {
        $.ajax({
            url: 'http://localhost:8080/demo/api/v1/customer/searchcustomer/' + typedVal + '/' + sortProp,
            type: 'get',
            contentType: 'application/json',
            success: function (res) {
                $('#admincustomertbody').children().remove();
                for (const data of res.data) {
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
    $('#custDocImg').attr('src', '');
    const email = path.parentNode.parentNode.children[2].textContent;
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/customer/searchonecustomer/' + email,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            const imgSrc = res.data.document.slice(90, res.data.document.length);
            console.log(imgSrc)
            $('#custDocImg').attr("src", imgSrc);
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
            for (const data of res.data) {
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
    currentDriver = did;
    if (did != "N/A") {
        $('#currentDriver').children().remove();
        $('#currentDriver').append(`<h5>${did}</h5>`)
        $('#currentDriver').append(`<h5 id="currentBookingId">${bid}</h5>`)
        $('#acceptModel').modal('show');
    } else {
        $('#noDriverBid').children().remove();
        $('#noDriverBid').append(`<h5>${bid}</h5>`);
        $('#acceptModelWithoutDriver').modal('show');
    }

}

function denyBooking(val) {
    console.log('inside deny booking')
    const bid = val.parentNode.parentNode.children[0].textContent;
    $('#denialBid').children().remove();
    $('#denialBid').append(`<h5>${bid}</h5>`)
    $('#denyModel').modal('show');
}


function loadDriverSchedules() {
    const dteFrom = $('#driveScheduleFrom').val();
    const dteTo = $('#driveScheduleTo').val();
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking/getdriverchedule/' + currentDriver + '/' + dteFrom + '/' + dteTo,
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

    $('#viewAlternativeDriver').css({display: ''})
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/driver/getdrivernames/' + currentDriver,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            $('#selectAlternativeDriver').children().remove();
            for (const data of res.data) {
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
    altDriver = selected;
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
        url: 'http://localhost:8080/demo/api/v1/booking/getdriverchedule/' + selected + '/' + dteFrom + '/' + dteTo,
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
    const bid = $('#currentDriver').children()[1].innerHTML;
    let driver = '';
    if ($('#radioCurrent').is(':checked')) {
        console.log(currentDriver, 'current driver is')
        driver = currentDriver;
    } else {
        console.log('Inside')
        driver = altDriver;
    }
    dataset = {
        "bid": bid,
        "did": driver
    }

    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking/finalizebooking',
        type: 'post',
        data: JSON.stringify(dataset),
        contentType: 'application/json',
        success: function (res) {
            if (res.code == 200) {
                loadpendingBookingsToAdminPanel();
                $('#acceptModel').modal('hide');
            }

        }
    })
}


function acceptBookingWithoutDriver() {
    const bookinID = $('#noDriverBid').children()[0].innerHTML;
    console.log("Booking id is" + bookinID);
    dataset = {
        "bid": bookinID,
        "did": "Temp"
    }

    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking/finalizebookingWithoutDriver',
        type: 'post',
        data: JSON.stringify(dataset),
        contentType: 'application/json',
        success: function (res) {
            if (res.code == 200) {
                $('#acceptModelWithoutDriver').modal('hide');
                loadpendingBookingsToAdminPanel();
            }
        }
    })
}


function denyFinalBooking() {
    const bookinID = $('#denialBid').children()[0].innerHTML;
    const msg = $('#denialMsg').val();
    const dataset = {
        "bid": bookinID,
        "msg": msg
    }

    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking/denyBooking',
        type: 'post',
        data: JSON.stringify(dataset),
        contentType: 'application/json',
        success: function (res) {
            if (res.code == 200) {
                $('#denyModel').modal('hide');
                loadpendingBookingsToAdminPanel();
            }
        }
    })

}


function getCarsScheduleByState() {
    const selection = $('#carScheduleStateSelect').val();
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car/getcarbystate/' + selection,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            $('#carScheduleTable').children().remove();
            for (const data of res.data) {
                $('#carScheduleTable').append(`
                    <tr>
                        <th scope="row">${data.reg}</th>
                        <td>${data.registrationNumb}</td>
                        <td>${data.color}</td>
                        <td>${data.milege}</td>
                        <td>${data.nmberOfPssngers}</td>
                        <td>${data.carState}</td>
                        <td>
                        <button type="button" class="btn btn-success" onclick="getIndividualSchedule(this)">View Schdule</button>
</td>
                    </tr>
                
                `);
            }
        }
    });
}


function changeCarToPreferedState(val) {
    const state = val.value;
    const crId = selectedCarForSchdule;
    console.log(crId);
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car/updatestate/' + crId + '/' + state,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            if (res.code == 200) {
                $('#modelIndividualCarSchedule').modal('hide');
                getCarsScheduleByState();
            }
        }

    })
}


function getIndividualSchedule(val) {
    const crId = val.parentNode.parentNode.children[0].innerText;
    $('#modelCarScheduleTableBoy').children().remove();
    $('#modalInsideCarId').children().remove();
    $('#modalInsideCarId').append(`<h5>${crId}</h5>`);
    selectedCarForSchdule = crId;
    console.log("car id " + crId);
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking/getcarshedule/' + crId,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            console.log(res);
            $('#modelIndividualCarSchedule').modal('show');
            for (const data of res.data) {
                $('#modelCarScheduleTableBoy').append(`
                <tr>
                            <th scope="row">${data.rqrdDate}</th>
                            <td>${data.retrunDate}</td>
                            <td>${data.driver}</td>
                        </tr>`)
            }
        }
    });

}

function viewBidDetailsFromBid() {
    const bid = $('#paymentReturnBidText').val();
    console.log(bid);
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking/paymentdetail/' + bid,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            if (res.code == 500) {
                $('.paymentAndReturnVal').children().remove();
                $('.paymentAndReturnVal').append(`
                    <div class="alert alert-danger" role="alert">
  ${res.msg}
</div>
                `)
            }
            $('#paymntCustName').children().remove();
            $('#paymentCarId').children().remove();
            $('#paymntDateOfRqrd').children().remove();
            $('#paymentStartingMilage').children().remove();
            $('#paymentDetailAutoUpdate').css({display: ''})
            $('#paymntCustName').append(`<p>Customer Name : ${res.data.customer.name}</p>`);
            $('#paymentCarId').append(`<p>Car Id : ${res.data.car.reg}</p>`);
            $('#paymntDateOfRqrd').append(`<p>Starting Date : ${res.data.dateTime}</p>`);
            $('#paymentStartingMilage').append(`<p>Car Milage : ${res.data.car.milege}</p>`);
        }
    })
}

function calculatePayment() {
    const actualReturnDate = $('#paymentCustActuallyReturned').val();
    const carDateOfAquire = $('#paymntDateOfRqrd').children()[0].textContent;
    const damages = $('#paymentDamages').val();
    const carEndingMilage = $('#paymentMilage').val();
    const milage = $('#paymentStartingMilage').children()[0].textContent;
    const carId = $('#paymentCarId').children()[0].textContent;
    const startingMilage = milage.slice(12, milage.length).trim();
    const form = new FormData();
    form.append('returndate', actualReturnDate);
    form.append('startdate', startingMilage);
    form.append('damage', damages);
    form.append('startmilage', startingMilage);
    form.append('endingmilage', carEndingMilage);
    form.append('driver', false);
    form.append('carId', 1);
    console.log(form);
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/return/calpayment',
        type: 'post',
        data: form,
        processData: false,
        contentType: false,
        success: function (res) {
        }
    })

}

function loadCarListing() {
    const carSearchCriteria = $('#carSearchCriteria').val();
    const carUserInput = $('#caSearchValue').val();
    console.log(carUserInput);
    if (carUserInput !== '') {
        $.ajax({
            url: 'http://localhost:8080/demo/api/v1/car/searchCars/' + carSearchCriteria + '/' + carUserInput,
            type: 'get',
            contentType: 'application/json',
            success: function (res) {
                $('#carViewTableBody').children().remove();
                for (const data of res.data) {
                    $('#carViewTableBody').append(`
            <tr>
                <th scope="row">${data.reg}</th>
                <td>${data.brand}</td>
                <td>${data.carType}</td>
                <td>${data.transmissionType}</td>
                <td>${data.carState}</td>
                <td>${data.carState}</td>
                <td><button type="button" class="btn btn-primary" onclick="viewCarImages(this)" value="${data.reg}">Images</button></td>
                                            </tr>`)
                }
            }
        })
    }

}


function viewCarImages(val) {
    const selection = val.value;


}
