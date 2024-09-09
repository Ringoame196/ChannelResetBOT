# ChannelResetBOT
チャンネルをリセットするDiscordBOT


https://github.com/user-attachments/assets/e0bc7e69-a8bf-423c-ae82-b7b6954ec7fd


## 概要
/resetを実行すると、実行したチャンネルの「ピン留めしているメッセージ以外の削除」「リアクションの削除」を実行します <br>
1回で削除されるメッセージは80メッセージまでです

## コマンド
- /test - botの起動確認
- /reset - リセットをする
- /stop - bot自体をシャットダウンする

## 開発環境
- kotlin:1.9.23
- JDA: 5.0.1 [Discordボット開発用のライブラリ]
- Trove4j: 3.0.3 [高速かつメモリ効率の良いコレクション操作ライブラリ]
- Jackson Databind: 2.15.0 [JSONデータのシリアライズ/デシリアライズ用ライブラリ]
- Jackson Core: 2.15.0 [Jacksonのコアライブラリ]
- Jackson Annotations: 2.15.0 [JSONデータに注釈を付けるためのライブラリ]
- Kotlin Coroutines Core: 1.6.4 [非同期処理を簡単にするKotlinのライブラリ]
