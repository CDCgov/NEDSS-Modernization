-- Addresses a performance issue when looking up notes by public_health_case_uid (note_parent_uid)
CREATE NONCLUSTERED INDEX IDX_NBS_Note_note_parent_uid
    ON dbo.NBS_Note (note_parent_uid);
