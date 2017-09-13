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
  - errorCode：1000 //エラーコード ★2017/9/13実装しました
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
- robohonFlg　：true（ロボホン）//★2017/9/13追加
- errorFlg:true⇒エラー時、false⇒正常
- errorCode:エラーコード（現状想定なし）
- serverTime:サーバー時刻

### （３）chatsend　メッセージ送信API
・パス：
/chatsend

・リクエストBODY：
{syuwaFlg: false,key: robohonKey,message:"ロボホンからのメッセージです（発話）"};
  - syuwaFlg：true⇒手話、false⇒発話
  - robohonFlg　：true⇒ロボホン、false⇒ユーザー //★2017/9/13追加
  - key：「（１）registerRoom　ルーム名登録」で取得したキーを利用
  - message:メッセージそのもの

・レスポンスBODY：
なし

・socketへのtopic通知のJSON.parse(message.body)の中身：
{messageType: 1, errorFlg: false, errorCode: null,message:"ロボホンからのメッセージです（発話）",serverTime:"2017-08-30 03:07:41:793",～～}
- messageType：0　（messageType自体は0⇒通常,1⇒開始,2⇒終了 ）
- errorFlg:true⇒エラー時、false⇒正常
- errorCode:エラーコード（現状想定なし）
- robohonFlg：true⇒ロボホン、false⇒ユーザー //★2017/9/13追加
- syuwaFlg：true⇒手話、false⇒発話 //★2017/9/13追加（設計書追記漏れでした）
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
  - errorCode：1000 //エラーコード ★2017/9/13実装しました
    - 1100:ルーム名かシリアルIDが誤っています。「手話通訳アプリ」の設定を確認してください。

### （５）socket
socketまわりは省略します。chat.jsを参考にしてください。


## ■■既存の手話判定(20170725_API仕様.mdも、この資料に追加しました)
## （１）/　ルーム名登録画像登録API
画像を受け取ってファイルシステムに保存するAPI

### request url,method
- POST
- http://robohon.ngrok.io/

### request header
```properties
"Connection", "Keep-Alive"
"Content-Type", "multipart/form-data; boundary=*****(UUID)*****"
```

### request body
** 詳細はロボホンのソースを確認**。multipart/form-dataのルールに則り送信

## 2-1. レスポンスのHTTPステータス
| ステータス <br> コード | レスポンス | レスポンスボディ | 説明 |
|-----------------|-----------|----------------|------|
| 200 | OK | json | 成功 |
| 400 | Bad Request | json | 不正なリクエスト |


### 2-2. 正常時レスポンス
```properties
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
```

//★2017/9/13,statusと時刻を追加
```js
{
  "status":0
  "startTime":"2017-08-30 03:07:41:793"
  "endTime":"2017-08-30 03:07:41:793"
  "duration":
}
```
- status: 0:正常
- duration: ナノ秒

### 2-3. 異常時レスポンス
```properties
HTTP/1.1 400
Content-Type: application/json;charset=UTF-8
```

//★2017/9/13時刻を追加
```js
{
  "status":1
  ,"startTime":"2017-08-30 03:07:41:793"
  ,"endTime":"2017-08-30 03:07:41:793"
  ,"duration":
}
```
- status: 1:異常
- duration: ナノ秒

## （２）/finish　結果取得API
１０枚のファイルを送った後にリクエストするAPI。
リクエストパラメータに１０枚のファイルのファイル名を設定する必要がある

### curlサンプル
```
curl -X POST http://robohon.ngrok.io/finish?img1=(ファイル名1)&img2=(ファイル名2)&img3=(ファイル名3)&img4=(ファイル名4)&img5=(ファイル名5)&img6=(ファイル名6)&img7=(ファイル名7)&img8=(ファイル名8)&img9=(ファイル名9)&img10=(ファイル名10)
```

### request header
ユーザーが設定する項目はない

### request body
(なし)

## 2-1. レスポンスのHTTPステータス
| ステータス <br> コード | レスポンス | レスポンスボディ | 説明 |
|-----------------|-----------|----------------|------|
| 200 | OK | json | 成功 |

### 2-2. 正常時レスポンス
```properties
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
```

//★2017/9/13時刻を追加
```js
{
  "label":7
  ,"prob":"0.25"
  ,"startTime":"2017-08-30 03:07:41:793"
  ,"endTime":"2017-08-30 03:07:41:793"
  ,"duration":
}
```
- label:認識した結果
- prob:精度（0～1)。1に近いほど正解している確率が高い。
※2017/7/20"voiceContent"を設定するのはやめます。
