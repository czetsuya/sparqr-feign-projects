insert into platform_user (id, email, version, created, disabled, gender, first_name, last_name, contact_no, dob, alias)
values (1001, 'kerri.legaspi@gmail.com', 0, current_timestamp, false, 'M', 'Kerri', 'Legaspi', '09152154717',
        '1984-10-13', 'u1001');

insert into platform_user (id, email, version, created, disabled, gender, first_name, last_name, contact_no, dob, alias)
values (1002, 'elianagraciela.legaspi@gmail.com', 0, current_timestamp, false, 'F', 'Eliana Graciela', 'Legaspi',
        '09152154718', '2021-07-21', 'u1002');

insert into platform_user (id, email, version, created, disabled, gender, first_name, last_name, contact_no, dob, alias)
values (1003, 'kerri.legaspi3@gmail.com', 0, current_timestamp, false, 'M', 'Kerri3', 'Legaspi3', '09152154717',
        '1984-10-13', 'u1003');

insert into platform_sso (external_ref, identity_provider, created, platform_user_id)
values ('G-100', 'Google', current_timestamp, 1001);

insert into platform_user_certification (platform_user_id, id, name, issuing_organization, credential_id,
                                         issued_date)
values (1001, 1001, 'certName1', 'orgName1', 'credId1', '2013-10-31');
insert into platform_user_certification (platform_user_id, id, name, issuing_organization, credential_id,
                                         issued_date, expiration_date)
values (1001, 1002, 'certName2', 'orgName2', 'credId2', '2013-10-31', '3013-10-31');

insert into platform_user_language (platform_user_id, id, language_code, language_level)
values (1001, 1001, 'EN', 'FULL_PROFESSIONAL');
insert into platform_user_language (platform_user_id, id, language_code, language_level)
values (1001, 1002, 'TL', 'NATIVE');

insert into platform_user_education (platform_user_id, id, degree, school, year_of_graduation)
values (1001, 1001, 'BS Computer Engineering', 'Lyceum University of the Philippines', 2005);

insert into platform_user_skill (platform_user_id, skill)
values (1001, 'Java');
insert into platform_user_skill (platform_user_id, skill)
values (1001, '.Net');