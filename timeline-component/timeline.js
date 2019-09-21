var Timeline = function (endpoint) {

  if (!endpoint) {
    throw new Error('É preciso de um endpoint de salvamento de projeto para instanciar Timeline');
  }

  function _createPopupElement(projeto, inputsHTML) {

    var popupElement = document.createElement('DIV');
    popupElement.classList.add('popup');
    popupElement.innerHTML = `
      <div class="popup-wrapper" data-popup-wrapper>
        <div class="popup__body">
          <div class="body-header"> 
            <h5 class="title">Titulo do projeto: ${ projeto.titulo}</h5>
            <a href class="action" data-popup-close-button">Fechar informações</a>
          </div>
          <div class="body-content">
            <form method="POST">
              ${ inputsHTML}
              <button type="submit" class="btn-submit">Enviar</button>
            </form>
          </div>
        </div>
      </div>
    `;

    popupElement
      .querySelector('.action')
      .addEventListener('click', function (event) {
        event.preventDefault();
        popupElement.remove();
      });

    var formElement = popupElement.querySelector('form');

    formElement
      .addEventListener('submit', function () {

        var descricaoCompleta = formElement.querySelector('[data-descricao-completa]');
        var descricaoTecnologia = formElement.querySelector('[data-descricao-tecnologias]');
        var linkExterno = formElement.querySelector('[data-link-externo]');
        var linkExterno2 = formElement.querySelector('[data-link-externo-2]');

        var dataReuniao = formElement.querySelector('[data-reuniao]');

        var newProject = { ...projeto };

        if (descricaoCompleta && descricaoTecnologia) {
          newProject = {
            ...newProject,
            'descricao-completa': descricaoCompleta.value,
            'descricao-tecnologias': descricaoTecnologia.value,
            'link-externo-1': linkExterno ? linkExterno.value : '',
            'link-externo-2': linkExterno2 ? linkExterno2.value : ''
          };
        }
        else if (dataReuniao) {
          var reuniaoData = dataReuniao.value.split('-');
          newProject = {
            ...newProject,
            fase: 5,
            reuniao: {
              data: reuniaoData[0],
              horario: reuniaoData[1]
            }
          };
        }
        else return;

        fetch(endpoint, {
          method: 'POST',
          body: JSON.stringify(newProject),
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          }
        })
          .then(() => location.reload());

        console.log(newProject);
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

  function insertTimeline(target, projeto) {

    function _getIcon(iconName) {
      return '';
    }

    function _getEventClass(fase) {

      var base = 'event-circle--';

      if (fase.isActive)
        return base + 'green';
      else if (fase.isPending)
        return base + 'yellow';
      else if (fase.isWaitingForInput)
        return base + 'blue';
      else
        return '';
    }

    function _getCadastroCompletoHTML() {
      return `
        <div class="form-section">
          <label>Descrição Completa</label>
          <textarea data-descricao-completa>${projeto['descricao-completa']}</textarea>
        </div>
        <div class="form-section">
          <label>Descrição Tecnologia</label>
          <textarea data-descricao-tecnologias>${projeto['descricao-tecnologias']}</textarea>
        </div>
        <div class="form-section">
          <label>Link externo:</label>
          <input type="text" data-link-externo value="${projeto['link-externo-1']}"/>
        </div>
        <div class="form-section">
          <label>Link externo 2:</label>
          <input type="text" data-link-externo-2 value="${projeto['link-externo-2']}" />
        </div>
      `;
    }

    function _getReuniaoHTML() {
      return `
        <div class="form-section">
          <label>Escolha uma data para a reunião:</label>
          <select data-reuniao>
            <option value="05/04/2019-15:50">05/04/2019 - 15:50</option>
          </select>
        </div>
      `;
    }

    function _openInputPopup(modelo) {
      var modeloHTML = {
        2: _getCadastroCompletoHTML(),
        4: _getReuniaoHTML()
      }[modelo];

      var popupElement = _createPopupElement(projeto, modeloHTML);

      document.body.append(popupElement);
    }

    var fases = [
      {
        icon: _getIcon(''),
        title: 'Cadastro Inicial',
        isActive: true,
        isPending: false,
        isWaitingForInput: false
      },
      {
        icon: _getIcon(''),
        title: 'Avaliação Inicial',
        isActive: projeto.fase > 1,
        isPending: projeto.fase == 1,
        isWaitingForInput: false
      },
      {
        icon: _getIcon(''),
        title: 'Cadastro Detalhado',
        isActive: projeto.fase > 2,
        isPending: projeto.fase == 2 && projeto['descricao-completa'] && projeto['descricao-tecnologias'],
        isWaitingForInput: projeto.fase == 2 && (!projeto['descricao-completa'] || !projeto['descricao-tecnologias'])
      },
      {
        icon: _getIcon(''),
        title: 'Avaliação Detalhada',
        isActive: projeto.fase > 3,
        isPending: projeto.fase == 3,
        isWaitingForInput: false
      },
      {
        icon: _getIcon(''),
        title: 'Reunião',
        isActive: projeto.fase > 4,
        isPending: projeto.fase == 4 && projeto.reuniao['datas-possiveis'].length,
        isWaitingForInput: projeto.fase == 4 && !projeto.reuniao['datas-possiveis'].length
      },
      {
        icon: _getIcon(''),
        title: 'Entrega',
        isActive: projeto.fase > 5,
        isPending: projeto.fase == 5,
        isWaitingForInput: false
      },
    ];

    target.innerHTML = `
      <div class="timeline fase-${ projeto.fase}">
        ${
      fases.map((fase, index) =>
        `<div 
                class="timeline__event" 
                data-open-to-input=${ fase.isWaitingForInput} 
                data-popup-modelo=${ index}>
                <div class="event-circle ${ _getEventClass(fase)}">
                  ${ fase.icon}
                </div>
                <label class="event-label">${ fase.title}</label>
              </div>`
      ).join('')
      }
      </div>
    `;

    target
      .querySelectorAll('[data-open-to-input=true]')
      .forEach(clicavel =>
        clicavel.addEventListener('click', function () {
          var modelo = clicavel.getAttribute('data-popup-modelo');
          _openInputPopup(modelo);
        })
      );
  }

  return {
    insertTimeline
  };
};
