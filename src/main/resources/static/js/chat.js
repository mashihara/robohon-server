var stompClient = null;
var appKey =null;
var robohonKey =null;

//■■■■■■■■■ロボホンが利用■■■■■■■■■
//ロボホンログインAPI
function registerRoom(){
	var loginUrl = './registerRoom';
	var requestdata = {serialId: $('#robohonSerialId').val(),roomName: $('#robohonRoomName').val()};
	$.ajax({
		type : 'post',                     // HTTPメソッド
		url  : loginUrl,           // POSTするURL
		data: JSON.stringify(requestdata),         // POSTするJSONデータ
		contentType: 'application/json',    // リクエストのContent-Type
		dataType: 'json',                   // レスポンスのデータ型
		success: function(response) {      // 成功時の処理
			if(response.errorFlg){
				console.log("エラー発生！エラーコード："+response.errorCode);
			}
			robohonKey = response.key;
		},
	});
}

//ロボホン会話開始API
function start(){
	var url = './startEnd';
	var requestdata = {key: robohonKey,startEnd:'start'};
	$.ajax({
		type : 'post',                      // HTTPメソッド
		url  : url,           // POSTするURL
		data: JSON.stringify(requestdata),         // POSTするJSONデータ
		contentType: 'application/json',    // リクエストのContent-Type
		dataType: 'json'                  // レスポンスのデータ型
	});
}
//ロボホン会話終了API
function end(){
	var url = './startEnd';
	var requestdata = {key: robohonKey,startEnd:'end'};
	$.ajax({
		type : 'post',                      // HTTPメソッド
		url  : url,           // POSTするURL
		data: JSON.stringify(requestdata),         // POSTするJSONデータ
		contentType: 'application/json',    // リクエストのContent-Type
		dataType: 'json'                  // レスポンスのデータ型
	});
}
//ロボホンから送信するときに利用
//restでメッセージを送信。発話のときに利用する。
function sendHatsuwaChatRestApi(){
	//var chatUrl = 'http://localhost/chatsend';
	var chatUrl = './chatsend';
	//syuwaFlg：手話の場合true、発話の場合false
	//roomName
	var requestdata = {syuwaFlg: false
		,key: robohonKey
		,message:"ロボホンからのメッセージです（発話）"};
	$.ajax({
		type : 'post',                      // HTTPメソッド
		url  : chatUrl,           // POSTするURL
		data: JSON.stringify(requestdata),         // POSTするJSONデータ
		contentType: 'application/json',    // リクエストのContent-Type
		dataType: 'json',                   // レスポンスのデータ型
		success: function(message) {      // 成功時の処理
				//showComment(message); //RESTの戻り値を表示
		},
	});
}

//ロボホンから送信するときに利用
//restでメッセージを送信。手話版。
function sendSyuwaChatRestApi(){
	//var chatUrl = 'http://localhost/chatsend';
	var chatUrl = './chatsend';
	var requestdata = {syuwaFlg: true
		,key: robohonKey
		,message:"ロボホンからのメッセージです（発話）"};
	$.ajax({
		type : 'post',                      // HTTPメソッド
		url  : chatUrl,           // POSTするURL
		data: JSON.stringify(requestdata),         // POSTするJSONデータ
		contentType: 'application/json',    // リクエストのContent-Type
		dataType: 'json',                   // レスポンスのデータ型
		success: function(message) {      // 成功時の処理
				//showComment(message); //RESTの戻り値を表示
		},
	});
}

//■■■■■■■■■アンドロイドアプリが利用■■■■■■■■■
//keyを取得する。
function checkRoom(){
	var loginUrl = './checkRoom';
	var requestdata = {serialId: $('#appSerialId').val(),roomName: $('#appRoomName').val()};
	$.ajax({
		type : 'post',                      // HTTPメソッド
		url  : loginUrl,           // POSTするURL
		data: JSON.stringify(requestdata),         // POSTするJSONデータ
		contentType: 'application/json',    // リクエストのContent-Type
		dataType: 'json',                   // レスポンスのデータ型
		success: function(response) {      // 成功時の処理
			if(response.errorFlg){
				console.log("エラー発生！エラーコード："+response.errorCode);
			}
			appKey = response.key;
		},
	});
}
//アプリ側ソケット
//SockJS,Stopmを使用
//socketに接続、topic登録
function connect() {
		var socket = new SockJS('/websocketEndpoint/'); // To connect WebSocket
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function (frame) {
			stompClient.subscribe('/topic/message/'+appKey, function (message) { // subscribe (/topic/greetings)
				showComment(JSON.parse(message.body));
			});
			stompClient.subscribe('/user/queue/errors', function (error) { // subscribe Error
				alert(JSON.parse(error.body).message);
			});
		});
}

function disconnect() {
    if (stompClient != null) {stompClient.disconnect();}
    console.log("Disconnected");
}

function showComment(message) {
	var comment;
	if(message.messageType==1){
		comment = "ロボホン開始しまーす";
	}else if(message.messageType==2){
		comment = "ロボホン終了しまーす";
	}else{
		comment = message.message;
	}
    $('#main_area').append($('<li>').text(message.serverTime+ ' 「'+comment+'」')).append($('<li>').text());
}

//アプリ側ソケット
//socketでtopicにメッセージ送信。
//サンプルで用意しているが、これは利用しない想定
function sendMessage() {
    stompClient.send("/app/message/"+appKey, {}, JSON.stringify(
			{'syuwaFlg': true
			,'serrialId':$('#appSerialId').val()
			,'roomName':$('#appRoomName').val()
			,'message':$('#comment').val()}));
}
