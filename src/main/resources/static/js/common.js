$(document).ready(function(){
	$('#main .container-fluit .row>div.col-xs-2 ul li').each(function(index,elem){
		$(this).click(function(){
			$('#main .container-fluit .row>div.col-xs-2 ul li').removeClass('active');
			$(this).addClass('active');
			$('#main .container-fluit .row>div.col-xs-10>div.nav').removeClass('show').addClass('hidden');
			$('#main .container-fluit .row>div.col-xs-10>div.nav').eq(index).removeClass('hidden').addClass('show');
		})
	})
})