USE meli_frescos_bd;

INSERT INTO user(id, name, email, password, role)
VALUES  (1, 'sellerTest', 'seller_test@teste.com', '123456', 1),
        (2, 'agentTest', 'agent_test@teste.com', '123456', 2),
        (3, 'customerTest', 'customer_test@teste.com', '123456', 3);

INSERT INTO warehouse(id, address, name, agent_id)
VALUES  (1, 'Rua teste 1, 1234', 'Armazém SP', 2);

INSERT INTO product(id, category, name, price, seller_id)
VALUES (1, 1, 'FRUTA 1', 10, 1),
       (2, 1, 'FRUTA 2', 10, 1),
       (3, 1, 'FRUTA 2', 10, 1),

       (4, 2, 'CARNE 1', 10, 1),
       (5, 2, 'CARNE 2', 10, 1),
       (6, 2, 'CARNE 3', 10, 1),

       (7, 3, 'PEIXE 1', 10, 1),
       (8, 3, 'PEIXE 2', 10, 1),
       (9, 3, 'PEIXE 3', 10, 1);

INSERT INTO section(id, capacity, category, min_temperature, warehouse_id)
VALUES  (1, 500, 1, 15, 1),
        (2, 500, 2, 10, 1),
        (3, 500, 3, -5, 1);

INSERT INTO inbound_order(id, order_date, section_id)
VALUES  (1, '2022-04-25', 1),
        (2, '2022-04-25', 2),
        (3, '2022-04-25', 3);

INSERT INTO batch_stock(id, product_quantity, current_temperature, due_date,
                        manufacturing_date, manufacturing_time, volume, inbound_order_id, product_id)
VALUES  (1, 10, 15, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY),  '2023-01-01', '2023-01-01 00:00:00', 5, 1, 1),
        (2, 10, 15, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 1, 2),
        (3, 10, 15, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 1, 3),

        (4, 10, 5, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 2, 4),
        (5, 10, 5, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 2, 5),
        (6, 10, 5, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 2, 6),

        (7, 10, -1, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 3, 4),
        (8, 10, -1, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 3, 5),
        (9, 10, -1, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '2023-01-01', '2023-01-01 00:00:00', 5, 3, 6);