## Created by jamesbarrows at 5/19/23
@patient_entry_drop_downs
Feature: Dropdown element retrieval for patient data entry
  AS A Data Entry user
  I WANT TO Be able to see dropdown elements on the data entry modals
  SO THAT I CAN use those options to complete the patient profile details.

  Background:
    Given A user exists
    And I have authenticated as a user
    And I have the authorities: "ADD-PATIENT" for the jurisdiction: "ALL" and program area: "STD"

  Scenario: I can retrieve Name Suffixes
    When I want to retrieve "Name Suffix"
    Then I get these key-value pairs:
      | Key | Value           |
      | ESQ | Esquire         |
      | II  | II / The Second |
      | III | III / The Third |
      | IV  | IV / The Fourth |
      | JR  | Jr.             |
      | SR  | Sr.             |
      | V   | V / The Fifth   |

  Scenario: I can retrieve Genders
    When I want to retrieve "Gender"
    Then I get these key-value pairs:
      | Key | Value   |
      | M   | Male    |
      | F   | Female  |
      | U   | Unknown |

  Scenario: I can retrieve Yes/No/Unknown
    When I want to retrieve "Yes/No/Unknown"
    Then I get these key-value pairs:
      | Key     | Value   |
      | YES     | Yes     |
      | NO      | No      |
      | UNKNOWN | Unknown |

  Scenario: I can retrieve Name Type
    When I want to retrieve "Name Type"
    Then I get these key-value pairs:
      | Key | Value                  |
      | A   | Artist/Stage Name      |
      | AD  | Adopted Name           |
      | AL  | Alias Name             |
      | BR  | Name at Birth          |
      | C   | License                |
      | I   | Indigenous/Tribal      |
      | L   | Legal                  |
      | M   | Maiden Name            |
      | MO  | Mother's Name          |
      | P   | Name of Partner/Spouse |
      | R   | Religious             |
      | S   | Coded Pseudo           |

  Scenario: I can retrieve Name Prefix
    When I want to retrieve "Name Prefix"
    Then I get these key-value pairs:
      | Key  | Value      |
      | BRO  | Brother    |
      | BSHP | Bishop     |
      | CARD | Cardinal   |
      | DR   | Doctor/Dr. |
      | FATH | Father     |
      | HON  | Honorable  |
      | MISS | Miss       |
      | MON  | Monsignor  |
      | MOTH | Mother     |
      | MR   | Mr.        |
      | MRS  | Mrs.       |
      | MS   | Ms.        |
      | PAST | Pastor     |
      | PROF | Professor  |
      | RAB  | Rabbi      |
      | REV  | Reverend   |
      | SIS  | Sister     |
      | SWM  | Swami      |


  Scenario: I can retrieve Degree
    When I want to retrieve "Degree"
    Then I get these key-value pairs:
      | Key | Value |
      | DO  | DO    |
      | DRN | DRN   |
      | DVM | DVM   |
      | JD  | JD    |
      | LLB | LLB   |
      | LPN | LPN   |
      | MA  | MA    |
      | MBA | MBA   |
      | MD  | MD    |
      | MED | MED   |
      | MPH | MPH   |
      | MS  | MS    |
      | MSN | MSN   |
      | NP  | NP    |
      | PA  | PA    |
      | PHD | PhD   |
      | RN  | RN    |

  Scenario: I can retrieve Address Types
    When I want to retrieve "Address Type"
    Then I get these key-value pairs:
      | Key  | Value                                        |
      | O    | Office                                       |
      | PF   | Prison - Federal                             |
      | PLOC | Jail                                         |
      | PNS  | Prison/Correctional Facility - not specified |
      | PS   | Prison - State                               |
      | RH   | Registry Home                                |
      | RTF  | Residential treatment facility               |
      | SHLT | Shelter - unspecified                        |
      | T    | Transitional Facility                        |
      | U    | Unknown                                      |

  Scenario: I can retrieve Marital Status
    When I want to retrieve "Marital Status"
    Then I get these key-value pairs:
      | Key  | Value                                     |
      | A    | Annulled                                  |
      | B    | Unmarried                                 |
      | C    | Common Law                                |
      | D    | Divorced                                  |
      | E    | Separated                                 |
      | F    | Unreported                                |
      | G    | Living Together                           |
      | I    | Interlocutory                             |
      | L    | Legally separated                         |
      | M    | Married                                   |
      | O    | Other                                     |
      | P    | Polygamous                                |
      | R    | Refused to answer                         |
      | S    | Single, never married                     |
      | T    | Domestic partner                          |
      | U    | Unknown                                   |
      | W    | Widowed                                   |

  Scenario: I can retrieve Primary Occupation
    When I want to retrieve "Primary Occupation"
    Then I get these key-value pairs:
      | Key | Value                                                                |
      |0010 | Chief executives                                                     |
      |0020 | General and operations managers                                      |
      |0030 | Legislators                                                          |
      |0040 | Advertising and promotions managers                                  |
      |0050 | Marketing and sales managers                                         |
      |0060 | Public relations and fundraising managers                            |
      |0100 | Administrative services managers                                     |
      |0110 | Computer and information systems managers                            |
      |0120 | Financial managers                                                   |
      |0135 | Compensation and benefits managers                                   |
      |0136 | Human resources managers                                             |
      |0137 | Training and development managers                                    |
      |0140 | Industrial production managers                                       |
      |0150 | Purchasing managers                                                  |
      |0160 | Transportation, storage, and distribution managers                   |
      |0205 | Farmers, ranchers, and other agricultural managers                   |
      |0220 | Construction managers                                                |
      |0230 | Education administrators                                             |
      |0300 | Architectural and engineering managers                               |
      |0310 | Food service managers                                                |
      |0325 | Funeral service managers                                             |
      |0330 | Gaming managers                                                      |
      |0340 | Lodging managers                                                     |
      |0350 | Medical and health services managers                                 |
      |0360 | Natural sciences managers                                            |
      |0400 | Postmasters and mail superintendents                                 |
      |0410 | Property, real estate, and community association managers            |
      |0420 | Social and community service managers                                |
      |0425 | Emergency management directors                                       |
      |0430 | Managers, all other                                                  |
      |0500 | Agents and business managers of artists, performers, and athletes    |
      |0510 | Buyers and purchasing agents, farm products                          |
      |0520 | Wholesale and retail buyers, except farm products                    |
      |0530 | Purchasing agents, except wholesale, retail, and farm products       |
      |0540 | Claims adjusters, appraisers, examiners, and investigators           |
      |0565 | Compliance officers                                                  |
      |0600 | Cost estimators                                                      |
      |0630 | Human resources workers                                              |
      |0640 | Compensation, benefits, and job analysis specialists                 |
      |0650 | Training and development specialists                                 |
      |0700 | Logisticians                                                         |
      |0710 | Management analysts                                                  |
      |0725 | Meeting, convention, and event planners                              |
      |0726 | Fundraisers                                                          |
      |0735 | Market research analysts and marketing specialists                   |
      |0740 | Business operations specialists, all other                           |
      |0800 | Accountants and auditors                                             |
      |0810 | Appraisers and assessors of real estate                              |
      |0820 | Budget analysts                                                      |
      |0830 | Credit analysts                                                      |
      |0840 | Financial analysts                                                   |
      |0850 | Personal financial advisors                                          |
      |0860 | Insurance underwriters                                               |
      |0900 | Financial examiners                                                  |
      |0910 | Credit counselors and loan officers                                  |
      |0930 | Tax examiners and collectors, and revenue agents                     |
      |0940 | Tax preparers                                                        |
      |0950 | Financial specialists, all other                                     |
      |1005 | Computer and information research scientists                         |
      |1006 | Computer systems analysts                                            |
      |1007 | Information security analysts                                        |
      |1010 | Computer programmers                                                 |
      |1020 | Software developers, applications and systems software               |
      |1030 | Web developers                                                       |
      |1050 | Computer support specialists                                         |
      |1060 | Database administrators                                              |
      |1105 | Network and computer systems administrators                          |
      |1106 | Computer network architects                                          |
      |1107 | Computer occupations, all other                                      |
      |1200 | Actuaries                                                            |
      |1210 | Mathematicians                                                       |
      |1220 | Operations research analysts                                         |
      |1230 | Statisticians                                                        |
      |1240 | Miscellaneous mathematical science occupations                       |
      |1300 | Architects, except naval                                             |
      |1310 | Surveyors, cartographers, and photogrammetrists                      |
      |1320 | Aerospace engineers                                                  |
      |1330 | Agricultural engineers                                               |
      |1340 | Biomedical engineers                                                 |
      |1350 | Chemical engineers                                                   |
      |1360 | Civil engineers                                                      |
      |1400 | Computer hardware engineers                                          |
      |1410 | Electrical and electronics engineers                                 |
      |1420 | Environmental engineers                                              |
      |1430 | Industrial engineers, including health and safety                    |
      |1440 | Marine engineers and naval architects                                |
      |1450 | Materials engineers                                                  |
      |1460 | Mechanical engineers                                                 |
      |1500 | Mining and geological engineers, including mining safety engineers   |
      |1510 | Nuclear engineers                                                    |
      |1520 | Petroleum engineers                                                  |
      |1530 | Engineers, all other                                                 |
      |1540 | Drafters                                                             |
      |1550 | Engineering technicians, except drafters                             |
      |1560 | Surveying and mapping technicians                                    |
      |1600 | Agricultural and food scientists                                     |
      |1610 | Biological scientists                                                |
      |1640 | Conservation scientists and foresters                                |
      |1650 | Medical scientists                                                   |
      |1660 | Life scientists, all other                                           |
      |1700 | Astronomers and physicists                                           |
      |1710 | Atmospheric and space scientists                                     |
      |1720 | Chemists and materials scientists                                    |
      |1740 | Environmental scientists and geoscientists                           |
      |1760 | Physical scientists, all other                                       |
      |1800 | Economists                                                           |
      |1815 | Survey researchers                                                   |
      |1820 | Psychologists                                                        |
      |1830 | Sociologists                                                         |
      |1840 | Urban and regional planners                                          |
      |1860 | Miscellaneous social scientists and related workers                  |
      |1900 | Agricultural and food science technicians                            |
      |1910 | Biological technicians                                               |
      |1920 | Chemical technicians                                                 |
      |1930 | Geological and petroleum technicians                                 |
      |1940 | Nuclear technicians                                                  |
      |1950 | Social science research assistants                                   |
      |1965 | Miscellaneous life, physical, and social science technicians         |
      |2000 | Counselors                                                           |
      |2010 | Social workers                                                       |
      |2015 | Probation officers and correctional treatment specialists            |
      |2016 | Social and human service assistants                                  |
      |2025 | Miscellaneous community and social service specialists, including health educators and community health workers |
      |2040 | Clergy                                                               |
      |2050 | Directors, religious activities and education                        |
      |2060 | Religious workers, all other                                         |
      |2100 | Lawyers                                                              |
      |2105 | Judicial law clerks                                                  |
      |2110 | Judges, magistrates, and other judicial workers                      |
      |2145 | Paralegals and legal assistants                                      |
      |2160 | Miscellaneous legal support workers                                  |
      |2200 | Postsecondary teachers                                               |
      |2300 | Preschool and kindergarten teachers                                  |
      |2310 | Elementary and middle school teachers                                |
      |2320 | Secondary school teachers                                            |
      |2330 | Special education teachers                                           |
      |2340 | Other teachers and instructors                                       |
      |2400 | Archivists, curators, and museum technicians                         |
      |2430 | Librarians                                                           |
      |2440 | Library technicians                                                  |
      |2540 | Teacher assistants                                                   |
      |2550 | Other education, training, and library workers                       |
      |2600 | Artists and related workers                                          |
      |2630 | Designers                                                            |
      |2700 | Actors                                                               |
      |2710 | Producers and directors                                              |
      |2720 | Athletes, coaches, umpires, and related workers                      |
      |2740 | Dancers and choreographers                                           |
      |2750 | Musicians, singers, and related workers                              |
      |2760 | Entertainers and performers, sports and related workers, all other   |
      |2800 | Announcers                                                           |
      |2810 | News analysts, reporters and correspondents                          |
      |2825 | Public relations specialists                                         |
      |2830 | Editors                                                              |
      |2840 | Technical writers                                                    |
      |2850 | Writers and authors                                                  |
      |2860 | Miscellaneous media and communication workers                        |
      |2900 | Broadcast and sound engineering technicians and radio operators      |
      |2910 | Photographers                                                        |
      |2920 | Television, video, and motion picture camera operators and editors   |
      |2960 | Media and communication equipment workers, all other                 |
      |3000 | Chiropractors                                                        |
      |3010 | Dentists                                                             |
      |3030 | Dietitians and nutritionists                                         |
      |3040 | Optometrists                                                         |
      |3050 | Pharmacists                                                          |
      |3060 | Physicians and surgeons                                              |
      |3110 | Physician assistants                                                 |
      |3120 | Podiatrists                                                          |
      |3140 | Audiologists                                                         |
      |3150 | Occupational therapists                                              |
      |3160 | Physical therapists                                                  |
      |3200 | Radiation therapists                                                 |
      |3210 | Recreational therapists                                              |
      |3220 | Respiratory therapists                                               |
      |3230 | Speech-language pathologists                                         |
      |3235 | Exercise physiologists                                               |
      |3245 | Therapists, all other                                                |
      |3250 | Veterinarians                                                        |
      |3255 | Registered nurses                                                    |
      |3256 | Nurse anesthetists                                                   |
      |3257 | Nurse midwives                                                       |
      |3258 | Nurse practitioners                                                  |
      |3260 | Health diagnosing and treating practitioners, all other              |
      |3300 | Clinical laboratory technologists and technicians                    |
      |3310 | Dental hygienists                                                    |
      |3320 | Diagnostic related technologists and technicians                     |
      |3400 | Emergency medical technicians and paramedics                         |
      |3420 | Health practitioner support technologists and technicians            |
      |3500 | Licensed practical and licensed vocational nurses                    |
      |3510 | Medical records and health information technicians                   |
      |3520 | Opticians, dispensing                                                |
      |3535 | Miscellaneous health technologists and technicians                   |
      |3540 | Other healthcare practitioners and technical occupations             |
      |3600 | Nursing, psychiatric, and home health aides                          |
      |3610 | Occupational therapy assistants and aides                            |
      |3620 | Physical therapist assistants and aides                              |
      |3630 | Massage therapists                                                   |
      |3640 | Dental assistants                                                    |
      |3645 | Medical assistants                                                   |

  Scenario: I can retrieve Highest Level Of Education
    When I want to retrieve "Highest Level Of Education"
    Then I get these key-value pairs:
      | Key    | Value                                                                         |
      |0       | No schooling completed                                                        |
      |1       | Nursery school                                                                |
      |10      | 10th grade                                                                    |
      |11      | 11th grade                                                                    |
      |12      | 12th grade, no diploma                                                        |
      |13      | Some college credit, but less than 1 year                                     |
      |16      | 1 or more years of college, no degree                                         |
      |4       | 1st, 2nd, 3rd, or 4th grade                                                   |
      |7       | 5th or 6th grade                                                              |
      |8       | 7th or 8th grade                                                              |
      |9       | 9th grade                                                                     |
      |AD      | Associate Degree, Academic Program                                            |
      |BD      | Bachelor's Degree                                                             |
      |DD      | Doctoral Degree                                                               |
      |GD      | GED - General Education Diploma                                               |
      |HD      | High School Graduate                                                          |
      |HS      | Some high school, degree status unknown                                       |
      |K       | Kindergarten                                                                  |
      |MD      | Master's Degree                                                               |
      |OV      | Occupational/Vocational degree                                                |
      |PD      | Professional Degree                                                           |
      |PK      | Pre-kindergarten - Includes nursery school, Pre-kindergarten, Head Start, etc |

  Scenario: I can retrieve Primary Language
    When I want to retrieve "Primary Language"
    Then I get these key-value pairs:
      | Key    | Value                                              |
      |AAR     | Afar                                               |
      |ABK     | Abkhazian                                          |
      |ACE     | Achinese                                           |
      |ACH     | Acoli                                              |
      |ADA     | Adangme                                            |
      |ADY     | Adyghe; Adygei                                     |
      |AFA     | Afro-Asiatic (Other)                               |
      |AFH     | Afrihili                                           |
      |AFR     | Afrikaans                                          |
      |AIN     | Ainu                                               |
      |AKA     | Akan                                               |
      |AKK     | Akkadian                                           |
      |ALB/SQI | Albanian                                           |
      |ALE     | Aleut                                              |
      |ALG     | Algonquian languages                               |
      |ALT     | Southern Altai                                     |
      |AMH     | Amharic                                            |
      |ANG     | English, Old (ca.450-1100)                         |
      |ANP     | Angika                                             |
      |APA     | Apache languages                                   |
      |ARA     | Arabic                                             |
      |ARC     | Aramaic                                            |
      |ARG     | Aragonese                                          |
      |ARM/HYE | Armenian                                           |
      |ARN     | Araucanian                                         |
      |ARP     | Arapaho                                            |
      |ARW     | Arawak                                             |
      |ASM     | Assamese                                           |
      |AST     | Asturian; Bable; Leonese; Asturleonese             |
      |ATH     | Athapascan languages                               |
      |AUS     | Australian languages                               |
      |AVA     | Avaric                                             |
      |AVE     | Avestan                                            |
      |AWA     | Awadhi                                             |
      |AYM     | Aymara                                             |
      |AZE     | Azerbaijani                                        |
      |BAD     | Banda                                              |
      |BAI     | Bamileke languages                                 |
      |BAK     | Bashkir                                            |
      |BAL     | Baluchi                                            |
      |BAM     | Bambara                                            |
      |BAN     | Balinese                                           |
      |BAQ/EUS | Basque                                             |
      |BAS     | Basa                                               |
      |BAT     | Baltic (Other)                                     |
      |BEJ     | Beja                                               |
      |BEL     | Belarusian                                         |
      |BEM     | Bemba                                              |
      |BEN     | Bengali                                            |
      |BER     | Berber (Other)                                     |
      |BHO     | Bhojpuri                                           |
      |BIH     | Bihari                                             |
      |BIK     | Bikol                                              |
      |BIN     | Bini                                               |
      |BIS     | Bislama                                            |
      |BLA     | Siksika                                            |
      |BNT     | Bantu (Other)                                      |
      |BOS     | Bosnian                                            |
      |BRA     | Braj                                               |
      |BRE     | Breton                                             |
      |BTK     | Batak (Indonesia)                                  |
      |BUA     | Buriat                                             |
      |BUG     | Buginese                                           |
      |BUL     | Bulgarian                                          |
      |BUR/MYA | Burmese                                            |
      |BYN     | Blin; Bilin                                        |
      |CAD     | Caddo                                              |
      |CAI     | Central American Indian (Other)                    |
      |CAR     | Carib                                              |
      |CAT     | Catalan                                            |
      |CAU     | Caucasian (Other)                                  |
      |CEB     | Cebuano                                            |
      |CEL     | Celtic (Other)                                     |
      |CH      | Chinese                                            |
      |CHA     | Chamorro                                           |
      |CHB     | Chibcha                                            |
      |CHE     | Chechen                                            |
      |CHG     | Chagatai                                           |
      |CHI/ZHO | Chinese                                            |
      |CHK     | Chuukese                                           |
      |CHM     | Mari                                               |
      |CHN     | Chinook jargon                                     |
      |CHO     | Choctaw                                            |
      |CHP     | Chipewyan                                          |
      |CHR     | Cherokee                                           |
      |CHU     | Church Slavic                                      |
      |CHV     | Chuvash                                            |
      |CHY     | Cheyenne                                           |
      |CMC     | Chamic languages                                   |
      |COP     | Coptic                                             |
      |COR     | Cornish                                            |
      |COS     | Corsican                                           |
      |CPE     | Creoles and pidgins, English-based (Other)         |
      |CPF     | Creoles and pidgins, French-based (Other)          |
      |CPP     | Creoles and pidgins, Portuguese-based (Other)      |
      |CRE     | Cree                                               |
      |CRH     | Crimean Tatar; Crimean Turkish                     |
      |CRP     | Creoles and pidgins(Other)                         |
      |CSB     | Kashubian                                          |
      |CUS     | Cushitic (Other)                                   |
      |CZE/CES | Czech                                              |
      |DAK     | Dakota                                             |
      |DAN     | Danish                                             |
      |DAR     | Dargwa                                             |
      |DAY     | Dayak                                              |
      |DEL     | Delaware                                           |
      |DEN     | Slave (Athapascan)                                 |
      |DGR     | Dogrib                                             |
      |DIN     | Dinka                                              |
      |DIV     | Divehi                                             |
      |DOI     | Dogri                                              |
      |DRA     | Dravidian (Other)                                  |
      |DSB     | Lower Sorbian                                      |
      |DUA     | Duala                                              |
      |DUM     | Dutch, Middle (ca. 1050-1350)                      |
      |DUT/NLD | Dutch                                              |
      |DYU     | Dyula                                              |
      |DZO     | Dzongkha                                           |
      |EFI     | Efik                                               |
      |EGY     | Egyptian (Ancient)                                 |
      |EKA     | Ekajuk                                             |
      |ELX     | Elamite                                            |
      |ENG     | English                                            |
      |ENM     | English, Middle (1100-1500)                        |
      |EPO     | Esperanto                                          |
      |EST     | Estonian                                           |
      |EWE     | Ewe                                                |
      |EWO     | Ewondo                                             |
      |FAN     | Fang                                               |
      |FAO     | Faroese                                            |
      |FAT     | Fanti                                              |
      |FIJ     | Fijian                                             |
      |FIL     | Filipino; Pilipino                                 |
      |FIN     | Finnish                                            |
      |FIU     | Finno-Ugrian (Other)                               |
      |FON     | Fon                                                |
      |FR      | French                                             |
      |FRE/FRA | French                                             |
      |FRM     | French, Middle (ca.1400-1600)                      |
      |FRO     | French, Old (842-ca.1400)                          |
      |FRR     | Northern Frisian                                   |
      |FRS     | Eastern Frisian                                    |
      |FRY     | Frisian                                            |
      |FUL     | Fulah                                              |
      |FUR     | Friulian                                           |
      |GAA     | Ga                                                 |
      |GAY     | Gayo                                               |
      |GBA     | Gbaya                                              |
      |GEM     | Germanic (Other)                                   |
      |GEO/KAT | Georgian                                           |
      |GER/DEU | German                                             |
      |GEZ     | Geez                                               |
      |GIL     | Gilbertese                                         |
      |GLA     | Gaelic                                             |
      |GLE     | Irish                                              |
      |GLG     | Gallegan                                           |
      |GLV     | Manx                                               |
      |GM      | German                                             |
      |GMH     | German, Middle High (ca.1050-1500)                 |
      |GOH     | German, Old High (ca.750-1050)                     |
      |GON     | Gondi                                              |
      |GOR     | Gorontalo                                          |
      |GOT     | Gothic                                             |
      |GRB     | Grebo                                              |
      |GRC     | Greek, Ancient (to 1453)                           |
      |GRE/ELL | Greek, Modern (1453-)                              |
      |GRN     | Guarani                                            |
      |GSW     | Swiss German; Alemannic; Alsatian                  |
      |GUJ     | Gujarati                                           |
      |GWI     | Gwich´in                                           |
      |HAI     | Haida                                              |
      |HAT     | Haitian; Haitian Creole                            |
      |HAU     | Hausa                                              |
      |HAW     | Hawaiian                                           |
      |HEB     | Hebrew                                             |
      |HER     | Herero                                             |
      |HIL     | Hiligaynon                                         |
      |HIM     | Himachali                                          |
      |HIN     | Hindi                                              |
      |HIT     | Hittite                                            |
      |HMN     | Hmong                                              |
      |HMO     | Hiri Motu                                          |
      |HSB     | Upper Sorbian                                      |
      |HUN     | Hungarian                                          |
      |HUP     | Hupa                                               |
      |IBA     | Iban                                               |
      |IBO     | Igbo                                               |
      |ICE/ISL | Icelandic                                          |
      |IDO     | Ido                                                |
      |III     | Sichuan Yi; Nuosu                                  |
      |IJO     | Ijo                                                |
      |IKU     | Inuktitut                                          |
      |ILE     | Interlingue                                        |
      |ILO     | Iloko                                              |
      |INA     | Interlingua (International Auxiliary Language Asso |
      |INC     | Indic (Other)                                      |
      |IND     | Indonesian                                         |
      |INE     | Indo-European (Other)                              |
      |INH     | Ingush                                             |
      |IPK     | Inupiaq                                            |
