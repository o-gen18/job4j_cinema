<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <title>Payment</title>
</head>
<body>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
<script>
    function getParams() {
        return location.search.substr(1).replace(/\D/g,'').split('');
    }

    $('document').ready(function () {
        var params = getParams();
        alert('Params=' + params);
        alert('err=' + params[2]);
        document.getElementById('header').innerHTML = 'Вы выбрали ряд ' + params[0] + ' место ' + params[1] + ', сумма: 500 рублей';
        if (params[3] !== undefined) {
            document.getElementById('header').appendChild(document.createTextNode(
                "Ошибка! Такой номер уже есть в базе, введите другой!"));
        }
    });

    function validate() {
        var username = $('#username').val();
        var phone = $('#phone').val();
        if (username === '' || phone === '') {
            alert('Пожалуйста, введите все данные!');
            return false;
        } else if (isNaN(phone) || phone.length !== 11) {
            alert('Введите одиннадцатизначный номер телефона!');
            return false;
        }
        $.ajax({
            method: 'POST',
            url: 'http://localhost:8080/cinema/booking',
            data: {
                username: username,
                phone: phone,
                row: getParams()[0],
                seat: getParams()[1]},
            dataType: 'json'
        });
    }
</script>

<div class="container">
    <div class="row pt-3">
        <h3 id="header">
        </h3>
    </div>
    <div class="row">
        <form>
            <div class="form-group">
                <label for="username">ФИО</label>
                <input type="text" class="form-control" id="username" placeholder="ФИО">
            </div>
            <div class="form-group">
                <label for="phone">Номер телефона</label>
                <input type="text" class="form-control" id="phone" placeholder="Номер телефона">
            </div>
            <button type="submit" class="btn btn-success" onclick="return validate()">Оплатить</button>
        </form>
    </div>
</div>
</body>
</html>