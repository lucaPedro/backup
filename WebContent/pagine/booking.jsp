<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Booking registration</title>

<style type="text/css" media="all">
p {
/* 	font-family: iatalic small-caps bold 15px georgia; */
/* 	font-style: italic; */
/* 	font-variant: small-caps bold; */
/* 	font-size: 22px; */
/* 	font-weight; */
	color: #36C;
}

/* * {
	padding-left: 20px;
	border: 1px solid black;
} */

/* selezione per id dell'elemento */
#tutteLePrenotazioni{
 	position:relative; /*relative: alla suo posizione attuale, absolute: rispetto all'angolo in alto a sx. */
	left: 300px; /*valori positivi si sposta a dx, valori negativi sis sposta a sx */
	top: -18px;
}

input[type="submit"]{
	color: green;
	border: solid thin #882d13;
	border-radius: .5em;
	background-color: #CCFFFF;
}

input[type="button"]{
	color: green;
	border: solid thin #882d13;
	border-radius: .5em;
	background-color: #FFFFFF;
}
</style>

</head>
<body>
	
	<h3>Inserisci i dati della prenotazione</h3>
	<p>Prenotato da: <input id="from" type="date"   size="40" maxlength="200" /></p>
	<p>Prenotato a: <input id="to" type="date"   size="40" maxlength="200" /></p>
	<p>Cliente <select id="client" type="text" class="clientContainer" /></select>
	<select id="prenotazioneCliente" type="text" value=" " readonly/></select>
	</p>
	<p>Stanza <select id="room" type="text" class="roomContainer" /></select></p>
	<input id="regPrenotazione" type="button" value="registra la prenotazione">
	
		<p>Prezzo <input id="price" type="text" class="priceContainer" readonly /></p>
		
	<form id="booking" > </form>
	
	
	<form id="parametri" action="stampavmc/StampaPrenotazione" method="get"> <!-- action indica il path di dove i dati vengono inviati quando premuto submit -->
 		<input id="stampa" type="submit" value="stampa la prenotazione" disabled> <!-- data-bind="click: stampa" -->
	</form>
	
	

	<form action="stampavmc/StampaTutteLePrenotazioni" method="get">
		<input id="tutteLePrenotazioni" type="submit" value="stampa tutte le prenotazioni">
	</form>
	
<div>
	<form>
		Seleziona l'optional:
		<select data-bind="options: availableOptionals, value: selectedOptional, optionsCaption: 'seleziona un optional',
				 optionsText: 'productName'"></select>
		<input data-bind="value: selectedOptional() ? selectedOptional().productPrice : ' '" readonly>
		<button data-bind="click: addOptional, enable: selectedOptional && selectedOptional() && selectedOptional().productPrice">Aggiungi</button>
	</form>
	
	<div>
<!-- 		Inserisci l'optional:
		<input data-bind="value: optional, valueUpdate: 'afterkeydown'" placeholder="Inserisci l'otional richiesto" >
		<input data-bind="value: price, valueUpdate: 'afterkeydown'" placeholder="Prezzo">
		<button data-bind="click: addOptional, enable: optional && optional() && price && price()">Aggiungi</button>
		<br> -->
		Optional richiesti:
		<ul data-bind="foreach : allTheOptionals">
			<li> 
				<input data-bind="value: $data.productName" readonly>
				<input data-bind="value: $data.productPrice + ' £'" readonly> 
				<a href="#" data-bind="click: $root.Delete">Elimina</a> <!-- bottone interattivo collegato alla funzione Delete nel ViewModel -->
			</li>
		</ul>
		Totale optionals:
 		<input data-bind="value: total" readonly>	
 		<button data-bind="click: stampa">Invia la richiesta optionals</button> 
	</div>
</div>
	

<!-- 	<script> and </script> contengono il codice javascript (in questo caso inserito nel body del file html) -->
<!-- document.write(...) -> scrittura nel file html -->
	<script type="text/javascript" src="../tools/jquery/jquery.min.js"></script>
	
	<!-- riferimenti a knockout -->
	<script type="text/javascript" src="../tools/knockout-3.3.0.js"></script>
	<script type="text/javascript">
   
// 	definisco la variabile prodotto caratterizzato da nome e prezzo
		var Product = function(nome, prezzo, unicità){
			this.productName = nome;
			this.productPrice = prezzo;
			this.unicity = unicità;
			this.quantity = 0;
		};
	
	function ViewModel(){
		var self = this;
// 		definizioni delle osservabili
		self.total = ko.observable()
		self.selectedOptional = ko.observable()

		//inserisco gli optionals disponibili
		self.availableOptionals = ko.observableArray([
			new Product("frigo-bar", 100, true),
			new Product("condizionatore", 55, true),
			new Product("asciugamano", 15, false),
			new Product("servizio in camera", 20, true),
			new Product("colazione", 8, false)
			])
		self.allTheOptionals = ko.observableArray([])
		
		self.total(0)
		this.addOptional = function(){		
			self.allTheOptionals.push(self.selectedOptional());
			console.log(self.allTheOptionals())
			console.log(ko.toJSON(self.allTheOptionals()))
			self.selectedOptional().quantity += 1
			self.total(parseInt(self.total()) + self.selectedOptional().productPrice) 
			self.selectedOptional();
			
			if (self.selectedOptional().unicity == true && self.selectedOptional().quantity == 1) {
                self.availableOptionals.remove(self.selectedOptional())
            	}
		}
			
		self.Delete = function(elem){
			self.total(self.total() - elem.productPrice)
			
			if (elem.unicity == true) {
                self.availableOptionals.push(elem)
                elem.quantity = 0
            }
			self.allTheOptionals.remove(elem);

		}
		
  		self.stampa = function() {		
			$.ajax({
				type : "GET",
				url : "stampavmc/StampaOptionals", 
				data :{	
					//cliente : $("#AllClients option:selected").val(),
					optionals : ko.toJSON(self.allTheOptionals())
				},
				dataType : "json",
				success : function(){
		
				},
				error : function(){
					//alert("non è arrivato nulla")
					
				}
			})
	    };  
		
	};
	
	ko.applyBindings(new ViewModel());
	
		
	$(document).ready(function(){	 
// menu a tendina per i clienti registarti nel database	
 			$.ajax({
					type : "POST",
					url : "prenotazionevmc/ListaClienti", 
					data :{	},
					dataType : "json",
					success : function(clienti){
						//console.log(JSON.stringify(clienti));
						clienti.forEach(function(value) {
						//console.log(JSON.stringify(value)); //equvale a System.out.println("")
							$( ".clientContainer" ).append( "<option value=" + JSON.stringify(value) + ">" + "Nome: " + value.nome + ", Cognome: " + value.cognome + "</option>");
						})
					
					//window.location("bestsoftware/nuovo") //per indirizzare in nuova pagina .jsp -> fare il mapping in front-view
					},
					error : function(){
					//alert("errore creazione camera")
					}
			}) 

$("#client").on("click", function(){
			$.ajax({
				type : "POST",
				url : "prenotazionevmc/RilevaPrenotazioni", 
				data :{	
					cliente : $("#client option:selected").val() //passo la stringa JSON corrispondente al cliente selezionato
				},
				dataType : "json",
				success : function(prenotazioni){
					//svuoto tutte le options
					$( "#prenotazioneCliente").empty()
					
					//prenotazioni ritorna le prenotazioni a carico del cliente selezionato
					prenotazioni.forEach(function(value){
						var from = myDateFormatter(value.from)
						var to = myDateFormatter(value.to)
						
						//console.log(d);	
						console.log(value.cliente);
						
						$( "#prenotazioneCliente" ).append( "<option value=" + JSON.stringify(value) + ">" + "C'e una prenotazione a carico del cliente selezionato dal: " + from + ", al: " + to + " </option>");
					})
				},
				error : function(){
				//alert("errore creazione camera")
				}
		})
	});
	
function myDateFormatter (data) {
        var d = new Date(data);
        var day = d.getDate();
        var month = d.getMonth() + 1;
        var year = d.getFullYear();
        if (day < 10) {
            day = "0" + day;
        }
        if (month < 10) {
            month = "0" + month;
        }
        var date = day + "/" + month + "/" + year;

        return date;
    }; 

		
//gestione Date di Prenotazione
 $("#to").on("change", function(){
 			$("#from").on("change", function(){
 				RegDatePrenotazione()
 			})
 })

 $("#from").on("change", function(){
 			$("#to").on("change", function(){
 				RegDatePrenotazione()
 			})
 })

function RegDatePrenotazione(){
	if ($("#from").val() > $("#to").val()){
		alert("la data di fine è antecedente a quella di inizio")		
	}else{
	 $.ajax({
			type : "POST",
			url : "prenotazionevmc/StanzeLibere", 
			data :{	
				da : $("#from").val(),
				a : $("#to").val()
			},
			dataType : "json",
			success : function(stanze){
				//console.log(JSON.stringify(stanze));
			
				stanze.forEach(function(value){
					console.log(value);	
					$( "#room" ).append( "<option value=" + JSON.stringify(value) + ">" + "Numero:" + value.numero + ", formato: " + value.tipologia.formato.descrizione + " </option>");
					console.log(value.tipologia.prezzo)
				})
			},
			error : function(){
			//alert("errore creazione camera")
			}
		})
	}
}


//menu a tendina per i prezzi registrati nel DB
 	$("#regPrenotazione").on("click", function(){
		$.ajax({
			type : "POST",
			url : "prenotazionevmc/NuovaPrenotazione", 
			data :{
				da : $("#from").val(),
				a : $("#to").val(),
				cliente : $("#client option:selected").val(), //oggetto cliente
				stanza : $("#room option:selected").val() //oggetto stanza
			},
			dataType : "json",
			success : function( response ){
				//response = oggetto Prenotazione
				//riempio la casella di testo con il prezzo da pagare per i gironi della vacanza a seconda della stanza selezionata
				console.log(response); //oggetto json
				console.log(JSON.stringify(response)); //stringa json
				//console.log(JSON.parse(response.prezzo));
				$( "#price" ).val(response.totale)
				$("#stampa").prop('disabled', false);
				$("#booking").append("<input type=text id=booked value=" + JSON.stringify(response) + " hidden>")
				alert("prenotazione registrata con successo"); 
			},
			error : function(){
				alert("prenotazione non registrata")
			}
		})
	}) 
	
//stampa della prenotazione
//l'indirizzo a "stampavmc/StampaTutteLePrenotazioni" viene fatto con attributo nell'html
		$("#stampa").on("click", function(){
			var parameters = {
				"cliente" : $("#client option:selected").val(),
//				"optionals" : ko.toJSON(self.allTheOptionals()),	// vettore osservabile allTheOptionals() => stringa Json
	/* 			"prezzo" : $("#price").val(),
				"da" : $("#from").val(),
				"a" : $("#to").val(), */
				"booking" : $("#booked").val()
			};
			
			console.log($("#booked").val());

	/* 		for (var p in parameters) {
				console.log(p) //chiave
				console.log(parameters[p]) //valore
			} */
			
			SendParameters(parameters)
			})
			
		
		function SendParameters(params){	
			for (var p in params) {
				console.log(p) //chiave
				console.log(params[p]) //valore
				$( "#parametri" ).append("<input type=text value=" + params[p] + " name = " + p +" hidden>")
			};
			$("#parametri").submit();
		}

	
})


	
	</script>
		
</body>
</html>