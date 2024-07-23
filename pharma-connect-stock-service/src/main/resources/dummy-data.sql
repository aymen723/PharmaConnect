CREATE EXTENSION IF NOT EXISTS postgis;

INSERT INTO pharmacy_location (location_coordinates, location_google_url)
VALUES -- Novell Ville
       ('POINT(6.5759889 36.2459931)', 'https://maps.app.goo.gl/cxjoMVR5H5hH659F8'), -- 1
       ('POINT(6.566777 36.242515)', null),                                          -- 2
       ('POINT(6.567676 36.245546)', 'https://maps.app.goo.gl/NbuLNVsfjbcCtdWb7'),   -- 3
       ('POINT(6.556193 36.241407)', 'https://maps.app.goo.gl/bMqP46H72Ym5xaYP7'),   -- 4
       ('POINT(6.583243 36.257310)', 'https://maps.app.goo.gl/2Jm4ZZXMCuGHcMEK8'),   -- 5
       -- El Khroub
       ('POINT(6.701239 36.254672)', 'https://maps.app.goo.gl/zcSTvTka5m6vLhKF6'),   -- 6
       ('POINT(6.705436 36.259044 )', 'https://maps.app.goo.gl/ACeKfHKmPk3X5Z6e9'),  -- 7
       ('POINT(6.698665 36.257566 )', 'https://maps.app.goo.gl/ewZ23QMtsGPiJi8g7'),  -- 8
       ('POINT(6.703354 36.253340 )', 'https://maps.app.goo.gl/uaJPkbaCgvHd8nR18'),  -- 9
       -- Ain Smara
       ('POINT(6.485924 36.263213)', 'https://maps.app.goo.gl/7UVHBGsHCdmSVxyr8'),   -- 10
       ('POINT(6.486330 36.264692)', null),                                          -- 11
       ('POINT(6.484385 36.261651)', 'https://maps.app.goo.gl/kCLgeSBPfnmo2bZ4A'),   -- 12
       ('POINT( 6.495705 36.265597)', 'https://maps.app.goo.gl/PSdDkim6omdLaHfL9'),  -- 13
       ('POINT( 6.487308 36.262228)', null); -- 14

INSERT INTO pharmacy (pharma_name, pharma_payment_support, pharma_enabled, pharma_picture, location_id)

VALUES -- Novell Ville
       ('Pharmacie MERZOUGUI F Zohra', false, true,
        'https://lh5.googleusercontent.com/p/AF1QipN5bo5sEG0Fu5r2cj36MT7Wri7AFWRlO14TFcCU=w408-h306-k-no', 1), -- 1
       ('Pharmacie Amal', false, true,
        'https://images.squarespace-cdn.com/content/v1/6357d9add6f0d711d5c27c9f/b62e3f02-ec34-4a6f-8ffe-9275fceb274a/parade-pharmacy.jpeg',
        2),                                                                                                    -- 2
       ('Pharmacie hadjout', false, true, null, 3),                                                            -- 3
       ('Pharmacie Berlat', false, true,
        'https://augustabusinessdaily.com/wp-content/uploads/2023/03/Living-Well-Pharmacy-1.jpeg', 4),         -- 4
       -- El Khroub
       ('Pharmacie Nehal FZ', false, true,
        'https://erepublic.brightspotcdn.com/dims4/default/1fe8975/2147483647/strip/false/crop/1200x800+0+0/resize/1200x800!/quality/90/?url=http%3A%2F%2Ferepublic-brightspot.s3.us-west-2.amazonaws.com%2Ff5%2Fcf%2F29ccf752eb71593f399096cfbea4%2Fcanadian-pharmacy.jpg',
        5),                                                                                                    -- 5
       ('pharmacie FADEL.S', false, true,
        'https://c8.alamy.com/comp/MJADB1/traditional-chemist-shop-with-vintage-frontage-with-a-gentleman-customer-entering-the-front-door-viewed-from-the-street-on-a-sunny-day-MJADB1.jpg',
        6),                                                                                                    -- 6
       ('Pharmacie chouiter sihem', false, true,
        'https://c8.alamy.com/comp/BX948Y/a-pharmacy-in-a-uk-city-BX948Y.jpg',
        7),                                                                                                    -- 7
       ('Pharmacie Belkhiri', false, true,
        'https://images.squarespace-cdn.com/content/v1/6357d9add6f0d711d5c27c9f/b62e3f02-ec34-4a6f-8ffe-9275fceb274a/parade-pharmacy.jpeg',
        8),                                                                                                    -- 8
       ('PHARMACIE BOUSSOUF.M', false, true,
        'https://pharmafocusgroup.s3.eu-west-2.amazonaws.com/chanachemist.co.uk/15/siteimages/about-us-logo-lambeth-pharmacy-15.jpg',
        9),                                                                                                    -- 9
       -- Ain Smara
       ('Parapharmacie Bien Etre & Santé', false, true,
        'https://c8.alamy.com/comp/RYJ957/stalls-in-row-at-supermarket-parapharmacy-products-france-RYJ957.jpg',
        10),                                                                                                   -- 10
       ('pharmacie Imane ', false, true,
        'https://www.frontsigns.com/wp-content/uploads/2023/06/glass-door-pharmacy-design.jpg',
        11),                                                                                                   -- 11
       ('PHARMACIE DELMI ABDERRAHIM BEY', false, true,
        'https://c8.alamy.com/comp/BX948Y/a-pharmacy-in-a-uk-city-BX948Y.jpg',
        12),                                                                                                   -- 12
       ('Pharmacie Sedrati Wafa', false, true,
        'https://www.olinspharmacy.co.uk/wp-content/uploads/2021/11/abt-front.jpg',
        13),                                                                                                   -- 13
       ('Pharmacie Fenway', false, true,
        'https://s3-media0.fl.yelpcdn.com/bphoto/0sz_eCvlLddDAV6kPWTrgQ/1000s.jpg',
        14); -- 14

INSERT INTO medical_product (product_name,product_price,product_barcode,product_description)
VALUES ('test',122, 'something', 'test description');

INSERT  INTO  stock (pharma_id,product_id,stock_is_available,stock_overridden,stock_overridden_availability,stock_product_price)
VALUES (1,1,true,false,false,500);