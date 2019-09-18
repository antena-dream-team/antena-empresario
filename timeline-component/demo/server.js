// CONFIGURANDO EXPRESS
let express = require('express');
let app = express();

let bodyParser = require('body-parser');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.use(express.static('./'));

// CONFIGURANDO ROTAS
let projetos = [
  {
    _id: '1o23u1io2jdpasd',
    titulo: 'Um projeto na fase 3',
    'descricao-breve': 'Nesta fase o usuário tem que esperar uma avaliação detalhada',
    'descricao-completa': 'Agora a descrição completa foi aprovada',
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
  }
];

app.get('/projetos', (req,res) => {
  res.status(200).json(projetos);
});

app.post('/update-projeto', (req,res) => {
  console.log('Chegou');
  console.log(req.body);
  let projeto = req.body;
  
  projetos.some((item,index) => {
    if(item._id == projeto._id) {
      projetos[index] = projeto;    
      return true;
    }
  });

  res.status(200).send();
});

// RODANDO SERVIDOR
app.listen(3548, () => {
  console.log('Rodando na porta 3548');
});