# Timeline Component

Este componente (feito de qualquer jeito para ser implementado o quanto antes) serve para exibir o progresso do projeto

## Como usar

1. Copie e cole os arquivos *timeline.css* e *timeline.js* dentro do seu projeto
2. Importe o script na página HTML onde ele será usado. **Certifique-se de que ele está sendo importado antes do arquivo javascript principal**
```html
<script src="timeline.js"></script>
```

3. Logo no inicio do seu arquivo javascript principal, instancie o componente de timeline passando um endpoint (rota do back-end) de salvamento de projeto.
```javascript
var timeline = new Timeline('/salvar-projeto');
```

4. Insira a timeline na tela indicando para a função *insertTimeline* o elemento HTML onde será adicionada a timeline e o projeto de onde virão as informações da timeline. Garanta que o projeto passado estará igual ao modelo.json
```javascript
var projeto; // Garanta que ele estará igual ao modelo.json
var elementoExemplo = document.querySelector('div');
timeline.insertTimeline(elementoExemplo, projeto);
```

5. Com isso a timeline ja deve estar aparecendo na tela quando carregada.


## Vendo exemplo

Se ainda tiver dúvidas sobre a implementação, olhe o exemplo criado na pasta *demo*.

Para executa-lo, siga os passos:
1. Garanta que você tem node instalado na sua maquina (para ver se tem mesmo, rode `node --version` no seu terminal)
2. Vá até o diretório *demo* pelo terminal
3. Execute `npm install`
4. Rode `npm start`
5. No seu navegador, digite `http://localhost:3548`
6. [OPCIONAL] Se quiser ver todos os projetos cadastrado no *demo*, acesse o link `http://localhost:3548/projetos`