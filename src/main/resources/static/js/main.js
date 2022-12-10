$('#password, #rpassword').on('keyup', function () {

    let pass1 = $('#password').val();
    let pass2 = $('#rpassword').val();

    if(pass1 != pass2){

        $('#registro').prop('disabled', true);
        $('#error').html('Las contraseñas no coinciden');

    } else {

        $('#registro').prop('disabled', false);
        $('#error').html('');
    }

});