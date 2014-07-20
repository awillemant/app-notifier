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
	
	
	var getAnchor = function() {
		var anchor;
		if (!anchorParam || importStyleParam) {
			anchor = document.querySelector(".appnotifier-banner");
			if (anchor == undefined) {
				anchor = document.createElement('div');
				anchor.classList.add("appnotifier-banner");
				document.body.appendChild(anchor);
			}
	
		} else {
			anchor = document.querySelector(anchorParam);
		}
		anchor.innerHTML="";
		return anchor;
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
	
	var buildNotification = function(notification,banner){
		var ligne = document.createElement('div');
		ligne.innerHTML=notification.message;
		ligne.classList.add("appnotifier-" + notification.type.toLowerCase());
		if (importStyleParam) {
			styleFunctions[notification.type.toLowerCase()](ligne);
		}
		banner.appendChild(ligne);
	};
	
	var buildBanner = function(data) {
		var banner = getAnchor();
		if (data.length > 0) {
			if (getParam("_classOfBanner")) {
				banner.classList.add(getParam("_classOfBanner"));
			}
			if (importStyleParam) {
				banner.style.textAlign="center";
				banner.style.color="#fff";
				banner.style.fontWeight="bold";
				banner.style.position="fixed";
				banner.style.bottom=0;
				banner.style.display="block";
				banner.style.width="100%";
			}
			for ( var i in data) {
				buildNotification(data[i],banner);
			}
			banner.style.display="block";
	
		} else {
			banner.style.display="none";
		}
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
	
	
	var callAppNotifier = function() {
		executeJsonPRequest("[[ROOT_URL]]rs/public/" + getParam("_appUid") ,buildBanner);
	};
	
	
	var launchAppNotifier = function(){
		if (refreshParam > 0) {
			window.setInterval(callAppNotifier, refreshParam * 1000);
		}
	};
	
	launchAppNotifier();
	

})(window,document);


