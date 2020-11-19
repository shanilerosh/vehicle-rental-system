loadCarForRegsitered()
let b=10;
function loadCarForRegsitered() {
    $('#displayAlltheCars').children().remove();
    let x=$('#displayAlltheCars').append(`<div class="row" id="tem0">`)
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car',
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
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



function viewDetail(evt) {
    const selectedReg = evt.parentElement.children[1].innerHTML;
    console.log(selectedReg)
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car/' + selectedReg,
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            console.log(res);
            $('#model').children().remove();
            console.log(res);
            console.log(res.data.frntImg);
            const frntImg=res.data.frntImg.slice(90,res.data.frntImg.length);
            const bckImg=res.data.bckImg.slice(90,res.data.bckImg.length);
            const sideImg=res.data.sideImg.slice(90,res.data.sideImg.length);
            const interiorImge=res.data.frntImg.slice(90,res.data.interiorImge.length);
            $('#model').append(`
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
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
              <div class="row">
                <div class="col-4">Name</div>
                <div class="col-4">Age</div>
                <div class="col-4">Salary</div>
                <div class="col-4">ad</div>
               </div>
</div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>
`)
            $('#myModal').modal('show');

        }
    })
}
