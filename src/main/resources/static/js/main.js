function getFieldsData() {
  
  var data = {};

  document
    .querySelectorAll('[data-field]')
    .forEach(function(item) {
      data[item.id] = item.value;
    });

  return data;
}

document
  .querySelector('[data-form]')
  .addEventListener('submit', function(event) {
    event.preventDefault();

    var data = getFieldsData();

    alert(JSON.stringify(data));
  });