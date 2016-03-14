
/* Drop Tables */

DROP TABLE IF EXISTS estimate_item;
DROP TABLE IF EXISTS estimate;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS staff;



/* Drop Sequences */

DROP SEQUENCE IF EXISTS estimate_seq;
DROP SEQUENCE IF EXISTS item_seq;
DROP SEQUENCE IF EXISTS staff_seq;




/* Create Sequences */

CREATE SEQUENCE estimate_seq;
CREATE SEQUENCE item_seq;
CREATE SEQUENCE staff_seq;



/* Create Tables */

-- 見積
CREATE TABLE estimate
(
	-- id
	id bigint NOT NULL,
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
	-- 社員ID
	staff_id bigint,
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- 見積ー商品
CREATE TABLE estimate_item
(
	-- id(自動採番)
	id serial NOT NULL,
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
	-- id
	id bigint NOT NULL,
	-- 商品名
	item_name varchar(256),
	-- 単価
	unit_price numeric(19,6),
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- 社員
CREATE TABLE staff
(
	-- id
	id bigint NOT NULL,
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
	ADD FOREIGN KEY (staff_id)
	REFERENCES staff (id)
	ON UPDATE RESTRICT
	ON DELETE SET NULL
;



/* Comments */

COMMENT ON TABLE estimate IS '見積';
COMMENT ON COLUMN estimate.id IS 'id';
COMMENT ON COLUMN estimate.estimate_title IS '見積タイトル';
COMMENT ON COLUMN estimate.tax_rate IS '消費税率(%)';
COMMENT ON COLUMN estimate.subtotal IS '小計';
COMMENT ON COLUMN estimate.tax IS '消費税額';
COMMENT ON COLUMN estimate.total IS '合計';
COMMENT ON COLUMN estimate.staff_id IS '社員ID';
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
COMMENT ON COLUMN item.id IS 'id';
COMMENT ON COLUMN item.item_name IS '商品名';
COMMENT ON COLUMN item.unit_price IS '単価';
COMMENT ON TABLE staff IS '社員';
COMMENT ON COLUMN staff.id IS 'id';
COMMENT ON COLUMN staff.staff_name IS '社員名';



