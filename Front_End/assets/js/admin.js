$('#admincustomermanage').css({display: 'none'})
$('#admincarmanage').css({display: 'none'})
$('#paymentAndReturn').css({display: 'none'})
$('#bookingDetailManage').css({display: 'none'})
$('#adminDashboard').css({display: 'none'});
loadAdminDashboard();
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
                loadCarListing();
                clearAllCarSaveFields();
            } else {
                displayErrorToast(res.data);
            }
        }
    });
}


function updateCar() {
    let formData = new FormData($('#carAddForm')[0]);
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car/updatecar',
        type: 'post',
        data: formData,
        contentType: false,
        processData: false,
        success: function (res) {
            if (res.msg == "Success") {
                displaySuccessToast("Car Successfully updated.");
                clearAllCarSaveFields();
            } else {
                displayErrorToast(res.data);
            }
        }
    });
    loadCarListing();
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
    $('#adminDashboard').css({display: 'none'});
    $('#admincustomermanage').css({display: ''});
    $('#admincarmanage').css({display: 'none'});
    $('#paymentAndReturn').css({display: 'none'});
    $('#bookingDetailManage').css({display: 'none'})
    searchCustomer();
}


function loadAdminDashboard() {
    loadNumberOfUsersForDash();
    loadNumberOfBookingsPerToday();
    loadNumberOfAvailableAndReserved();
    loadActiveBookingsAsToday();
    loadAvailableAndOccupiesDrivers();
    loadCarThatNeedMaintainance();
    $('#admincustomermanage').css({display: 'none'});
    $('#admincarmanage').css({display: 'none'});
    $('#paymentAndReturn').css({display: 'none'});
    $('#bookingDetailManage').css({display: 'none'})
    $('#adminDashboard').css({display: ''})

}

function loadAdminCarManager() {
    $('#admincustomermanage').css({display: 'none'});
    $('#admincarmanage').css({display: ''});
    $('#paymentAndReturn').css({display: 'none'})
    $('#bookingDetailManage').css({display: 'none'})
    $('#adminDashboard').css({display: 'none'})
    loadCarListing();
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
    $('#admincustomermanage').css({display: 'none'})
    $('#admincarmanage').css({display: 'none'})
    $('#paymentAndReturn').css({display: 'none'})
    $('#bookingDetailManage').css({display: ''})
    $('#adminDashboard').css({display: 'none'})

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


function calculatePayment(val) {
    const bid = val.value;
    $('#returnViewForReturn').children().remove();
    $('#returnViewForReturn').append(`<!-- Modal -->
<div class="modal fade" id="returnAndPaymentModel" tabindex="-1" role="dialog" aria-labelledby="exampleModalScrollableTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-scrollable" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalScrollableTitle">Booking ID: ${bid} : Record Return And Payment</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <div class="row">
            <div class="col-6">
                <div class="form-group">
    <label>Damages</label>
    <input type="text" class="form-control form-control-sm" id="returnPaymentDmges" aria-describedby="emailHelp">
    <small id="emailHelp" class="form-text text-muted">Applicable only if there are any damages</small>
  </div>
            </div>
            <div class="col-6">
    <label>Milage</label>
    <input type="text" class="form-control form-control-sm" id="returnPaymentMilage" aria-describedby="emailHelp">
        </div>
       </div> 
        <div class="row">
        <div class="col-10">
            <label>Date of Actual Return</label>
            <input type="date" id="returnPaymentActualReturn">
        </div>
</div>
  <button type="button" value="${bid}" class="btn btn-primary" onclick="calculatePaymentAmount(this,$('#returnPaymentDmges').val(),$('#returnPaymentMilage').val(),$('#returnPaymentActualReturn').val())">Calculate Payment</button>
           
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>`)

    $('#returnAndPaymentModel').modal('show');
}


function loadCarDetailToTheFields(selected) {
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car/getbucket/' + selected,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            console.log(res);
            if (res.data !== null) {
                $('#carName').val(res.data.name);
                $('#carBrand').val(res.data.brand);
                $('#carRegNumber').val(res.data.registrationNumb);
                $('#carMonthlyRate').val(res.data.mnthlyRate);
                $('#carDailyRate').val(res.data.dlyRate);
                $('#carFreeKmPerDay').val(res.data.freeKmPerDay);
                $('#carFreeKmPerMonth').val(res.data.freeKmPerMonth);
                $('#carPricePerExtraKm').val(res.data.pricePerExtrakm);
                $('#carNumberOfPassenger').val(res.data.nmberOfPssngers);
                $('#carColor').val(res.data.color);
                $('#carDeposit').val(res.data.deposit);
                $('#carMilage').val(res.data.milege);
                $('#carStateSelect').val(res.data.carState);
                $('#carTransType').val(res.data.transmissionType);
                $('#carFuelType').val(res.data.fuelType);
                $('#carTypeSelect').val(res.data.carType);
            }
        }
    })
}

function loadCarListing() {
    const carSearchCriteria = $('#carSearchCriteria').val();
    const carUserInput = $('#caSearchValue').val();
    console.log(carUserInput);
    if (carUserInput === '') {
        $.ajax({
            url: 'http://localhost:8080/demo/api/v1/car/searchCars/Type/Premium',
            type: 'get',
            contentType: 'application/json',
            success: function (res) {
                $('#carViewTableBody').children().remove();
                for (const data of res.data) {
                    $('#carViewTableBody tr').off('click');
                    $('#carViewTableBody').append(`
            <tr>
                <th scope="row">${data.reg}</th>
                <td>${data.brand}</td>
                <td>${data.carType}</td>
                <td>${data.transmissionType}</td>
                <td>${data.carState}</td>
                <td>${data.carState}</td>
                <td><button type="button" class="btn btn-outline-success" onclick="viewCarImages(this)" value="${data.reg}">Images</button></td>
                                            </tr>`)
                    $('#carViewTableBody tr').on('click', (e) => {
                        const selected = e.currentTarget.children[0].innerText;
                        loadCarDetailToTheFields(selected);


                    })
                }
            }
        })
    } else if (carUserInput !== '') {
        $.ajax({
            url: 'http://localhost:8080/demo/api/v1/car/searchCars/' + carSearchCriteria + '/' + carUserInput,
            type: 'get',
            contentType: 'application/json',
            success: function (res) {
                $('#carViewTableBody').children().remove();
                for (const data of res.data) {
                    $('#carViewTableBody tr').off('click');
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
                    $('#carViewTableBody tr').on('click', (e) => {
                        const selected = e.currentTarget.children[0].innerText;
                        loadCarDetailToTheFields(selected);


                    })
                }
            }
        })
    }

}


function viewCarImages(val) {
    const selection = val.value;

    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car/getbucket/' + selection,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            $('#imageViewForCar').children().remove();
            const frntImg = res.data.frntImg.slice(90, res.data.frntImg.length);
            const bckImg = res.data.bckImg.slice(90, res.data.bckImg.length);
            const sideImg = res.data.sideImg.slice(90, res.data.sideImg.length);
            const interiorImge = res.data.frntImg.slice(90, res.data.interiorImge.length);
            console.log(res.data.reg + " Interior " + interiorImge);
            $('#imageViewForCar').append(`<div class="modal" tabindex="-1" role="dialog" id="imageViewForCarModal">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Car Images for Car ${selection}</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
            <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img class="d-block w-100" src="${frntImg}" alt="First slide">
    </div>
    <div class="carousel-item">
      <img class="d-block w-100" src="${bckImg}" alt="Second slide">
    </div>
    <div class="carousel-item">
      <img class="d-block w-100" src="${sideImg}" alt="Third slide">
    </div>
    <div class="carousel-item">
      <img class="d-block w-100" src="${interiorImge}" alt="Third slide">
    </div>
  </div>
  <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>`);
            console.log("Comes to here")
            $('#imageViewForCarModal').modal('show');
        }
    })


}


function loadpendingReturnAndPaymentToAdminPanel() {
    $('#admincustomermanage').css({display: 'none'})
    $('#admincarmanage').css({display: 'none'})
    $('#paymentAndReturn').css({display: ''})
    $('#bookingDetailManage').css({display: 'none'})
    $('#adminDashboard').css({display: 'none'});
}


function loadBookingDetailToReturn() {
    const adminInput = $('#paymentReturnBidText').val().trim();
    const adminSelection = $('#paymentReturnSelection').val();
    ``
    console.log(adminSelection);
    if (adminInput !== '') {
        $.ajax({
            url: 'http://localhost:8080/demo/api/v1/booking/getopen/' + adminSelection + '/' + adminInput,
            type: 'get',
            contentType: 'application/json',
            success: function (res) {
                console.log(res.data);
                $('#ReturnPaymentTblBody').children().remove();
                if (res.data != null) {
                    for (const data of res.data) {
                        $('#ReturnPaymentTblBody').append(`
                <tr>
                                        <th scope="row">${data.bookingId}</th>
                                        <td>${data.customerName}</td>
                                        <td>${data.rqrdLocation}</td>
                                        <td>${data.dateOfReturn}</td>
                                        <td>${data.driverId}</td>
                                        <td>
                                          <button type="button" class="btn btn-success" value="${data.bookingId}" onclick="calculatePayment(this)">Finalize Payment</button>
</td>
                                    </tr>
`)
                    }
                }
            }
        });
    }


}


function calculatePaymentAmount(val, dmges, milage, dte) {
    console.log("Comes here")
    const bid = val.value;
    if (milage.trim().length == 0) {
        displayErrorToast("Error! Closing milage in the vehicle is not there")
    } else if (dte.trim().length == 0) {
        displayErrorToast("Error! Vehicle actually returned is not there");
    } else {
        $('#returnAndPaymentModel').modal('hide');

        $.ajax({
            url: 'http://localhost:8080/demo/api/v1/return/savereturn',
            type: 'post',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({
                "bid": bid,
                "damages": dmges,
                "dteOfReturn": dte
            }),
            success: function (res) {
                if (res.msg === "Success") {
                    displaySuccessToast("Return has been successully placed")
                    $('#returnAndPaymentModel').modal('hide');
                    finalizePayments(bid, dmges, dte, milage, res.data.rid);
                }
            }

        });


    }

}


function finalizePayments(bid, dmges, dte, milage, rid) {
    const dataSet = new FormData();
    dataSet.append('rid', rid);
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/payment',
        type: 'post',
        data: dataSet,
        contentType: false,
        processData: false,
        success: function (res) {
            $('#paymentViewForPayment').children().remove();
            if (res.msg === "Success") {
                $('#paymentViewForPayment').append(`
                    <div class="modal fade" id="modalCart" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
aria-hidden="true">
    <div class="modal-dialog" role="document">
    <div class="modal-content">
    <!--Header-->
    <div class="modal-header">
    <h4 class="modal-title" id="myModalLabel"><a class="badge badge-info">Payment Details</a></h4>
<button type="button" class="close" data-dismiss="modal" aria-label="Close">
    <span aria-hidden="true">×</span>
</button>
</div>
<!--Body-->
<div class="modal-body">
    <table class="table table-hover">
    <thead>
    <tr>
    <th>Date of Payment</th>
    <th>Basis</th>
    <th>Number of Days</th>
    <th>Driver Chaargers</th>
    <th>Total Amount</th>
</tr>
</thead>
<tbody>
<tr>
<td scope="row">${res.data.dteOfPayment}</td>
       <td><span class="badge badge-secondary">${res.data.isDly == true ? "Daily" : "Monthly"}</span></td>
<td>${res.data.days}</td>
<td>${res.data.driverAmt}</td>
<th>${res.data.totalAmount}</th>
</tr>
</tbody>
</table>
    <div class="row flex-column align-content-center"><span class="badge badge-success">Total Value : </span> ${(res.data.totalAmount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,')}</div>
</div>
<!--Footer-->
<div class="modal-footer">
    <button type="button" class="btn btn-outline-primary" data-dismiss="modal">Close</button>
    </div>
    </div>
    </div>
    </div>                
                `)

                $('#modalCart').modal('show');


            }

        }
    });
}


function loadNumberOfUsersForDash() {
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/customer/customercount',
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            $('#dashUserCount').children().remove();
            $('#dashUserCount').append(`<h3>${res.data}</h3>`)
        }
    });
}


function loadNumberOfBookingsPerToday() {
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking/bookingcount',
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            $('#dashBookingCount').children().remove();
            $('#dashBookingCount').append(`<h3>${res.data}</h3>`)
        }
    });
}


function loadNumberOfAvailableAndReserved() {
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car/availableAndReserved',
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            console.log("Available");
            $('#dashAvlAndResCount').children().remove();
            $('#dashAvlAndResCount').append(`<h6>Available : ${res.data[0]}</h6>`)
            $('#dashAvlAndResCount').append(`<h6>Reserved : ${res.data[1]}</h6>`)
        }
    });
}


function loadActiveBookingsAsToday() {
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking/activebookingcount',
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            console.log("Response " + res);
            $('#dashActveBooking').children().remove();
            $('#dashActveBooking').append(`<h3>${res.data}</h3>`)
        }
    });
}


function loadAvailableAndOccupiesDrivers() {
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/driver/availableAndOccupied',
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            $('#dashAvlAndOccupiedDriver').children().remove();
            $('#dashAvlAndOccupiedDriver').append(`<h6>Available : ${res.data[0]}</h6>`)
            $('#dashAvlAndOccupiedDriver').append(`<h6>Occupied : ${res.data[1]}</h6>`)
        }
    });
}


function loadCarThatNeedMaintainance() {
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car/countcarsformaintainance',
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            $('#dashCrsThatNeedMaintainace').children().remove();
            $('#dashCrsThatNeedMaintainace').append(`<h3>${res.data}</h3>`)
        }
    });
}


