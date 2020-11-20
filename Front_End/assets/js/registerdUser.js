let lisOfBookings=[];
$('#confAlert').alert('close');
let listOfAllCars='';
let customerEmail='sdgds';

loadCarForRegsitered();


let b=10;
function loadCarForRegsitered() {
    $('#displayAlltheCars').children().remove();
    let x=$('#displayAlltheCars').append(`<div class="row" id="tem0">`)
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car',
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            listOfAllCars=res.data;
            let counter=0;
            for(let resp of res.data) {
                const imgSrc=resp.frntImg.slice(90,resp.frntImg.length);
                b=resp;
                console.log(imgSrc);
                $('#tem0').append(`<div class="col-4 mt-3 mb-3"><div class="card" style="width: 18rem;">
  <img width="200px" height="200px" src="${imgSrc}" class="card-img-top" alt="...">
  <div class="card-body">
    <h5 class="card-title">${resp.name}</h5>
    <small>${resp.registrationNumb}</small>
    <p class="card-text">${resp.brand}</p>
    <p class="card-text">${resp.carType}</p>
    <button class="btn btn-primary" type="button" onclick="viewDetail(this)">Go somewhere</button>
  </div>
</div></div>`)
            }
        }
    })
}

$('#carView').append(`</div>`);


let crid='';
let carBrand='';
let color='';
let price='';
let carName=''
function viewDetail(evt) {
    const selectedReg = evt.parentElement.children[1].innerHTML;
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car/' + selectedReg,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            crid=res.data.registrationNumb;
            carBrand=res.data.brand;
            color=res.data.brand;
            price=res.data.mnthlyRate;
            price=res.data.name;
            //Change this to field in the deposit
            depAmount=res.data.mnthlyRate;
            $('#model').children().remove();
            console.log(res.data.frntImg);
            const frntImg=res.data.frntImg.slice(90,res.data.frntImg.length);
            const bckImg=res.data.bckImg.slice(90,res.data.bckImg.length);
            const sideImg=res.data.sideImg.slice(90,res.data.sideImg.length);
            const interiorImge=res.data.frntImg.slice(90,res.data.interiorImge.length);
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
                <div id="carouselExampleIndicators" class="carousel slide active" data-ride="carousel">
  <ol class="carousel-indicators">
    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
  </ol>
  <div class="carousel-inner">
    <div class="carousel-item active d-block img-fluid">
      <img class="d-block w-100" src="${frntImg}" alt="First slide">
    </div>
    <div class="carousel-item d-block img-fluid">
      <img class="d-block w-100" src="${bckImg}" alt="Second slide">
    </div>
    <div class="carousel-item d-block img-fluid">
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
                <button type="button" class="btn btn-primary" onclick="confirmToBucket(crid,depAmount,color,price,carBrand,name)">Add to Cart</button>
            </div>
        </div>
    </div>
</div>
`)
            $('#myModal').modal('show');
        }
    })
}


function confirmToBucket(crid,depAmount,color,price,carBrand,name) {
    $('#myModal').modal('hide');
    lisOfBookings.push({
        reg:crid,
        dep:depAmount,
        col:color,
        prc:price,
        brand:carBrand
    });
    loadAllDataToBucket()
}


function loadAllDataToBucket() {
    $('#bucketTbody').children().remove();
    for (let i = 0; i <lisOfBookings.length; i++) {
        $('#bucketTbody').append(`  <tr><th scope="row">${lisOfBookings[i].rid}</th>
      <td>${lisOfBookings[i].reg}</td>
      <td>${lisOfBookings[i].col}</td>
      <td>${lisOfBookings[i].price}</td></tr>`)
        $($('#bucketTbody').children()[i]).click((e)=>{
            console.log($(e.currentTarget).children().get(1).innerHTML);
            $('#bcktReg').val($(e.currentTarget).children().get(1).innerHTML);
            $('.bucketVal').children().remove();
            $('.bucketVal').append(`<div class="alert alert-info" role="alert">
  <h5 class="alert-heading">Waiver Deposit</h5>
  <p>In order to confirm the booking. You must upload an image of the bank slip of the waiver deposit of Rs</p>
</div>`)

        })
    }
}




function finalizeBooking() {
    if(validateBookingFields()) {
    }
    const myForm = new FormData($('#bcktForm')[0]);
    const checkBox=$('#bcktDriver').is(":checked");
    const crid=$('#bcktReg').val();
    console.log('car Id',crid,checkBox);
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
            if(res.msg=="Success") {
                const reg = $('#bcktReg').val();
                $('#bucketTbody tr').each(()=>{

                })
            }
        }
    });
}

function validateBookingFields() {

}


function getOrdersBasedOnStatus() {
    const option = $('#orderSortOption').val();
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/booking/getonstatus/'+customerEmail+'/'+option,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            $('#orderStatustbody').children().remove();
            const dataSet=res.data;
            for(const data of dataSet) {
                $('#orderStatustbody').append(`
                    <tr>
                        <th scope="row">${data.detail}</th>
                        <td>${data.car.registrationNumb}</td>
                        <td>${data.dateTime}</td>
                        <td>${data.status}</td>
                        <td>${data.driver==null ? "No Driver" : data.driver.driverName}</td>
                    </tr>
                `)
            }
        }
    });
}


$('#carsSortOption').on('change',()=>{
    $('#opt')
})
