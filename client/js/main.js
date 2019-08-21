(function() {

  function _getUserInfo() {
    return {
      nome: 'Fábio Romeiro',
      email: 'flromeiroc@gmail.com',
      cpf: '11111111111',
      senha: '123'
    };
  }

  function _getProjects() {
    return [
      {
        _id: 'a65we1q5d15as1d6wqe',
        titulo: 'Projeto de engenharia pa',
        descricaobreve: 'Descricao pequena descrevendo da vida, o universo e tudo mais',
        descricaocompleta: '',
        fase: 4,
        status: {
          negado: false,
          motivo: ''
        },
        alunos: [
          'aluno@email.com',
          'aluno2@email.com'
        ],
        'responsavel-cad': 'cad@hotmail.com',
        'responsavel-professor': [
          'cad@yahoo.com'
        ],
        'responsavel-empresario': 'Bruna',
        submissao: 'linkzinho.com'
      }
    ];
  }

  function _getFieldsData() {

    var data = {};

    document
      .querySelectorAll('[data-field]')
      .forEach(function (item) {
        data[item.id] = item.value;
      });

    return data;
  }

  function _addProjectsToView(dadElement, projects) {

    projects.forEach(function(project) {
      var projectElement = document.createElement('TR');
      var nameColumn = document.createElement('TD');
      var descriptionColumn = document.createElement('TD');
      var statusColumn = document.createElement('TD');
      
      projectElement.setAttribute('data-project-item', project._id);
      nameColumn.innerText = project.titulo;
      descriptionColumn.innerText = project.descricaobreve;

      statusColumn.innerText = 'Em andamento';
      statusColumn.classList.add('status')
      statusColumn.classList.add('status--yellow')

      projectElement.append(nameColumn);
      projectElement.append(descriptionColumn);
      projectElement.append(statusColumn);

      dadElement.append(projectElement);
    });  
  }

  function _createPopupElement(projeto) {

    function _getDescricaoCompletaHTML() {
      if (projeto.descricaocompleta) {
        return `
          <div class="body-content-section body-content-section--long">
            <div class="title">
              <span>Descrição breve:</span>
            </div>
            <div class="text">
              <p>${ projeto.descricaocompleta }</p>
            </div>
          </div>
        `;
      };
      return '';
    }
    function _getSubmissaoHTML() {
      if (projeto.submissao) {
        return `
          <div class="body-content-section body-content-section--long">
            <div class="title">
              <span>Link da submissão:</span>
            </div>
            <div class="text">
              <a href="${ projeto.submissao }">${ projeto.submissao }</a>
            </div>
          </div>
        `;
      };
    }
    function _getProfessoresHTML() {
      var professores = projeto['responsavel-professor']; 
      if (professores.length) {
        return `
          <div class="body-content-section body-content-section--long">
            <div class="title">
              <span>Professores responsaveis:</span>
            </div>
            <div class="list">
              <ul>
                ${ professores.map(professor => 
                  `<li>${professor}</li>`).join('') }
              </ul>
            </div>
          </div>
        `;
      };
    }

    var popupElement = document.createElement('DIV');
    popupElement.classList.add('popup');
    popupElement.innerHTML = `
      <div class="popup-wrapper" data-popup-wrapper>
        <div class="popup__body">
          <div class="body-header"> 
            <h5 class="title">${ projeto.titulo }</h5>
            <a href class="action" data-popup-close-button">Fechar informações</a>
          </div>
          <div class="body-content">
            <div class="body-content-row">
              <div class="body-content-section body-content-section--progresso">
                <div class="title">
                  <span>Progresso:</span>
                </div>
                <div class="timeline">
                </div>
              </div> 
            </div>
            <div class="body-content-row">
              <div class="body-content-section body-content-section--short">
                <div class="title">
                  <span>Descrição breve:</span>
                </div>
                <div class="text">
                  <p>${ projeto.descricaobreve }</p>
                </div>
              </div>
              ${_getDescricaoCompletaHTML()}
              ${_getProfessoresHTML()}
              ${_getSubmissaoHTML()}
            </div> 
          </div>
        </div>
      </div>
    `;

    popupElement
      .querySelector('.action')
      .addEventListener('click', function(event) {
        event.preventDefault();
        popupElement.remove();
      });

    var shadowElement = document.createElement('DIV');
    shadowElement.classList.add('popup__shadow');
    popupElement.addEventListener('click', function (event) {
      if (event.target.hasAttribute('data-popup-wrapper')) {
        popupElement.remove();
      }
    });

    popupElement.append(shadowElement);

    return popupElement;
  }

  function _openPopup(event) {
    
    var itemTr = event.target.closest('tr');
    var id = itemTr.getAttribute('data-project-item');
    
    var projetoSelecionado = projetos.filter(function(projeto) {
      return projeto._id === id;
    })[0];

    var popupElement = _createPopupElement(projetoSelecionado);

    body.append(popupElement);
  }

  function _addUserInfoToView(element, userInfo) {
    element.innerHTML = `
      <div class="empresario-info">
        <span class="name">${userInfo.nome}</span>
        <span class="company">${ userInfo.empresa || '' }</span>
      </div>
      <div class="empresario-pic">
        <img src="${ userInfo.picture || 'https://picsum.photos/100' }" alt="Foto de ${ userInfo.nome }">
      </div>
    `;
  }

  var body = document.querySelector('body');

  var user = null;
  var projetos = null;

  projetos = _getProjects();
  user = _getUserInfo();

  var usuarioInfo = document.querySelector('[data-empresario-info]');
  _addUserInfoToView(usuarioInfo, user);

  var tbody = document.querySelector('[data-projects-wrapper]');
  _addProjectsToView(tbody, projetos);

  document
    .querySelector('[data-project-item]')
    .addEventListener('click', _openPopup);
  
  document
    .querySelector('[data-form]')
    .addEventListener('submit', function (event) {
      event.preventDefault();

      var data = _getFieldsData();

      alert(JSON.stringify(data));
    });

})();