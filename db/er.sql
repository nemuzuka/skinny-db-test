
/* Drop Tables */

DROP TABLE IF EXISTS estimate_item;
DROP TABLE IF EXISTS estimate;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS staff;




/* Create Tables */

-- 見積
CREATE TABLE estimate
(
	-- id(自動採番)
	id bigserial NOT NULL,
	-- 見積タイトル
	estimate_title varchar(256),
	-- 消費税率(%)
	tax_rate numeric(19,6),
	-- 小計
	subtotal decimal(19,6),
	-- 消費税額
	tax numeric(19,6),
	-- 合計
	total numeric(19,6),
	-- 見積日
	estimate_date date,
	-- 作成社員ID
	create_staff_id bigint,
	-- 作成日時
	create_at timestamp,
	-- 最終更新社員ID
	last_update_staff_id bigint,
	-- 最終更新日時
	last_update_at timestamp,
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- 見積ー商品
CREATE TABLE estimate_item
(
	-- id(自動採番)
	id bigserial NOT NULL,
	-- 見積ID
	estimate_id bigint NOT NULL,
	-- 商品ID
	item_id bigint,
	-- ソート順
	sort_num bigint NOT NULL,
	-- 商品名
	item_name varchar(256),
	-- 単価
	unit_price numeric(19,6),
	-- 数量
	quantity bigint,
	-- 合計
	total numeric(19,6),
	-- メモ
	memo text,
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- 商品マスタ
CREATE TABLE item
(
	-- id(自動採番)
	id bigserial NOT NULL,
	-- 商品名
	item_name varchar(256),
	-- 単価
	unit_price numeric(19,6),
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- 社員
CREATE TABLE staff
(
	-- id(自動採番)
	id bigserial NOT NULL,
	-- 社員名
	staff_name varchar(256) NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;

-- 社員(DIテスト用)
CREATE TABLE staff_di
(
	-- id(自動採番)
	id bigserial NOT NULL,
	-- 社員名
	staff_name varchar(256) NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


/* Create Foreign Keys */

ALTER TABLE estimate_item
	ADD FOREIGN KEY (estimate_id)
	REFERENCES estimate (id)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE estimate_item
	ADD FOREIGN KEY (item_id)
	REFERENCES item (id)
	ON UPDATE RESTRICT
	ON DELETE SET NULL
;


ALTER TABLE estimate
	ADD FOREIGN KEY (create_staff_id)
	REFERENCES staff (id)
	ON UPDATE RESTRICT
	ON DELETE SET NULL
;


ALTER TABLE estimate
	ADD FOREIGN KEY (last_update_staff_id)
	REFERENCES staff (id)
	ON UPDATE RESTRICT
	ON DELETE SET NULL
;



/* Comments */

COMMENT ON TABLE estimate IS '見積';
COMMENT ON COLUMN estimate.id IS 'id(自動採番)';
COMMENT ON COLUMN estimate.estimate_title IS '見積タイトル';
COMMENT ON COLUMN estimate.tax_rate IS '消費税率(%)';
COMMENT ON COLUMN estimate.subtotal IS '小計';
COMMENT ON COLUMN estimate.tax IS '消費税額';
COMMENT ON COLUMN estimate.total IS '合計';
COMMENT ON COLUMN estimate.estimate_date IS '見積日';
COMMENT ON COLUMN estimate.create_staff_id IS '作成社員ID';
COMMENT ON COLUMN estimate.create_at IS '作成日時';
COMMENT ON COLUMN estimate.last_update_staff_id IS '最終更新社員ID';
COMMENT ON COLUMN estimate.last_update_at IS '最終更新日時';
COMMENT ON TABLE estimate_item IS '見積ー商品';
COMMENT ON COLUMN estimate_item.id IS 'id(自動採番)';
COMMENT ON COLUMN estimate_item.estimate_id IS '見積ID';
COMMENT ON COLUMN estimate_item.item_id IS '商品ID';
COMMENT ON COLUMN estimate_item.sort_num IS 'ソート順';
COMMENT ON COLUMN estimate_item.item_name IS '商品名';
COMMENT ON COLUMN estimate_item.unit_price IS '単価';
COMMENT ON COLUMN estimate_item.quantity IS '数量';
COMMENT ON COLUMN estimate_item.total IS '合計';
COMMENT ON COLUMN estimate_item.memo IS 'メモ';
COMMENT ON TABLE item IS '商品マスタ';
COMMENT ON COLUMN item.id IS 'id(自動採番)';
COMMENT ON COLUMN item.item_name IS '商品名';
COMMENT ON COLUMN item.unit_price IS '単価';
COMMENT ON TABLE staff IS '社員';
COMMENT ON COLUMN staff.id IS 'id(自動採番)';
COMMENT ON COLUMN staff.staff_name IS '社員名';
COMMENT ON TABLE staff_di IS '社員(DI用)';
COMMENT ON COLUMN staff_di.id IS 'id(自動採番)';
COMMENT ON COLUMN staff_di.staff_name IS '社員名';



