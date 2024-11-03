function verify() {
    var password1 = document.forms['form']['password'].value;
    var password2 = document.forms['form']['verifyPassword'].value;
    if (password1 == null || password2 == "" || password1 != password2) {
        document.getElementById("error").innerHTML= "Please make sure all fields and entered, and that both passwords match.";
        return false;
    }
    var role1 = document.forms['form']['admin'];
    var role2 = document.forms['form']['member'];
    var role3 = document.forms['form']['guest'];

    if (role1.checked == false && role2.checked == false && role3.checked == false) {
        document.getElementById("error").innerHTML= "You must select at least one role.";
        return false;
    }
}