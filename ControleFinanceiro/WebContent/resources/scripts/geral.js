function mascaras(){
	$('.hasCalendar').mask('00/00/0000', { reverse : true, placeholder: "__/__/____" });
	
	$('.cep').mask('00000-000');
	$('.telefone').mask('(00) 0?0000-0000');
	$('.cpf').mask('000.000.000-00', { reverse : true, placeholder: "___.___.___-__" });
	$('.cnpj').mask('00.000.000/000-00', { reverse : true, placeholder: "__.___.___/___-__" });
	$('.rg').mask('00.000.000-*', { reverse : true, placeholder: "__.___.___-_" });
	$('.money').mask("#.##0,00", {
		reverse : true,
		maxlength : false
	});
	
	// mascara da campos monetários
	$(".money").maskMoney({symbol:'R$ ', showSymbol:true, thousands:'.', decimal:',', symbolStay: true, allowNegative: true});

}

$(document).ready(function(){
	mascaras();
});