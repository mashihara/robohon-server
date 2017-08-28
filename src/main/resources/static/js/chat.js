var stompClient = null;
var key =null;

//ログインAPI
//keyを取得する。
function chatLogin(){
	var loginUrl = 'http://localhost/chatlogin';
	var requestdata = {roomName: $('#roomName').val()};
	$.ajax({
		type : 'post',                      // HTTPメソッド
		url  : loginUrl,           // POSTするURL
		data: JSON.stringify(requestdata),         // POSTするJSONデータ
		contentType: 'application/json',    // リクエストのContent-Type
		dataType: 'json',                   // レスポンスのデータ型
		success: function(response) {      // 成功時の処理
				key = response.key;
		},
	});
}

//SockJS,Stopmを使用
//socketに接続、topic登録
function connect() {
		var socket = new SockJS('/websocketEndpoint/'); // To connect WebSocket
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function (frame) {
			var test =key;
			stompClient.subscribe('/topic/message/'+key, function (message) { // subscribe (/topic/greetings)
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
    $('#main_area').append($('<li>').text(message.serverTime+ ' 「'+message.message+'」')).append($('<li>').text());
}

//socketでtopicにメッセージ送信。
//chatアプリからこれは利用しない想定
function sendMessage() {
    var comment = $('#comment').val();
    stompClient.send("/app/message/"+key, {}, JSON.stringify({'syuwaFlg': true,'roomName': 'testroom','message':comment}));
}


//restでメッセージを送信。発話のときに利用する。
function sendHatsuwaChatRestApi(){
	var chatUrl = 'http://localhost/chatsend';
	//syuwaFlg：手話の場合true、発話の場合false
	//roomName
	var requestdata = {syuwaFlg: false,roomName: "testroom",message:"テストです"};
	$.ajax({
		type : 'post',                      // HTTPメソッド
		url  : chatUrl,           // POSTするURL
		data: JSON.stringify(requestdata),         // POSTするJSONデータ
		contentType: 'application/json',    // リクエストのContent-Type
		dataType: 'json',                   // レスポンスのデータ型
		success: function(message) {      // 成功時の処理
				showComment(message);
		},
	});
}

//restでメッセージを送信。手話版。
function sendSyuwaChatRestApi(){
	var chatUrl = 'http://localhost/chatsend';
	var requetdata = {syuwaFlg: true,roomName: "hogeserial",message:"テストです"};
	$.ajax({
		type : 'post',                      // HTTPメソッド
		url  : chatUrl,           // POSTするURL
		data: JSON.stringify(requetdata),         // POSTするJSONデータ
		contentType: 'application/json',    // リクエストのContent-Type
		dataType: 'json',                   // レスポンスのデータ型
		success: function(message) {      // 成功時の処理
				showComment(message);
		},
	});
}
