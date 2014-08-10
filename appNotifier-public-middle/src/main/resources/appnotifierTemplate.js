(function AppNotifierLoadFunction( window, document, undefined ) {
	
	var getParam = function(paramName) {
		for (param in _appNotifier) {
			if (_appNotifier[param][0] == paramName) {
				return _appNotifier[param][1];
			}
		}
		return undefined;
	};
	
	var refreshParam = getParam("_refresh");
	var anchorParam = getParam("_anchor");
	var importStyleParam = getParam("_importStyle");
	var anchor = undefined;
	var anchorLoadTries = 0;
	var anchorMaxTries = 60;
	var timeBetweenTries = 1500;
	var thread = undefined;
	
	var callAppNotifier = function() {
		executeJsonPRequest("[[ROOT_URL]]rs/public/" + getParam("_appUid") ,buildBanner);
	};
	
	var initializeAnchor = function(callback) {
		if(anchor!=undefined){
			anchor.innerHTML="";
			callback();
			return;
		}
		if (!anchorParam || importStyleParam) {
			anchor = document.querySelector(".appnotifier-banner");
			if (anchor == undefined) {
				anchor = document.createElement('div');
				anchor.classList.add("appnotifier-banner");
				document.body.appendChild(anchor);
			}else{
				anchor.innerHTML="";
			}
			callback();
		}
		
		if(anchorParam && !importStyleParam){
			waitForAnchor(callback);
		}
	};
	
	var waitForAnchor = function(callback){
		if(anchor!=undefined){
			callback();
			return;
		}
		anchorLoadTries++;
		console.log("waiting for anchor, try #"+anchorLoadTries+"...")
		if(anchorLoadTries<anchorMaxTries){
			anchor = document.querySelector(anchorParam);
			if(anchor==undefined){
				window.setTimeout(function(){waitForAnchor(callback);},timeBetweenTries);
			}else{
				callback();
			}
		}else{
			window.clearInterval(thread);
			throw "impossible to find anchor !";
		}
	};
	

	var styleFunctions = {
		"info" : function(elt) {
			elt.style.backgroundColor = "#000";
			elt.style.padding = "5px 0";
		},
		"error" : function(elt) {
			elt.style.backgroundColor = "#f00";
			elt.style.padding = "5px 0";
		},
		"warning" : function(elt) {
			elt.style.backgroundColor = "#ffbf00";
			elt.style.color="#000";
			elt.style.padding = "5px 0";
		}
	};
	
	var buildNotification = function(notification){
		var ligne = document.createElement('div');
		ligne.innerHTML=notification.message;
		ligne.classList.add("appnotifier-" + notification.type.toLowerCase());
		if (importStyleParam) {
			styleFunctions[notification.type.toLowerCase()](ligne);
		}
		anchor.appendChild(ligne);
	};
	
	var buildBanner = function(data) {
		initializeAnchor(function(){
			if (data.length > 0) {
				if (getParam("_classOfBanner")) {
					anchor.classList.add(getParam("_classOfBanner"));
				}
				if (importStyleParam) {
					anchor.style.textAlign="center";
					anchor.style.color="#fff";
					anchor.style.fontWeight="bold";
					anchor.style.position="fixed";
					anchor.style.bottom=0;
					anchor.style.display="block";
					anchor.style.width="100%";
				}
				for ( var i in data) {
					buildNotification(data[i]);
				}
				anchor.style.display="block";
		
			} else {
				anchor.style.display="none";
			}
		});
	};
	
	
	var executeJsonPRequest = function (url,successCallBack){
		var head = document.head || document.getElementsByTagName( 'head' )[0];
		var scriptTag = document.createElement( 'script' );
		scriptTag.type="text/javascript";
		var callbackName = "__an_"+new Date().getTime();
		var urlToCall = url+"?callback="+callbackName;
		window[callbackName] = function(data){
			buildBanner(data);
			head.removeChild(scriptTag);
			scriptTag=null;
			delete window[callbackName];
		};
		scriptTag.src=urlToCall;
		head.appendChild(scriptTag);
	};
	
	
	
	
	
	var launchAppNotifier = function(){
		if (refreshParam > 0) {
			thread = window.setInterval(callAppNotifier, refreshParam * 1000);
		}else{
			callAppNotifier();
		}
	};
	
	launchAppNotifier();
	

})(window,document);


