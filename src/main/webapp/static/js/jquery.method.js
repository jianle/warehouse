/**
 * JQUERY插件 
 * 倪庆洋 2011-06-20
 */


/* $(".test1").wordLimit(); 自动获取css宽度进行处理，如果css中未对.test1给定宽度，则不起作用
	$(".test2").wordLimit(24); 截取字符数，值为大于0的整数，这里表示class为test2的标签内字符数最多24个
*/
(function($){
	
	$.fn.fieldValue = function(successful) {
		for (var val=[], i=0, max=this.length; i < max; i++) {
			var el = this[i];
			var v = $.fieldValue(el, successful);
			if (v === null || typeof v == 'undefined' || (v.constructor == Array && !v.length))
				continue;
			v.constructor == Array ? $.merge(val, v) : val.push(v);
		}
		return val;
	};
	
	$.fn.fieldValue = function(successful) {
		for (var val=[], i=0, max=this.length; i < max; i++) {
			var el = this[i];
			var v = $.fieldValue(el, successful);
			if (v === null || typeof v == 'undefined' || (v.constructor == Array && !v.length))
				continue;
			v.constructor == Array ? $.merge(val, v) : val.push(v);
		}
		return val;
	};

	/**
	 * Returns the value of the field element.
	 */
	$.fieldValue = function(el, successful) {
		var n = el.name, t = el.type, tag = el.tagName.toLowerCase();
		if (typeof successful == 'undefined') successful = true;

		if (successful && (!n || el.disabled || t == 'reset' || t == 'button' ||
			(t == 'checkbox' || t == 'radio') && !el.checked ||
			(t == 'submit' || t == 'image') && el.form && el.form.clk != el ||
			tag == 'select' && el.selectedIndex == -1))
				return null;

		if (tag == 'select') {
			var index = el.selectedIndex;
			if (index < 0) return null;
			var a = [], ops = el.options;
			var one = (t == 'select-one');
			var max = (one ? index+1 : ops.length);
			for(var i=(one ? index : 0); i < max; i++) {
				var op = ops[i];
				if (op.selected) {
					var v = op.value;
					if (!v) // extra pain for IE...
						v = (op.attributes && op.attributes['value'] && !(op.attributes['value'].specified)) ? op.text : op.value;
					if (one) return v;
					a.push(v);
				}
			}
			return a;
		}
		return el.value;
	};

	
	/**
	 *
	 * 将任意元素内的所有表单元素转换为JSON字符串
     * 
	 * formToArray() gathers form element data into an array of objects that can
	 * be passed to any of the following ajax functions: $.get, $.post, or load.
	 * Each object in the array has both a 'name' and 'value' property.  An example of
	 * an array for a simple login form might be:
	 *
	 * [ { name: 'username', value: 'jresig' }, { name: 'password', value: 'secret' } ]
	 *
	 * It is this array that is passed to pre-submit callback functions provided to the
	 * ajaxSubmit() and ajaxForm() methods.
	 * 
	 * 布尔标志，表示数据是否必须严格按照语义顺序（slower？）来进行提交。注意：一般来说，表单
	 * 已经按照语义顺序来进行了串行化（或序列化），除了type="image"的input元素。如果你的服务
	 * 器有严格的语义要求，以及表单中包含有一个type="image"的input元素，就应该将semantic设置
	 * 为true。（译注：这一段由于无法理解，翻译出来可能语不达意，但请达人指正。）
	 * 默认值：false 
	 * 
	 */
	$.fn.toJson = function(filter, semantic, proName) {
		var jsonStr = "";
		var a = [];
		if (this.length == 0) return jsonStr;
		
		var form = this[0];
		var els = semantic ? form.getElementsByTagName('*') : $(form).find(":input");
		
		if (!els) return jsonStr;
		
		$(els).each(function(){
			var el = $(this).get(0);
			var n = el.name;
			if(proName){
				n = $(this).attr(proName);
			}
			if (n){
				if (semantic && form.clk && el.type == "image") {
					// handle image inputs on the fly when semantic == true
					if(!el.disabled && form.clk == el) {
						a.push({name: n, value: $(el).val()});
						a.push({name: n+'.x', value: form.clk_x}, {name: n+'.y', value: form.clk_y});
					}
				}else{
					var v = $.fieldValue(el, true);

					if (v && v.constructor == Array) {
						for(var j=0, jmax=v.length; j < jmax; j++)
							a.push({name: n, value: v[j]});
					}
					else if (v !== null && typeof v != 'undefined')
						a.push({name: n, value: v});
				}
			}
		});
		
		if (!semantic && form.clk) {
			// input type=='image' are not found in elements array! handle it here
			var $input = $(form.clk), input = $input[0], n = input.name;
			if (n && !input.disabled && input.type == 'image') {
				a.push({name: n, value: $input.val()});
				a.push({name: n+'.x', value: form.clk_x}, {name: n+'.y', value: form.clk_y});
			}
		}
		if(filter == undefined){
			filter = true;
		}
		//return a;
		var datas = a;

		for(var i = 0; i < datas.length; i++){
			var name= datas[i].name;
			var value= datas[i].value;
			if(filter){
				if(!(value != undefined && value.length > 0)){
					continue;
				}
			}
			jsonStr += ", \""+name+"\":\""+value+"\"";
		}
		if(jsonStr.length > 0){
			jsonStr = jsonStr.substring(1);
		}
		jsonStr = "{" + jsonStr + "}";
		return jsonStr;
	};

	/**
	 * 
	 * 将JavaScript对象转换为JSON字符串
	 * 
	 */
	$.toJsonString = function(obj){
		switch(typeof(obj)){
			case 'object':
				var ret = [];
				if (obj instanceof Array){
					for (var i = 0, len = obj.length; i < len; i++){
						ret.push($.toJsonString(obj[i]));
					}
					return '[' + ret.join(',') + ']';
				} else if (obj instanceof RegExp){
					return obj.toString();
				} else{
					for (var a in obj){
						ret.push(a + ':' + $.toJsonString(obj[a]));
					}
					return '{' + ret.join(',') + '}';
				}
			case 'function':
				return 'function() {}';
			case 'number':
				return obj.toString();
			case 'string':
				return "\"" + obj.replace(/(\\|\")/g, "\\$1").replace(/\n|\r|\t/g, function(a) {return ("\n"==a)?"\\n":("\r"==a)?"\\r":("\t"==a)?"\\t":"";}) + "\"";
			case 'boolean':
				return obj.toString();
			default:
				if(obj != null){
					return obj.toString();
				}else{
					null;
				}
		}
	}
	
	/**
	 * MD5算法：用法$.MD5("计算的值")
	 * @param {Object} inputStr 输入参数
	 * @return {TypeName} 
	 */
	$.MD5 = function(inputStr){
		/*
		 * A JavaScript implementation of the RSA Data Security, Inc. MD5 Message
		 * Digest Algorithm, as defined in RFC 1321.
		 * Version 2.1 Copyright (C) Paul Johnston 1999 - 2002.
		 * Other contributors: Greg Holt, Andrew Kepert, Ydnar, Lostinet
		 * Distributed under the BSD License
		 * See http://pajhome.org.uk/crypt/md5 for more info.
		 */
		
		/*
		 * Configurable variables. You may need to tweak these to be compatible with
		 * the server-side, but the defaults work in most cases.
		 */
		var hexcase = 0;  /* hex output format. 0 - lowercase; 1 - uppercase        */
		var b64pad  = ""; /* base-64 pad character. "=" for strict RFC compliance   */
		var chrsz   = 8;  /* bits per input character. 8 - ASCII; 16 - Unicode      */
		
		/*
		 * These are the functions you'll usually want to call
		 * They take string arguments and return either hex or base-64 encoded strings
		 */
		function hex_md5(s){ return binl2hex(core_md5(str2binl(s), s.length * chrsz));}
		function b64_md5(s){ return binl2b64(core_md5(str2binl(s), s.length * chrsz));}
		function str_md5(s){ return binl2str(core_md5(str2binl(s), s.length * chrsz));}
		function hex_hmac_md5(key, data) { return binl2hex(core_hmac_md5(key, data)); }
		function b64_hmac_md5(key, data) { return binl2b64(core_hmac_md5(key, data)); }
		function str_hmac_md5(key, data) { return binl2str(core_hmac_md5(key, data)); }
		
		/*
		 * Perform a simple self-test to see if the VM is working
		 */
		function md5_vm_test()
		{
		  return hex_md5("abc") == "900150983cd24fb0d6963f7d28e17f72";
		}
		
		/*
		 * Calculate the MD5 of an array of little-endian words, and a bit length
		 */
		function core_md5(x, len)
		{
		  /* append padding */
		  x[len >> 5] |= 0x80 << ((len) % 32);
		  x[(((len + 64) >>> 9) << 4) + 14] = len;
		
		  var a =  1732584193;
		  var b = -271733879;
		  var c = -1732584194;
		  var d =  271733878;
		
		  for(var i = 0; i < x.length; i += 16)
		  {
		    var olda = a;
		    var oldb = b;
		    var oldc = c;
		    var oldd = d;
		
		    a = md5_ff(a, b, c, d, x[i+ 0], 7 , -680876936);
		    d = md5_ff(d, a, b, c, x[i+ 1], 12, -389564586);
		    c = md5_ff(c, d, a, b, x[i+ 2], 17,  606105819);
		    b = md5_ff(b, c, d, a, x[i+ 3], 22, -1044525330);
		    a = md5_ff(a, b, c, d, x[i+ 4], 7 , -176418897);
		    d = md5_ff(d, a, b, c, x[i+ 5], 12,  1200080426);
		    c = md5_ff(c, d, a, b, x[i+ 6], 17, -1473231341);
		    b = md5_ff(b, c, d, a, x[i+ 7], 22, -45705983);
		    a = md5_ff(a, b, c, d, x[i+ 8], 7 ,  1770035416);
		    d = md5_ff(d, a, b, c, x[i+ 9], 12, -1958414417);
		    c = md5_ff(c, d, a, b, x[i+10], 17, -42063);
		    b = md5_ff(b, c, d, a, x[i+11], 22, -1990404162);
		    a = md5_ff(a, b, c, d, x[i+12], 7 ,  1804603682);
		    d = md5_ff(d, a, b, c, x[i+13], 12, -40341101);
		    c = md5_ff(c, d, a, b, x[i+14], 17, -1502002290);
		    b = md5_ff(b, c, d, a, x[i+15], 22,  1236535329);
		
		    a = md5_gg(a, b, c, d, x[i+ 1], 5 , -165796510);
		    d = md5_gg(d, a, b, c, x[i+ 6], 9 , -1069501632);
		    c = md5_gg(c, d, a, b, x[i+11], 14,  643717713);
		    b = md5_gg(b, c, d, a, x[i+ 0], 20, -373897302);
		    a = md5_gg(a, b, c, d, x[i+ 5], 5 , -701558691);
		    d = md5_gg(d, a, b, c, x[i+10], 9 ,  38016083);
		    c = md5_gg(c, d, a, b, x[i+15], 14, -660478335);
		    b = md5_gg(b, c, d, a, x[i+ 4], 20, -405537848);
		    a = md5_gg(a, b, c, d, x[i+ 9], 5 ,  568446438);
		    d = md5_gg(d, a, b, c, x[i+14], 9 , -1019803690);
		    c = md5_gg(c, d, a, b, x[i+ 3], 14, -187363961);
		    b = md5_gg(b, c, d, a, x[i+ 8], 20,  1163531501);
		    a = md5_gg(a, b, c, d, x[i+13], 5 , -1444681467);
		    d = md5_gg(d, a, b, c, x[i+ 2], 9 , -51403784);
		    c = md5_gg(c, d, a, b, x[i+ 7], 14,  1735328473);
		    b = md5_gg(b, c, d, a, x[i+12], 20, -1926607734);
		
		    a = md5_hh(a, b, c, d, x[i+ 5], 4 , -378558);
		    d = md5_hh(d, a, b, c, x[i+ 8], 11, -2022574463);
		    c = md5_hh(c, d, a, b, x[i+11], 16,  1839030562);
		    b = md5_hh(b, c, d, a, x[i+14], 23, -35309556);
		    a = md5_hh(a, b, c, d, x[i+ 1], 4 , -1530992060);
		    d = md5_hh(d, a, b, c, x[i+ 4], 11,  1272893353);
		    c = md5_hh(c, d, a, b, x[i+ 7], 16, -155497632);
		    b = md5_hh(b, c, d, a, x[i+10], 23, -1094730640);
		    a = md5_hh(a, b, c, d, x[i+13], 4 ,  681279174);
		    d = md5_hh(d, a, b, c, x[i+ 0], 11, -358537222);
		    c = md5_hh(c, d, a, b, x[i+ 3], 16, -722521979);
		    b = md5_hh(b, c, d, a, x[i+ 6], 23,  76029189);
		    a = md5_hh(a, b, c, d, x[i+ 9], 4 , -640364487);
		    d = md5_hh(d, a, b, c, x[i+12], 11, -421815835);
		    c = md5_hh(c, d, a, b, x[i+15], 16,  530742520);
		    b = md5_hh(b, c, d, a, x[i+ 2], 23, -995338651);
		
		    a = md5_ii(a, b, c, d, x[i+ 0], 6 , -198630844);
		    d = md5_ii(d, a, b, c, x[i+ 7], 10,  1126891415);
		    c = md5_ii(c, d, a, b, x[i+14], 15, -1416354905);
		    b = md5_ii(b, c, d, a, x[i+ 5], 21, -57434055);
		    a = md5_ii(a, b, c, d, x[i+12], 6 ,  1700485571);
		    d = md5_ii(d, a, b, c, x[i+ 3], 10, -1894986606);
		    c = md5_ii(c, d, a, b, x[i+10], 15, -1051523);
		    b = md5_ii(b, c, d, a, x[i+ 1], 21, -2054922799);
		    a = md5_ii(a, b, c, d, x[i+ 8], 6 ,  1873313359);
		    d = md5_ii(d, a, b, c, x[i+15], 10, -30611744);
		    c = md5_ii(c, d, a, b, x[i+ 6], 15, -1560198380);
		    b = md5_ii(b, c, d, a, x[i+13], 21,  1309151649);
		    a = md5_ii(a, b, c, d, x[i+ 4], 6 , -145523070);
		    d = md5_ii(d, a, b, c, x[i+11], 10, -1120210379);
		    c = md5_ii(c, d, a, b, x[i+ 2], 15,  718787259);
		    b = md5_ii(b, c, d, a, x[i+ 9], 21, -343485551);
		
		    a = safe_add(a, olda);
		    b = safe_add(b, oldb);
		    c = safe_add(c, oldc);
		    d = safe_add(d, oldd);
		  }
		  return Array(a, b, c, d);
		
		}
		
		/*
		 * These functions implement the four basic operations the algorithm uses.
		 */
		function md5_cmn(q, a, b, x, s, t)
		{
		  return safe_add(bit_rol(safe_add(safe_add(a, q), safe_add(x, t)), s),b);
		}
		function md5_ff(a, b, c, d, x, s, t)
		{
		  return md5_cmn((b & c) | ((~b) & d), a, b, x, s, t);
		}
		function md5_gg(a, b, c, d, x, s, t)
		{
		  return md5_cmn((b & d) | (c & (~d)), a, b, x, s, t);
		}
		function md5_hh(a, b, c, d, x, s, t)
		{
		  return md5_cmn(b ^ c ^ d, a, b, x, s, t);
		}
		function md5_ii(a, b, c, d, x, s, t)
		{
		  return md5_cmn(c ^ (b | (~d)), a, b, x, s, t);
		}
		
		/*
		 * Calculate the HMAC-MD5, of a key and some data
		 */
		function core_hmac_md5(key, data)
		{
		  var bkey = str2binl(key);
		  if(bkey.length > 16) bkey = core_md5(bkey, key.length * chrsz);
		
		  var ipad = Array(16), opad = Array(16);
		  for(var i = 0; i < 16; i++)
		  {
		    ipad[i] = bkey[i] ^ 0x36363636;
		    opad[i] = bkey[i] ^ 0x5C5C5C5C;
		  }
		
		  var hash = core_md5(ipad.concat(str2binl(data)), 512 + data.length * chrsz);
		  return core_md5(opad.concat(hash), 512 + 128);
		}
		
		/*
		 * Add integers, wrapping at 2^32. This uses 16-bit operations internally
		 * to work around bugs in some JS interpreters.
		 */
		function safe_add(x, y)
		{
		  var lsw = (x & 0xFFFF) + (y & 0xFFFF);
		  var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
		  return (msw << 16) | (lsw & 0xFFFF);
		}
		
		/*
		 * Bitwise rotate a 32-bit number to the left.
		 */
		function bit_rol(num, cnt)
		{
		  return (num << cnt) | (num >>> (32 - cnt));
		}
		
		/*
		 * Convert a string to an array of little-endian words
		 * If chrsz is ASCII, characters >255 have their hi-byte silently ignored.
		 */
		function str2binl(str)
		{
		  var bin = Array();
		  var mask = (1 << chrsz) - 1;
		  for(var i = 0; i < str.length * chrsz; i += chrsz)
		    bin[i>>5] |= (str.charCodeAt(i / chrsz) & mask) << (i%32);
		  return bin;
		}
		
		/*
		 * Convert an array of little-endian words to a string
		 */
		function binl2str(bin)
		{
		  var str = "";
		  var mask = (1 << chrsz) - 1;
		  for(var i = 0; i < bin.length * 32; i += chrsz)
		    str += String.fromCharCode((bin[i>>5] >>> (i % 32)) & mask);
		  return str;
		}
		
		/*
		 * Convert an array of little-endian words to a hex string.
		 */
		function binl2hex(binarray)
		{
		  var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
		  var str = "";
		  for(var i = 0; i < binarray.length * 4; i++)
		  {
		    str += hex_tab.charAt((binarray[i>>2] >> ((i%4)*8+4)) & 0xF) +
		           hex_tab.charAt((binarray[i>>2] >> ((i%4)*8  )) & 0xF);
		  }
		  return str;
		}
		
		/*
		 * Convert an array of little-endian words to a base-64 string
		 */
		function binl2b64(binarray)
		{
		  var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
		  var str = "";
		  for(var i = 0; i < binarray.length * 4; i += 3)
		  {
		    var triplet = (((binarray[i   >> 2] >> 8 * ( i   %4)) & 0xFF) << 16)
		                | (((binarray[i+1 >> 2] >> 8 * ((i+1)%4)) & 0xFF) << 8 )
		                |  ((binarray[i+2 >> 2] >> 8 * ((i+2)%4)) & 0xFF);
		    for(var j = 0; j < 4; j++)
		    {
		      if(i * 8 + j * 6 > binarray.length * 32) str += b64pad;
		      else str += tab.charAt((triplet >> 6*(3-j)) & 0x3F);
		    }
		  }
		  return str;
		}
		return hex_md5(inputStr);
	};
	
	/*
	 * 创建cookie： jQuery.jCookie('cookie','value')
	 * 删除cookie： jQuery.jCookie('cookie',null)
	 * 读取cookie： jQuery.jCookie('cookie'),如果没有值则返回false
	 *
	 * 需要注意的是不能存数字0。如果确实要存0，则先转为string型。如 var i=0;
	 */
	$.jCookie = function (i, b, l, j) {
		if (!navigator.cookieEnabled) {
			return false;
		}
		var j = j || {};
		if (typeof (arguments[0]) !== "string" && arguments.length === 1) {
			j = arguments[0];
			i = j.name;
			b = j.value;
			l = j.expires;
		}
		i = encodeURI(i);
		if (b && (typeof (b) !== "number" && typeof (b) !== "string" && b !== null)) {
			return false;
		}
		var e = j.path ? "; path=" + j.path : "";
		var f = j.domain ? "; domain=" + j.domain : "";
		var d = j.secure ? "; secure" : "";
		var g = "";
		if (b || (b === null && arguments.length == 2)) {
			l = (l === null || (b === null && arguments.length == 2)) ? -1 : l;
			if (typeof (l) === "number" && l != "session" && l !== undefined) {
				var k = new Date();
				k.setTime(k.getTime() + (l * 24 * 60 * 60 * 1000));
				g = ["; expires=", k.toGMTString()].join("");
			}
			document.cookie = [i, "=", encodeURI(b), g, f, e, d].join("");
			return true;
		}
		if (!b && typeof (arguments[0]) === "string" && arguments.length == 1 && document.cookie && document.cookie.length) {
			var a = document.cookie.split(";");
			var h = a.length;
			while (h--) {
				var c = a[h].split("=");
				if (jQuery.trim(c[0]) === i) {
					return decodeURI(c[1]);
				}
			}
		}
		return false;
	};
	$.parseURL = function(part){
		if(!url || url == null || url.length == 0){
			url = window.location.href;
		}
		
	}
	$.getQueryString = function(queryStringName){ 
		var returnValue=""; 
		var URLString = new String(document.location); 
		var serachLocation=-1; 
		var queryStringLength=queryStringName.length; 
		do { 
			serachLocation=URLString.indexOf(queryStringName+"\=");
			if (serachLocation!=-1) { 
				if ((URLString.charAt(serachLocation-1)=='?') || (URLString.charAt(serachLocation-1)=='&')) { 
					URLString=URLString.substr(serachLocation); 
					break; 
				} 
				URLString=URLString.substr(serachLocation+queryStringLength+1); 
			}
		} while (serachLocation!=-1) 
		if (serachLocation!=-1) { 
			var seperatorLocation=URLString.indexOf("&"); 
			if (seperatorLocation==-1) { 
				returnValue=URLString.substr(queryStringLength+1); 
			} else { 
				returnValue=URLString.substring(queryStringLength+1,seperatorLocation); 
			}  
		} 
		return returnValue;
	};
	/**
	 * 获取字符串所占的字节数
	 * @param {Object} str
	 * @return {TypeName} 
	 */
	$.getBytesLength = function(str){
		var totalLength = 0;
	    var charCode;
	    for (var i = 0; i < str.length; i++) {
	        charCode = str.charCodeAt(i);
	        if (charCode < 0x007f)  {
	            totalLength++;
	        } else if ((0x0080 <= charCode) && (charCode <= 0x07ff))  {
	            totalLength += 2;
	        } else if ((0x0800 <= charCode) && (charCode <= 0xffff))  {
	            totalLength += 3;
	        } else{
	            totalLength += 4;
	        }
	    }
	    return totalLength;
	}
	/**
	 * 复制内容到剪贴板
	 * @return {TypeName} 
	 */
	$.copy = function(content){
		var clipBoardContent = content;
		if (window.clipboardData) {
			window.clipboardData.setData("Text", clipBoardContent);
			return true;
		} else {
			alert("复制功能请在IE浏览器上使用！");
		}
		return false;
	}
	
})(jQuery);


Base64 = {};

/**
 * Base64编码 JQUERY插件
 * 
 * 倪庆洋 2012-05-28
 * @param {Object} $
 * @return {TypeName} 
 * 
 */
(function(Base64){
	Base64.encode = function(src) {
		return base64encode(utf16to8(src));
	}
	
	Base64.decode = function(src) {
		return utf8to16(base64decode(src));
	}
	
	
	var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	var base64DecodeChars = new Array(
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
	    52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
	    -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
	    15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
	    -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
	    41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);
	
	function base64encode(str) {
	    var out, i, len;
	    var c1, c2, c3;
	
	    len = str.length;
	    i = 0;
	    out = [];
	    while(i < len) {
		    c1 = str.charCodeAt(i++) & 0xff;
		    if(i == len)
		    {
		        out[out.length] = base64EncodeChars.charAt(c1 >> 2);
	    	    out[out.length] = base64EncodeChars.charAt((c1 & 0x3) << 4);
		        out[out.length] = "==";
		        break;
	    	}
		    c2 = str.charCodeAt(i++);
	    	if(i == len)
		    {
		        out[out.length] = base64EncodeChars.charAt(c1 >> 2);
	    	    out[out.length] = base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
		        out[out.length] = base64EncodeChars.charAt((c2 & 0xF) << 2);
		        out[out.length] = "=";
	    	    break;
		    }
	    	c3 = str.charCodeAt(i++);
		    out[out.length] = base64EncodeChars.charAt(c1 >> 2);
	    	out[out.length] = base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
		    out[out.length] = base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >>6));
	    	out[out.length] = base64EncodeChars.charAt(c3 & 0x3F);
	    }
	    return out.join('');
	}
	
	function base64decode(str) {
	    var c1, c2, c3, c4;
	    var i, len, out;
	
	    len = str.length;
	    i = 0;
	    out = [];
	    while(i < len) {
		    /* c1 */
	    	do {
		        c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
	    	} while(i < len && c1 == -1);
		    if(c1 == -1) break;
	
	    	/* c2 */
		    do {
		        c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
	    	} while(i < len && c2 == -1);
		    if(c2 == -1) break;
	
	    	out[out.length] = String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));
	
	    	/* c3 */
		    do {
		        c3 = str.charCodeAt(i++) & 0xff;
	    	    if(c3 == 61) return out.join('');
		        c3 = base64DecodeChars[c3];
	    	} while(i < len && c3 == -1);
		    if(c3 == -1) break;
	
	    	out[out.length] = String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));
	
	    	/* c4 */
		    do {
		        c4 = str.charCodeAt(i++) & 0xff;
	    	    if(c4 == 61) return out.join('');
	    	    c4 = base64DecodeChars[c4];
		    } while(i < len && c4 == -1);
	    	if(c4 == -1) break;
		    out[out.length] = String.fromCharCode(((c3 & 0x03) << 6) | c4);
	    }
	    return out.join('');
	}
	function utf16to8(str) {
	    var out, i, len, c;
	
	    out = "";
	    len = str.length;
	    for(i = 0; i < len; i++) {
		c = str.charCodeAt(i);
		if ((c >= 0x0001) && (c <= 0x007F)) {
		    out += str.charAt(i);
		} else if (c > 0x07FF) {
		    out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
		    out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));
		    out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
		} else {
		    out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));
		    out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
		}
	    }
	    return out;
	}
	function utf8to16(str) {
	    var out, i, len, c;
	    var char2, char3;
	
	    out = "";
	    len = str.length;
	    i = 0;
	    while(i < len) {
		c = str.charCodeAt(i++);
		switch(c >> 4)
		{ 
		  case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7:
		    // 0xxxxxxx
		    out += str.charAt(i-1);
		    break;
		  case 12: case 13:
		    // 110x xxxx   10xx xxxx
		    char2 = str.charCodeAt(i++);
		    out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
		    break;
		  case 14:
		    // 1110 xxxx  10xx xxxx  10xx xxxx
		    char2 = str.charCodeAt(i++);
		    char3 = str.charCodeAt(i++);
		    out += String.fromCharCode(((c & 0x0F) << 12) |
						   ((char2 & 0x3F) << 6) |
						   ((char3 & 0x3F) << 0));
		    break;
		}
	   }
	    return out;
	}
})(Base64)
