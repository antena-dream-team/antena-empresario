$(document).ready(function() {

    var login = document.getElementById('login');
    var cadastro = document.getElementById('cadastro');
    
    window.onclick = function(event) {
        if (event.target == login) {
            login.style.display = "none";
        }

        if (event.target == cadastro) {
            cadastro.style.display = "none";
        }
    }

    $('#cpf-cadastro').mask('000.000.000-00', {reverse: true});

});

$("#btn-cadastro").click(function(event) {

    event.preventDefault();

    var json = {
      nome: $("#nome-cadastro").val(),
      email: $("#email-cadastro").val(),
      cpf: $("#cpf-cadastro").val(),
      senha: $("#senha-cadastro").val()
    }

    var jsonString = JSON.stringify(json);

    $.post("/cadastroempresario", jsonString, 'json')
        .done(function(){
            location.reload();
        });
})

$('[data-login-form]').on('submit', function(event){
    event.preventDefault();

    var email = event.currentTarget.querySelector('#email-login').value;
    var pass = event.currentTarget.querySelector('#senha-login').value;

    var data = {
        email, senha: pass
    };

    $.post("/Auth", JSON.stringify(data), 'json')
        .done(function(token){
            localStorage.setItem('token', token);
            location.replace('/empresa.html');
        })
});

function abrePopupLogin(event) {
    event.preventDefault();
    document.getElementById('login').style.display='block';    
}

function fechaPopupLogin(event) {
    event.preventDefault();
    document.getElementById('login').style.display='none';    
}

function abrePopupCadastro(event) {
    event.preventDefault();
    document.getElementById('cadastro').style.display='block';    
}

function fechaPopupCadastro(event) {
    event.preventDefault();
    document.getElementById('cadastro').style.display='none';    
}