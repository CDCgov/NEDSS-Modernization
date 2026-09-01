import { generateUniqueELR } from '@utils/elr-generator';

export const getInsertELRQuery = function () {
    const elr = generateUniqueELR();
    const sanitizedXmlMessage = elr.message.replace(/'/g, "''");

    return `
        INSERT INTO [NBS_MSGOUTE].[dbo].[NBS_interface]
        (
            payload,
            imp_exp_ind_cd,
            record_status_cd,
            record_status_time,
            add_time,
            system_nm,
            doc_type_cd,
            filler_order_nbr,
            lab_clia,
            order_test_code,
            specimen_coll_date
        )
        VALUES (
            N'${sanitizedXmlMessage}',
            N'I',                                 
            N'QUEUED',                          
            CURRENT_TIMESTAMP,                 
            CURRENT_TIMESTAMP,                      
            N'NBS',                            
            N'11648804',                           
            N'${elr.fillerNumber}',                    
            N'11D1111111',                         
            N'20416-4',
            CAST(CURRENT_TIMESTAMP AS DATETIME)
        );
        SELECT record_status_cd
        FROM [NBS_MSGOUTE].[dbo].[NBS_interface]
        ORDER BY record_status_time DESC;
    `;
};
