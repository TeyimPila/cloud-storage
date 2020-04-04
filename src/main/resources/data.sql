insert into ROLES select * from (
    select 1, 'USER'
) x where not exists(select * from ROLES);