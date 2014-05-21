
function mascaras(){
	$('.hasDatepicker').mask('99/99/9999', { reverse : true});
	
	$('.soNumero').mask('[0-9]');
	$('.cep').mask('99999-999');
	$('.telefone').mask('(99) 9?9999-9999');
	$('.cpf').mask('999.999.999-99', { reverse : true });
	$('.cnpj').mask('99.999.999/999-99', { reverse : true});
	$('.rg').mask('99.999.999-*', { reverse : true });
	
	// mascara da campos monetários
	$(".money").maskMoney({symbol:'R$ ', showSymbol:true, thousands:'.', decimal:',', symbolStay: true, allowNegative: true});

}

$(document).ready(function(){
	mascaras();
});