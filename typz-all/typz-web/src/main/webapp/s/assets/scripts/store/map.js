var lnglatXY;
var address;
var la;
var lo;
var mapgd = new AMap.Map("container", {
    resizeEnable: true,
    center: [116.397428, 39.90923],
    zoom: 13
});
var marker = new AMap.Marker({
    position: mapgd.getCenter(),
    draggable: true,
    cursor: 'move',
    raiseOnDrag: true
});
AMap.event.addListener(marker, 'dragend', onComplete);//返回定位信息

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


