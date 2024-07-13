function validate() {
    const loginField = document.getElementById("login");
    const passField = document.getElementById("password");
    const errorElement = document.getElementById("error");

    const regex = /^[A-Za-z0-9]{4,}$/;

    if(!regex.test(loginField.value)) {
        errorElement.innerHTML = "Login musi mieć co najmniej 4 znaki i składać się wyłącznie z liter i liczb";
        errorElement.style.display = "block";
        return false;
    }

    if(!regex.test(passField.value)) {
        errorElement.innerHTML = "Hasło musi mieć co najmniej 4 znaki i składać się wyłącznie z liter i liczb";
        errorElement.style.display = "block";
        return false;
    }

    return true;
}