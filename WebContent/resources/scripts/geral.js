
function mascaras(){
	$('.hasDatepicker').mask('99/99/9999', { reverse : true});
	
	$('.soNumero').on('input', function (event) { 
	    this.value = this.value.replace(/[^0-9]/g, '');
	});
	
	$('.cep').mask('99999-999');
	$('.telefone').mask('(99) 9999-9999?9');
	$('.cpf').mask('999.999.999-99', { reverse : true });
	$('.cnpj').mask('99.999.999/999-99', { reverse : true});
	$('.rg').mask('99.999.999-*', { reverse : true });
	
	// mascara da campos monetários
	$(".moeda").maskMoney({symbol:'R$ ', showSymbol:true, thousands:'.', decimal:',', symbolStay: true, allowNegative: true});

}

$(document).ready(function(){
	mascaras();
});