var lnglatXY;
var address;
var la;
var lo;
var placeSearch;
var mapgd = new AMap.Map("container", {
    resizeEnable: true,
    center: [116.397428, 39.90923],
    zoom: 13
});
var marker = new AMap.Marker({
    position: mapgd.getCenter(),
    draggable: true,
    cursor: 'move',
    icon:$("#contextpath").val()+'/s/assets/img/position.png',
    raiseOnDrag: true
});
AMap.event.addListener(marker, 'dragend', onComplete);//返回定位信息
//search  插件
AMap.service('AMap.PlaceSearch',function(){//回调函数
	//实例化PlaceSearch
	placeSearch= new AMap.PlaceSearch({ //构造地点查询类
		map: mapgd
	});
});
//关键字查询
AMap.plugin('AMap.Autocomplete',function(){//回调函数
//实例化Autocomplete
	var autoOptions = {
		city: "", //城市，默认全国
		input:"tipinput"//使用联想输入的input的id
	};
	autocomplete= new AMap.Autocomplete(autoOptions);
	//TODO: 使用autocomplete对象调用相关功能
});  //构造地点查询类
AMap.event.addListener(autocomplete, "select", select);//注册监听，当选中某条记录时会触发
function select(e) {
	  placeSearch.setCity(e.poi.adcode);
      placeSearch.search(e.poi.name, function(status, result) {
    	  var x = e.poi.location.lng;  
    	  var y = e.poi.location.lat;
    	  la = y;
    	  lo = x;
    	  lnglatXY = new AMap.LngLat(x,y);
    	  general();
    	  marker.setPosition(mapgd.getCenter());
		});  //关键字查询查询
}
var mGeocoder;
function general(){
	mapgd.plugin(["AMap.Geocoder"], function() {          
		mGeocoder = new AMap.Geocoder({
			radius: 1000,  
			extensions: "all"  
		});  
		//返回地理编码结果   
		AMap.event.addListener(mGeocoder, "complete", geocoder_CallBack);   
		 //逆地理编码  
		mGeocoder.getAddress(lnglatXY);
	}); 

}
$("#cconfirmMap").click(function(){
	$("#la").val(la);
	$("#lo").val(lo);
	$("#address").val(address);
	$("#mapDialog").modal('hide');
});

marker.setMap(mapgd);
 //解析定位结果
function onComplete(data) {
	var x = data.lnglat.lng;  
	var y = data.lnglat.lat;
	la = y;
	lo = x;
	lnglatXY = new AMap.LngLat(x,y);
	general();
}
//回调函数  
function geocoder_CallBack(data) {  
	//返回地址描述  
	address = data.regeocode.formattedAddress;  
	//返回结果拼接输出  
}
function mapDialog(){
	$("#mapDialog").modal({ 
        backdrop: 'static',  
        keyboard: false  
    }).modal('show');
}


