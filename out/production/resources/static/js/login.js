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

//    nome = $("#nome-cadastro").val();
//    email = $("#email-cadastro").val();
//    cpf = $("#cpf-cadastro").val();
//    senha = $("#senha-cadastro").val();

    json = {
           nome: $("#nome-cadastro").val(),
           email: $("#email-cadastro").val(),
           cpf: $("#cpf-cadastro").val(),
           senha: $("#senha-cadastro").val()
       }

    jsonString = JSON.stringify(json);

    $.post("/cadastroempresario",jsonString,'json');
})

$('[data-login-form]').on('submit', function(event){
    event.preventDefault();

    var email = event.currentTarget.querySelector('#email-login').value;
    var pass = event.currentTarget.querySelector('#senha-login').value;

    var data = {
        email, pass
    };

    $.post("/loginempresario", JSON.stringify(data), 'json');
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