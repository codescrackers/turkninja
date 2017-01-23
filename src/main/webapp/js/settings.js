$(document).ready(function() {
    $("#emailForm").validate({
        rules: {
            email: {
                required: true,
                email: true
            },
            currentPassword: {
                required: true
            }
        },
        messages: {
            email: {
                required: "Email giriniz"
            },
            currentPassword: {
                required: "Şifre giriniz"
            }
        }
    });

    $("#passwordForm").validate({
        rules: {
            currentPassword: {
                required: true
            },
            password: {
                required: true,
                minlength: 6
            },
            password2: {
                required: true,
                equalTo: "#password"
            }
        },
        messages: {
            password: {
                required: "Şifre giriniz",
                minlength: "Şifre çok kısa"
            },
            password2: {
                required: "Şifre giriniz",
                equalTo: "Şifreler eşleşmiyor"
            },
            currentPassword: {
                required: "Şifre giriniz"
            }
        }
    });
});
