export default class Test {
    constructor(public readonly code: string, public readonly description: any) {}

    static readonly ACANTHAMOEBA_IDENTIFIED = new Test('T-50095', 'ACANTHAMOEBA IDENTIFIED');
    static readonly AEROBIC_BACTERIA_IDENTIFIED = new Test('T-50205', 'AEROBIC BACTERIA IDENTIFIED');
    static readonly AMOEBA_IDENTIFIED = new Test('T-50380', 'AMOEBA IDENTIFIED');
    static readonly ANAEROBIC_BACTERIA_IDENTIFIED = new Test('T-50460', 'ANAEROBIC BACTERIA IDENTIFIED');
    static readonly ARBOVIRUS_IDENTIFIED = new Test('T-50675', 'ARBOVIRUS IDENTIFIED');
    static readonly ACID_FAST_STAIN = new Test('T-50130', 'Acid-Fast Stain');
    static readonly ACID_FAST_STAIN_MODIFIED_FOR_CRYPTOSPORIDIUM = new Test(
        'T-50135',
        'Acid-Fast Stain, Modified for Cryptosporidium'
    );
    static readonly AMOEBA_STAIN = new Test('T-50390', 'Amoeba, stain');
    static readonly AMOEBA_WET_PREP_MICROSCOPY = new Test('T-50395', 'Amoeba, wet prep microscopy');
    static readonly ARBOVIRUS_RESULT = new Test('T-50655', 'Arbovirus - Result');
    static readonly ARBOVIRUS_ANTIBODY = new Test('T-50660', 'Arbovirus antibody');
    static readonly ARBOVIRUS_ANTIBODY_IGG = new Test('T-50665', 'Arbovirus antibody, IgG');
    static readonly ARBOVIRUS_ANTIBODY_IGM = new Test('T-50670', 'Arbovirus antibody, IgM');
    static readonly BACTERIA_IDENTIFIED = new Test('T-50960', 'BACTERIA IDENTIFIED');
    static readonly BLASTOMYCES_IDENTIFIED = new Test('T-51275', 'BLASTOMYCES IDENTIFIED');
    static readonly BORRELIA_SP_IDENTIFIED = new Test('T-51485', 'BORRELIA SP IDENTIFIED');
    static readonly BRUCELLA_SP_IDENTIFIED = new Test('T-51625', 'BRUCELLA SP IDENTIFIED');
    static readonly BABESIA_RESULT = new Test('T-50820', 'Babesia - Result');
    static readonly BACILLIS_ANTHRACIS_RESULT = new Test('T-50925', 'Bacillis anthracis - Result');
    static readonly BLASTOMYCES_RESULT = new Test('T-51215', 'Blastomyces - Result');
    static readonly BLASTOMYCES_DERMATITIDIS_RESULT = new Test('T-51240', 'Blastomyces dermatitidis - Result');
    static readonly BLASTOMYCES_DERMATITIDIS_ANTIBODIES_IGG = new Test(
        'T-51245',
        'Blastomyces dermatitidis Antibodies, IgG'
    );
    static readonly BLASTOMYCES_DERMATITIDIS_ANTIBODIES_IGM = new Test(
        'T-51250',
        'Blastomyces dermatitidis Antibodies, IgM'
    );
    static readonly BORDETELLA_RESULT = new Test('T-51290', 'Bordetella - Result');
    static readonly BORDETELLA_PERTUSSIS_RESULT = new Test('T-51330', 'Bordetella pertussis - Result');
    static readonly BORDETELLA_PERTUSSIS_SMEAR_BY_DFA = new Test('T-51375', 'Bordetella pertussis Smear, by DFA');
    static readonly BORDETELLA_PERTUSSIS_ANTIBODY = new Test('T-51350', 'Bordetella pertussis antibody');
    static readonly BORDETELLA_PERTUSSIS_ANTIBODY_IGG = new Test('T-51355', 'Bordetella pertussis antibody, IgG');
    static readonly BORDETELLA_PERTUSSIS_ANTIBODY_IGM = new Test('T-51360', 'Bordetella pertussis antibody, IgM');
    static readonly BORDETELLA_PERTUSSIS_ANTIGEN = new Test('T-51365', 'Bordetella pertussis antigen');
    static readonly BORDETELLA_PERTUSSIS_TOXIN_ANTIBODY = new Test('T-51395', 'Bordetella pertussis, toxin antibody');
    static readonly BORRELIA_RESULT = new Test('T-51405', 'Borrelia - Result');
    static readonly BORRELIA_BURGDORFERI_LYME_DISEASE_ANTIBODY_BLOOD_SEROLOGY = new Test(
        'T-60775',
        'Borrelia burgdorferi (Lyme Disease) Antibody, Blood (serology)'
    );
    static readonly BORRELIA_BURGDORFERI_LYME_DISEASE_ANTIBODY_BLOOD_ELISA = new Test(
        'T-60780',
        'Borrelia burgdorferi (Lyme Disease) Antibody, Blood, ELISA'
    );
    static readonly BORRELIA_BURGDORFERI_LYME_DISEASE_ANTIBODY_BLOOD_IFA = new Test(
        'T-60785',
        'Borrelia burgdorferi (Lyme Disease) Antibody, Blood, IFA'
    );
    static readonly BORRELIA_BURGDORFERI_LYME_DISEASE_ANTIBODY_CERBROSPINAL_FLUID_CSF_ELISA = new Test(
        'T-60815',
        'Borrelia burgdorferi (Lyme Disease) Antibody, Cerbrospinal fluid (CSF), ELISA'
    );
    static readonly BORRELIA_BURGDORFERI_LYME_DISEASE_ANTIBODY_IGG_BANDING_PATTERN_BLOOD_WESTERN_BLOT_WB = new Test(
        'T-60805',
        'Borrelia burgdorferi (Lyme Disease) Antibody, IgG banding pattern, Blood, Western blot (WB)'
    );
    static readonly BORRELIA_BURGDORFERI_LYME_DISEASE_ANTIBODY_IGG_BLOOD_WESTERN_BLOT_WB = new Test(
        'T-60800',
        'Borrelia burgdorferi (Lyme Disease) Antibody, IgG, Blood, Western blot (WB)'
    );
    static readonly BORRELIA_BURGDORFERI_LYME_DISEASE_ANTIBODY_IGM_BANDING_PATTERN_BLOOD_WESTERN_BLOT_WB = new Test(
        'T-60795',
        'Borrelia burgdorferi (Lyme Disease) Antibody, IgM banding pattern, Blood, Western blot (WB)'
    );
    static readonly BORRELIA_BURGDORFERI_LYME_DISEASE_ANTIBODY_IGM_BLOOD_WESTERN_BLOT_WB = new Test(
        'T-60790',
        'Borrelia burgdorferi (Lyme Disease) Antibody, IgM, Blood, Western blot (WB)'
    );
    static readonly BORRELIA_BURGDORFERI_LYME_DISEASE_ANTIGEN_URINE = new Test(
        'T-60840',
        'Borrelia burgdorferi (Lyme Disease) Antigen, Urine'
    );
    static readonly BORRELIA_BURGDORFERI_LYME_DISEASE_DNA_BLOOD_PCR = new Test(
        'T-60810',
        'Borrelia burgdorferi (Lyme Disease) DNA, Blood, PCR'
    );
    static readonly BORRELIA_BURGDORFERI_LYME_DISEASE_DNA_CERBROSPINAL_FLUID_CSF_PCR = new Test(
        'T-60820',
        'Borrelia burgdorferi (Lyme Disease) DNA, Cerbrospinal fluid (CSF), PCR'
    );
    static readonly BORRELIA_BURGDORFERI_LYME_DISEASE_DNA_SKIN_BIOPSY_PCR = new Test(
        'T-60830',
        'Borrelia burgdorferi (Lyme Disease) DNA, Skin biopsy, PCR'
    );
    static readonly BORRELIA_BURGDORFERI_LYME_DISEASE_DNA_SYNOVIAL_FLUID_KNEE_PCR = new Test(
        'T-60835',
        'Borrelia burgdorferi (Lyme Disease) DNA, Synovial fluid (knee), PCR'
    );
    static readonly BORRELIA_BURGDORFERI_LYME_DISEASE_DNA_URINE_PCR = new Test(
        'T-60845',
        'Borrelia burgdorferi (Lyme Disease) DNA, Urine, PCR'
    );
    static readonly BRUCELLA_RESULT = new Test('T-51500', 'Brucella - Result');
    static readonly BRUCELLA_ANTIBODY = new Test('T-51535', 'Brucella antibody');
    static readonly BRUCELLA_ANTIBODY_IGG = new Test('T-51540', 'Brucella antibody, IgG');
    static readonly BRUCELLA_ANTIBODY_IGM = new Test('T-51545', 'Brucella antibody, IgM');
    static readonly BRUCELLA_ANTIGEN = new Test('T-51550', 'Brucella antigen');
    static readonly C_BOTULINUM_ISOLATED = new Test('33695-8', 'C. botulinum isolated');
    static readonly C_BOTULINUM_TOXIN_BY_MOUSE_BIOASSAY = new Test('33696-6', 'C. botulinum toxin by mouse bioassay');
    static readonly C_BOTULINUM_ORGANISM_SPECIFIC_CULTURE_SPECIMEN = new Test(
        '33694-1',
        'C. botulinum, organism specific culture, specimen'
    );
    static readonly C_BOTULINUM_TOXIN_IN_SERUM_POSITIVE_ANIMAL_INNOCULATION = new Test(
        '20705-0',
        'C. botulinum, toxin in serum, positive animal innoculation'
    );
    static readonly C_BOTULINUM_TOXIN_IN_SPECIMEN_OTHER_THAN_SERUM_POSITIVE_ANIMAL_INNOCULATION = new Test(
        '20706-8',
        'C. botulinum, toxin in specimen other than serum, positive animal innoculation'
    );
    static readonly CAMPYLOBACTER_SP_IDENTIFIED = new Test('T-51870', 'CAMPYLOBACTER SP IDENTIFIED');
    static readonly CD4_ABSOLUTE_SERUM = new Test('T-52035', 'CD4, Absolute, Serum');
    static readonly CD4_PERCENT_SERUM = new Test('T-52040', 'CD4, Percent, Serum');
    static readonly CHLAMYDIA_IDENTIFIED = new Test('T-52260', 'CHLAMYDIA IDENTIFIED');
    static readonly CHLAMYDIA_TRACHOMATIS_IDENTIFIED = new Test('T-52315', 'CHLAMYDIA TRACHOMATIS IDENTIFIED');
    static readonly COCCIDIOIDES_IDENTIFIED = new Test('T-52765', 'COCCIDIOIDES IDENTIFIED');
    static readonly CRYPTOCOCCUS_IDENTIFIED = new Test('T-53270', 'CRYPTOCOCCUS IDENTIFIED');
    static readonly RYPTOSPORIDIUM_SMEAR_STOOL = new Test('008755', 'CRYPTOSPORIDIUM SMEAR, STOOL');
    static readonly CRYPTOSPORIDIUM_SP = new Test('T-53325', 'CRYPTOSPORIDIUM SP');
    static readonly CYCLOSPORA_IDENTIFIED = new Test('T-53380', 'CYCLOSPORA IDENTIFIED');
    static readonly CALIFORNIA_ENCEPHALITIS_VIRUS_ANTIBODIES_IGG = new Test(
        'T-51765',
        'California Encephalitis Virus Antibodies, IgG'
    );
    static readonly CALIFORNIA_ENCEPHALITIS_VIRUS_ANTIBODIES_IGM = new Test(
        'T-51770',
        'California Encephalitis Virus Antibodies, IgM'
    );
    static readonly CALYMMATOBACTERIUM_GRANULOMATIS_RESULT = new Test(
        'T-51775',
        'Calymmatobacterium granulomatis - Result'
    );
    static readonly CAMPYLOBACTER_RESULT = new Test('T-51785', 'Campylobacter - Result');
    static readonly CAMPYLOBACTER_JEJUNI_ANTIBODY_IGG = new Test('T-51845', 'Campylobacter jejuni antibody, IgG');
    static readonly CAMPYLOBACTER_JEJUNI_ANTIBODY_IGM = new Test('T-51850', 'Campylobacter jejuni antibody, IgM');
    static readonly AMPYLOBACTER_SP_IDENTIFIED_IN_CULTURE_OTHER_THAN_BLOOD_OR_STOOL = new Test(
        '6332-1',
        'Campylobacter sp identified in culture other than blood or stool'
    );
    static readonly CAMPYLOBACTER_SP_ISOLATED = new Test('20738-1', 'Campylobacter sp isolated');
    static readonly CAMPYLOBACTER_SP_ISOLATED_FROM_SPECIMEN_OTHER_THAN_BLOOD_OR_STOOL = new Test(
        '20739-9',
        'Campylobacter sp isolated, from specimen other than blood or stool'
    );
    static readonly CAMPYLOBACTER_SP_ISOLATED_FROM_TISSUE = new Test(
        '20740-7',
        'Campylobacter sp isolated, from tissue'
    );
    static readonly AMPYLOBACTER_SPECIES_NOS_FROM_BLOOD_CULTURE = new Test(
        '6330-5',
        'Campylobacter species NOS, from blood culture'
    );
    static readonly AMPYLOBACTER_SPECIES_NOS_FROM_STOOL_CULTURE = new Test(
        '6331-3',
        'Campylobacter species NOS, from stool culture'
    );
    static readonly CHLAMYDIA_RESULT = new Test('T-52240', 'Chlamydia - Result');
    static readonly CHLAMYDIA_DNA = new Test('T-52255', 'Chlamydia DNA');
    static readonly CHLAMYDIA_TRACHOMATIS_RESULT = new Test('T-52280', 'Chlamydia trachomatis - Result');
    static readonly CLOSTRIDIUM_BOTULINUM_RESULT = new Test('T-52630', 'Clostridium botulinum - Result');
    static readonly CLOSTRIDIUM_TETANI_RESULT = new Test('T-52660', 'Clostridium tetani - Result');
    static readonly CLOSTRIDIUM_TETANI_ANTIBODY = new Test('T-52665', 'Clostridium tetani antibody');
    static readonly COCCIDIOIDES_RESULT = new Test('T-52740', 'Coccidioides - Result');
    static readonly COCCIDIOIDES_IMMITIS_RESULT = new Test('T-52770', 'Coccidioides immitis - Result');
    static readonly COCCIDIOIDES_IMMITIS_ANTIBODY_IGG = new Test('T-52785', 'Coccidioides immitis antibody, IgG');
    static readonly COCCIDIOIDES_IMMITIS_ANTIBODY_IGM = new Test('T-52790', 'Coccidioides immitis antibody, IgM');
    static readonly CORYNEBACTERIUM_DIPHTHERIAE_RESULT = new Test('T-53010', 'Corynebacterium diphtheriae - Result');
    static readonly CORYNEBACTERIUM_SPECIES_TOXIN_RESULT = new Test(
        'T-53055',
        'Corynebacterium species toxin - Result'
    );
    static readonly COXIELLA_BURNETII_Q_FEVER_RESULT = new Test('T-53060', 'Coxiella burnetii (Q fever) - Result');
    static readonly CRYPTOCOCCUS_RESULT = new Test('T-53235', 'Cryptococcus - Result');
    static readonly CRYPTOCOCCUS_NEOFORMANS_RESULT = new Test('T-53275', 'Cryptococcus neoformans - Result');
    static readonly CRYPTOSPORIDIUM_RESULT = new Test('T-53310', 'Cryptosporidium - Result');
    static readonly CRYPTOSPORIDIUM_SMEAR_STOOL = new Test('T-53320', 'Cryptosporidium Smear, Stool');
    static readonly CRYPTOSPORIDIUM_SPECIES_ACID_FAST_STAIN = new Test(
        'T-53330',
        'Cryptosporidium species, acid fast stain'
    );
    static readonly RYPTOSPORIDIUM_DFA = new Test('6372-7', 'Cryptosporidium, DFA');
    static readonly RYPTOSPORIDIUM_ELISA = new Test('6371-9', 'Cryptosporidium, ELISA');
    static readonly CRYPTOSPORIDIUM_PCR = new Test('T-LNX004', 'Cryptosporidium, PCR');
    static readonly CRYPTOSPORIDIUM_IODINE_STAIN_CONC_WET_MOUNT = new Test(
        'T-LNX002',
        'Cryptosporidium, iodine stain, conc wet mount'
    );
    static readonly CRYPTOSPORIDIUM_MODIFIED_ACID_FAST_STAIN = new Test(
        'T-LNX003',
        'Cryptosporidium, modified acid-fast stain'
    );
    static readonly CRYPTOSPORIDIUM_OTHER = new Test('T-LNX005', 'Cryptosporidium, other');
    static readonly CRYPTOSPORIDIUM_RAPID_CARTRIDGE_TEST = new Test(
        'T-LNX017',
        'Cryptosporidium, rapid cartridge test'
    );
    static readonly CRYPTOSPORIDIUM_UNSTAINED_CONC_WET_MOUNT = new Test(
        'T-LNX001',
        'Cryptosporidium, unstained conc wet mount'
    );
    static readonly CULTURE_IDENTIFICATION = new Test('T-53345', 'Culture Identification');
    static readonly CYCLOSPORA_RESULT = new Test('T-53370', 'Cyclospora - Result');
    static readonly CYCLOSPORA_PCR = new Test('T-LNX010', 'Cyclospora, PCR');
    static readonly CYCLOSPORA_UV_FLUORESCENCE = new Test('T-LNX009', 'Cyclospora, UV fluorescence');
    static readonly CYCLOSPORA_IODINE_STAIN_CONC_WET_MOUNT = new Test(
        'T-LNX007',
        'Cyclospora, iodine stain, conc wet mount'
    );
    static readonly CYCLOSPORA_MODIFIED_ACID_FAST_STAIN = new Test('10659-1', 'Cyclospora, modified acid-fast stain');
    static readonly CYCLOSPORA_OTHER = new Test('T-LNX011', 'Cyclospora, other');
    static readonly CYCLOSPORA_SAFRANIN_STAIN = new Test('T-LNX008', 'Cyclospora, safranin stain');
    static readonly CYCLOSPORA_UNSTAINED_CONC_WET_MOUNT = new Test('T-LNX006', 'Cyclospora, unstained conc wet mount');
    static readonly CYSTICERCUS_RESULT = new Test('T-53410', 'Cysticercus - Result');
    static readonly DIPHTHERIA_SPECIES_IDENTIFIED = new Test('T-53630', 'DIPHTHERIA SPECIES IDENTIFIED');
    static readonly DENGUE_VIRUS_RESULT = new Test('T-53555', 'Dengue virus - Result');
    static readonly DIPHTHERIA_SPECIES_RESULT = new Test('T-53625', 'Diphtheria species - Result');
    static readonly E_COLI_SHIGA_POS_STOOL = new Test('16835-1', 'E. coli Shiga pos , Stool');
    static readonly E_COLI_VEROTOXIC_STOOL = new Test('16836-9', 'E. coli Verotoxic, Stool');
    static readonly E_COLI_SEROTYPE = new Test('20789-4', 'E. coli serotype');
    static readonly _COLI_SHIGA_TOXIN_TEST_EIA_OR_IMMUNOASSAY = new Test(
        'PHC468',
        'E. coli shiga toxin test, EIA or immunoassay'
    );
    static readonly _COLI_SHIGA_TOXIN_TEST_PCR = new Test('PHC469', 'E. coli shiga toxin test, PCR');
    static readonly E_COLI_O157_H7_IN_STOOL = new Test('10851-4', 'E. coli, O157:H7 in stool');
    static readonly E_COLI_SHIGA_LIKE_BY_EIA = new Test('21262-1', 'E. coli, Shiga-like, by EIA');
    static readonly ENTAMOEBA_HISTOLYTICA_IDENTIFIED = new Test('T-53970', 'ENTAMOEBA HISTOLYTICA IDENTIFIED');
    static readonly ENTEROCOCCUS_IDENTIFIED = new Test('T-53985', 'ENTEROCOCCUS IDENTIFIED');
    static readonly EPSTEIN_BARR_VIRUS_IDENTIFIED = new Test('T-54060', 'EPSTEIN BARR VIRUS IDENTIFIED');
    static readonly ESCHERICHIA_COLI_IDENTIFIED = new Test('T-54105', 'ESCHERICHIA COLI IDENTIFIED');
    static readonly ESCHERICHIA_COLI_O157_H7_IDENTIFIED = new Test('T-54130', 'ESCHERICHIA COLI O157:H7 IDENTIFIED');
    static readonly EASTERN_EQUINE_ENCEPHALITIS_VIRUS_RESULT = new Test(
        'T-53640',
        'Eastern equine encephalitis virus - Result'
    );
    static readonly EBOLA_VIRUS_RESULT = new Test('T-53675', 'Ebola virus - Result');
    static readonly ECHINOCOCCUS_RESULT = new Test('T-53700', 'Echinococcus - Result');
    static readonly EHRLICHIA_RESULT = new Test('T-53800', 'Ehrlichia - Result');
    static readonly EHRLICHIA_CHAFFEENSIS_RESULT = new Test('T-53840', 'Ehrlichia chaffeensis - Result');
    static readonly EHRLICHIA_PHAGOCYTOPHILA_RESULT = new Test('T-53900', 'Ehrlichia phagocytophila - Result');
    static readonly ENTAMOEBA_HISTOLYTICA_RESULT = new Test('T-53940', 'Entamoeba histolytica - Result');
    static readonly ESCHERICHIA_COLI_RESULT = new Test('T-54095', 'Escherichia coli - Result');
    static readonly ESCHERICHIA_COLI_SEROTYPE_RESULT = new Test('T-54135', 'Escherichia coli serotype - Result');
    static readonly FUNGUS_IDENTIFIED = new Test('T-54510', 'FUNGUS IDENTIFIED');
    static readonly UNGUS_STAIN = new Test('188244', 'FUNGUS STAIN');
    static readonly FRANCISELLA_TULARENSIS_RESULT = new Test('T-54410', 'Francisella tularensis - Result');
    static readonly FRANCISELLA_TULARENSIS_ANTIBODY_IGG = new Test('T-54430', 'Francisella tularensis antibody, IgG');
    static readonly FRANCISELLA_TULARENSIS_ANTIBODY_IGM = new Test('T-54435', 'Francisella tularensis antibody, IgM');
    static readonly FUNGUS_RESULT = new Test('T-54505', 'Fungus - Result');
    static readonly FUNGUS_STAIN = new Test('T-54515', 'Fungus Stain');
    static readonly FUNGUS_STAIN_KOH_WET_PREP = new Test('T-54520', 'Fungus Stain (KOH/ Wet Prep)');
    static readonly GIARDIA_SP_IDENTIFIED = new Test('T-54620', 'GIARDIA SP IDENTIFIED');
    static readonly GIARDIA_RESULT = new Test('T-54585', 'Giardia - Result');
    static readonly GIARDIA_LAMBLIA_RESULT = new Test('T-54595', 'Giardia lamblia - Result');
    static readonly GIARDIA_LAMBLIA_ANTIBODY_IGG = new Test('T-54605', 'Giardia lamblia antibody, IgG');
    static readonly GIARDIA_LAMBLIA_ANTIBODY_IGM = new Test('T-54610', 'Giardia lamblia antibody, IgM');
    static readonly GIARDIA_DFA = new Test('14210-9', 'Giardia, DFA');
    static readonly IARDIA_ELISA = new Test('6412-1', 'Giardia, ELISA');
    static readonly GIARDIA_IODINE_STAIN_CONC_WET_MOUNT = new Test('T-LNX013', 'Giardia, iodine stain, conc wet mount');
    static readonly GIARDIA_RAPID_CARTRIDGE_TEST = new Test('T-LNX016', 'Giardia, rapid cartridge test');
    static readonly GIARDIA_TRICHROME = new Test('T-LNX014', 'Giardia, trichrome');
    static readonly GIARDIA_UNSTAINED_CONC_WET_MOUNT = new Test('T-LNX012', 'Giardia, unstained, conc wet mount');
    static readonly GRAM_STAIN = new Test('T-54720', 'Gram Stain');
    static readonly HAEMOPHILUS_INFLUENZAE_IDENTIFIED = new Test('T-54920', 'HAEMOPHILUS INFLUENZAE IDENTIFIED');
    static readonly HAEMOPHILUS_SP_IDENTIFIED = new Test('T-54930', 'HAEMOPHILUS SP IDENTIFIED');
    static readonly HERPES_SIMPLEX_VIRUS_HSV_IDENTIFIED = new Test('T-55470', 'HERPES SIMPLEX VIRUS (HSV) IDENTIFIED');
    static readonly HERPES_VIRUS_IDENTIFIED = new Test('T-55500', 'HERPES VIRUS IDENTIFIED');
    static readonly HISTOPLASMA_IDENTIFIED = new Test('T-55625', 'HISTOPLASMA IDENTIFIED');
    static readonly HIV_RESULT = new Test('T-55630', 'HIV - Result');
    static readonly HIV_ANTIGEN = new Test('T-55755', 'HIV Antigen');
    static readonly HIV_DNA = new Test('T-55770', 'HIV DNA');
    static readonly IV_DNA_PCR = new Test('162405', 'HIV DNA PCR');
    static readonly IV_DNA_POLYMERASE_CHAIN_RXN = new Test('162157', 'HIV DNA POLYMERASE CHAIN RXN.');
    static readonly HIV_GENOTYPE = new Test('T-55785', 'HIV Genotype');
    static readonly HIV_IDENTIFIED = new Test('T-55790', 'HIV IDENTIFIED');
    static readonly HIV_PCR_DNA_OR_RNA = new Test('T-55795', 'HIV PCR (DNA or RNA)');
    static readonly HIV_PHENOTYPE = new Test('T-55805', 'HIV Phenotype');
    static readonly HIV_RNA = new Test('T-55815', 'HIV RNA');
    static readonly HIV_SCREENING_EIA_ELISA = new Test('T-55820', 'HIV Screening (EIA / ELISA)');
    static readonly HAEMOPHILUS_DUCREYI_RESULT = new Test('T-54755', 'Haemophilus ducreyi - Result');
    static readonly HAEMOPHILUS_INFLUENZAE_RESULT = new Test('T-54770', 'Haemophilus influenzae - Result');
    static readonly HAEMOPHILUS_INFLUENZAE_A_RESULT = new Test('T-54775', 'Haemophilus influenzae A - Result');
    static readonly HAEMOPHILUS_INFLUENZAE_B_RESULT = new Test('T-54820', 'Haemophilus influenzae B - Result');
    static readonly HANTAVIRUS_RESULT = new Test('T-54940', 'Hantavirus - Result');
    static readonly HANTAVIRUS_SIN_NOMBRE_ANTIBODY = new Test('T-55010', 'Hantavirus sin nombre antibody');
    static readonly HANTAVIRUS_SIN_NOMBRE_ANTIBODY_IGG = new Test('T-55015', 'Hantavirus sin nombre antibody, IgG');
    static readonly HANTAVIRUS_SIN_NOMBRE_ANTIBODY_IGM = new Test('T-55020', 'Hantavirus sin nombre antibody, IgM');
    static readonly HEPATITIS_A_VIRUS_ANTIBODY_IGG_HAVAB_IGG = new Test(
        'T-55165',
        'Hepatitis A virus Antibody, IgG (HAVAb IgG)'
    );
    static readonly HEPATITIS_A_VIRUS_ANTIBODY_IGM_HAVAB_IGM = new Test(
        'T-55170',
        'Hepatitis A virus Antibody, IgM (HAVAb IgM)'
    );
    static readonly HEPATITIS_A_VIRUS_RNA = new Test('T-55175', 'Hepatitis A virus RNA');
    static readonly HEPATITIS_A_VIRUS_ANTIBODY = new Test('T-55160', 'Hepatitis A virus antibody');
    static readonly HEPATITIS_B_VIRUS_HBV = new Test('T-55180', 'Hepatitis B virus (HBV)');
    static readonly HEPATITIS_B_VIRUS_CORE_ANTIBODY_IGG = new Test('T-55190', 'Hepatitis B virus Core Antibody, IgG');
    static readonly HEPATITIS_B_VIRUS_CORE_ANTIBODY_IGM = new Test('T-55195', 'Hepatitis B virus Core Antibody, IgM');
    static readonly HEPATITIS_B_VIRUS_CORE_ANTIGEN = new Test('T-55200', 'Hepatitis B virus Core antigen');
    static readonly HEPATITIS_B_VIRUS_E_ANTIBODY = new Test('T-55205', 'Hepatitis B virus e antibody');
    static readonly HEPATITIS_B_VIRUS_E_ANTIGEN = new Test('T-55210', 'Hepatitis B virus e antigen');
    static readonly HEPATITIS_C_VIRUS_HCV_GENOTYPING = new Test('T-55290', 'Hepatitis C virus (HCV), Genotyping');
    static readonly HEPATITIS_C_VIRUS_HCV_NGI_SUPERQUANT = new Test(
        'T-55295',
        'Hepatitis C virus (HCV), NGI SuperQuant™'
    );
    static readonly HEPATITIS_C_VIRUS_HCV_NGI_ULTRAQUAL = new Test(
        'T-55300',
        'Hepatitis C virus (HCV), NGI UltraQual™'
    );
    static readonly HEPATITIS_C_VIRUS_HCV_QUANTASURE_PLUS_QUANTITA = new Test(
        'T-55305',
        'Hepatitis C virus (HCV), QuantaSure™ Plus Quantita'
    );
    static readonly HEPATITIS_C_VIRUS_HCV_RNA_QUALITATIVE_BY_PCR = new Test(
        'T-55315',
        'Hepatitis C virus (HCV), RNA, Qualitative, by PCR'
    );
    static readonly HEPATITIS_C_VIRUS_ANTIBODY_BAND_PATTERN = new Test(
        'T-55320',
        'Hepatitis C virus antibody band pattern'
    );
    static readonly HEPATITIS_D_VIRUS_ANTIGEN = new Test('T-55340', 'Hepatitis D virus antigen');
    static readonly HEPATITIS_E_VIRUS_ANTIBODY = new Test('T-55350', 'Hepatitis E virus antibody');
    static readonly HERPES_SIMPLEX_VIRUS_HSV_RESULT = new Test('T-55380', 'Herpes simplex virus (HSV) - Result');
    static readonly HERPES_VIRUS_RESULT = new Test('T-55480', 'Herpes virus - Result');
    static readonly HISTOPLASMA_RESULT = new Test('T-55535', 'Histoplasma - Result');
    static readonly HISTOPLASMA_CAPSULATUM_RESULT = new Test('T-55565', 'Histoplasma capsulatum - Result');
    static readonly HUMAN_PAPILLOMA_VIRUS_RESULT = new Test('T-55960', 'Human papilloma virus - Result');
    static readonly HYDATID_CYST_RESULT = new Test('T-56000', 'Hydatid cyst - Result');
    static readonly INFLUENZA_VIRUS_IDENTIFIED = new Test('T-56240', 'INFLUENZA VIRUS IDENTIFIED');
    static readonly INFLUENZA_A_VIRUS_RESULT = new Test('T-56135', 'Influenza A virus - Result');
    static readonly INFLUENZA_B_VIRUS_RESULT = new Test('T-56170', 'Influenza B virus - Result');
    static readonly INFLUENZA_VIRUS_RESULT = new Test('T-56215', 'Influenza virus - Result');
    static readonly ISOSPORA_RESULT = new Test('T-56315', 'Isospora - Result');
    static readonly JAMESTOWN_CANYON_VIRUS_RESULT = new Test('T-56330', 'Jamestown Canyon virus - Result');
    static readonly JAPANESE_ENCEPHALITIS_VIRUS_RESULT = new Test('T-56355', 'Japanese encephalitis virus - Result');
    static readonly LEGIONELLA_SP_IDENTIFIED = new Test('T-56650', 'LEGIONELLA SP IDENTIFIED');
    static readonly LEPTOSPIRA_SP_IDENTIFIED = new Test('T-56735', 'LEPTOSPIRA SP IDENTIFIED');
    static readonly LA_CROSSE_VIRUS_RESULT = new Test('T-56420', 'La Crosse virus - Result');
    static readonly LASSA_VIRUS_RESULT = new Test('T-56500', 'Lassa virus - Result');
    static readonly EAD_BLOOD = new Test('007626', 'Lead, Blood');
    static readonly LEAD_BLOOD = new Test('T-56540', 'Lead, Blood');
    static readonly EAD_URINE = new Test('019315', 'Lead, Urine');
    static readonly LEAD_URINE = new Test('T-56550', 'Lead, Urine');
    static readonly LEGIONELLA_RESULT = new Test('T-56555', 'Legionella - Result');
    static readonly LEGIONELLA_PNEUMOPHILA_RESULT = new Test('T-56615', 'Legionella pneumophila - Result');
    static readonly LEGIONELLA_PNEUMOPHILA_DNA = new Test('T-56635', 'Legionella pneumophila DNA');
    static readonly LEGIONELLA_PNEUMOPHILA_ANTIBODY = new Test('T-56620', 'Legionella pneumophila antibody');
    static readonly LEGIONELLA_PNEUMOPHILA_ANTIGEN = new Test('T-56625', 'Legionella pneumophila antigen');
    static readonly LEPTOSPIRA_RESULT = new Test('T-56670', 'Leptospira - Result');
    static readonly LEPTOSPIRA_DNA = new Test('T-56715', 'Leptospira DNA');
    static readonly LEPTOSPIRA_ANTIBODY = new Test('T-56675', 'Leptospira antibody');
    static readonly LEPTOSPIRA_ANTIBODY_CSF = new Test('T-56680', 'Leptospira antibody, CSF');
    static readonly LEPTOSPIRA_ANTIBODY_IGG = new Test('T-56685', 'Leptospira antibody, IgG');
    static readonly LEPTOSPIRA_ANTIBODY_IGM = new Test('T-56690', 'Leptospira antibody, IgM');
    static readonly LEPTOSPIRA_ANTIGEN = new Test('T-56695', 'Leptospira antigen');
    static readonly LEPTOSPIRA_ANTIGEN_CSF = new Test('T-56700', 'Leptospira antigen, CSF');
    static readonly LEPTOSPIRA_INTERROGANS_RESULT = new Test('T-56720', 'Leptospira interrogans - Result');
    static readonly LISTERIA_RESULT = new Test('T-56795', 'Listeria - Result');
    static readonly LISTERIA_MONOCYTOGENES_RESULT = new Test('T-56820', 'Listeria monocytogenes - Result');
    static readonly ISTERIA_MONOCYTOGENES_CULTURE_POSITIVE = new Test(
        '6609-2',
        'Listeria monocytogenes, culture positive'
    );
    static readonly LYMPHOCYTIC_CHORIOMENINGITIS_VIRUS_RESULT = new Test(
        'T-56885',
        'Lymphocytic choriomeningitis virus - Result'
    );
    static readonly MICROORGANISM_IDENTIFIED = new Test('T-57185', 'MICROORGANISM IDENTIFIED');
    static readonly MYCOBACTERIUM_IDENTIFIED = new Test('T-57345', 'MYCOBACTERIUM IDENTIFIED');
    static readonly MYCOBACTERIUM_TUBERCULOSIS_IDENTIFIED = new Test(
        'T-57370',
        'MYCOBACTERIUM TUBERCULOSIS IDENTIFIED'
    );
    static readonly MALARIA_PLASMODIUM_THICK_SMEAR = new Test('T-56950', 'Malaria (Plasmodium) - thick smear');
    static readonly MALARIA_PLASMODIUM_THIN_SMEAR = new Test('T-56955', 'Malaria (Plasmodium) - thin smear');
    static readonly MALARIA_PLASMODIUM_SMEAR = new Test('T-56960', 'Malaria (Plasmodium) smear');
    static readonly MEASLES_RUBEOLA_VIRUS_RESULT = new Test('T-56990', 'Measles (Rubeola) virus - Result');
    static readonly MEASLES_RUBEOLA_VIRUS_ANTIBODY = new Test('T-57005', 'Measles (Rubeola) virus antibody');
    static readonly MEASLES_RUBEOLA_VIRUS_ANTIBODY_CSF = new Test('T-57010', 'Measles (Rubeola) virus antibody, CSF');
    static readonly MICROFILARIAL_FILARIA_SMEAR = new Test('T-57180', 'Microfilarial (Filaria) Smear');
    static readonly MISCELLANEOUS_TEST = new Test('T-57200', 'Miscellaneous Test');
    static readonly MUMPS_VIRUS_RESULT = new Test('T-57220', 'Mumps virus - Result');
    static readonly MUMPS_VIRUS_RNA = new Test('T-57260', 'Mumps virus RNA');
    static readonly MUMPS_VIRUS_RNA_CSF = new Test('T-57265', 'Mumps virus RNA, CSF');
    static readonly MUMPS_VIRUS_ANTIBODY = new Test('T-57235', 'Mumps virus antibody');
    static readonly MUMPS_VIRUS_ANTIBODY_CSF = new Test('T-57240', 'Mumps virus antibody, CSF');
    static readonly URINE_TYPHUS_ANTIBODIES_IGG = new Test('016188', 'Murine Typhus Antibodies, IgG');
    static readonly MURINE_TYPHUS_ANTIBODIES_IGG = new Test('T-57285', 'Murine Typhus Antibodies, IgG');
    static readonly MYCOBACTERIUM_RESULT = new Test('T-57295', 'Mycobacterium - Result');
    static readonly MYCOBACTERIUM_DNA = new Test('T-57335', 'Mycobacterium DNA');
    static readonly MYCOBACTERIUM_DNA_CSF = new Test('T-57340', 'Mycobacterium DNA, CSF');
    static readonly MYCOBACTERIUM_RNA = new Test('T-57350', 'Mycobacterium RNA');
    static readonly MYCOBACTERIUM_BOVIS_RESULT = new Test('T-57310', 'Mycobacterium bovis - Result');
    static readonly MYCOBACTERIUM_TUBERCULOSIS_RESULT = new Test('T-57355', 'Mycobacterium tuberculosis - Result');
    static readonly MYCOBACTERIUM_TUBERCULOSIS_DNA = new Test('T-57360', 'Mycobacterium tuberculosis DNA');
    static readonly MYCOBACTERIUM_TUBERCULOSIS_DNA_CSF = new Test('T-57365', 'Mycobacterium tuberculosis DNA, CSF');
    static readonly NEISSERIA_GONORRHOEAE_IDENTIFIED = new Test('T-57490', 'NEISSERIA GONORRHOEAE IDENTIFIED');
    static readonly NEISSERIA_MENINGITIDIS_IDENTIFIED = new Test('T-57530', 'NEISSERIA MENINGITIDIS IDENTIFIED');
    static readonly NAEGLERIA_FOWLERI_RESULT = new Test('T-57405', 'Naegleria fowleri - Result');
    static readonly NEISSERIA_GONORRHOEAE_RESULT = new Test('T-57455', 'Neisseria gonorrhoeae - Result');
    static readonly NEISSERIA_GONORRHOEAE_DNA = new Test('T-57485', 'Neisseria gonorrhoeae DNA');
    static readonly NEISSERIA_GONORRHOEAE_RNA = new Test('T-57495', 'Neisseria gonorrhoeae, RNA');
    static readonly NEISSERIA_MENINGITIDIS_RESULT = new Test('T-57500', 'Neisseria meningitidis - Result');
    static readonly NEISSERIA_MENINGITIDIS_ANTIBODY = new Test('T-57505', 'Neisseria meningitidis antibody');
    static readonly NEISSERIA_MENINGITIDIS_ANTIBODY_IGG = new Test('T-57510', 'Neisseria meningitidis antibody, IgG');
    static readonly NEISSERIA_MENINGITIDIS_ANTIBODY_IGM = new Test('T-57515', 'Neisseria meningitidis antibody, IgM');
    static readonly NEISSERIA_MENINGITIDIS_ANTIGEN = new Test('T-57520', 'Neisseria meningitidis antigen');
    static readonly NEISSERIA_MENINGITIDIS_ANTIGEN_CSF = new Test('T-57525', 'Neisseria meningitidis antigen, CSF');
    static readonly NO_INFORMATION_GIVEN = new Test('T-57630', 'No Information Given');
    static readonly NORWALK_VIRUS_BY_MICROSCOPY = new Test('10696-3', 'Norwalk virus, by microscopy');
    static readonly OVA_AND_PARASITES_IDENTIFIED = new Test('T-57775', 'OVA AND PARASITES IDENTIFIED');
    static readonly ORIENTIA_TSUTSUGAMUSHI_RESULT = new Test('T-57715', 'Orientia tsutsugamushi - Result');
    static readonly OVA_AND_PARASITES_RESULT = new Test('T-57770', 'Ova and parasites - Result');
    static readonly PARAINFLUENZA_VIRUS_IDENTIFIED = new Test('T-57830', 'PARAINFLUENZA VIRUS IDENTIFIED');
    static readonly NEUMOCYSTIS_SMEAR = new Test('180232', 'PNEUMOCYSTIS SMEAR');
    static readonly PARASITE_IDENTIFICATION = new Test('T-57835', 'Parasite Identification');
    static readonly PARVOVIRUS_RESULT = new Test('T-57870', 'Parvovirus - Result');
    static readonly PLASMODIUM_RESULT = new Test('T-58040', 'Plasmodium - Result');
    static readonly PLASMODIUM_FALCIPARUM_RESULT = new Test('T-58050', 'Plasmodium falciparum - Result');
    static readonly PLASMODIUM_MALARIAE_RESULT = new Test('T-58080', 'Plasmodium malariae - Result');
    static readonly PLASMODIUM_OVALE_RESULT = new Test('T-58105', 'Plasmodium ovale - Result');
    static readonly PLASMODIUM_VIVAX_RESULT = new Test('T-58130', 'Plasmodium vivax - Result');
    static readonly PLATELET_COUNT = new Test('T-58155', 'Platelet Count');
    static readonly PNEUMOCYSTIS_RESULT = new Test('T-58175', 'Pneumocystis - Result');
    static readonly PNEUMOCYSTIS_SMEAR = new Test('T-58205', 'Pneumocystis Smear');
    static readonly POLIO_VIRUS_RESULT = new Test('T-58210', 'Polio virus - Result');
    static readonly POLIO_VIRUS_ANTIBODY = new Test('T-58215', 'Polio virus antibody');
    static readonly POLIO_VIRUS_ANTIBODY_CSF = new Test('T-58220', 'Polio virus antibody, CSF');
    static readonly POLIO_VIRUS_ANTIBODY_IGG = new Test('T-58225', 'Polio virus antibody, IgG');
    static readonly POLIO_VIRUS_ANTIBODY_IGM = new Test('T-58230', 'Polio virus antibody, IgM');
    static readonly POWASSAN_VIRUS_RESULT = new Test('T-58280', 'Powassan virus - Result');
    static readonly POWASSAN_VIRUS_ANTIBODY = new Test('T-58285', 'Powassan virus antibody');
    static readonly POWASSAN_VIRUS_ANTIBODY_IGG = new Test('T-58290', 'Powassan virus antibody, IgG');
    static readonly POWASSAN_VIRUS_ANTIBODY_IGM = new Test('T-58295', 'Powassan virus antibody, IgM');
    static readonly RESPIRATORY_SYNCYTIAL_VIRUS_IDENTIFIED = new Test(
        'T-58585',
        'RESPIRATORY SYNCYTIAL VIRUS IDENTIFIED'
    );
    static readonly RICKETTSIA_RICKETTSII_IDENTIFIED = new Test('T-58765', 'RICKETTSIA RICKETTSII IDENTIFIED');
    static readonly RICKETTSIA_SP_IDENTIFIED = new Test('T-58775', 'RICKETTSIA SP IDENTIFIED');
    static readonly ROTAVIRUS_IDENTIFIED = new Test('T-58930', 'ROTAVIRUS IDENTIFIED');
    static readonly RPR_SCREEN = new Test('T-58950', 'RPR Screen');
    static readonly RPR_TITER = new Test('T-58955', 'RPR Titer');
    static readonly RABIES_VIRUS_RESULT = new Test('T-58495', 'Rabies virus - Result');
    static readonly RABIES_VIRUS_ANTIBODY = new Test('T-58500', 'Rabies virus antibody');
    static readonly RABIES_VIRUS_ANTIBODY_CSF = new Test('T-58505', 'Rabies virus antibody, CSF');
    static readonly RABIES_VIRUS_ANTIBODY_IGG = new Test('T-58510', 'Rabies virus antibody, IgG');
    static readonly RABIES_VIRUS_ANTIBODY_IGM = new Test('T-58515', 'Rabies virus antibody, IgM');
    static readonly RED_BLOOD_CELL_RBC_COUNT = new Test('T-58555', 'Red Blood Cell (RBC) Count');
    static readonly RESULT_1 = new Test('T-58590', 'Result 1');
    static readonly RESULT_2 = new Test('T-58595', 'Result 2');
    static readonly RESULT_3 = new Test('T-58600', 'Result 3');
    static readonly RESULT_4 = new Test('T-58605', 'Result 4');
    static readonly RESULT_5 = new Test('T-58610', 'Result 5');
    static readonly RESULT_6 = new Test('T-58615', 'Result 6');
    static readonly RESULT_FINDINGS = new Test('T-58620', 'Result/Findings');
    static readonly RICKETTSIA_RESULT = new Test('T-58665', 'Rickettsia - Result');
    static readonly RICKETTSIA_ANTIBODY = new Test('T-58690', 'Rickettsia antibody');
    static readonly RICKETTSIA_ANTIBODY_IGG = new Test('T-58695', 'Rickettsia antibody, IgG');
    static readonly RICKETTSIA_ANTIBODY_IGM = new Test('T-58700', 'Rickettsia antibody, IgM');
    static readonly RICKETTSIA_PROWAZEKII_RESULT = new Test('T-58705', 'Rickettsia prowazekii - Result');
    static readonly RICKETTSIA_RICKETTSII_RESULT = new Test('T-58735', 'Rickettsia rickettsii - Result');
    static readonly RICKETTSIA_RICKETTSII_RNA = new Test('T-58770', 'Rickettsia rickettsii RNA');
    static readonly RICKETTSIA_RICKETTSII_ANTIBODY = new Test('T-58740', 'Rickettsia rickettsii antibody');
    static readonly RICKETTSIA_RICKETTSII_ANTIBODY_CSF = new Test('T-58745', 'Rickettsia rickettsii antibody, CSF');
    static readonly RICKETTSIA_RICKETTSII_ANTIBODY_IGG = new Test('T-58750', 'Rickettsia rickettsii antibody, IgG');
    static readonly RICKETTSIA_RICKETTSII_ANTIBODY_IGM = new Test('T-58755', 'Rickettsia rickettsii antibody, IgM');
    static readonly RICKETTSIA_RICKETTSII_ANTIGEN = new Test('T-58760', 'Rickettsia rickettsii antigen');
    static readonly RICKETTSIA_SPOTTED_FEVER_GROUP_RESULT = new Test(
        'T-58780',
        'Rickettsia spotted fever group - Result'
    );
    static readonly RICKETTSIA_TYPHI_RESULT = new Test('T-58810', 'Rickettsia typhi - Result');
    static readonly ROTAVIRUS_BY_MICROSCOPY = new Test('10714-4', 'Rotavirus, by microscopy');
    static readonly RUBELLA_VIRUS_RESULT = new Test('T-58960', 'Rubella virus - Result');
    static readonly RUBELLA_VIRUS_ANTIBODIES_IGG = new Test('T-58970', 'Rubella virus Antibodies, IgG');
    static readonly RUBELLA_VIRUS_ANTIBODIES_IGM = new Test('T-58975', 'Rubella virus Antibodies, IgM');
    static readonly RUBELLA_VIRUS_ANTIBODIES = new Test('T-58965', 'Rubella virus antibodies');
    static readonly SALMONELLA_SP_IDENTIFIED = new Test('T-59085', 'SALMONELLA SP IDENTIFIED');
    static readonly SHIGELLA_SP_IDENTIFIED = new Test('T-59265', 'SHIGELLA SP IDENTIFIED');
    static readonly STAPHYLOCOCCUS_AUREUS_IDENTIFIED = new Test('T-59380', 'STAPHYLOCOCCUS AUREUS IDENTIFIED');
    static readonly STREPTOCOCCUS_GROUP_A_STREPTOCOCCUS_PYOGENES_IDE = new Test(
        'T-59450',
        'STREPTOCOCCUS GROUP A (STREPTOCOCCUS PYOGENES) IDE'
    );
    static readonly STREPTOCOCCUS_GROUP_B_STREPTOCOCCUS_AGALACTIAE_I = new Test(
        'T-59455',
        'STREPTOCOCCUS GROUP B (STREPTOCOCCUS AGALACTIAE) I'
    );
    static readonly STREPTOCOCCUS_IDENTIFIED = new Test('T-59460', 'STREPTOCOCCUS IDENTIFIED');
    static readonly STREPTOCOCCUS_PNEUMONIAE_IDENTIFIED = new Test('T-59495', 'STREPTOCOCCUS PNEUMONIAE IDENTIFIED');
    static readonly STREPTOCOCCUS_BETA_HEMOLYTIC_IDENTIFIED = new Test(
        'T-59540',
        'STREPTOCOCCUS, BETA-HEMOLYTIC IDENTIFIED'
    );
    static readonly SAINT_LOUIS_ENCEPHALITIS_VIRUS_RESULT = new Test(
        'T-59000',
        'Saint Louis encephalitis virus - Result'
    );
    static readonly SAINT_LOUIS_ENCEPHALITIS_VIRUS_ANTIBODY = new Test(
        'T-59005',
        'Saint Louis encephalitis virus antibody'
    );
    static readonly SAINT_LOUIS_ENCEPHALITIS_VIRUS_ANTIBODY_IGG = new Test(
        'T-59010',
        'Saint Louis encephalitis virus antibody, IgG'
    );
    static readonly SAINT_LOUIS_ENCEPHALITIS_VIRUS_ANTIBODY_IGM = new Test(
        'T-59015',
        'Saint Louis encephalitis virus antibody, IgM'
    );
    static readonly SAINT_LOUIS_ENCEPHALITIS_VIRUS_ANTIGEN = new Test(
        'T-59020',
        'Saint Louis encephalitis virus antigen'
    );
    static readonly SALMONELLA_RESULT = new Test('T-59040', 'Salmonella - Result');
    static readonly SALMONELLA_ANTIBODY = new Test('T-59045', 'Salmonella antibody');
    static readonly SALMONELLA_ANTIBODY_IGG = new Test('T-59050', 'Salmonella antibody, IgG');
    static readonly SALMONELLA_ANTIBODY_IGM = new Test('T-59055', 'Salmonella antibody, IgM');
    static readonly SALMONELLA_ENTERITIDIS_RESULT = new Test('T-59065', 'Salmonella enteritidis - Result');
    static readonly SALMONELLA_GALLINARUM_RESULT = new Test('T-59070', 'Salmonella gallinarum - Result');
    static readonly SALMONELLA_PARATYPHI_RESULT = new Test('T-59075', 'Salmonella paratyphi - Result');
    static readonly SALMONELLA_PULLORUM_RESULT = new Test('T-59080', 'Salmonella pullorum - Result');
    static readonly SALMONELLA_SEROTYPE_BY_AGGLUTINATION = new Test('20951-0', 'Salmonella serotype, by agglutination');
    static readonly SALMONELLA_SP_IDENTIFIED_FROM_SPECIMEN_OTHER_THAN_STOOL_AND_TISSUE = new Test(
        '20954-4',
        'Salmonella sp identified, from specimen other than stool and tissue'
    );
    static readonly SALMONELLA_SP_IDENTIFIED_FROM_TISSUE = new Test('20953-6', 'Salmonella sp identified, from tissue');
    static readonly SALMONELLA_SPECIES_NOS_IN_STOOL_CULTURE = new Test(
        '20955-1',
        'Salmonella species NOS, in stool culture'
    );
    static readonly SALMONELLA_SPECIES_SEROTYPE_RESULT = new Test('T-59090', 'Salmonella species serotype - Result');
    static readonly SALMONELLA_SPECIES_SEROTYPE_IDENTIFICATION = new Test(
        'T-59095',
        'Salmonella species serotype identification'
    );
    static readonly SALMONELLA_TYPHI_RESULT = new Test('T-59100', 'Salmonella typhi - Result');
    static readonly SALMONELLA_TYPHI_ANTIBODY = new Test('T-59105', 'Salmonella typhi antibody');
    static readonly SALMONELLA_TYPHI_ANTIBODY_IGG = new Test('T-59110', 'Salmonella typhi antibody, IgG');
    static readonly SALMONELLA_TYPHI_ANTIBODY_IGM = new Test('T-59115', 'Salmonella typhi antibody, IgM');
    static readonly SALMONELLA_TYPHIMURIUM_RESULT = new Test('T-59125', 'Salmonella typhimurium - Result');
    static readonly SHIGELLA_RESULT = new Test('T-59185', 'Shigella - Result');
    static readonly SHIGELLA_SEROGROUP_SEROTYPE_IDENTIFICATION = new Test(
        'T-LNX015',
        'Shigella serogroup/serotype identification'
    );
    static readonly SNOWSHOE_HARE_VIRUS_RESULT = new Test('T-59300', 'Snowshoe hare virus - Result');
    static readonly SNOWSHOE_HARE_VIRUS_ANTIBODY = new Test('T-59305', 'Snowshoe hare virus antibody');
    static readonly SNOWSHOE_HARE_VIRUS_ANTIBODY_IGG = new Test('T-59310', 'Snowshoe hare virus antibody, IgG');
    static readonly SNOWSHOE_HARE_VIRUS_ANTIBODY_IGM = new Test('T-59315', 'Snowshoe hare virus antibody, IgM');
    static readonly ST_LOUIS_ENCEPHALITIS_VIRUS_ANTIBODIES_IGG = new Test(
        'T-59360',
        'St Louis Encephalitis Virus Antibodies, IgG'
    );
    static readonly ST_LOUIS_ENCEPHALITIS_VIRUS_ANTIBODIES_IGM = new Test(
        'T-59365',
        'St Louis Encephalitis Virus Antibodies, IgM'
    );
    static readonly STAPHYLOCOCCUS_AUREUS_RESULT = new Test('T-59370', 'Staphylococcus aureus - Result');
    static readonly STAPHYLOCOCCUS_AUREUS_METHICILLIN_RESISTANT_MRSA = new Test(
        'T-59395',
        'Staphylococcus aureus, methicillin resistant (MRSA'
    );
    static readonly STREPTOCOCCUS_RESULT = new Test('T-59405', 'Streptococcus - Result');
    static readonly STREPTOCOCCUS_AGALACTIAE_RESULT = new Test('T-59410', 'Streptococcus agalactiae - Result');
    static readonly STREPTOCOCCUS_PNEUMONIAE_RESULT = new Test('T-59465', 'Streptococcus pneumoniae - Result');
    static readonly STREPTOCOCCUS_PNEUMONIAE_ANTIBODY_IGM = new Test(
        'T-59480',
        'Streptococcus pneumoniae antibody, IgM'
    );
    static readonly STREPTOCOCCUS_PNEUMONIAE_ANTIGEN_CSF = new Test('T-59490', 'Streptococcus pneumoniae antigen, CSF');
    static readonly STREPTOCOCCUS_PYOGENES_RESULT = new Test('T-59505', 'Streptococcus pyogenes - Result');
    static readonly SYPHILIS_SEROLOGY = new Test('T-59625', 'Syphilis serology');
    static readonly TAENIA_RESULT = new Test('T-59650', 'Taenia - Result');
    static readonly EST_LAB_FOR_VALIDATION = new Test('XYZ123', 'Test Lab for Validation');
    static readonly TOXOCARA_RESULT = new Test('T-59880', 'Toxocara - Result');
    static readonly TOXOPLASMA_RESULT = new Test('T-59915', 'Toxoplasma - Result');
    static readonly TOXOPLASMA_GONDII_RESULT = new Test('T-59940', 'Toxoplasma gondii - Result');
}
