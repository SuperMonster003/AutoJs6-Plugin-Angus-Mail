******

### リリース履歴

******

# v1.0.0

###### 2026/09/18

* `ヒント` P0 開発プレビュー: リポジトリの骨組み, ローカルサーバーテスト付きのメールコア, AutoJs6 プラグインセンター向けのプラグイン識別情報. Binder コントラクト, スクリプト API, 設定ページは ROADMAP.md のフェーズに沿って進めます.
* `機能` プラグイン識別子 `angus-mail` (エンジン `mail`), INFO サービス, Wake Activity, ホスト検出用の `org.autojs.plugin.MAIL` サービスの骨組み
* `機能` Eclipse Angus Mail 上のメールコア: SSL または STARTTLS を使う IMAP / POP3 / SMTP のセッション設定, パスワードと XOAUTH2 認証, SMTP 送信と IMAP 受信トレイ一覧をローカルの GreenMail サーバーで検証
* `機能` 10 言語の README, プラグインセンターの説明, 更新履歴
* `依存関係` Eclipse Angus Mail 2.0.5 (`org.eclipse.angus:jakarta.mail`) と Angus Activation 2.0.3, Jakarta Activation API 2.1.4
* `依存関係` JVM メールコアテスト用に GreenMail 2.1.13 を追加 (テストスコープのみ)
* `依存関係` 共有プラグインコントラクトとして `common-plugin-api.aar` (AutoJs6 モジュール `plugin-api/common-plugin-api`, ホストビルド 6.8.0 / 5281, MPL 2.0) を追加し, `locks/host-api-aars.lock` でハッシュを固定
