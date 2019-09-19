(function() {

  let defaultModel = {
    titulo: '',
    'descricao-breve': '',
    'descricao-completa': '',
    'descricao-tecnologias': '',
    'link-externo-1': '',
    'link-externo-2': '',
    fase: 0,
    reuniao: {
      data: '',
      horario: '',
      local: '',
      'datas-possiveis': []
    },
    status: {
      negado: false,
      motivo: ''
    },
    entregas: [],
    alunos: [],
    'responsavel-cadi': '',
    'responsavel-professor': [],
    'responsavel-empresario': ''
  };

  let publishProjectButton = $('[data-publish-project]');
  
  publishProjectButton.click(function(e) {
    
    let formNewProject = $('[data-form-new-project]');
    let inputsData = formNewProject.serializeArray();
    let project = {
      ...defaultModel,
      fase: 1
    };

    inputsData.forEach(input => {
      project[input.name] = input.value; 
    });

    $.ajax({
      type: "POST",
      url: '/cadastroprojeto',
      data: JSON.stringify(project),
      success: function() {
        console.log('foi');
      },
      dataType: 'json'
    });
  });
})();