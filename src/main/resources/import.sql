USE meli_frescos_bd;

INSERT INTO user(id, name, email, password, role) VALUES (1, 'sellerTest1', 'seller_test@teste.com', '123456', 0);
INSERT INTO user(id, name, email, password, role) VALUES (2, 'agentTest', 'agent_test@teste.com', '123456', 1);
INSERT INTO user(id, name, email, password, role) VALUES (3, 'customerTest1', 'customer_test1@teste.com', '123456', 2);
INSERT INTO user(id, name, email, password, role) VALUES (4, 'customerTest2', 'customer_test2@teste.com', '123456', 2);
INSERT INTO user(id, name, email, password, role) VALUES (5, 'customerTest3', 'customer_test3@teste.com', '123456', 2);
INSERT INTO user(id, name, email, password, role) VALUES (6, 'customerTest4', 'customer_test4@teste.com', '123456', 2);
INSERT INTO user(id, name, email, password, role) VALUES (7, 'sellerTest2', 'seller_test@teste.com', '123456', 0);
INSERT INTO user(id, name, email, password, role) VALUES (8, 'sellerTest3', 'seller_test3@teste.com', '123456', 0);


INSERT INTO warehouse(id, address, name, agent_id) VALUES  (1, 'Rua teste 1, 1234', 'Armazém SP', 2);

INSERT INTO product(id, category, name, price, seller_id) VALUES (1, 0, 'FRUTA 1', 10, 1);
INSERT INTO product(id, category, name, price, seller_id) VALUES (2, 0, 'FRUTA 2', 10, 1);
INSERT INTO product(id, category, name, price, seller_id) VALUES (3, 0, 'FRUTA 2', 10, 1);

INSERT INTO product(id, category, name, price, seller_id) VALUES (4, 1, 'CARNE 1', 10, 7);
INSERT INTO product(id, category, name, price, seller_id) VALUES (5, 1, 'CARNE 2', 10, 7);
INSERT INTO product(id, category, name, price, seller_id) VALUES (6, 1, 'CARNE 3', 10, 7);

INSERT INTO product(id, category, name, price, seller_id) VALUES (7, 2, 'PEIXE 1', 10, 8);
INSERT INTO product(id, category, name, price, seller_id) VALUES (8, 2, 'PEIXE 2', 10, 8);
INSERT INTO product(id, category, name, price, seller_id) VALUES (9, 2, 'PEIXE 3', 10, 8);


INSERT INTO section(id, capacity, category, min_temperature, warehouse_id) VALUES (1, 500, 0, 15, 1);
INSERT INTO section(id, capacity, category, min_temperature, warehouse_id) VALUES (2, 500, 1, 10, 1);
INSERT INTO section(id, capacity, category, min_temperature, warehouse_id) VALUES (3, 500, 2, -5, 1);


INSERT INTO inbound_order(id, order_date, section_id) VALUES (1, '2022-04-25', 1);
INSERT INTO inbound_order(id, order_date, section_id) VALUES (2, '2022-04-25', 2);
INSERT INTO inbound_order(id, order_date, section_id) VALUES (3, '2022-04-25', 3);


INSERT INTO batch_stock(id, product_quantity, current_temperature, due_date, manufacturing_date, manufacturing_time, volume, inbound_order_id, product_id) VALUES (1, 10, 15, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY),  '2023-01-01', '2023-01-01 00:00:00', 5, 1, 1);
INSERT INTO batch_stock(id, product_quantity, current_temperature, due_date, manufacturing_date, manufacturing_time, volume, inbound_order_id, product_id) VALUES (2, 10, 15, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 1, 2);
INSERT INTO batch_stock(id, product_quantity, current_temperature, due_date, manufacturing_date, manufacturing_time, volume, inbound_order_id, product_id) VALUES (3, 10, 15, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 1, 3);

INSERT INTO batch_stock(id, product_quantity, current_temperature, due_date, manufacturing_date, manufacturing_time, volume, inbound_order_id, product_id) VALUES (4, 10, 5, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 2, 4);
INSERT INTO batch_stock(id, product_quantity, current_temperature, due_date, manufacturing_date, manufacturing_time, volume, inbound_order_id, product_id) VALUES (5, 10, 5, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 2, 5);
INSERT INTO batch_stock(id, product_quantity, current_temperature, due_date, manufacturing_date, manufacturing_time, volume, inbound_order_id, product_id) VALUES (6, 10, 5, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 2, 6);

INSERT INTO batch_stock(id, product_quantity, current_temperature, due_date, manufacturing_date, manufacturing_time, volume, inbound_order_id, product_id) VALUES (7, 10, -1, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 3, 4);
INSERT INTO batch_stock(id, product_quantity, current_temperature, due_date, manufacturing_date, manufacturing_time, volume, inbound_order_id, product_id) VALUES (8, 10, -1, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 3, 5);
INSERT INTO batch_stock(id, product_quantity, current_temperature, due_date, manufacturing_date, manufacturing_time, volume, inbound_order_id, product_id) VALUES (9, 10, -1, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 3, 6);


INSERT INTO rating(customer_id, product_id, created_at, rating, updated_at) VALUES(3,5,CURRENT_TIMESTAMP,5,null);
INSERT INTO rating(customer_id, product_id, created_at, rating, updated_at) VALUES(3,6,CURRENT_TIMESTAMP,4,null);
INSERT INTO rating(customer_id, product_id, created_at, rating, updated_at) VALUES(4,5,CURRENT_TIMESTAMP,5,null);
INSERT INTO rating(customer_id, product_id, created_at, rating, updated_at) VALUES(5,5,CURRENT_TIMESTAMP,5,null);
INSERT INTO rating(customer_id, product_id, created_at, rating, updated_at) VALUES(5,6,CURRENT_TIMESTAMP,4.5,null);
