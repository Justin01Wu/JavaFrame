(function() {	
    'use strict';
    
	if(!window.API_UTIL){
		window.API_UTIL = {};
	} 
	
	var self = window.API_UTIL;

	function callApi(url) {

		var callBack = display;

		callGetAPI(url, callBack);

	}

	function callGetAPI(url, callBack) {
		var request = new XMLHttpRequest()

		request.open('GET', url, true)

		request.onload = function() {
			callBack(request);
		}

		request.send();

	}
	
	function callPostAPI(url, withError) {
		
		var request = new XMLHttpRequest();
		
		var callBack = display;
		request.open('POST', url, true)
		request.setRequestHeader("Content-Type", "application/json");
		
		request.onload = function() {
			callBack(request);
		}
		let user = {};
		if(withError){
			user = {name:'fromUI', email:'aaa', gender:'male'};	
		}else{
			user = {name:'fromUI', email:'aaa@bbb.com', gender:'male'};
		}
		
		let payload = JSON.stringify(user)
		request.send(payload);

	}

	function display(request) {
		var info = document.getElementById('ApiLabel');
		if (request.status >= 200 && request.status < 400) {
			// Begin accessing data here
			console.log(request.response);
			info.innerHTML = request.response;
		} else {

			var msg = 'error on status: ' + request.status;
			let res = JSON.parse(request.response);
			if(res.message){
				msg = msg + ", " + res.message; 
			}
			info.innerHTML = msg;
		}
	}
	
	API_UTIL.display = display;
	API_UTIL.callApi = callApi;
	API_UTIL.display = display;
	API_UTIL.callPostAPI = callPostAPI;
	

})(); // directly run anonymous function
