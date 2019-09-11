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
        titulo: 'Um projeto na fase 1',
        'descricao-breve': 'É um projeto que foi criado pelo empresário com apenas o nome e uma descrição breve',
        'descricao-completa': '',
        'descricao-tecnologias': '',
        'link-externo-1': '',
        'link-externo-2': '',
        fase: 1,
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
        'responsavel-empresario': 'Bruna'
      },
      {
        _id: 'jwoiheou13h3o1u28hou',
        titulo: 'Um projeto na fase 2',
        'descricao-breve': 'Nesta fase o usuário tem que preencher a descrição completa',
        'descricao-completa': '',
        'descricao-tecnologias': '',
        'link-externo-1': '',
        'link-externo-2': '',
        fase: 2,
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
        'responsavel-empresario': 'Bruna'
      },
      {
        _id: '1o23u1io2jdpasd',
        titulo: 'Um projeto na fase 3',
        'descricao-breve': 'Nesta fase o usuário tem que esperar uma avaliação detalhada',
        'descricao-completa': 'Agora a descrição completa foi aprovada',
        'descricao-tecnologias': '',
        'link-externo-1': '',
        'link-externo-2': '',
        fase: 3,
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
        'responsavel-empresario': 'Bruna'
      },
      {
        _id: 'ipokjp1o23jpi12j39',
        titulo: 'Um projeto na fase 4',
        'descricao-breve': 'Nesta fase o usuário tem que dar uma sugestão para horario reunião',
        'descricao-completa': 'Apos definido o horario, agora vamos asodk',
        'descricao-tecnologias': '',
        'link-externo-1': '',
        'link-externo-2': '',
        fase: 4,
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
        'responsavel-empresario': 'Bruna'
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
      descriptionColumn.innerText = project['descricao-breve'];

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

    function _hasFieldsToChange() {
      return (
        (projeto.fase == 2 && !projeto['descricao-completa']) ||
        (projeto.fase == 4 && !projeto.reuniao['datas-possiveis'].length)
      );
    }

    function _getDescricaoCompletaHTML() {
      
      var descricaoCompleta = projeto['descricao-completa'];
      
      if (!descricaoCompleta) return '';

      return `
        <div class="body-content-row">
          <div class="body-content-section body-content-section--long">
            <div class="title">
              <span>Descrição completa:</span>
            </div>
            <div class="text">
              <p>${ descricaoCompleta }</p>
            </div>
          </div>
        </div>
      `;
    }
    function _getInputDescricaoCompletaHTML() {
      
      if (projeto.fase != 2 || projeto['descricao-completa']) return '';

      return `
        <div class="body-content-row">
          <div class="body-content-section body-content-section--long">
            <div class="title">
              <span>Digite a descrição completa:</span>
            </div>
            <div class="text">
              <textarea></textarea>
            </div>
          </div>
        </div>
        <div class="body-content-row">
          <div class="body-content-section body-content-section--long">
            <div class="title">
              <span>Digite a descrição de tecnologia:</span>
            </div>
            <div class="text">
              <textarea></textarea>
            </div>
          </div>
        </div>
      `;
    }
    function _getSubmissaoHTML() {

      var submissoes = projeto.entregas;

      if (!submissoes.length) return '';
      
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
    }   
    function _getProfessoresHTML() {
      var professores = projeto['responsavel-professor']; 
      
      if (!professores.length) return '';

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
    }

    function _getStatusColor(project, fase) {
      if (fase == 0) {
        return 'green';
      }
      if (fase == 1 || fase == 3 || (fase == 4 && project.reuniao['datas-possiveis'].length) || fase == 5 || (fase == 2 && project['descricao-completa'])) {
        return 'yellow';
      }
      if ((fase == 2 && !project['descricao-completa']) || (fase == 4 && !project.reuniao['datas-possiveis'].length)) {
        return 'blue';
      }
    }

    function _getEvents(project) {
      let elements = [];
      let labels = ['Cadastro inicial', 'Avaliação Inicial', 'Cadastro detalhado', 'Avaliação Detalhada', 'Reunião', 'Entrega'];
      for (let i = 0; i < 6; i++) {

        let classe = 'circle';
        let statusColor;

        if (i < project.fase) {
          statusColor = 'green'
        } else if(i == project.fase) {
          statusColor = _getStatusColor(project, i);
        }

        if (statusColor) {
          classe += ` circle--${statusColor}`;
        }

        let element = `<div class="${classe}"></div>`;

        let dadElement = `
          <div class="event">
            ${ element }
            <span class="label">
              ${ labels[i] }
            </span>
          </div>
        `;

        elements.push(dadElement);
      }
      return elements.join('');
    }

    var popupElement = document.createElement('DIV');
    popupElement.classList.add('popup');
    popupElement.innerHTML = `
      <div class="popup-wrapper" data-popup-wrapper>
        <div class="popup__body">
          <div class="body-header"> 
            <h5 class="title">Titulo do projeto: ${ projeto.titulo }</h5>
            <a href class="action" data-popup-close-button">Fechar informações</a>
          </div>
          <div class="body-content">
            <div class="body-content-row">
              <div class="body-content-section body-content-section--progresso">
                <div class="timeline">
                  ${ _getEvents(projeto) }
                </div>
              </div> 
            </div>
            <div class="body-content-row">
              <div class="body-content-section body-content-section--short">
                <div class="title">
                  <span>Descrição breve:</span>
                </div>
                <div class="text">
                  <p>${ projeto['descricao-breve'] }</p>
                </div>
              </div>
            </div>
              ${ _getProfessoresHTML() }
              ${ _getDescricaoCompletaHTML() }
              ${ _getInputDescricaoCompletaHTML() }
              ${ _getSubmissaoHTML() }
              ${ _hasFieldsToChange() ? `<button data-submit-changes>Salvar e submeter</button>` : '' }
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
    .querySelectorAll('[data-project-item]')
    .forEach(item => item.addEventListener('click', _openPopup));
  
  document
    .querySelector('[data-add-project-form]')
    .addEventListener('submit', function (event) {
      
      event.preventDefault();

      var data = _getFieldsData();

      fetch('/cadastroprojeto', {
        method: 'POST',
        body: JSON.stringify(data) 
      });
    });

})();