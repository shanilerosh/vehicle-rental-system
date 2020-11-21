$('#admincustomermanage').css({display:'none'})

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

