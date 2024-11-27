insert into roles (id, role, version)
values('1ffdc9e5-ece0-4420-ba12-b4ac1ed25573', 'ADMIN', 0);
insert into roles (id, role, version)
values('1e6d19c6-4594-4d5f-8578-0a0479cd6caa', 'MODERATOR', 0);
insert into roles (id, role, version)
values('888f4834-c6e4-472e-808b-171febbb9137', 'USER', 0);


insert into users(id, login, password, email, role_id, version)
values ('68c8432c-b963-46c2-b1ff-345452609ecf', 'kazina', '$2y$10$6lSrg0Ao0g8H1x7mp7bCRertVWA.thsfgIQY.TyNvpp6d51lWp9za', 'kazina@mail.ru', '1ffdc9e5-ece0-4420-ba12-b4ac1ed25573', 0);
insert into users(id, login, password, email, role_id, version)
values ('a8fd2366-51d0-47d0-a06b-819f41af4cb8', 'naumenko', '$2y$10$ecbqDrSY6HL.79kIOGWkK.b1fjd/7BjPtZa/1Fd7IOWoNfKwvo6bu', 'naumeko@mail.ru','888f4834-c6e4-472e-808b-171febbb9137', 0);
insert into users(id, login, password, email, role_id, version)
values ('e5050cc4-98c4-4b48-9e47-8b5057c6b46c', 'marozova', '$2y$10$eFbYvZne/FFfGUlvJfyqzuWTAj9Hq/7gta/uQAm0ZW9nKw.XFypOu', 'maroz@mail.ru','1e6d19c6-4594-4d5f-8578-0a0479cd6caa', 0);

insert into user_roles (role_id, user_id, version)
values('1ffdc9e5-ece0-4420-ba12-b4ac1ed25573', '68c8432c-b963-46c2-b1ff-345452609ecf', 0);
insert into user_roles (role_id, user_id, version)
values('888f4834-c6e4-472e-808b-171febbb9137', 'a8fd2366-51d0-47d0-a06b-819f41af4cb8', 0);
insert into user_roles (role_id, user_id, version)
values('1e6d19c6-4594-4d5f-8578-0a0479cd6caa', 'e5050cc4-98c4-4b48-9e47-8b5057c6b46c', 0);


insert into media_storages(id, entity_id, content_type, data, file_name, size, type_entity, version)
values('8651fdf6-6f7b-482d-97eb-4d9c7165e8a0', 'dd5dc5ea-f858-47c0-b518-be2d1565856d',
'image/png', pg_read_binary_file('D:\KODING\magicvolley\src\main\resources\static\image.png')::bytea,
'image.png', 547840, 'CAMP', 0);

insert into media_storages(id, entity_id, content_type, data, file_name, size, type_entity, version)
values('8751fdf6-6f7b-482d-97eb-4d9c7165e8a0', 'dd5dc5ea-f858-47c0-b518-be2d1565856d',
'image/png', pg_read_binary_file('D:\KODING\magicvolley\src\main\resources\static\image.png')::bytea,
'image.png', 547840, 'CAMP', 0);

insert into media_storages(id, entity_id, content_type, data, file_name, size, type_entity, version)
values('8851fdf6-6f7b-482d-97eb-4d9c7165e8a0', '7573f07f-3769-4f34-90cd-412fa1aab705',
'image/png', pg_read_binary_file('D:\KODING\magicvolley\src\main\resources\static\image.png')::bytea,
'image.png', 547840, 'CAMP', 0);

insert into media_storages(id, entity_id, content_type, data, file_name, size, type_entity, version)
values('8951fdf6-6f7b-482d-97eb-4d9c7165e8a0', '7573f07f-3769-4f34-90cd-412fa1aab705',
'image/png', pg_read_binary_file('D:\KODING\magicvolley\src\main\resources\static\image.png')::bytea,
'image.png', 547840, 'CAMP', 0);

insert into media_storages(id, entity_id, content_type, data, file_name, size, type_entity, version)
values('8a51fdf6-6f7b-482d-97eb-4d9c7165e8a0', 'df9bacad-8a55-418c-a654-39a02344c09a',
'image/png', pg_read_binary_file('D:\KODING\magicvolley\src\main\resources\static\image.png')::bytea,
'image.png', 547840, 'CAMP', 0);

insert into media_storages(id, entity_id, content_type, data, file_name, size, type_entity, version)
values('8b51fdf6-6f7b-482d-97eb-4d9c7165e8a0', 'df9bacad-8a55-418c-a654-39a02344c09a',
'image/png', pg_read_binary_file('D:\KODING\magicvolley\src\main\resources\static\image.png')::bytea,
'image.png', 547840, 'CAMP', 0);

insert into media_storages(id, entity_id, content_type, data, file_name, size, type_entity, version)
values('8c51fdf6-6f7b-482d-97eb-4d9c7165e8a0', 'dd5dc5ea-f858-47c0-b518-be2d1565856d',
'image/png', pg_read_binary_file('D:\KODING\magicvolley\src\main\resources\static\image.png')::bytea,
'image.png', 547840, 'CAMP', 0);

insert into media_storages(id, entity_id, content_type, data, file_name, size, type_entity, version)
values('3551fdf6-6f7b-482d-97eb-4d9c7165e8a0', 'dd5dc5ea-f858-47c0-b518-be2d1565856d',
'image/png', pg_read_binary_file('D:\KODING\magicvolley\src\main\resources\static\image.png')::bytea,
'image.png', 547840, 'CAMP', 0);

insert into media_storages(id, entity_id, content_type, data, file_name, size, type_entity, version)
values('3751fdf6-6f7b-482d-97eb-4d9c7165e8a0', 'dd5dc5ea-f858-47c0-b518-be2d1565856d',
'image/png', pg_read_binary_file('D:\KODING\magicvolley\src\main\resources\static\image.png')::bytea,
'image.png', 547840, 'CAMP', 0);

insert into media_storages(id, entity_id, content_type, data, file_name, size, type_entity, version)
values('6651fdf6-6f7b-482d-97eb-4d9c7165e8a0', '7573f07f-3769-4f34-90cd-412fa1aab705',
'image/png', pg_read_binary_file('D:\KODING\magicvolley\src\main\resources\static\image.png')::bytea,
'image.png', 547840, 'CAMP', 0);

insert into media_storages(id, entity_id, content_type, data, file_name, size, type_entity, version)
values('7651fdf6-6f7b-482d-97eb-4d9c7165e8a0', 'df9bacad-8a55-418c-a654-39a02344c09a',
'image/png', pg_read_binary_file('D:\KODING\magicvolley\src\main\resources\static\image.png')::bytea,
'image.png', 547840, 'CAMP', 0);

insert into media_storages(id, entity_id, content_type, data, file_name, size, type_entity, version)
values('5751fdf6-6f7b-482d-97eb-4d9c7165e8a0', 'a8fd2366-51d0-47d0-a06b-819f41af4cb8',
'image/png', pg_read_binary_file('D:\KODING\magicvolley\src\main\resources\static\naumenko.png')::bytea,
'naumenko.png', 547840, 'USER', 0);

insert into media_storages(id, entity_id, content_type, data, file_name, size, type_entity, version)
values('6395feba-bbca-4215-83be-8fe7be869bc0', '3deeb485-e987-48cf-91c1-377a420702f1',
'image/jpg', pg_read_binary_file('D:\KODING\magicvolley\src\main\resources\static\kochetkov.jpg')::bytea,
'kochetkov.jpg', 547840, 'COACH', 0);

insert into media_storages(id, entity_id, content_type, data, file_name, size, type_entity, version)
values('b1eb52d7-47f5-4106-a87e-bd8b5bdb6a37', '1c62bad8-8faf-43af-8f77-85fdc7eb72b7',
'image/jpg', pg_read_binary_file('D:\KODING\magicvolley\src\main\resources\static\volkov.jpg')::bytea,
'volkov.jpg', 547840, 'COACH', 0);


insert into coaches(id, coach_name, info, promo, image_id, version, created_at, updated_at)
values('3deeb485-e987-48cf-91c1-377a420702f1','Михаил Кочетков',
'Основтель школы Magic Volley;Тренер школы Magic Volley;Тренерский стаж 10 лет;КМС по волейболу', 'promo',
'6395feba-bbca-4215-83be-8fe7be869bc0', 0, '2024-10-27T15:56:59.069749', '2024-10-27T15:56:59.069749');
insert into coaches(id, coach_name, info, promo, image_id, version, created_at, updated_at)
values('1c62bad8-8faf-43af-8f77-85fdc7eb72b7','Андрей Волков',
'Тренер школы Magic Volley;Тренерский стаж 8 лет;КМС по волейболу', 'promo',
'b1eb52d7-47f5-4106-a87e-bd8b5bdb6a37', 0, '2024-10-27T15:57:59.069749', '2024-10-27T15:57:59.069749');



--
--INSERT INTO magic_volley.status(id, name, code) VALUES (1,'Черновик','DRAFT');
--INSERT INTO magic_volley.status(id, name, code) VALUES (2,'Активный','ACTIVE');
--INSERT INTO magic_volley.status(id, name, code) VALUES (3,'Архивный','ARCHIVE');
--
--

insert into camps(id, camp_name, camp_type, info, place, date_start, date_end, count_all,
count_free, main_image_id, cart_image_id, version)
values('dd5dc5ea-f858-47c0-b518-be2d1565856d', '23 февраля день защитника отечества', 'SHORT',
'Кемп для настоящих мужчин1;Кемп для настоящих мужчин2;Кемп для настоящих мужчин3;',
'Пляж House Сумбулово', '2024-02-22','2024-02-24', 30, 0, '8651fdf6-6f7b-482d-97eb-4d9c7165e8a0', '8651fdf6-6f7b-482d-97eb-4d9c7165e8a0', 0);
insert into camps(id, camp_name, camp_type, info, place, date_start, date_end, count_all,
count_free, main_image_id, cart_image_id, version)
values('7573f07f-3769-4f34-90cd-412fa1aab705', 'третие майские', 'SHORT',
'Пляж House Сумбулово', 'Пляж House Сумбулово', '2024-05-09', '2024-05-12', 30, 15,
'8651fdf6-6f7b-482d-97eb-4d9c7165e8a0', '8651fdf6-6f7b-482d-97eb-4d9c7165e8a0', 0);
insert into camps(id, camp_name, camp_type, info, place, date_start, date_end, count_all,
count_free, main_image_id, cart_image_id, version)
values('df9bacad-8a55-418c-a654-39a02344c09a','киргизия','LONG','киргизия ждет тебя',
'Киргизия', '2024-07-09', '2024-07-19', 70, 70,'8651fdf6-6f7b-482d-97eb-4d9c7165e8a0', '8651fdf6-6f7b-482d-97eb-4d9c7165e8a0', 0);
--
--
insert into camp_users(camp_id, user_id, booking_confirmed, is_reserved, is_past)
values('dd5dc5ea-f858-47c0-b518-be2d1565856d', 'a8fd2366-51d0-47d0-a06b-819f41af4cb8', true, true, true);
insert into camp_users(camp_id, user_id, booking_confirmed, is_reserved, is_past)
values('7573f07f-3769-4f34-90cd-412fa1aab705', 'a8fd2366-51d0-47d0-a06b-819f41af4cb8', true, true, false);
insert into camp_users(camp_id, user_id, booking_confirmed, is_reserved, is_past)
values('df9bacad-8a55-418c-a654-39a02344c09a', 'a8fd2366-51d0-47d0-a06b-819f41af4cb8', false, true, false);
--
--
insert into camp_coaches(camp_id, coach_id, version) values ('dd5dc5ea-f858-47c0-b518-be2d1565856d','3deeb485-e987-48cf-91c1-377a420702f1', 0);
insert into camp_coaches(camp_id, coach_id, version) values ('df9bacad-8a55-418c-a654-39a02344c09a','3deeb485-e987-48cf-91c1-377a420702f1', 0);
insert into camp_coaches(camp_id, coach_id, version) values ('dd5dc5ea-f858-47c0-b518-be2d1565856d','1c62bad8-8faf-43af-8f77-85fdc7eb72b7', 0);
insert into camp_coaches(camp_id, coach_id, version) values ('7573f07f-3769-4f34-90cd-412fa1aab705','1c62bad8-8faf-43af-8f77-85fdc7eb72b7', 0);

insert into user_profile (user_id, image_id, ful_name, telephone, birthday, version)
values ('a8fd2366-51d0-47d0-a06b-819f41af4cb8', '5751fdf6-6f7b-482d-97eb-4d9c7165e8a0', 'Науменко Сергей',
'+7998620452','1993-03-24', 0);

insert into profile_camps (profile_id, camp_id, is_past, is_booked, version)
values ('a8fd2366-51d0-47d0-a06b-819f41af4cb8', 'dd5dc5ea-f858-47c0-b518-be2d1565856d', true, false, 0);
insert into profile_camps (profile_id, camp_id, is_past, is_booked, version)
values ('a8fd2366-51d0-47d0-a06b-819f41af4cb8', '7573f07f-3769-4f34-90cd-412fa1aab705', true, false, 0);
insert into profile_camps (profile_id, camp_id, is_past, is_booked, version)
values ('a8fd2366-51d0-47d0-a06b-819f41af4cb8', 'df9bacad-8a55-418c-a654-39a02344c09a', false, true, 0);


insert into questions (id, question, answer, version)
values ('d1ee3bef-9295-4b2f-8c07-6abf16c5d7f9', 'вопрос1','ответ1', 0);
insert into questions (id, question, answer, version)
values ('7a869b3c-f152-42e8-965d-75b3d694702b', 'вопрос2','ответ2', 0);
insert into questions (id, question, answer, version)
values ('42ecaf3b-7854-4709-89c9-f1e00ea65355', 'вопрос3','ответ3', 0);


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
values ('0c6e0dfd-5d79-4e2c-89ab-c7ab53b67d75', 'Дети 7-11 лет', 0, 0);

insert into schedule_groups (id, group_name, version, order_number)
values ('426e8a14-f426-4ed8-b7db-40ab72345166', 'Дети 12-15 лет', 0, 1);

insert into schedule_groups (id, group_name, version, order_number)
values ('cc7d3de2-e451-4d9d-a55d-35fcca88332e', 'Взрослая начальная', 0, 2);

insert into schedule_groups (id, group_name, version, order_number)
values ('ed1c5e9c-1027-4599-9f00-e8df5d251987', 'Средняя', 0, 3);

insert into schedule_groups (id, group_name, version, order_number)
values ('7db95126-90dd-4cc0-b35f-ab6021b0c1ec', 'Продвинутая', 0, 4);

insert into schedule_groups (id, group_name, version, order_number)
values ('afbd13e0-f551-4369-bfd2-08e5177ec5fe', 'Мужкая средняя+', 0, 5);

insert into schedule_groups (id, group_name, version, order_number)
values ('5d6c06e9-d873-468a-8f35-9c0d3cd7fe39', 'Женская средняя+', 0, 6);

--'Дети 7-11 лет'
insert into schedule (id, day, start_time, end_time, address, version, schedule_group_id)
values ('72e047e1-8d21-41a8-a9a4-0fba52a6855d','MONDAY', '17:15:00','18:45:00', 'Адрес проведения тренировки', 0, '0c6e0dfd-5d79-4e2c-89ab-c7ab53b67d75');
insert into schedule (id, day, start_time, end_time, address, version, schedule_group_id)
values ('d9211366-0907-4e0f-ba38-e2011afe42bf','WEDNESDAY', '17:15:00','18:45:00', 'Адрес проведения тренировки', 0, '0c6e0dfd-5d79-4e2c-89ab-c7ab53b67d75');

--'Дети 12-15 лет'
insert into schedule (id, day, start_time, end_time, address, version, schedule_group_id)
values ('c054ba6a-b8d9-406d-965b-dbf653485bd0','TUESDAY', '17:15:00','18:45:00', 'Адрес проведения тренировки', 0, '426e8a14-f426-4ed8-b7db-40ab72345166');
insert into schedule (id, day, start_time, end_time, address, version, schedule_group_id)
values ('7214b9de-05bf-43dd-a274-649d95266600','THURSDAY', '17:15:00','18:45:00', 'Адрес проведения тренировки', 0, '426e8a14-f426-4ed8-b7db-40ab72345166');

--'Взрослая начальная'
insert into schedule (id, day, start_time, end_time, address, version, schedule_group_id)
values ('3728b141-e483-4d0f-a00a-94d64204cdda','MONDAY', '18:45:00','20:15:00', 'Адрес проведения тренировки', 0, 'cc7d3de2-e451-4d9d-a55d-35fcca88332e');
insert into schedule (id, day, start_time, end_time, address, version, schedule_group_id)
values ('7bdd55c0-5daf-4718-8da7-e27d45209707','WEDNESDAY', '18:45:00','20:15:00', 'Адрес проведения тренировки', 0, 'cc7d3de2-e451-4d9d-a55d-35fcca88332e');

--'Средняя'
insert into schedule (id, day, start_time, end_time, address, version, schedule_group_id)
values ('d36d01dd-7dd4-4493-9fc9-8fea949fb881','TUESDAY', '18:45:00','20:15:00', 'Адрес проведения тренировки', 0, 'ed1c5e9c-1027-4599-9f00-e8df5d251987');
insert into schedule (id, day, start_time, end_time, address, version, schedule_group_id)
values ('d54e4d1f-d0c1-4774-b946-df60912960bc','THURSDAY', '18:45:00','20:15:00', 'Адрес проведения тренировки', 0, 'ed1c5e9c-1027-4599-9f00-e8df5d251987');


--'Продвинутая'
insert into schedule (id, day, start_time, end_time, address, version, schedule_group_id)
values ('dbdb413b-fed1-45a0-8eb8-7581b631fb69','TUESDAY', '18:45:00','20:15:00', 'Адрес проведения тренировки', 0, '7db95126-90dd-4cc0-b35f-ab6021b0c1ec');
insert into schedule (id, day, start_time, end_time, address, version, schedule_group_id)
values ('b771c31a-6119-455c-9aa4-49f56b441702','THURSDAY', '18:45:00','20:15:00', 'Адрес проведения тренировки', 0, '7db95126-90dd-4cc0-b35f-ab6021b0c1ec');

--'Мужкая средняя+'
insert into schedule (id, day, start_time, end_time, address, version, schedule_group_id)
values ('6837de31-0436-4fd3-a4f8-f94d725021dd','MONDAY', '18:45:00','20:15:00', 'Адрес проведения тренировки', 0, 'afbd13e0-f551-4369-bfd2-08e5177ec5fe');
insert into schedule (id, day, start_time, end_time, address, version, schedule_group_id)
values ('a785d7a2-6760-4c3d-8b27-595abd988030','FRIDAY', '18:45:00','20:15:00', 'Адрес проведения тренировки', 0, 'afbd13e0-f551-4369-bfd2-08e5177ec5fe');

--'Женская средняя+'
insert into schedule (id, day, start_time, end_time, address, version, schedule_group_id)
values ('183811d8-59df-4976-80cb-8b3d19f539b2','MONDAY', '18:45:00','20:15:00', 'Адрес проведения тренировки', 0, '5d6c06e9-d873-468a-8f35-9c0d3cd7fe39');
insert into schedule (id, day, start_time, end_time, address, version, schedule_group_id)
values ('e4dc9ed5-0c33-4ecc-adf0-5ac1c3e70424','WEDNESDAY', '18:45:00','20:15:00', 'Адрес проведения тренировки', 0, '5d6c06e9-d873-468a-8f35-9c0d3cd7fe39');
insert into schedule (id, day, start_time, end_time, address, version, schedule_group_id)
values ('aba123d8-24ff-48c1-820b-58ad329c970e','FRIDAY', '18:45:00','20:15:00', 'Адрес проведения тренировки', 0, '5d6c06e9-d873-468a-8f35-9c0d3cd7fe39');



insert into package_card (id, name, info, cost_naming_link, version)
values(1, 'Premium','пункт1;пкнкт2;пункт3;пункт4;пункт5;пункт6;пункт7','Повышение цен согласно графику',0);
insert into package_card (id, name, info, cost_naming_link, version)
values(2, 'Gold','пункт1;пкнкт2;пункт3;пункт4;пункт5;пункт6','Повышение цен согласно графику',0);
insert into package_card (id, name, info, cost_naming_link, version)
values(3, 'Silver','пункт1;пкнкт2;пункт3;пункт4;пункт5','Повышение цен согласно графику',0);
insert into package_card (id, name, info, cost_naming_link, version)
values(4, 'Tur','пункт1;пкнкт2;пункт3','Детский тариф',0);



insert into camp_package_card (camp_id, package_card_id, info, total_price, booking_price, first_price, first_limitation,
 second_price, second_limitation, third_price, third_limitation, version)
values('dd5dc5ea-f858-47c0-b518-be2d1565856d', 1, 'пункт1;пкнкт2;пункт3;пункт4;пункт5;пункт6;пункт7', 
29990, 7000, 23000, '2023-12-01', 25000, '2024-01-01', 29000, '2024-02-01', 0);
insert into camp_package_card (camp_id, package_card_id, info, total_price, booking_price, first_price, first_limitation,
 second_price, second_limitation, third_price, third_limitation, version)
values('dd5dc5ea-f858-47c0-b518-be2d1565856d', 2, 'пункт1;пкнкт2;пункт3;пункт4;пункт5;пункт6', 
25900, 7000, 19000, '2023-12-01', 23000, '2024-01-01', 25000, '2024-02-01', 0);
insert into camp_package_card (camp_id, package_card_id, info, total_price, booking_price, first_price, first_limitation,
 second_price, second_limitation, third_price, third_limitation, version)
values('dd5dc5ea-f858-47c0-b518-be2d1565856d', 3, 'пункт1;пкнкт2;пункт3;пункт4;пункт5',
22900, 7000, 17000, '2023-12-01', 19000, '2024-01-01', 22000, '2024-02-01', 0);
insert into camp_package_card (camp_id, package_card_id, info, total_price, booking_price, first_price, first_limitation,
 second_price, second_limitation, third_price, third_limitation, version)
values('dd5dc5ea-f858-47c0-b518-be2d1565856d', 4, 'пункт1;пкнкт2;пункт3',
9900, 3000, 5000, 'до 3 лет', 7000, 'до 6 лет', 8000, 'до 10 лет', 0);
