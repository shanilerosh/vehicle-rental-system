$('#mytab a').on('click', function (e) {
    console.log("fsfa")
    e.preventDefault()
    $(this).tab('show')
})



function savecustomer() {
        if(!validateFields()){
            return;
        }
        $('.signupstatus').children().remove();
        let formData=new FormData($('#signupform')[0]);
        console.log(formData);
        $.ajax({
            url:'http://localhost:8080/demo/api/v1/customer/savecustomer',
            type:'post',
            data: formData,
            contentType:false,
            processData:false,
            success: function (res) {
                console.log(res);
                if(res.msg=="Success"){
                    $('.signupstatus').append(
                        `<div class="alert alert-dismissible alert-success">
  <button type="button" class="close" data-dismiss="alert">&times;</button>
  <strong>Successfully created</strong> You have successfully registered please login to enjoy our service</a>.
</div>`)} else{

                    $('.signupstatus').append(
                        `<div class="alert alert-dismissible alert-danger">
  <button type="button" class="close" data-dismiss="alert">&times;</button>
  <strong>Error ! </strong> ${res.data}</div>`)
                }
            }
        })
 }


 function checkLoginDetail() {
    const loginEmail = $('#loginEmail').val();
    const loginPass = $('#loginPassword').val();


     function validateLoginFields() {
         $('.loginEmailVal').children().remove();
         $('.loginPassVal').children().remove();
        if(loginEmail.trim()==''){
            $('.loginEmailVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
                '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
                '  <strong> Email is Empty</strong>It is compulsory to provide your email to proceed.\n' +
                '</div>');
            return false;
        }
         if(loginPass.trim()==''){
             $('.loginPassVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
                 '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
                 '  <strong> Password is Empty </strong>It is compulsory to provide your Password to proceed.\n' +
                 '</div>');
             return false;
         }
         return true;
     }


     if(!validateLoginFields()){
         return;
     }

     $('.logingVal').children().remove();

     $.ajax({
                url: 'http://localhost:8080/demo/api/v1/user/checkuser',
                type: 'GET',
                dataType: 'json',
                headers : {
                    username: loginEmail,
                    password: loginPass,
                },
                success: function (res) {
                    if(res.code==500) {
                        $('.logingVal').append(
                            `<div class="alert alert-dismissible alert-danger">
  <button type="button" class="close" data-dismiss="alert">&times;</button>
  <strong>Error ⚠ </strong>${res.data} 
</div>`)

                    }else {
                        $('.logingVal').append(
                            `<div class="alert alert-dismissible alert-success">
  <button type="button" class="close" data-dismiss="alert">&times;</button>
  <strong>Success ☺ </strong> You have successfully Logged in
</div>`)

                        if (res.data.role === "customer") {
                            sessionStorage.setItem("custEmail", res.data.partyId)
                            window.location.href = "registereduserpage.html"
                        } else if (res.data.role === "Admin") {
                            sessionStorage.setItem("adminEmail", res.data.partyId)
                            window.location.href = "AdminPanel.html"
                        } else if (res.data.role == "Driver") {
                            sessionStorage.setItem("driverId", res.data.partyId)
                            window.location.href = "Driver.html"
                        }
                    }
                }
         })

 }





 const validateFields = () => {
    const custName = $('#customerName').val();
    const customerAddress = $('#customerAddress').val();
    const customerDoc = $('#customerDoc').val();
    const customerPassword = $('#customerPassword').val();
    const customerEmail = $('#customerEmail').val();

    $('.custNameVal').children().remove();
    $('.emailVal').children().remove();
    $('.docVal').children().remove();
    $('.passwordVal').children().remove();
    $('.addressVal').children().remove();

    if(custName.trim()=='') {
        $('.custNameVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
            '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
            '  <strong>Name is Empty!</strong> Dont worry we protect your privacy.\n' +
            '</div>')
        return false;
    }

     if(customerEmail.trim()=='') {
         $('.emailVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
             '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
             '  <strong>Email is empty!</strong> Your email is considered as your username. So this is important.Noting to worry about we won;t spam you.\n' +
             '</div>')
         return false;
     }

     if(customerAddress.trim()=='') {
         $('.addressVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
             '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
             '  <strong>Address is Empty !</strong> You matter to us. Just for a reference we are keeping this.\n' +
             '</div>')
         return false;
     }
     if(customerPassword.trim()=='') {
         $('.passwordVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
             '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
             '  <strong>Password is Empty!</strong>Password is compulsory to log in your account.\n' +
             '</div>')
         return false;
     }
     if(customerPassword.trim().length<7) {
         $('.passwordVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
             '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
             '  <strong>Password length is less than 7! </strong>In order to have a strong password enter atleast 7 characters.\n' +
             '</div>')
         return false;
     }

     if($('#customerDoc')[0].files.length==0) {
         $('.docVal').append('<div class="alert alert-dismissible alert-danger mt-2">\n' +
             '  <button type="button" class="close" data-dismiss="alert">&times;</button>\n' +
             '  <strong>Document not provided!</strong> We have to keep track of a valid document.Please upload one..\n' +
             '</div>')
         return false;
     }
     return true;
 }

