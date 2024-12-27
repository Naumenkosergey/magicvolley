insert into roles (id, role, version)
values('1ffdc9e5-ece0-4420-ba12-b4ac1ed25573', 'ADMIN', 0);
insert into roles (id, role, version)
values('1e6d19c6-4594-4d5f-8578-0a0479cd6caa', 'MODERATOR', 0);
insert into roles (id, role, version)
values('888f4834-c6e4-472e-808b-171febbb9137', 'USER', 0);


insert into users(id, telephone, username, password, email,is_blocked, role_id, version)
values ('68c8432c-b963-46c2-b1ff-345452609ecf','admin', 'Казина Татьяна', '$2y$10$6lSrg0Ao0g8H1x7mp7bCRertVWA.thsfgIQY.TyNvpp6d51lWp9za',
'kazina@mail.ru', false,'1ffdc9e5-ece0-4420-ba12-b4ac1ed25573', 0);

insert into user_profile (user_id, image_id, ful_name, telephone, birthday, version)
values ('68c8432c-b963-46c2-b1ff-345452609ecf', null, 'Казина Татьяна',
'+79969103047', null, 0);

insert into questions (id, question, answer, version)
values ('d1ee3bef-9295-4b2f-8c07-6abf16c5d7f9', 'Есть ли у вас рассрочка на спорт-пакет?',
'У нас к каждому участнику индивидуальный подход во всем - оплата не исключени!
Если у тебя нет возможности до начала кемпа внести всю сумму спортпакета, то напиши нам и мы предложим тебе варианты оплаты!', 0);
insert into questions (id, question, answer, version)
values ('7a869b3c-f152-42e8-965d-75b3d694702b', 'Обязательно ли покупать тур у вас?',
'Вы можете приобрести тур самостоятельно. Но, приобретая тур у нас, вы получаете лучшую цену, круглосуточную поддержку на протяжении всего кемпа, индивидуальный подход по любому возникшему вопросу.', 0);
insert into questions (id, question, answer, version)
values ('42ecaf3b-7854-4709-89c9-f1e00ea65355', 'Я никогда не играл в волейбол. Есть ли у вас группы для новичков?','ответ3', 0);
insert into questions (id, question, answer, version)
values ('39baa098-5cb6-48da-8a0c-99fb8a8a830b', 'Где проходят кемпы выходного дня?',
'Кемпы выходного дня проходят на территории собственного комплекса Пляж House. Это уникальный гостевой дом, для любителей пляжного волейбола и активного отдыха, в сосновом бору.', 0);
insert into questions (id, question, answer, version)
values ('92ecd29f-a4b7-4c98-87fa-e15419fb8263', 'Если я один, как мне оформить тур с 2-ух местным размещением?',
'Стоимость данного размещения будет отличаться от стандартной стоимости. Для оформления тура с двухместным размещением сообщите об этом нашему менеджеру.', 0);


insert into subscriptions (id, subscription_name, version, order_number)
values ('f4ffa742-5277-4f90-813e-9817a5304be9', 'Разовые тренировки', 0, 0);
insert into subscriptions (id, subscription_name, version, order_number)
values ('b760cd06-7c98-490f-98d0-f2e25450f92e', 'Абонемент на 4 занятияй', 0, 1);
insert into subscriptions (id, subscription_name, version, order_number)
values ('5af0e36c-3ec1-463c-801e-6d1c41fa5fa0', 'Абонемент на 8 занятияй', 0, 2);
insert into subscriptions (id, subscription_name, version, order_number)
values ('35e3f8ae-1d19-4c00-bfed-e160a760a053', 'Абонемент на 10 занятияй', 0, 3);

insert into subscription_prices (id, title, sub_title, price, version, subscription_id)
values ('6602547c-b9e2-45ba-9063-6776d6b2a86e', 'Взрослые', null, 750, 0, 'f4ffa742-5277-4f90-813e-9817a5304be9');
insert into subscription_prices (id, title, sub_title, price, version, subscription_id)
values ('a95cff8c-abd7-4a04-ace6-26926551c085', 'Детские группы', null, 650, 0, 'f4ffa742-5277-4f90-813e-9817a5304be9');

insert into subscription_prices (id, title, sub_title, price, version, subscription_id)
values ('93ed7d77-95e1-421b-b8bd-784025807ff5', 'Взрослые', '650 ₽ за тренировку', 2600, 0, 'b760cd06-7c98-490f-98d0-f2e25450f92e');
insert into subscription_prices (id, title, sub_title, price, version, subscription_id)
values ('43049277-65ae-4e9a-9e0a-85a9671c3b2e', 'Детские группы', '380 ₽ за тренировку', 1500, 0, 'b760cd06-7c98-490f-98d0-f2e25450f92e');

insert into subscription_prices (id, title, sub_title, price, version, subscription_id)
values ('46c5a9c6-0cc1-4433-a47e-4d093bbee9ab', 'Взрослые', '525 ₽ за тренировку', 4200, 0, '5af0e36c-3ec1-463c-801e-6d1c41fa5fa0');
insert into subscription_prices (id, title, sub_title, price, version, subscription_id)
values ('ae733b64-6661-41a6-9e11-a2fba8e12e35', 'Детские группы', '490 ₽ за тренировку', 3900, 0, '5af0e36c-3ec1-463c-801e-6d1c41fa5fa0');

insert into subscription_prices (id, title, sub_title, price, version, subscription_id)
values ('62026fe3-5b38-43a1-9582-587ae7246a46', 'Взрослые', '510 ₽ за тренировку', 5100, 0, '35e3f8ae-1d19-4c00-bfed-e160a760a053');
insert into subscription_prices (id, title, sub_title, price, version, subscription_id)
values ('d99acbb9-9a22-462a-948d-71b0e969b6aa', 'Детские группы', '470 ₽ за тренировку', 4700, 0, '35e3f8ae-1d19-4c00-bfed-e160a760a053');

insert into schedule_groups (id, group_name, version, order_number)
values ('0c6e0dfd-5d79-4e2c-89ab-c7ab53b67d75', 'Детская 7-11 лет', 0, 1);

insert into schedule_groups (id, group_name, version, order_number)
values ('426e8a14-f426-4ed8-b7db-40ab72345166', 'Детская 12-16 лет', 0, 2);

insert into schedule_groups (id, group_name, version, order_number)
values ('cc7d3de2-e451-4d9d-a55d-35fcca88332e', 'Взрослая начальная и начальная +', 0, 3);

insert into schedule_groups (id, group_name, version, order_number)
values ('ed1c5e9c-1027-4599-9f00-e8df5d251987', 'Детская 10-13 лет', 0, 4);

insert into schedule_groups (id, group_name, version, order_number)
values ('7db95126-90dd-4cc0-b35f-ab6021b0c1ec', 'Детская 13-16 лет', 0, 5);

insert into schedule_groups (id, group_name, version, order_number)
values ('afbd13e0-f551-4369-bfd2-08e5177ec5fe', 'Взорослая средняя+', 0, 6);

insert into schedule_groups (id, group_name, version, order_number)
values ('5d6c06e9-d873-468a-8f35-9c0d3cd7fe39', 'Мужская группа выше среднего (команда)', 0, 7);

--'Детская 7-11 лет'
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('72e047e1-8d21-41a8-a9a4-0fba52a6855d','TUESDAY', '19:00:00', 'Циолковского, 22', 0, '0c6e0dfd-5d79-4e2c-89ab-c7ab53b67d75');
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('d9211366-0907-4e0f-ba38-e2011afe42bf','THURSDAY', '19:00:00', 'Циолковского, 22', 0, '0c6e0dfd-5d79-4e2c-89ab-c7ab53b67d75');

--'Детская 12-16 лет'
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('c054ba6a-b8d9-406d-965b-dbf653485bd0','MONDAY', '19:30:00', 'Циолковского, 22', 0, '426e8a14-f426-4ed8-b7db-40ab72345166');
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('930a2156-1c22-4984-8a8f-f7847d3adf27','TUESDAY', '17:30:00', 'Циолковского, 22', 0, '426e8a14-f426-4ed8-b7db-40ab72345166');
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('951a827d-ed82-44ae-a7e3-b86e36625a4b','WEDNESDAY', '17:15:00', 'Циолковского, 22', 0, '426e8a14-f426-4ed8-b7db-40ab72345166');
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('7214b9de-05bf-43dd-a274-649d95266600','THURSDAY', '17:30:00', 'Адрес проведения тренировки', 0, '426e8a14-f426-4ed8-b7db-40ab72345166');

--'Взрослая начальная и начальная +'
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('3728b141-e483-4d0f-a00a-94d64204cdda','TUESDAY', '19:00:00', 'Циолковского, 22', 0, 'cc7d3de2-e451-4d9d-a55d-35fcca88332e');
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('7bdd55c0-5daf-4718-8da7-e27d45209707','THURSDAY', '19:00:00', 'Циолковского, 22', 0, 'cc7d3de2-e451-4d9d-a55d-35fcca88332e');

--'Детская 10-13 '
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('d36d01dd-7dd4-4493-9fc9-8fea949fb881','TUESDAY', '19:00:00', 'Чернышевского, 16', 0, 'ed1c5e9c-1027-4599-9f00-e8df5d251987');
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('d54e4d1f-d0c1-4774-b946-df60912960bc','THURSDAY', '19:00:00', 'Чернышевского, 16', 0, 'ed1c5e9c-1027-4599-9f00-e8df5d251987');


--'Детская 13-16 лет'
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('dbdb413b-fed1-45a0-8eb8-7581b631fb69','TUESDAY', '17:30:00', 'Чернышевского, 16', 0, '7db95126-90dd-4cc0-b35f-ab6021b0c1ec');
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('b771c31a-6119-455c-9aa4-49f56b441702','THURSDAY', '17:30:00', 'Чернышевского, 16', 0, '7db95126-90dd-4cc0-b35f-ab6021b0c1ec');

--'Взрослая средняя'
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('6837de31-0436-4fd3-a4f8-f94d725021dd','MONDAY', '18:30:00', 'Чернышевского, 16', 0, 'afbd13e0-f551-4369-bfd2-08e5177ec5fe');
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('a785d7a2-6760-4c3d-8b27-595abd988030','WEDNESDAY', '18:30:00', 'Чернышевского, 16', 0, 'afbd13e0-f551-4369-bfd2-08e5177ec5fe');

--'Женская группа выше среднего (команда)'
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('183811d8-59df-4976-80cb-8b3d19f539b2','MONDAY', '20:00:00', 'Чернышевского, 16', 0, '5d6c06e9-d873-468a-8f35-9c0d3cd7fe39');
insert into schedule (id, day, training_time, address, version, schedule_group_id)
values ('e4dc9ed5-0c33-4ecc-adf0-5ac1c3e70424','WEDNESDAY', '20:00:00', 'Чернышевского, 16', 0, '5d6c06e9-d873-468a-8f35-9c0d3cd7fe39');


insert into package_card (id, name_package, code,  info, cost_naming_link, version)
values(1, 'Пакет "Premium" выходного дня','Пакет "Premium"', 'Пакет "Gold";майка участника;игра в паре с тренером;индивидуальначя тренировка 45 минут',
'Повышение цен согласно графику',0);
insert into package_card (id, name_package, code, info, cost_naming_link, version)
values(2, 'Пакет "Gold" выходного дня', 'Пакет "Gold"','Проживание;3-ех разовое питание;баня, бассейн, джакузи;3 тренировки;
2 турнира;фото и видео;аренда кортов;теоретические семинары;вечерняя программа','Повышение цен согласно графику',0);
insert into package_card (id, name_package, code, info, cost_naming_link, version)
values(3, 'Пакет "Silver" выходного дня', 'Пакет "Silver"','Проживание;3-ех разовое питание;вечерняя программа;баня, бассейн, джакузи',
'Повышение цен согласно графику',0);

insert into package_card (id, name_package, code, info, cost_naming_link, version)
values(4, 'Пакет "Premium" выездной','Пакет "Premium"', 'Пакет "Gold";игра в паре с тренером;индивидуальначя тренировка 45 минут',
'Повышение цен согласно графику',0);
insert into package_card (id, name_package, code, info, cost_naming_link, version)
values(5, 'Пакет "Gold" выездной', 'Пакет "Gold"','8 тренировок;2 турнира;фото и видео;майка участника;
аренда кортов;теоретические семинары;вечерняя программа','Повышение цен согласно графику',0);
insert into package_card (id, name_package, code, info, cost_naming_link, version)
values(6, 'Пакет "Silver"  выездной', 'Пакет "Silver"','4 тренировки;2 турнира;фото и видео;майка участника;
аренда кортов;теоретические семинары;вечерняя программа','Повышение цен согласно графику',0);
insert into package_card (id, name_package, code, info, cost_naming_link, version)
values(7,'Пакет "Тур"', 'Пакет "Тур"','Проживание;питание;трансфер;страховка;перелет','Детский тариф',0);

insert into home_page (id, title, subtitle, main_image_id, image_admin_id, text_under_image, link_vk, link_tg,
link_instagram, email, contacts, version)
   values ('eb411f80-eed7-4a00-9293-2dd0219e14e8','Magic Volley', 'Первая школа волейбола города Рязани',
   null, null, 'Ваш менеджер – Татьяна',
   'https://vk.com/magicvolley', '@Tanya_volley', 'https://www.instagram.com/magicvolleyryazan/',
   'volleymagic@mail.ru', '+7(996)910-30-47, +7(996)910-30-47  Пн-Вс с 10:00 до 21:00', 0);


insert into about_us_page (id, title, subtitle_first, subtitle_second, number_of_workouts,
    number_of_camps, number_of_students, version)
values ('77d97d40-7281-49b3-a454-54f6d6153743','Magic Volley', 'Организаторы кемпов по всему миру',
'Делаем вашу жизнь яркой, спортивной и разнообразной', 35000, 100, 3000 ,0);

insert into activity (id, title, created_at, updated_at, order_number, version)
values ('ae6c4086-4117-4bd7-9175-247c614e87c3', 'Тренировки по пляжному и классическому волейболу', now(), now(), 4, 0);

insert into activity (id, title, created_at, updated_at, order_number, version)
values ('b3d4b72c-a5eb-409d-b6b1-fb3b56694c5e', 'Турниры', now(), now(), 5, 0);

insert into activity (id, title, created_at, updated_at, order_number, version)
values ('12b3e8df-6552-48a1-a723-09432a63c33f', 'Мастер-классы', now(), now(), 6, 0);

insert into activity (id, title, created_at, updated_at, order_number, version)
values ('29080213-11d9-4fa8-9047-aac546e4b9ca', 'Кемпы по всему миру', now(), now(), 2, 0);

insert into activity (id, title, created_at, updated_at, order_number, version)
values ('f335ad7c-dd94-49d2-b55b-8e256c1142e3', 'Кемпы выходного дня', now(), now(), 1, 0);

insert into activity (id, title, created_at, updated_at, order_number, version)
values ('1ad96cf6-eb89-4134-9536-b0dc0eeeb52d', 'Корпаративные праздники', now(), now(), 7, 0);

insert into activity (id, title, created_at, updated_at, order_number, version)
values ('fed10f3e-9f70-45db-a90d-6f4e98de0897', 'Детские лагеря', now(), now(), 3, 0);

insert into master (id, name_master, info, image_id, version)
Values ('9935770c-5896-4118-9ecb-1ce06045a968', 'Михаил Кочетков',
'Основатель школы;Главный тренер;Финалист чемпионата России;Призёр международных соревнований', null, 0);