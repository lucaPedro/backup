<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<!-- 	<base href="localhost:8080/dedalusApp"> -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>dedalus Albergo</title>
</head>
<body>
<!-- 	<input id="myinput" type="button" value="cliccami">
	<label id="mylabel"></label> -->
	
	<h3>Seleziona il Piano</h3>
	<p>Inserisci il numero del piano	<input id="numFloor" type="text" placeholder="type the text" size="40" maxlength="200" /></p>
	<p>Inserisci il numero delle stanze	<input id="numRooms" type="text" placeholder="type the text" size="40" maxlength="200" /></p>
	<input id="sendPiano" type="button" value="registra il piano">
	
	<h3>Inserisci i dati del cliente</h3>
	<p>Nome <input id="name" type="text" placeholder="type the text" size="40" maxlength="200" data-bind="value: firstName, valueUpdate: 'afterkeydown'"/></p>
	<p>Cognome <input id="surname" type="text" placeholder="type the text" size="40" maxlength="200" data-bind="value: lastName, valueUpdate: 'afterkeydown'"/></p>
	<p>Carta d'Identità <input id="id" type="text" placeholder="type the text" size="40" maxlength="200" /></p>
	<p>Codice Fiscale <input id="fiscalCode" type="text" placeholder="type the text" size="40" maxlength="200" /></p>
	<input id="regClient" type="button" value="registra il cliente">
	
	<form>
		<p> Elimina un cliente <select data-bind="options: array, value: elemArray, optionsCaption: 'seleziona un cliente', 
			optionsText: function(elem){return elem.nome + ' ' + elem.cognome}"></select>
		<button data-bind="click: Delete">Elimina il cliente</button>
		</p>
	</form>
	
	
	<input id="myinput" type="button" value="Vai alla registrazione della Stanza">
	
<!-- prova di knockout: View -->
<!-- 	<p>First Name: <input data-bind="value: firstName" /></p> -->
<!-- 	<p>Last Name: <input data-bind="value: lastName" /></p> -->
	<h2 data-bind="visible: !firstName().length > 0">Benvenuti all'Hotel Dedalus!!!</h2>
	<h2><span data-bind="visible: firstName && firstName() && firstName().length > 0, text: fullName"></span></h2>
	<button data-bind="click : prova">Prova</button>

<!-- riferimenti per le jquery -->
	<script type="text/javascript" src="../tools/jquery/jquery.min.js"></script>

<!-- riferimenti a knockout -->
	<script type="text/javascript" src="../tools/knockout-3.3.0.js"></script>
	<script type="text/javascript">

	<!-- View model -->
	var self = this;
	self.array = ko.observableArray([]);
	self.elemArray = ko.observable();

	var ViewModel = function(){
		this.firstName = ko.observable(""); 
		this.lastName = ko.observable("");

		
		this.fullName = ko.computed(function(){
// 			console.log(this.firstName().length)
// 			console.log(!this.firstName().length > 0)
			return this.firstName() + " " + this.lastName() + ", benvenuto nel nostro Hotel!!!";
       		
       	}, this);
	};
	
	$.ajax({
		type : "POST",
		url : "clientevmc/tuttiClienti", 
		data :{	},
		dataType : "json",
		success : function(clienti){
//			clienti.forEach(function(value) {
//			//$("#AllClients").append("<option value=" + JSON.stringify(value) + ">" + value.nome + " " + value.cognome + "</option>");
//				this.array.push(value);
//			})
			self.array(clienti);
			
		},
	error : function(){
		alert("errore creazione cliente")
		
		}
	}) 
	
	this.prova = function(){
		$.ajax(
		{
        	type: "POST", 
        	url: "clientevmc/prova",
        	data: { },
//        	dataType : "json",
        	success: function(response){
        		console.log(response);
        		alert("fatto!!!") 
        	},
        	error : function(response){
        		console.log(response);
        	}
		})
	}
	
	

	this.Delete = function(){
			$.ajax({
				type : "POST",
				url : "clientevmc/eliminaCliente", 
				data :{	
					//cliente : $("#AllClients option:selected").val(),
					cliente : ko.toJSON(self.elemArray())
				},
				dataType : "json",
				success : function(message){
					console.log(message)
					console.log(JSON.stringify(message))
					alert(message[0])
				},
				error : function(){
					alert("errore nell'eliminazione del cliente selezionato")
				
				}
			})
	}

	
	ko.applyBindings(new ViewModel());

	</script>
	<script type="text/javascript">
	
		$(document).ready(function(){
			
 			$("#sendPiano").on("click", function(){
 				$.ajax({
 					type : "POST",
 					url : "miaprovavmc/nuovoPiano", 
 					data :{
 						numeroPiano : $("#numFloor").val(),
 						numeroStanze : $("#numRooms").val()
 					},
 					dataType : "json",
 					success : function(idpiano){
 						console.log("piano registrato con successo con id " + idpiano + " !!!"); //equvale a System.out.println("")
 						$("#pianoId").val(idpiano) 
 						//window.location("bestsoftware/nuovo") //per indirizzare in nuova pagina .jsp -> fare il mapping in front-view
 					},
					error : function(){
						alert("errore creazione camera")
					}
 				})
			})
			
			$("#regClient").on("click", function(){
 				$.ajax({
 					type : "POST",
 					url : "clientevmc/nuovoCliente", 
 					data :{
 						nomeCliente : $("#name").val(),
 						cognomeCliente : $("#surname").val(),
 						ID : $("#id").val(),
 						codiceFiscale : $("#fiscalCode").val()
 					},
 					dataType : "json",
 					success : function(client){
 						alert("cliente " + client.nome + " registrato con successo!!!") //equvale a System.out.println("")
 						
 					},
					error : function(){
						alert("errore creazione cliente")
						
					}
 				})
			})
			
			
			$("#myinput").on("click", function(){					
 						window.location.assign("nuovo"); //per indirizzare in nuova pagina .jsp -> fare il mapping in front-view
 				})
			
			/* 
			$("#elimina").on("click", function(){
 				$.ajax({
 					type : "POST",
 					url : "clientevmc/eliminaCliente", 
 					data :{	
 						cliente : $("#AllClients option:selected").val(),
 						clint : ko.mapping.toJSON(this.elemArray)
 					},
 					dataType : "json",
 					success : function(message){
 						console.log(message)
 						console.log(JSON.stringify(message))
						alert(message[0])
 					},
					error : function(){
						alert("errore nell'eliminazione del cliente selezionato")
						
					}
 				})
			}) */
			
		})
		
	</script>
</body>
</html>