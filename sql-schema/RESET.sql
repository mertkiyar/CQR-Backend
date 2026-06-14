DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (
        SELECT tablename
        FROM pg_tables
        WHERE schemaname = 'public'
    ) LOOP
        EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(r.tablename) || ' CASCADE';
    END LOOP;
END $$;

DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (
        SELECT t.typname
        FROM pg_type t
        JOIN pg_enum e ON t.oid = e.enumtypid
        GROUP BY t.typname
    ) LOOP
        EXECUTE 'DROP TYPE IF EXISTS ' || quote_ident(r.typname) || ' CASCADE';
    END LOOP;
END $$;

DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (
        SELECT proname, oidvectortypes(proargtypes) AS args
        FROM pg_proc
        JOIN pg_namespace n ON n.oid = pg_proc.pronamespace
        WHERE n.nspname = 'public'
    ) LOOP
        EXECUTE 'DROP FUNCTION IF EXISTS '
            || quote_ident(r.proname)
            || '(' || r.args || ') CASCADE';
    END LOOP;
END $$;