(function() {	
    'use strict';
    
	if(!window.API_UTIL){
		window.API_UTIL = {};
	} 
	
	var self = window.API_UTIL;

	function loadTokeNFromResponse(request) {
		var info = document.getElementById('infoLabel');
		if (request.status >= 200 && request.status < 400) {
			// Begin accessing JSON data here
			var data = JSON.parse(request.response);

			console.log(data);
			info.innerHTML = data.token;
		} else {

			var msg = 'error on status: ' + request.status;
			info.innerHTML = msg;
		}
	}
	
	
	function getToken() {

		var url = '/public/authenticate?username=justin&password=password';
		var callBack = loadTokeNFromResponse;
		callGetAPI(url, callBack);
	}

	function callTokenApiA(url) {

		var callBack = display;

		var info = document.getElementById('infoLabel');
		var token = info.innerHTML;

		callGetAPI(url, callBack, token);

	}

	function callGetAPI(url, callBack, token) {
		var request = new XMLHttpRequest()

		request.open('GET', url, true)
		if (token) {
			request.setRequestHeader("Authorization", "Bearer " + token);
		}

		request.onload = function() {
			callBack(request);
		}

		request.send();

	}

	function display(request) {
		var info = document.getElementById('ApiLabel');
		if (request.status >= 200 && request.status < 400) {
			// Begin accessing data here
			console.log(request.response);
			info.innerHTML = request.response;
		} else {

			var msg = 'error on status: ' + request.status;
			info.innerHTML = msg;
		}
	}
	
	API_UTIL.display = display;
	API_UTIL.getToken = getToken;
	API_UTIL.callTokenApi = callTokenApiA;
	API_UTIL.display = display;

})(); // directly run anonymous function
