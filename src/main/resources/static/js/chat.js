var stompClient = null;

//SockJS,Stopmを使用
//socketに接続、topic登録
function connect() {
		var socket = new SockJS('/websocketEndpoint/'); // To connect WebSocket
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function (frame) {
			stompClient.subscribe('/topic/message', function (message) { // subscribe (/topic/greetings)
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
    stompClient.send("/app/message", {}, JSON.stringify({'syuwaFlg': true,'serialId': 'hogeserial','message':comment}));
}

var chatUrl = 'http://localhost/chatsend';

//restでメッセージを送信。発話のときに利用する。
function sendHatsuwaChatRestApi(){
	//syuwaFlg：手話の場合true、発話の場合false
	//serialId
	var requetdata = {syuwaFlg: false,serialId: "hogeserial",message:"テストです"};
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

//restでメッセージを送信。手話版。
//これは基本的には使う想定がない。送信のsyuwaFlg: true,にしただけ
function sendSyuwaChatRestApi(){
	var requetdata = {syuwaFlg: true,serialId: "hogeserial",message:"テストです"};
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
