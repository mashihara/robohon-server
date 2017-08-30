REST APIはすべてPOSTです。
URLはすべて同じです。

## ■■■ロボホンからのアクセス
### （１）registerRoom　ルーム名登録
・パス：
/registerRoom

・リクエストBODY：
{serialId: $('#robohonSerialId').val(),roomName: $('#robohonRoomName').val()}
  - serialId：シリアルID
  - roomName：ルーム名

・レスポンスBODY：
{key:robohonkey,errorFlg：true,errorCode：'true' ,～}
  - key:このキーをもとにロボホンとアンドロイドを紐づける。シリアルID＋ルーム名
  - errorFlg：true //ture正常、false異常
  - errorCode：1000 //エラーコード
    - 1000:すでに他の端末でルーム名が登録されています

### （２）startEnd　開始終了API
・パス：
/startEnd

・リクエストBODY：
{key: robohonKey,startEnd:'start'};
  - key：「（１）registerRoom　ルーム名登録」で取得したキーを利用
  - startEnd:'start'⇒開始時 or 'end'⇒終了時

・レスポンスBODY：
なし

・socketへのtopic通知のJSON.parse(message.body)の中身：
{messageType: 1, errorFlg: false, errorCode: null,serverTime:"2017-08-30 03:07:41:793",～}
- messageType：1 or 2　（messageType自体は0⇒通常,1⇒開始,2⇒終了 ）
- errorFlg:true⇒エラー時、false⇒正常
- errorCode:エラーコード（現状想定なし）
- serverTime:サーバー時刻

### （３）chatsend　メッセージ送信API
・パス：
/chatsend

・リクエストBODY：
{syuwaFlg: false,key: robohonKey,message:"ロボホンからのメッセージです（発話）"};
  - syuwaFlg：true⇒手話、false⇒発話
  - key：「（１）registerRoom　ルーム名登録」で取得したキーを利用
  - message:メッセージそのもの

・レスポンスBODY：
なし

・socketへのtopic通知のJSON.parse(message.body)の中身：
{messageType: 1, errorFlg: false, errorCode: null,message:"ロボホンからのメッセージです（発話）",serverTime:"2017-08-30 03:07:41:793",～～}
- messageType：0　（messageType自体は0⇒通常,1⇒開始,2⇒終了 ）
- errorFlg:true⇒エラー時、false⇒正常
- errorCode:エラーコード（現状想定なし）
- message:メッセージそのもの
- serverTime:サーバー時刻

## ■■■androidアプリからのアクセス
### （４）checkRoom　ルーム名確認
・パス：
/checkRoom

・リクエストBODY：
{serialId: $('#robohonSerialId').val(),roomName: $('#robohonRoomName').val()}
  - serialId：シリアルID
  - roomName：ルーム名

・レスポンスBODY：
{key:robohonkey,errorFlg：true,errorCode：'true' ,～}
  - key:このキーをもとにロボホンとアンドロイドを紐づける。シリアルID＋ルーム名
  - errorFlg：true //ture正常、false異常
  - errorCode：1000 //エラーコード
    - 1100:ルーム名かシリアルIDが誤っています。「手話通訳アプリ」の設定を確認してください。

### （５）socket
socketまわりは省略します。chat.jsを参考にしてください。
