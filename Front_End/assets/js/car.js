loadCar();

function loadCar() {
    $('#carView').children().remove();
    $.ajax({
        url: 'http://localhost:8080/demo/api/v1/car',
        type: 'get',
        contentType: 'application/json',
        success: function (res) {
            console.log(res);
            for(let resp of res.data) {
                console.log(resp.frntImg);
                const imgSrc=resp.frntImg.slice(90,resp.frntImg.length);
                console.log(imgSrc);
                $('#carView').append(`<div class="card" style="width: 18rem;">
  <img class="card-img-top" src="${imgSrc}" alt="Card image cap">
  <div class="card-body">
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
  </div>
</div>`)
            }
        }
    })
}




function saveCar() {
    if(!isCarValid()){
        return;
    }

    let formData = new FormData($('#carAddForm')[0]);
        $.ajax({
            url: 'http://localhost:8080/demo/api/v1/car/savecar',
            type: 'post',
            data: formData,
            contentType: false,
            processData: false,
            success: function (res) {
                console.log(res);
                loadCar();
            }
        });
}



function isCarValid() {
    const carName = $('#carName').val();
    const carBrand = $('#carBrand').val();
    const carRegNumber = $('#carRegNumber').val();
    const carMonthlyRate = $('#carMonthlyRate').val();
    const carDailyRate = $('#carDailyRate').val();
    const carFreeKmPerDay = $('#carFreeKmPerDay').val();
    const carFreeKmPerMonth = $('#carFreeKmPerMonth').val();
    const carPricePerExtraKm = $('#carPricePerExtraKm').val();
    const carNumberOfPassenger = $('#carNumberOfPassenger').val();
    const carStateSelect = $('#carStateSelect').val();
    const carColor = $('#carColor').val();


    $('.carNameVal').children().remove();
    $('.carBrandVal').children().remove();
    $('.carRegVal').children().remove();
    $('.carMonthlyRateeVal').children().remove();
    $('.carDailyRateVal').children().remove();
    $('.carFreeKmPerDayVal').children().remove();
    $('.carFreeKmPerMonthVal').children().remove();
    $('.carPricePerExtraKmVal').children().remove();
    $('.carNumberOfPassengerVal').children().remove();
    $('.carColorVal').children().remove();
    $('.caImageBackVal').children().remove();
    $('.caImageSideVal').children().remove();
    $('.caImageFrontVal').children().remove();
    $('.caImageInteriorVal').children().remove();

    if(carName.trim()==''){
        $('.carNameVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
            '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
            '  <strong>Empty! </strong> Enter the Car Name.\n' +
            '</div>');
        return false;
    }
    if(carBrand.trim()==''){
        $('.carBrandVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
            '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
            '  <strong>Empty! </strong>Car Name is compulsory\n' +
            '</div>');
        return false;
    }
    if(carRegNumber.trim()==''){
        $('.carRegVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
            '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
            '  <strong>Empty! </strong>Car Registration is compulsory\n' +
            '</div>');
        return false;
    }
    if(carMonthlyRate.trim()==''){
        $('.carMonthlyRateeVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
            '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
            '  <strong>Empty! </strong>Monthly rate is compulsory\n' +
            '</div>');
        return false;
    }
    if(carDailyRate.trim()==''){
        $('.carDailyRateVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
            '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
            '  <strong>Empty! </strong>Daily rate is compulsory\n' +
            '</div>');
        return false;
    }
    if(carFreeKmPerDay.trim()==''){
        $('.carFreeKmPerDayVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
            '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
            '  <strong>Empty! </strong>Free km is compulsory\n' +
            '</div>');
        return false;
    }
    if(carFreeKmPerMonth.trim()==''){
        $('.carFreeKmPerMonthVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
            '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
            '  <strong>Empty! </strong>Monthly Free km is compulsory\n' +
            '</div>');
        return false;
    }
    if(carPricePerExtraKm.trim()==''){
        $('.carPricePerExtraKmVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
            '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
            '  <strong>Empty! </strong>Car Price per extra km is compulsory\n' +
            '</div>');
        return false;
    }
    if(carNumberOfPassenger.trim()==''){
        $('.carNumberOfPassengerVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
            '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
            '  <strong>Empty! </strong>Passenger No is compulsory\n' +
            '</div>');
        return false;
    }
    if(carColor.trim()==''){
        $('.carColorVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
            '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
            '  <strong>Empty! </strong>Car color is compulsory\n' +
            '</div>');
        return false;
    }

    if($('#carImageInterior')[0].files.length==0){
        $('.caImageInteriorVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
            '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
            '  <strong>Empty! </strong>Interior Image is compulsory\n' +
            '</div>');
        return false;
    }

    if($('#carImageFront')[0].files.length==0){
        $('.caImageFrontVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
            '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
            '  <strong>Empty! </strong>Front Image is compulsory\n' +
            '</div>');
        return false;
    }

    if($('#carImageSide')[0].files.length==0){
        $('.caImageSideVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
            '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
            '  <strong>Empty! </strong>Side Image is compulsory\n' +
            '</div>');
        return false;
    }

    if($('#carImageBack')[0].files.length==0){
        $('.caImageBackVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
            '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
            '  <strong>Empty! </strong>Back Image is compulsory\n' +
            '</div>');
        return false;
    }

    return true;

}

function validateCar(evt) {
    var theEvent = evt || window.event;

    // Handle paste
    if (theEvent.type === 'paste') {
        key = event.clipboardData.getData('text/plain');
    } else {
        // Handle key press
        var key = theEvent.keyCode || theEvent.which;
        key = String.fromCharCode(key);
    }
    var regex = /[0-9]|\./;
    if( !regex.test(key) ) {
        theEvent.returnValue = false;
        if(theEvent.preventDefault) theEvent.preventDefault();
    }
}
