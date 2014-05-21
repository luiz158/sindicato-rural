function mascaras(){
	$('.date').mask('11/11/1111', {placeholder: ""});
	$('.time').mask('00:00:00');
	$('.date_time').mask('00/00/0000 00:00:00');
	$('.cep').mask('00000-000');
	$('.phone').mask('(00) 0?0000-0000');
	$('.cpf').mask('000.000.000-00', {
		reverse : true
	});
	$('.money').mask("#.##0,00", {
		reverse : true,
		maxlength : false
	});
	$('.percent').mask('##0,00%', {
		reverse : true
	});
	
}

$(document).ready(function(){
	mascaras();
});