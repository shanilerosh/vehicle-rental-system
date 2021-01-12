loadCar();

function loadCar() {
    $('#carsToMainPage').children().remove();
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car//getcarbystate/Availabe',
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            console.log(res);
       for (let resp of res.data) {
           console.log(resp.frntImg);
           const imgSrc = resp.frntImg.slice(90, resp.frntImg.length);
           console.log(imgSrc);
           $('#carsToMainPage').append(`<div class="card ml-3 mt-4" style="width: 18rem;">
  <img class="card-img-top" src="${imgSrc}" alt="Card image cap" style="width: 18rem; height: 15rem">
  <div class="card-body">
    <p class="card-text">Car Registration : ${resp.registrationNumb}</p>
    <p class="card-text">Car color : ${resp.color}</p>  
    <p class="card-text">Car Brand : ${resp.brand}</p>  
    <p class="card-text">Car Passengers : ${resp.nmberOfPssngers}</p>
    <button type="button" class="btn btn-success" value="${resp.reg}" onclick="viewMoreDetailsOfCar(this)">More Info</button>
  </div>
</div>`)
            }
        }
    })
}


function renderCarsByFilter() {
    const selected = $('#nonregisteredselection').val();
    const custInput = $('#nonregisteredselectionval').val();

    if(custInput.trim()===''){
        loadCar();
    }else{
        $('#carsToMainPage').children().remove();
        $.ajax({
            url: 'http://localhost:8080/demo/api/v1/car/filter/'+selected+'/'+custInput,
            type: 'get',
            contentType: 'application/json',
            success: function (res) {
                console.log(res);
                if(res.msg==="Success"){
                    for (let resp of res.data) {
                        console.log(resp.frntImg);
                        const imgSrc = resp.frntImg.slice(90, resp.frntImg.length);
                        console.log(imgSrc);
                        $('#carsToMainPage').append(`<div class="card ml-3 mt-4" style="width: 18rem;">
  <img class="card-img-top" src="${imgSrc}" alt="Card image cap" style="width: 18rem; height: 15rem">
  <div class="card-body">
    <p class="card-text">Car Registration : ${resp.registrationNumb}</p>
    <p class="card-text">Car color : ${resp.color}</p>  
    <p class="card-text">Car Brand : ${resp.brand}</p>  
    <p class="card-text">Car Passengers : ${resp.nmberOfPssngers}</p>
    <button type="button" class="btn btn-success" value="${resp.reg}" onclick="viewMoreDetailsOfCar(this)">More Info</button>
  </div>
</div>`)
                }
                }
            }
        })

    }

}


function viewMoreDetailsOfCar(val) {
    const crid = val.value;
    console.log(crid);
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car/getbucket/' + crid,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            $('#unregisteredDetailHeading').children().remove();
            const frntImg = res.data.frntImg.slice(90, res.data.frntImg.length);
            const bckImg = res.data.bckImg.slice(90, res.data.bckImg.length);
            const sideImg = res.data.sideImg.slice(90, res.data.sideImg.length);
            const interiorImge = res.data.frntImg.slice(90, res.data.interiorImge.length);
            $('#additionInfo').children().remove();
            $('#additionInfo').append(`
<div class="modal fade small" id="additionalInforModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true" style="color: black">
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
                <button type="button" class="btn btn-primary" onclick="loadLoginPage()">Login to Buy</button></a>
            </div>
        </div>
    </div>
</div>
`)
            $('#additionalInforModal').modal('show');

        }
    })


}



function loadLoginPage() {
    window.location.href = "login.html";
}
