<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<h3>Formato Stanza</h3>
	<p>Seleziona il formato della stanza</p>
  	<select id="type">
  		<option></option>
  		<option id="fam" value=0>Famiglia</option>
  		<option id="sing" value=1>Singola</option>
  		<option id="dop" value=2>Doppia</option>
  		<option id="matr" value=3>Matrimoniale</option>
   		<option id="del" value=4>Delux</option>
  		<option id="lov" value=5>Lovely</option>
	</select>
	
	<h3>Seleziona il Piano</h3>
	<input id="floor" type="button" value="seleziona il piano">
	<select id="listFloor" class="container" style="display: none"></select>
	
	<h3>Tipologia della stanza</h3>
	<p>Prezzo per notte <input id="price" type="text" placeholder="type the text" size="40" maxlength="200" /></p>
	<input id="regPrice" type="button" value="registra il prezzo">
	
	<h3>Inserisci la stanza</h3>
	<p>Numero <input id="number" type="text" placeholder="type the number" size="40" maxlength="200" /></p>
	<p>Numero Posti Letto <input id="beds" type="text" placeholder="type the number" size="40" maxlength="200" /></p>
	<p>Metri Quadri <input id="mq" type="text" placeholder="type the mq" size="40" maxlength="200" /></p>
	
	<p>Id del piano <input id="pianoId" type="text" size="40" maxlength="200" readonly/></p>
	<p>Tipologia <input id="tipo" type="text" size="40" maxlength="200" readonly/></p>
	<input id="regStanza" type="button" value="registra la stanza">
	
	<input id="booking" value="vai alla registrazione della prenotazione" type="button">

<!-- 	per poter scrivere jquery -->
	<script type="text/javascript" src="../tools/jquery/jquery.min.js"></script>
	<script type="text/javascript">
	
	$(document).ready(function(){
		
		//$(document).on("dblclick", "#type", function(){
		//$("#type").dblclick(function(){
		$("#type").on("change", function(){
			//inserisci controllo dei valori inseriti!!!!!
			$.ajax({
				type : "POST",
				url : "formatovmc/nuovoFormato", 
				data :{
					formato : $("#type").val(),
				},
				dataType : "json",
				success : function(formato){
					console.log("l'Id del formato selezionato è: " + formato.id) //equvale a System.out.println("")
					var i = formato
					console.log(i)
					//$("#tipo").val(i.descrizione)
					//$( "#tipo" ).attr( "value", JSON.stringify(formato));
					//window.location("bestsoftware/nuovo")
				},
			error : function(){
				console.log("formato non trovato")
			}
		})
	})


	$("#floor").on("click", function(){
		$("#listFloor").show();// and $("#dv1").hide();
		$.ajax({
			type : "POST",
			url : "miaprovavmc/listaPiani", 
			data :{	},
			dataType : "json",
			success : function(pianiDisp){
				//codice json inviato da java al codice html
				//{id:1, numPiano:3, numStanze:100}
				
				//ciclo for in Javascript
				pianiDisp.forEach(function(value) {
					//console.log(value)
					
					//posso richiamare i valori con la notazione value.nomedellavariabile
					$( ".container" ).append( "<option value=" + JSON.stringify(value) + ">" + "Numero del piano: " + value.numPiano + ", Numero delle stanze: " + value.numStanze + "</option>");
					});
			},

			error : function(){
			}
		})
					$("#listFloor").on("change", function(){
						var i = JSON.parse($("#listFloor option:selected").val())
						console.log(i.id)
						console.log(i)
						//$( "#pianoId" ).attr( "placeholder", "ciao" );
						$( "#pianoId" ).attr( "value", JSON.stringify(i));
//						$("#pianoId").val(i.id);
					});
	})
	
	
	$("#regPrice").on("click", function(){
				$.ajax({
					type : "POST",
					url : "tipologiavmc/nuovaTipologia", 
					data :{
						prezzo : $("#price").val(),
						piano : $("#listFloor option:selected").val(), //passo l'oggetto piano
						formatoId : $("#type").val()	//passo l'id del formato					
					},
					dataType : "json",
					success : function(tipo){
						console.log("Prezzo registrato" + tipo.prezzo) //equvale a System.out.println("")
						$( "#tipo" ).attr( "value", JSON.stringify(tipo));
						
					},
					error : function(error){
					console.log("errore registrazione!!!")
				 	}
			})
/* 			$("#regPrice").on("change", function(){
				var i = JSON.parse($("#listFloor option:selected").val())
				console.log(i.id)
				console.log(i)
				//$( "#pianoId" ).attr( "placeholder", "ciao" );
				$( "#pianoId" ).attr( "value", JSON.stringify(i));
//				$("#pianoId").val(i.id);
			}); */
		})
		
		
		$("#regStanza").on("click", function(){
				$.ajax({
					type : "POST",
					url : "stanzavmc/nuovaStanza", 
					data :{
						numeroStanza : $("#number").val(),
						numeroLetti : $("#beds").val(),
						mq : $("#mq").val(),
						piano : $("#listFloor").val(), //passo l'oggetto piano
						tipologia : $("#tipo").val() //passo l'oggetto topologia
					},
					dataType : "json",
					success : function(stanza){
						console.log("stanza registrata con successo con id" + stanza.id) //equvale a System.out.println("")
						
					},
					error : function(){
						console.log("errore creazione stanza")
					}
				})
			})
		
		$("#booking").on("click", function(){
				window.location.assign("prenotazione"); //per indirizzare in nuova pagina .jsp -> fare il mapping in front-view
		})
		
		
})
	
	</script>
	
</body>
</html>