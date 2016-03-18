# skinny-db-test
SkinnyFrameworkを使用したDBテストのサンプル

# 設定

1. PostgreSQLをインストール
2. 接続情報を `src/main/resources/application.conf` に記述(testの箇所)
3. testに指定した接続先のdatabaseに対して、 `db/er.sql` を実行
4. `./skinny test` で実行するとDBを使用したテストが実行できます

# IntelliJで実行する場合
テストクラスはScalaTestで書かれています。IntelliJで実行する場合、ScalaTestのデフォルト設定として「VM parameters」に

`-Dskinny.env=test`

を設定して実行して下さい


# システム日付を固定にする方法
システム日付やシステム日時を取得する場合、

util.CurrentDateUtil#now / util.CurrentDateUtil#nowDateTime 

を使用して取得するようにすると、

VMのパラメータに

`-DfixDate=2016-01-01 -DfixDateTime=2016-01-01T23:59:59`

と設定することで、システム日付、システム日時を固定で取得することができます。

この方式にすると、例えば以前記録したSeleniumのシナリオを流すような、「システム日付に左右されず、常に同じ結果を返したい」ような場合にメリットがあると考えています



# テストクラスでやっていること
### model.StaffSpec

DbUnitを使用してExcelを元に初期データを登録するパターン

### model.ExtraEstimateSpec

DbUnitを使用してExcelを元に外部キーを貼ったtable間の初期データを登録するパターン

引数によって検索条件を動的に変える時のパターン

### logic.StaffLogic / logic.StaffLogicCreateSpec

n個以上のmodelを呼び出すlogicレイヤーのメソッドの書き方。カリー化した `(implicit session:DBSession)` がミソ。

この方式にしないと、AutoRollback を mixinした場合のテストクラスでrollbackされない(他に良いやり方無いかな)...

ちなみに、controllerからlogicレイヤーを呼び出す時は、controller.RootController を参照

# DBを使用したテストで気にする所

1. 何回やっても同じ結果になるように、複数件取得できる検索処理の場合、order by は必須
2. 外部キーを張っている場合、データの登録順(Excelのシートの記述順)に注意すること
    * 参照テーブルを先に登録する
