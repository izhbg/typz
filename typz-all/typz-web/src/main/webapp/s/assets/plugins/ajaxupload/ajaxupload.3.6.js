(function() {
	var d = document,
	w = window;
	function get(element) {
		if (typeof element == "string")
		 element = d.getElementById(element);
		return element;
	}
	function addEvent(el, type, fn) {
		if (w.addEventListener) {
			el.addEventListener(type, fn, false);
		} else if (w.attachEvent) {
			var f = function() {
				fn.call(el, w.event);
			};
			el.attachEvent('on' + type, f)
		}
	}
	var toElement = function() {
		var div = d.createElement('div');
		return function(html) {
			div.innerHTML = html;
			var el = div.childNodes[0];
			div.removeChild(el);
			return el;
		}
	} ();
	function hasClass(ele, cls) {
		return ele.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
	}
	function addClass(ele, cls) {
		if (!hasClass(ele, cls)) ele.className += " " + cls;
	}
	function removeClass(ele, cls) {
		var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
		ele.className = ele.className.replace(reg, ' ');
	}
	if (document.documentElement["getBoundingClientRect"]) {
		var getOffset = function(el) {
			var box = el.getBoundingClientRect(),
			doc = el.ownerDocument,
			body = doc.body,
			docElem = doc.documentElement,
			clientTop = docElem.clientTop || body.clientTop || 0,
			clientLeft = docElem.clientLeft || body.clientLeft || 0,
			zoom = 1;
			if (body.getBoundingClientRect) {
				var bound = body.getBoundingClientRect();
				zoom = (bound.right - bound.left) / body.clientWidth;
			}
			if (zoom > 1) {
				clientTop = 0;
				clientLeft = 0;
			}
			var top = box.top / zoom + (window.pageYOffset || docElem && docElem.scrollTop / zoom || body.scrollTop / zoom) - clientTop,
			left = box.left / zoom + (window.pageXOffset || docElem && docElem.scrollLeft / zoom || body.scrollLeft / zoom) - clientLeft;
			return {
				top: top,
				left: left
			};
		}
	} else {
		var getOffset = function(el) {
			if (w.jQuery) {
				return jQuery(el).offset();
			}
			var top = 0,
			left = 0;
			do {
				top += el.offsetTop || 0;
				left += el.offsetLeft || 0;
			}
			while (el = el.offsetParent);
			return {
				left: left,
				top: top
			};
		}
	}
	function getBox(el) {
		var left,
		right,
		top,
		bottom;
		var offset = getOffset(el);
		left = offset.left;
		top = offset.top;
		right = left + el.offsetWidth;
		bottom = top + el.offsetHeight;
		return {
			left: left,
			right: right,
			top: top,
			bottom: bottom
		};
	}
	function getMouseCoords(e) {
		if (!e.pageX && e.clientX) {
			var zoom = 1;
			var body = document.body;
			if (body.getBoundingClientRect) {
				var bound = body.getBoundingClientRect();
				zoom = (bound.right - bound.left) / body.clientWidth;
			}
			return {
				x: e.clientX / zoom + d.body.scrollLeft + d.documentElement.scrollLeft,
				y: e.clientY / zoom + d.body.scrollTop + d.documentElement.scrollTop
			};
		}
		return {
			x: e.pageX,
			y: e.pageY
		};
	}
	var getUID = function() {
		var id = 0;
		return function() {
			return 'ValumsAjaxUpload' + id++;
		}
	} ();
	function fileFromPath(file) {
		return file.replace(/.*(\/|\\)/, "");
	}
	function getExt(file) {
		return (/[.]/.exec(file)) ? /[^.]+$/.exec(file.toLowerCase()) : '';
	}
	Ajax_upload = AjaxUpload = function(button, options) {
		if (button.jquery) {
			button = button[0];
		} else if (typeof button == "string" && /^#.*/.test(button)) {
			button = button.slice(1);
		}
		button = get(button);
		this._input = null;
		this._button = button;
		this._disabled = false;
		this._submitting = false;
		this._justClicked = false;
		this._parentDialog = d.body;
		if (window.jQuery && jQuery.ui && jQuery.ui.dialog) {
			var parentDialog = jQuery(this._button).parents('.ui-dialog');
			if (parentDialog.length) {
				this._parentDialog = parentDialog[0];
			}
		}
		this._settings = {
			action: 'upload.php',
			name: 'userfile',
			data: {},
			autoSubmit: true,
			responseType: false,
			onChange: function(file, extension) {},
			onSubmit: function(file, extension) {},
			onComplete: function(file, response) {}
		};
		for (var i in options) {
			this._settings[i] = options[i];
		}
		this._createInput();
		this._rerouteClicks();
	}
	AjaxUpload.prototype = {
		setData: function(data) {
			this._settings.data = data;
		},
		disable: function() {
			this._disabled = true;
		},
		enable: function() {
			this._disabled = false;
		},
		destroy: function() {
			if (this._input) {
				if (this._input.parentNode) {
					this._input.parentNode.removeChild(this._input);
				}
				this._input = null;
			}
		},
		_createInput: function() {
			var self = this;
			var input = d.createElement("input");
			input.setAttribute('type', 'file');
			input.setAttribute('name', this._settings.name);
			var styles = {
				'position': 'absolute',
				'margin': '-5px 0 0 -175px',
				'padding': 0,
				'width': '220px',
				'height': '30px',
				'fontSize': '14px',
				'opacity': 0,
				'cursor': 'pointer',
				'display': 'none',
				'zIndex': 2147483583
			};
			for (var i in styles) {
				input.style[i] = styles[i];
			}
			if (! (input.style.opacity === "0")) {
				input.style.filter = "alpha(opacity=0)";
			}
			this._parentDialog.appendChild(input);
			addEvent(input, 'change',
			function() {
				var file = fileFromPath(this.value);
				if (self._settings.onChange.call(self, file, getExt(file)) == false) {
					return;
				}
				if (self._settings.autoSubmit) {
					self.submit();
				}
			});
			addEvent(input, 'click',
			function() {
				self.justClicked = true;
				setTimeout(function() {
					self.justClicked = false;
				},
				2500);
			});
			this._input = input;
		},
		_rerouteClicks: function() {
			var self = this;
			var box,
			dialogOffset = {
				top: 0,
				left: 0
			},
			over = false;
			addEvent(self._button, 'mouseover',
			function(e) {
				if (!self._input || over) return;
				over = true;
				box = getBox(self._button);
				if (self._parentDialog != d.body) {
					dialogOffset = getOffset(self._parentDialog);
				}
			});
			addEvent(document, 'mousemove',
			function(e) {
				var input = self._input;
				if (!input || !over) return;
				if (self._disabled) {
					removeClass(self._button, 'hover');
					input.style.display = 'none';
					return;
				}
				var c = getMouseCoords(e);
				if ((c.x >= box.left) && (c.x <= box.right) && (c.y >= box.top) && (c.y <= box.bottom)) {
					input.style.top = c.y - dialogOffset.top + 'px';
					input.style.left = c.x - dialogOffset.left + 'px';
					input.style.display = 'block';
					addClass(self._button, 'hover');
				} else {
					over = false;
					var check = setInterval(function() {
						if (self.justClicked) {
							return;
						}
						if (!over) {
							input.style.display = 'none';
						}
						clearInterval(check);
					},
					25);
					removeClass(self._button, 'hover');
				}
			});
		},
		_createIframe: function() {
			var id = getUID();
			var iframe = toElement('<iframe src="javascript:false;" name="' + id + '" />');
			iframe.id = id;
			iframe.style.display = 'none';
			d.body.appendChild(iframe);
			return iframe;
		},
		submit: function() {
			var self = this,
			settings = this._settings;
			if (this._input.value === '') {
				return;
			}
			var file = fileFromPath(this._input.value);
			if (! (settings.onSubmit.call(this, file, getExt(file)) == false)) {
				var iframe = this._createIframe();
				var form = this._createForm(iframe);
				form.appendChild(this._input);
				form.submit();
				d.body.removeChild(form);
				form = null;
				this._input = null;
				this._createInput();
				var toDeleteFlag = false;
				addEvent(iframe, 'load',
				function(e) {
					if (iframe.src == "javascript:'%3Chtml%3E%3C/html%3E';" || iframe.src == "javascript:'<html></html>';") {
						if (toDeleteFlag) {
							setTimeout(function() {
								d.body.removeChild(iframe);
							},
							0);
						}
						return;
					}
					var doc = iframe.contentDocument ? iframe.contentDocument: frames[iframe.id].document;
					if (doc.readyState && doc.readyState != 'complete') {
						return;
					}
					if (doc.body && doc.body.innerHTML == "false") {
						return;
					}
					var response;
					if (doc.XMLDocument) {
						response = doc.XMLDocument;
					} else if (doc.body) {
						response = doc.body.innerHTML;
						if (settings.responseType && settings.responseType.toLowerCase() == 'json') {
							if (doc.body.firstChild && doc.body.firstChild.nodeName.toUpperCase() == 'PRE') {
								response = doc.body.firstChild.firstChild.nodeValue;
							}
							if (response) {
								response = window["eval"]("(" + response + ")");
							} else {
								response = {};
							}
						}
					} else {
						var response = doc;
					}
					settings.onComplete.call(self, file, response);
					toDeleteFlag = true;
					iframe.src = "javascript:'<html></html>';";
				});
			} else {
				d.body.removeChild(this._input);
				this._input = null;
				this._createInput();
			}
		},
		_createForm: function(iframe) {
			var settings = this._settings;
			var form = toElement('<form method="post" enctype="multipart/form-data"></form>');
			form.style.display = 'none';
			form.action = settings.action;
			form.target = iframe.name;
			d.body.appendChild(form);
			for (var prop in settings.data) {
				var el = d.createElement("input");
				el.type = 'hidden';
				el.name = prop;
				el.value = settings.data[prop];
				form.appendChild(el);
			}
			return form;
		}
	};
})();