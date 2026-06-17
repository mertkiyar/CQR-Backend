DO $$
DECLARE
    -- ID Variables
    v_fac_id INT; v_dept_id INT; v_lang_tr INT; v_lang_en INT;
    v_user_id UUID; v_lecturer_id UUID; v_student_id UUID; v_course_id UUID;
    v_attendance_id UUID; v_session_id UUID;

    -- Loop and logic var
    i INT; j INT; k INT;
    v_gender GENDER_TYPE;
    v_rand_fname TEXT; 
    v_rand_lname TEXT; 
    v_rand_title TEXT;

    v_email TEXT;
    v_password TEXT;
    v_entry_year INT;
    v_student_number TEXT;

    -- Names
    v_male_names TEXT[] := ARRAY[
        'Ahmet', 'Mehmet', 'Mustafa', 'Can', 'Mert', 'Burak', 'Emre', 'Tolga', 'Kaan', 'Arda', 
        'Baran', 'Umut', 'Ozan', 'Ege', 'Kerem', 'Yiğit', 'Onur', 'Alp', 'Cem', 'Berk', 
        'Volkan', 'Serkan', 'Hakan', 'Oğuz', 'Sinan', 'Fatih', 'Ali', 'Ömer', 'Yusuf', 'İbrahim'
    ];
    
    v_female_names TEXT[] := ARRAY[
        'Ayşe', 'Fatma', 'Zeynep', 'Elif', 'Ceren', 'Gamze', 'Selin', 'İrem', 'Ece', 'Nazlı', 
        'Pelin', 'Damla', 'Buse', 'Gizem', 'Melis', 'Aslı', 'Derya', 'Esra', 'Seda', 'Merve', 
        'Büşra', 'Kübra', 'Aleyna', 'Beyza', 'İlayda', 'Simge', 'Dilara', 'Cansu', 'Ezgi', 'Gözde'
    ];

    v_last_names TEXT[] := ARRAY[
        'Yılmaz', 'Kaya', 'Demir', 'Şahin', 'Çelik', 'Yıldız', 'Öztürk', 'Aydın', 'Özdemir', 'Arslan', 
        'Doğan', 'Kılıç', 'Aslan', 'Çetin', 'Kara', 'Koç', 'Kurt', 'Özkan', 'Şimşek', 'Polat', 
        'Korkmaz', 'Bulut', 'Erdoğan', 'Yavuz', 'Tekin', 'Yüksel', 'Aksoy', 'Uçar', 'Güler', 'Çetinkaya',
		'Elmas', 'Kadıoğlu', 'Gül', 'Yıldırım'
    ];
    
    -- Faculties
    v_faculty_names TEXT[] := ARRAY['Mühendislik Fak.', 'Tıp Fak.', 'Hukuk Fak.', 'İİBF', 'Mimarlık Fak.', 'Fen Edebiyat Fak.', 'İletişim Fak.', 'Eğitim Fak.'];
    v_fac_ids INT[];
    v_dept_record RECORD;

    -- Courses
    v_courses_ceng TEXT[] := ARRAY['Intro to Programming', 'Discrete Math', 'Digital Logic', 'Data Structures', 'OOP', 'Database Systems', 'Operating Systems', 'Computer Arch.', 'Algorithms', 'Computer Networks', 'Software Eng.', 'Automata Theory', 'AI', 'Cyber Security', 'Web Dev', 'Mobile Dev', 'Machine Learning'];
    v_courses_eee TEXT[] := ARRAY['Circuit Theory', 'Electronics', 'Electromagnetics', 'Signals Systems', 'DSP', 'Control Systems', 'Communication', 'Microprocessors', 'Power Systems', 'Logic Design', 'Energy Conv.', 'Embedded Systems'];
    v_courses_ie TEXT[] := ARRAY['Intro to IE', 'Linear Algebra', 'Eng. Statistics', 'Operations Research', 'Work Study', 'Production Planning', 'Supply Chain', 'Quality Control', 'Simulation', 'Cost Analysis', 'Project Mgmt'];
    v_courses_mech TEXT[] := ARRAY['Statics', 'Dynamics', 'Thermodynamics', 'Fluid Mechanics', 'Strength of Mat.', 'Machine Elements', 'Heat Transfer', 'Manufacturing', 'System Dynamics', 'Control Theory', 'Vibrations', 'Robotics'];
    v_courses_med TEXT[] := ARRAY['Tıbbi Biyoloji', 'Anatomi', 'Fizyoloji', 'Histoloji', 'Biyokimya', 'Mikrobiyoloji', 'Patoloji', 'Farmakoloji', 'Dahiliye', 'Genel Cerrahi', 'Pediatri', 'Kadın Doğum', 'Nöroloji', 'Kardiyoloji'];
    v_courses_dent TEXT[] := ARRAY['Diş Morfolojisi', 'Maddeler Bilgisi', 'Protetik Diş', 'Restoratif Diş', 'Endodonti', 'Periodontoloji', 'Çene Cerrahisi', 'Ortodonti', 'Pedodonti'];
    v_courses_law TEXT[] := ARRAY['Hukuk Başlangıcı', 'Roma Hukuku', 'Medeni Hukuk', 'Anayasa Hukuku', 'Borçlar Hukuku', 'Ceza Hukuku', 'İdare Hukuku', 'Uluslararası Hukuk', 'Ticaret Hukuku', 'Eşya Hukuku', 'Medeni Usul', 'İcra İflas', 'İş Hukuku'];
    v_courses_man TEXT[] := ARRAY['Intro to Business', 'Financial Acc.', 'Microeconomics', 'Macroeconomics', 'Business Law', 'Marketing', 'Org. Behavior', 'HR Management', 'Financial Mgmt', 'Operations Mgmt', 'Strategic Mgmt'];
    v_courses_arch TEXT[] := ARRAY['Basic Design', 'Arch. Drawing', 'History of Arch.', 'Building Materials', 'Statics', 'Design Studio I', 'Design Studio II', 'Urban Planning', 'Building Physics', 'Restoration'];
    v_courses_comm TEXT[] := ARRAY['İletişime Giriş', 'Temel Gazetecilik', 'Halkla İlişkiler', 'Radyo TV Sinema', 'Yeni Medya', 'İletişim Hukuku', 'Medya Tarihi', 'Kitle İletişim', 'Reklamcılık', 'Sosyal Medya'];

    v_selected_courses TEXT[];
    v_current_course_name TEXT;
    v_current_lang INT;
    v_course_code TEXT;
    v_course_uuids UUID[];
    v_start_time TIMESTAMP; v_end_time TIMESTAMP;

BEGIN
    -- Clean
    EXECUTE 'ALTER TABLE ATTENDANCE_RECORDS DISABLE TRIGGER ALL';
    EXECUTE 'ALTER TABLE COURSES DISABLE TRIGGER ALL';
    
    RAISE NOTICE 'Veritabanı Temizleniyor...';
    TRUNCATE TABLE ATTENDANCE_RECORDS CASCADE;
    TRUNCATE TABLE ATTENDANCE_SESSIONS CASCADE;
    TRUNCATE TABLE ATTENDANCES CASCADE;
    TRUNCATE TABLE GRADES CASCADE;
    TRUNCATE TABLE STUDENT_COURSE CASCADE;
    TRUNCATE TABLE LECTURER_COURSE CASCADE;
    TRUNCATE TABLE COURSES CASCADE;
    TRUNCATE TABLE STUDENTS CASCADE;
    TRUNCATE TABLE LECTURERS CASCADE;
    TRUNCATE TABLE USERS CASCADE;
    TRUNCATE TABLE DEPARTMENTS CASCADE;
    TRUNCATE TABLE FACULTIES CASCADE;
    TRUNCATE TABLE LANGUAGES CASCADE;

    INSERT INTO LANGUAGES (LANGUAGE_NAME) VALUES ('Türkçe') RETURNING LANGUAGE_ID INTO v_lang_tr;
    INSERT INTO LANGUAGES (LANGUAGE_NAME) VALUES ('English') RETURNING LANGUAGE_ID INTO v_lang_en;

    -- Faculty
    FOR i IN 1..array_length(v_faculty_names, 1) LOOP
        INSERT INTO FACULTIES (FACULTY_NAME) VALUES (v_faculty_names[i]) RETURNING FACULTY_ID INTO v_fac_id;
        v_fac_ids := array_append(v_fac_ids, v_fac_id);
    END LOOP;

    -- Dept, Course, Lecturer
    RAISE NOTICE 'Creating mockup data...';
    
    FOR v_dept_record IN 
        SELECT * FROM (VALUES 
            ('Bilgisayar Mühendisliği', 'CENG', 1), ('Elektrik-Elektronik Müh.', 'EEE', 1), ('Endüstri Mühendisliği', 'IE', 1), ('Makine Mühendisliği', 'MECH', 1),
            ('Tıp', 'MED', 2), ('Diş Hekimliği', 'DENT', 2),
            ('Hukuk', 'LAW', 3),
            ('İşletme', 'MAN', 4), ('İktisat', 'ECON', 4), ('Uluslararası İlişkiler', 'IR', 4),
            ('Mimarlık', 'ARCH', 5), ('İç Mimarlık', 'INT', 5),
            ('Psikoloji', 'PSY', 6), ('Sosyoloji', 'SOC', 6), ('Tarih', 'HIST', 6),
            ('Radyo, Televizyon ve Sinema', 'RTV', 7),
            ('İngilizce Öğretmenliği', 'ELT', 8)
        ) AS t(d_name, d_code, f_index)
    LOOP
        v_fac_id := v_fac_ids[v_dept_record.f_index];
        v_current_lang := CASE WHEN v_dept_record.f_index IN (1, 4, 5) THEN v_lang_en ELSE v_lang_tr END;
        
        INSERT INTO DEPARTMENTS (DEPARTMENT_NAME, DEPARTMENT_CODE, LANGUAGE_ID, FACULTY_ID) 
        VALUES (v_dept_record.d_name, v_dept_record.d_code, v_current_lang, v_fac_id)
        RETURNING DEPARTMENT_ID INTO v_dept_id;

        FOR j IN 1..(6 + floor(random() * 5)) LOOP
            IF random() < 0.65 THEN 
                v_gender := 'MALE';
                v_rand_fname := v_male_names[1 + floor(random() * array_length(v_male_names, 1))::int];
            ELSE
                v_gender := 'FEMALE';
                v_rand_fname := v_female_names[1 + floor(random() * array_length(v_female_names, 1))::int];
            END IF;
            
            v_rand_lname := v_last_names[1 + floor(random() * array_length(v_last_names, 1))::int];
            
            IF j=1 THEN v_rand_title:='PROFESSOR'; ELSIF j=2 THEN v_rand_title:='ASSOCIATE_PROFESSOR'; ELSE v_rand_title:='DOCTOR_LECTURER'; END IF;

            v_email := lower(translate(v_rand_fname || '.' || v_rand_lname, 'çğıöşüÇĞİÖŞÜ', 'cgiosuCGIOSU')) || '_' || v_dept_id::text || j::text || '@classqroom.edu';
            v_password := md5(random()::text) || md5(random()::text); 

            INSERT INTO USERS (USER_ID, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_PASSWORD, GENDER, USER_TYPE, DEPARTMENT_ID, CREATED_AT)
            VALUES (gen_random_uuid(), v_rand_fname, v_rand_lname, v_email, v_password, v_gender, 'LECTURER', v_dept_id, NOW())
            RETURNING USER_ID INTO v_user_id;

            INSERT INTO LECTURERS (LECTURER_ID, LECTURER_TITLE, LECTURER_ROLE) 
            VALUES (v_user_id, v_rand_title::ACADEMIC_TITLE, 'LECTURER');
        END LOOP;

        CASE v_dept_record.d_code
            WHEN 'CENG' THEN v_selected_courses := v_courses_ceng;
            WHEN 'EEE' THEN v_selected_courses := v_courses_eee;
            WHEN 'IE' THEN v_selected_courses := v_courses_ie;
            WHEN 'MECH' THEN v_selected_courses := v_courses_mech;
            WHEN 'MED' THEN v_selected_courses := v_courses_med;
            WHEN 'DENT' THEN v_selected_courses := v_courses_dent;
            WHEN 'LAW' THEN v_selected_courses := v_courses_law;
            WHEN 'MAN' THEN v_selected_courses := v_courses_man;
            WHEN 'ARCH' THEN v_selected_courses := v_courses_arch;
            ELSE v_selected_courses := v_courses_comm;
        END CASE;

        FOR k IN 1..array_length(v_selected_courses, 1) LOOP
            v_current_course_name := v_selected_courses[k];
            
            v_course_code := v_dept_record.d_code || (1 + (k / 6))::text || LPAD((k % 10 + 1)::text, 2, '0');
            IF length(v_course_code) > 7 OR EXISTS (SELECT 1 FROM COURSES WHERE COURSE_CODE = v_course_code) THEN
                v_course_code := LEFT(v_course_code, 6) || chr(65 + (k % 26));
            END IF;

            INSERT INTO COURSES (
                COURSE_ID, COURSE_NAME, COURSE_CODE, 
                COURSE_ECTS, COURSE_CREDIT, HOURS_THEORETICAL, HOURS_PRACTICAL, 
                MIN_ATTENDANCE_PERCENT, LANGUAGE_ID, IS_ONLINE, IS_ELECTIVE, DEPARTMENT_ID
            ) VALUES (
                gen_random_uuid(), v_current_course_name, v_course_code, 
                4 + floor(random() * 4), 3, 3, 0, 70, v_current_lang, FALSE, FALSE, v_dept_id
            ) RETURNING COURSE_ID INTO v_course_id;
            
            v_course_uuids := array_append(v_course_uuids, v_course_id);
            
            SELECT LECTURER_ID INTO v_lecturer_id FROM USERS u JOIN LECTURERS l ON u.USER_ID=l.LECTURER_ID WHERE u.DEPARTMENT_ID=v_dept_id ORDER BY random() LIMIT 1;
            INSERT INTO LECTURER_COURSE (LECTURER_ID, COURSE_ID, ENROLLED_AT) VALUES (v_lecturer_id, v_course_id, NOW());
        END LOOP;
    END LOOP;

    -- Student
    RAISE NOTICE '2000+ Student saving...';
    FOR i IN 1..2200 LOOP
        IF random() < 0.5 THEN 
            v_gender := 'MALE';
            v_rand_fname := v_male_names[1 + floor(random() * array_length(v_male_names, 1))::int];
        ELSE
            v_gender := 'FEMALE';
            v_rand_fname := v_female_names[1 + floor(random() * array_length(v_female_names, 1))::int];
        END IF;

        v_rand_lname := v_last_names[1 + floor(random() * array_length(v_last_names, 1))::int];
        v_dept_id := (SELECT DEPARTMENT_ID FROM DEPARTMENTS ORDER BY random() LIMIT 1);
        
        v_entry_year := 21 + floor(random() * 4);
        v_student_number := '20' || v_entry_year::text || LPAD(i::text, 4, '0');
        
        v_email := lower(translate(v_rand_fname || v_rand_lname, 'çğıöşüÇĞİÖŞÜ', 'cgiosuCGIOSU')) || v_entry_year::text || '_' || i::text || '@classqroom.edu';
        v_password := md5(random()::text) || md5(random()::text); -- this is not real hash variable, so the accounts will be unreachable

        INSERT INTO USERS (USER_ID, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_PASSWORD, GENDER, USER_TYPE, DEPARTMENT_ID, CREATED_AT)
        VALUES (gen_random_uuid(), v_rand_fname, v_rand_lname, v_email, v_password, v_gender, 'STUDENT', v_dept_id, NOW())
        RETURNING USER_ID INTO v_student_id;

        INSERT INTO STUDENTS (STUDENT_ID, STUDENT_NUMBER, YEAR_OF_STUDY, GPA, IS_ACTIVE, IS_IN_CAMPUS)
        VALUES (v_student_id, v_student_number, 1+floor(random()*4), 2.0+(random()*2.0), TRUE, TRUE);

        INSERT INTO STUDENT_COURSE (STUDENT_ID, COURSE_ID, ENROLLED_AT)
        SELECT v_student_id, COURSE_ID, NOW() - INTERVAL '3 months'
        FROM COURSES WHERE DEPARTMENT_ID = v_dept_id ORDER BY random() LIMIT 6;
    END LOOP;

    -- Attendance
    RAISE NOTICE 'Assigning Attendances...';
    FOR i IN 1..250 LOOP 
        v_course_id := v_course_uuids[1 + floor(random() * array_length(v_course_uuids, 1))::int];
        
        v_start_time := NOW() - (floor(random()*14) * INTERVAL '1 day') + (9 * INTERVAL '1 hour');
        v_end_time := v_start_time + INTERVAL '50 minutes';

        INSERT INTO ATTENDANCES (ATTENDANCE_ID, COURSE_ID, LATITUDE, LONGITUDE, ALLOWED_ATTENDANCE_TYPE, SESSION_HOURS, STARTED_AT, EXPIRES_AT, IS_ACTIVE)
        VALUES (gen_random_uuid(), v_course_id, 41.0, 29.0, 'QR_CODE', 1, v_start_time, v_end_time, FALSE)
        RETURNING ATTENDANCE_ID INTO v_attendance_id;

        INSERT INTO ATTENDANCE_SESSIONS (ATTENDANCE_SESSION_ID, ATTENDANCE_ID, SIX_DIGIT_CODE, CREATED_AT, EXPIRES_AT, IS_ACTIVE)
        VALUES (gen_random_uuid(), v_attendance_id, '123456', v_start_time, v_start_time + INTERVAL '5 minutes', FALSE)
        RETURNING ATTENDANCE_SESSION_ID INTO v_session_id;

        INSERT INTO ATTENDANCE_RECORDS (STUDENT_ID, ATTENDANCE_ID, ATTENDANCE_SESSION_ID, ATTENDANCE_TYPE, DEVICE_ID, CLIENT_IP, ATTEND_AT, IS_LATE)
        SELECT STUDENT_ID, v_attendance_id, v_session_id, 'QR_CODE', gen_random_uuid(), '127.0.0.1', 
        v_start_time + (floor(random()*15)*INTERVAL '1 minute'), CASE WHEN random()<0.15 THEN TRUE ELSE FALSE END
        FROM STUDENT_COURSE WHERE COURSE_ID = v_course_id AND random() < 0.8;
    END LOOP;

    EXECUTE 'ALTER TABLE ATTENDANCE_RECORDS ENABLE TRIGGER ALL';
    EXECUTE 'ALTER TABLE COURSES ENABLE TRIGGER ALL';
    RAISE NOTICE 'The mockup database created successfuly!';
END $$;