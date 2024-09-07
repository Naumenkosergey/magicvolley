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


insert into media_storages(id, content_type, data, file_name, size, type_entity, version, created_at, updated_at)
values('5651fdf6-6f7b-482d-97eb-4d9c7165e8a0', 'image/png', pg_read_binary_file('D:\KODING\magicvolley\src\main\resources\db\sql\image.png')::bytea,
'QR-code.png',547840, 'COACH', 0, now(), now());


insert into coaches(id, coach_name, surename, info, image_id, version)
values('3deeb485-e987-48cf-91c1-377a420702f1','Михаил','Кочетков',
'Основтель школы Magic Volley;Тренер школы Magic Volley;Тренерский стаж 10 лет;КМС по волейболу',
'5651fdf6-6f7b-482d-97eb-4d9c7165e8a0', 0);
insert into coaches(id, coach_name, surename, info, image_id, version)
values('1c62bad8-8faf-43af-8f77-85fdc7eb72b7','Андрей','Волков',
'Тренер школы Magic Volley;Тренерский стаж 8 лет;КМС по волейболу',
'5651fdf6-6f7b-482d-97eb-4d9c7165e8a0', 0);



--
--INSERT INTO magic_volley.status(id, name, code) VALUES (1,'Черновик','DRAFT');
--INSERT INTO magic_volley.status(id, name, code) VALUES (2,'Активный','ACTIVE');
--INSERT INTO magic_volley.status(id, name, code) VALUES (3,'Архивный','ARCHIVE');
--
--
insert into camps(id, camp_name, camp_type, info, place, date_start, date_end, count_all,
count_free, price, image_id, version)
values('dd5dc5ea-f858-47c0-b518-be2d1565856d', '23 февраля день защитника отечества', 'SHORT',
'Кемп для настоящих мужчин1;Кемп для настоящих мужчин2;Кемп для настоящих мужчин3;',
'Пляж House Сумбулово', '2024-02-22','2024-02-22', 30, 0, 19.99, '5651fdf6-6f7b-482d-97eb-4d9c7165e8a0', 0);
insert into camps(id, camp_name, camp_type, info, place, date_start, date_end, count_all,
count_free, price, image_id, version)
values('7573f07f-3769-4f34-90cd-412fa1aab705', 'третие майские', 'SHORT',
'Пляж House Сумбулово', 'Пляж House Сумбулово', '2024-05-09', '2024-05-12', 30, 15, 19.99,
'5651fdf6-6f7b-482d-97eb-4d9c7165e8a0', 0);
insert into camps(id, camp_name, camp_type, info, place, date_start, date_end, count_all,
count_free,price, image_id, version)
values('df9bacad-8a55-418c-a654-39a02344c09a','киргизия','LONG','киргизия ждет тебя',
'Киргизия', '2024-07-09', '2024-07-19', 70, 70, 70.99,'5651fdf6-6f7b-482d-97eb-4d9c7165e8a0', 0);
--
--
INSERT INTO camp_users(camp_id, user_id, is_apruve, is_brone, is_past)
VALUES('dd5dc5ea-f858-47c0-b518-be2d1565856d', 'a8fd2366-51d0-47d0-a06b-819f41af4cb8', true, true, true);
INSERT INTO camp_users(camp_id, user_id, is_apruve, is_brone, is_past)
VALUES('7573f07f-3769-4f34-90cd-412fa1aab705', 'a8fd2366-51d0-47d0-a06b-819f41af4cb8', true, true, false);
INSERT INTO camp_users(camp_id, user_id, is_apruve, is_brone, is_past)
VALUES('df9bacad-8a55-418c-a654-39a02344c09a', 'a8fd2366-51d0-47d0-a06b-819f41af4cb8', false, true, false);
--
--
INSERT INTO camp_coaches(camp_id, coach_id) VALUES ('dd5dc5ea-f858-47c0-b518-be2d1565856d','3deeb485-e987-48cf-91c1-377a420702f1');
INSERT INTO camp_coaches(camp_id, coach_id) VALUES ('df9bacad-8a55-418c-a654-39a02344c09a','3deeb485-e987-48cf-91c1-377a420702f1');
INSERT INTO camp_coaches(camp_id, coach_id) VALUES ('dd5dc5ea-f858-47c0-b518-be2d1565856d','1c62bad8-8faf-43af-8f77-85fdc7eb72b7');
INSERT INTO camp_coaches(camp_id, coach_id) VALUES ('7573f07f-3769-4f34-90cd-412fa1aab705','1c62bad8-8faf-43af-8f77-85fdc7eb72b7');

INSERT INTO user_profile (user_id, image_id, ful_name, telephone, birthday, version)
VALUES ('a8fd2366-51d0-47d0-a06b-819f41af4cb8', '5651fdf6-6f7b-482d-97eb-4d9c7165e8a0', 'Науменко Сергей',
'+7998620452','1993-03-24', 0);

INSERT INTO profile_camps (profile_id, camp_id, is_past, is_booked, version)
VALUES ('a8fd2366-51d0-47d0-a06b-819f41af4cb8', 'dd5dc5ea-f858-47c0-b518-be2d1565856d', true, false, 0);
INSERT INTO profile_camps (profile_id, camp_id, is_past, is_booked, version)
VALUES ('a8fd2366-51d0-47d0-a06b-819f41af4cb8', '7573f07f-3769-4f34-90cd-412fa1aab705', true, false, 0);
INSERT INTO profile_camps (profile_id, camp_id, is_past, is_booked, version)
VALUES ('a8fd2366-51d0-47d0-a06b-819f41af4cb8', 'df9bacad-8a55-418c-a654-39a02344c09a', false, true, 0);


INSERT INTO questions (id, question, answer, version)
VALUES ('d1ee3bef-9295-4b2f-8c07-6abf16c5d7f9', 'вопрос1','ответ1', 0);
INSERT INTO questions (id, question, answer, version)
VALUES ('7a869b3c-f152-42e8-965d-75b3d694702b', 'вопрос2','ответ2', 0);
INSERT INTO questions (id, question, answer, version)
VALUES ('42ecaf3b-7854-4709-89c9-f1e00ea65355', 'вопрос3','ответ3', 0);



