# skinny-db-test
SkinnyFrameworkを使用したDBテストのサンプル

# 設定

1. PostgreSQLをインストール
2. 接続情報を `src/main/resources/application.conf` に記述(testの箇所)
3. testに指定した接続先のdatabaseに対して、 `db/er.sql` を実行
4. `./skinny test` で実行するとDBを使用したテストが実行できます

# 見るべきクラス
### model.StaffSpec

DbUnitを使用してExcelを元に初期データを登録するパターン

### logic.StaffLogic / logic.StaffLogicCreateSpec

n個以上のmodelを呼び出すlogicレイヤーのメソッドの書き方。カリー化した `(implicit session:DBSession)` がミソ。

この方式にしないと、AutoRollback を mixinした場合のテストクラスでrollbackされない(他に良いやり方無いかな)...

ちなみに、controllerからlogicレイヤーを呼び出す時は、controller.RootController を参照

# DBを使用したテストで気にする所

1. PostgreSQLを利用する場合、PK項目にserial型を使用している場合、Excelでその項目にデータを設定しても反映されない(シーケンス値で上書きされる)
    * 外部キーを使用している場合、pkをbigintにして、実際はシーケンスから取得する
        * model.Staff / model.Estimate がシーケンスから取得する際のsample
        * model.EstimateItem のid項目は自動採番
2. 何回やっても同じ結果になるように、複数件取得できる検索処理の場合、order by は必須
3. 外部キーを張っている場合、データの登録順(Excelのシートの記述順)に注意すること
    * 参照テーブルを先に登録する
