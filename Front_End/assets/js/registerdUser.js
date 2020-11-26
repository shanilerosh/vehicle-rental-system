$('#customerOrder').css({display: 'none'});
$('#bookingMngt').css({display: 'none'});
$('#viewTheBucket').css({display: 'none'});
$('#profileSettings').css({display: 'none'});

let lisOfBookings = [];
let listOfAllCars = '';
let isEditable = false;
let customerEmail = sessionStorage.getItem("custEmail");
console.log(customerEmail);
localStorage.clear();
loadCarForRegsitered();
countBookingDetails();


setInterval(function () {
    const item = sessionStorage.getItem("custEmail");

    if (item == null) {
        alert("Error your account has been logged out please login again")
    }

}, 3000);


function loadCarForRegsitered() {
    $('#displayAlltheCars').children().remove();
    let x = $('#displayAlltheCars').append(`<div class="row" id="tem0">`)
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car',
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            listOfAllCars=res.data;
            let counter=0;
            for(let resp of res.data) {
                const imgSrc=resp.frntImg.slice(90,resp.frntImg.length);
                console.log(imgSrc);
                $('#tem0').append(`<div class="col-4 mt-3 mb-3"><div class="card" style="width: 18rem;">
  <img width="200px" height="200px" src="${imgSrc}" class="card-img-top" alt="...">
  <div class="card-body">
    <h5 class="card-title">${resp.name}</h5>
    <small>${resp.registrationNumb}</small>
    <p class="card-text">${resp.brand}</p>
    <p class="card-text">${resp.carType}</p>
    <button class="btn btn-primary" type="button" onclick="viewDetail(this)">Detail</button>
  </div>
</div></div>`)
            }
        }
    })
}


let crid = '';
let registration = '';
let carBrand = '';
let color = '';
let dlyprice = '';
let mntlyprice = '';
let numberOfPssng = '';
let carName = ''
let depAmount = '';

function viewDetail(evt) {
    const selectedReg = evt.parentElement.children[1].innerHTML;
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car/' + selectedReg,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            console.log(res.data.deposit);
            crid = res.data.reg;
            registration = res.data.registrationNumb;
            numberOfPssng = res.data.nmberOfPssngers;
            carBrand = res.data.brand
            carName = res.data.name;
            color = res.data.brand;
            depAmount = res.data.deposit;
            dlyprice = res.data.dlyRate;
            mntlyprice = res.data.mnthlyRate;
            $('#model').children().remove();
            console.log(res.data.frntImg);
            const frntImg = res.data.frntImg.slice(90, res.data.frntImg.length);
            const bckImg = res.data.bckImg.slice(90, res.data.bckImg.length);
            const sideImg = res.data.sideImg.slice(90, res.data.sideImg.length);
            const interiorImge = res.data.frntImg.slice(90, res.data.interiorImge.length);
            $('#model').append(`
<div class="modal fade small" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">${res.data.reg}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                          <div id="carouselExampleIndicators1" class="carousel slide" data-ride="carousel">
                                    <div id="carouselExampleIndicators2" class="carousel slide" data-ride="carousel">
                                    <div id="carouselExampleIndicators3" class="carousel slide" data-ride="carousel">
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
  </div>
  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>
              <div class="modal-body">
              <div class="row border-1">
                <div class="col-3">Brand: ${res.data.brand}</div>
                <div class="col-3">Name: ${res.data.name}</div>
                <div class="col-3">Color :${res.data.color}</div>
                <div class="col-3">Price(Daily): ${res.data.dlyRate}</div>
              </div>
               <div class="row">
                <div class="col-4">Passengers: ${res.data.nmberOfPssngers}</div>
                <div class="col-4">Registration: ${res.data.registrationNumb}</div>
               </div>
               <hr />
</div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="confirmToBucket(crid,depAmount,color,carBrand,name,registration,numberOfPssng,dlyprice,mntlyprice)">Add to Cart</button>
            </div>
        </div>
    </div>
</div>
`)
            $('#myModal').modal('show');
        }
    })
}


function confirmToBucket(crid,depAmount,color,carBrand,crname,registration,numberOfPssng,dlyprice,mntlyprice) {
    $('#myModal').modal('hide');
    $('#alertSuccessToTheBucket').modal('show');
    lisOfBookings.push({
        reg:crid,
        dep:depAmount,
        col:color,
        brand:carBrand,
        name:crname,
        registration:registration,
        psg:numberOfPssng,
        dly:dlyprice,
        mthly:mntlyprice
    });
    loadAllDataToBucket();
}


function loadAllDataToBucket() {
    $('#bucketTbody').children().remove();
    console.log(lisOfBookings);
    if(lisOfBookings.length!==0){
        for (let i = 0; i <lisOfBookings.length; i++) {
            $('#bucketTbody').append(`  <tr><th scope="row">${lisOfBookings[i].reg}</th>
      <td>${lisOfBookings[i].registration}</td>
      <td>${lisOfBookings[i].name}</td>
      <td>${lisOfBookings[i].psg}</td>
      <td>${formatToCurrency(lisOfBookings[i].dly)}</td>
      <td>${formatToCurrency(lisOfBookings[i].mthly)}</td>
      </tr>`)

            $($('#bucketTbody').children()[i]).click((e)=>{
                console.log('click',$(e.currentTarget).children().get(1).innerHTML);
                $('#bcktReg').val($(e.currentTarget).children().get(1).innerHTML);
                $('.bucketVal').children().remove();
                $('.bucketVal').append(`<div class="alert alert-info" role="alert">
  <h5 class="alert-heading">Waiver Deposit</h5>
  <p>In order to confirm the booking. You must upload an image of the bank slip of the waiver deposit</p>
</div>`)

            })
        }
    }

}


function formatToCurrency(amount){
    return (amount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
}


function finalizeBooking() {
    const myForm = new FormData($('#bcktForm')[0]);
    const checkBox=$('#bcktDriver').is(":checked");
    const crid=$('#bcktReg').val();
    console.log('crid',crid)
    myForm.append('customer',customerEmail);
    myForm.append('driver',checkBox);
    myForm.append('vehicleId',crid);
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking//bookingdetail',
        type: 'post',
        data: myForm,
        contentType: false,
        processData: false,
        success: function (res) {
            $('.bucketVal').children().remove();
            if(res.msg=="Success") {
                displaySuccessToast("You Booking has been successfully placed");
                for (let i = 0; i < lisOfBookings.length; i++) {
                    if(lisOfBookings[i].registration===crid){
                        lisOfBookings.splice(i,1);
                    }
                }

                $('#bucketTbody').children().remove();
                loadAllDataToBucket();
                clearFieldsOfBucket();
                $('#bookingSuccessAlert').modal('show');
            }else{
                $('.bucketVal').append(`<div class="alert alert-danger" role="alert">
  <h5 class="alert-heading">Error</h5>
  <p>${res.data}</p>
</div>`)

            }
        }
    });
}

function validateBookingFields() {

}


function getOrdersBasedOnStatus() {
    const option = $('#orderSortOption').val();
    console.log(option);
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking/getonstatus/' + customerEmail + '/' + option,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            console.log(res.data);
            $('#orderStatustbody').children().remove();
            for (const data of res.data) {
                console.log(data);
                $('#orderStatustbody').append(`
                    <tr>
                        <th scope="row">${data.detail}</th>
                        <td>${data.car.registrationNumb}</td>
                        <td>${data.dateTime}</td>
                        <td><span class="badge badge-info">${data.status}</span></td>
                        <td>${data.driver === null ? "No Driver" : data.driver.did}</td>
                        <td><button type="button" class="btn btn-success" value="${data.detail}" onclick="viewDetailOfBookingMgt(this)">Details</button></td>
                    </tr>
                `)
            }
        }
    });
}


$('#carsSortOption').on('change',()=>{
    $('#opt')
})




function renderCarsForRegisteredUser() {
    $('#displayAlltheCars').children().remove();
    let x = $('#displayAlltheCars').append(`<div class="row" id="tem0">`)
    const selected = $('#registeredselection').val();
    const custInput = $('#registeredselectionval').val();
    if (custInput.trim() === '') {
        loadCarForRegsitered();
    } else {

        $.ajax({
            url: 'http://localhost:8080/demo/api/v1/car/filter/' + selected + '/' + custInput,
            type: 'get',
            contentType: 'application/json',
            success: function (res) {
                listOfAllCars = res.data;
                for (let resp of res.data) {
                    const imgSrc = resp.frntImg.slice(90, resp.frntImg.length);
                    console.log(imgSrc);
                    $('#tem0').append(`<div class="col-4 mt-3 mb-3"><div class="card" style="width: 18rem;">
  <img width="200px" height="200px" src="${imgSrc}" class="card-img-top" alt="...">
  <div class="card-body">
    <h5 class="card-title">${resp.name}</h5>
    <small>${resp.registrationNumb}</small>
    <p class="card-text">${resp.brand}</p>
    <p class="card-text">${resp.carType}</p>
    <button class="btn btn-primary" type="button" onclick="viewDetail(this)">Details</button>
  </div>
</div></div>`)
                }
            }
        })
    }
}


function displayCarsOnClick() {
    $('#bookingMngt').css({display: 'none'});
    $('#viewTheBucket').css({display: 'none'});
    $('#profileSettings').css({display: 'none'});
    $('#carDisplay').css({display: ''});
    loadCarForRegsitered();
}


function displayBucketOnClick() {
    $('#alertSuccessToTheBucket').modal('hide');
    $('#bookingMngt').css({display:'none'});
    $('#viewTheBucket').css({display:''});
    $('#carDisplay').css({display: 'none'});
    $('#profileSettings').css({display: 'none'});
}

function displayBookingMgt() {
    getOrdersBasedOnStatus();
    $('#bookingMngt').css({display:''});
    $('#viewTheBucket').css({display:'none'});
    $('#carDisplay').css({display: 'none'});
    $('#profileSettings').css({display: 'none'});
}



function clearFieldsOfBucket() {
    $('#bcktReg').val('');
    $('#bcktLocation').val('');
    $('#bcktDate').val('');
    $('#returnDate').val('');
    $('#customerDoc').val('')
}


function viewDetailOfBookingMgt(val) {
    console.log("herew isnse")
    const bookingId = val.value;
    console.log(bookingId);
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking//getOneDetail/' + bookingId,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            console.log(res.data);
            if (res.data.status === "pending") {
                $('#bookingDetailOpenStatusDes').children().remove();
                $('#bookingDetailPendingStatus').modal('show');
                $('#bookingDetailOpenStatusDes').append(`<p>Hold on your request is still pending</p>`);
            } else if (res.data.status === "open") {
                $('#bookingDetailOpenStatusDes').children().remove();
                $('#bookingDetailOpenStatus').modal('show');
                $('#bookingDetailOpenStatusDes').append(`<p>
                You booking Detail has been accepted.${res.data.driverId == "No Driver" ? "You can visit us to get the car" : "You driver Details are : "}
                ${res.data.driverId !== "No Driver" ? "Driver ID :" + res.data.driverId : ""


                }  
</p>`)
            } else if (res.data.status === "denied") {
                $('#bookingDetailDenyStatusDes').children().remove();
                $('#bookingDetailDenyStatus').modal('show');
                $('#bookingDetailDenyStatusDes').append(`<p>This reason for the denial is ${res.data.remarks}. You deposit will be refunded back to your account</p>`)
            }
        }
    });

}


function countBookingDetails() {
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


function editProfileSettings() {
    $('#bookingMngt').css({display: 'none'});
    $('#viewTheBucket').css({display: 'none'});
    $('#carDisplay').css({display: 'none'});
    $('#profileSettings').css({display: ''});
    isEditable = true;
    let mybookingList = '';
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking/getonstatus/' + customerEmail + '/' + 'open',
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            if (res.data.length != 0) {
                isEditable = false;
                for (const data of res.data) {
                    mybookingList += (" " + data.detail);
                    console.log(mybookingList)
                }
                displayWarningToast("Only contact field is editable as there are bookinf as : " + mybookingList)
            }
            if (!isEditable) {
                $('#regCustomerName').prop("readonly", true);
                $('#regCustomerAddress').prop("readonly", true);
                $('#regCustNIC').prop("disabled", true);
                $('#regCustomerPassword').prop("readonly", true);
                $('#regCustomerRePassword').prop("readonly", true);
            }
        }
    });
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/customer/searchonecustomer/' + customerEmail,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            const data = res.data;
            console.log(data);
            $('#regCustomerName').val(data.name);
            $('#regCustomerAddress').val(data.address);
            $('#regCustomerContact').val(data.contact);
        }
    });

}

function updateRegCustomer() {
    if (isEditable) {
        let formData = new FormData($('#customerUpdateform')[0]);
        $.ajax({
            url: 'http://localhost:8080/demo/api/v1/customer/updateregistered/' + customerEmail,
            type: 'post',
            data: formData,
            contentType: false,
            processData: false,
            success: function (res) {
                if (res.msg == "Success") {
                    displaySuccessToast("Contact Detail Successfull updates")
                } else {
                    displayErrorToast(res.data);
                }
            }
        })
    } else {
        let formData = new FormData();
        const contact = $('#regCustomerContact').val();
        formData.append("contact", contact);
        $.ajax({
            url: 'http://localhost:8080/demo/api/v1/customer/updatecontact/' + customerEmail,
            type: 'post',
            data: formData,
            contentType: false,
            processData: false,
            success: function (res) {
                if (res.msg == "Success") {
                    displaySuccessToast("Contact Detail Successfull updates")
                } else {
                    displayErrorToast(res.data);
                }
            }
        })


    }

}
