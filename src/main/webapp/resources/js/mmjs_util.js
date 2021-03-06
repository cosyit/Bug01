//简单封装id获取dom对象
function dom(id){
	return document.getElementById(id);
};

//随机颜色,op参数为透明度，默认为1
function randDomColor(op){
	var r = Math.floor(Math.random()*256);
	var g = Math.floor(Math.random()*256);
	var b = Math.floor(Math.random()*256);
	return "rgba("+r+","+g+","+b+","+(op||1)+")";
};

//给元素添加样式list
function css(dom,opts){
	for(var key in opts){
		var val = opts[key];
		if(typeof val=="number"){
		val+="px";
	}
	dom["style"][key] = val;
	}
};



/**
 * 判断非空
 * 
 * @param val
 * @returns {Boolean}
 */
function isEmpty(val) {
	val = $.trim(val);
	if (val == null)
		return true;
	if (val == undefined || val == 'undefined')
		return true;
	if (val == "")
		return true;
	if (val.length == 0)
		return true;
	if (!/[^(^\s*)|(\s*$)]/.test(val))
		return true;
	return false;
}

function isNotEmpty(val) {
	return !isEmpty(val);
}
