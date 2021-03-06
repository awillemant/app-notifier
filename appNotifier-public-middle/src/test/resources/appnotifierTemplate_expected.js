(function(e, a, g, h, f, c, b, d) {
	if (!(f = e.jQuery) || g > f.fn.jquery || h(f)) {
		c = a.createElement("script");
		c.type = "text/javascript";
		c.src = "https://ajax.googleapis.com/ajax/libs/jquery/" + g + "/jquery.min.js";
		c.onload = c.onreadystatechange = function() {
			if (!b && (!(d = this.readyState) || d == "loaded" || d == "complete")) {
				h((f = e.jQuery).noConflict(1), b = 1);
				f(c).remove();
			}
		};
		a.documentElement.childNodes[0].appendChild(c);
	}
})(window, document, "1.5", function($, L) {
	// CODE TO EXECUTE

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

	var callAppNotifier = function() {

		var jqxhr = $.getJSON("http://myserver:1234/myContext/rs/public/" + getParam("_appUid") + "?callback=?");
		jqxhr.done(buildBanner);
		jqxhr.fail(function(jqxhr, textStatus, error) {
			console.error("error while retrieving jsonp data");
			console.error(jqxhr);
			console.error(textStatus);
			console.error(error);
		});
	};

	var getAnchor = function() {
		var anchor;
		if (!anchorParam || importStyleParam) {
			anchor = $(".appnotifier-banner");
			if (anchor.size() == 0) {
				anchor = $("<div>").addClass("appnotifier-banner");
				$("body").append(anchor);
			}

		} else {
			anchor = $(anchorParam);
		}
		anchor.html('');
		return anchor;
	};

	var styleFunctions = {
		"info" : function(elt) {
			elt.css("background-color", "#000").css("padding", "5px 0");
		},
		"error" : function(elt) {
			elt.css("background-color", "#f00").css("padding", "5px 0");
		},
		"warning" : function(elt) {
			elt.css("background-color", "#ffbf00").css("color", "#000").css("padding", "5px 0");
		}
	};

	var buildBanner = function(data) {
		console.log(data);
		var content = getAnchor();
		if (data.length > 0) {
			if (getParam("_classOfBanner")) {
				content.addClass(getParam("_classOfBanner"));
			}
			if (importStyleParam) {
				content.css("text-align", "center");
				content.css("color", "#fff");
				content.css("font-weight", "bold");
				content.css("position", "fixed");
				content.css("bottom", "0");
				content.css("display", "block");
				content.css("width", "100%");
			}
			for ( var d in data) {
				var ligne = $("<div>").html(data[d].message).addClass("appnotifier-" + data[d].type.toLowerCase());
				if (importStyleParam) {
					styleFunctions[data[d].type.toLowerCase()](ligne);
				}
				content.append(ligne);
			}
			content.show();

		} else {
			content.hide();
		}
		console.log(content);
	};

	callAppNotifier();
	if (refreshParam > 0) {
		window.setInterval(callAppNotifier, refreshParam * 1000);
	}

});
