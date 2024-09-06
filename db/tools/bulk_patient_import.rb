STARTING_PERSON_UID = 10_068_317 # select max(person_uid)+1 from person
STARTING_LOCATOR_UID = 10_090_360 # select max(locator_uid)+1 from Entity_locator_participation

ID_CSV = 'ids.csv' # input file with rows prefixed with sequential ids

# csv input format: Information as of Date,Comments,Last,First,Middle,Suffix,Date of birth,Current sex,Birth sex,Is this patient deceased?,Date of death,Marital Status,State HIV case ID,Street address 1,Street address 2,City,State,Zip,County,Census Tract,Country,Home phone,Work phone,Ext,Cell phone,Email,Ethnicity,Race,ID type,Assigning authority,ID value
class BulkPatientImport
  def initialize(db, user, pwd)
    @db = db
    @user = user
    @pwd = pwd
    @state_name_to_code = {}
    @county_name_to_code = {}
  end

  def main(main_csv)
    load_lookups
    create_ids_csv(main_csv)
    create_entity_csv
    create_person_csv
    create_person_name_csv
    create_entity_id_csv
    create_person_race_csv
    create_elp_csv
    %w[entity person person_name entity_id person_race entity_locator_participation tele_locator
       postal_locator].each do |table|
      bcp(table)
    end
  end

  def create_ids_csv(main_csv)
    id = STARTING_PERSON_UID
    file = File.open(ID_CSV, 'w')
    IO.foreach(main_csv).with_index do |line, index|
      next if index.zero?

      file.write("#{id},#{line.chomp}\n")
      id += 1
    end
    file.close
  end

  def create_entity_csv
    create_csv(output: 'entity', format: proc { |fields| "#{fields[0]},PSN" })
  end

  def create_person_csv
    # person_uid,add_reason_cd,add_time,add_user_id,administrative_gender_cd,age_calc,age_calc_time,age_calc_unit_cd,age_category_cd,age_reported,age_reported_time,age_reported_unit_cd,birth_gender_cd,birth_order_nbr,birth_time,birth_time_calc,cd,cd_desc_txt,curr_sex_cd,deceased_ind_cd,deceased_time,description,education_level_cd,education_level_desc_txt,ethnic_group_ind,last_chg_reason_cd,last_chg_time,last_chg_user_id,local_id,marital_status_cd,marital_status_desc_txt,mothers_maiden_nm,multiple_birth_ind,occupation_cd,preferred_gender_cd,prim_lang_cd,prim_lang_desc_txt,record_status_cd,record_status_time,status_cd,status_time,survived_ind_cd,user_affiliation_txt,first_nm,last_nm,middle_nm,nm_prefix,nm_suffix,preferred_nm,hm_street_addr1,hm_street_addr2,hm_city_cd,hm_city_desc_txt,hm_state_cd,hm_zip_cd,hm_cnty_cd,hm_cntry_cd,hm_phone_nbr,hm_phone_cntry_cd,hm_email_addr,cell_phone_nbr,wk_street_addr1,wk_street_addr2,wk_city_cd,wk_city_desc_txt,wk_state_cd,wk_zip_cd,wk_cnty_cd,wk_cntry_cd,wk_phone_nbr,wk_phone_cntry_cd,wk_email_addr,SSN,medicaid_num,dl_num,dl_state_cd,race_cd,race_seq_nbr,race_category_cd,ethnicity_group_cd,ethnic_group_seq_nbr,adults_in_house_nbr,children_in_house_nbr,birth_city_cd,birth_city_desc_txt,birth_cntry_cd,birth_state_cd,race_desc_txt,ethnic_group_desc_txt,version_ctrl_nbr,as_of_date_admin,as_of_date_ethnicity,as_of_date_general,as_of_date_morbidity,as_of_date_sex,electronic_ind,person_parent_uid,dedup_match_ind,group_nbr,group_time,edx_ind,speaks_english_cd,additional_gender_cd,ehars_id,ethnic_unk_reason_cd,sex_unk_reason_cd
    create_csv(output: 'person',
               format: proc { |fields|
                         "#{fields[0]},,,,,,,,,,,,#{gender_code(fields[9])},,#{fields[7]},,PAT,,#{gender_code(fields[8])},#{fields[10]},#{fields[11]},#{fields[2]},,,,,,,PSN1#{format(
                           '%07d', (fields[0].to_i % 10_000_000)
                         )}GA01,#{marital_status_code(fields[12])},,,,,,,,ACTIVE,,A,,,,#{fields[4]},#{fields[3]},#{fields[5]},,#{fields[6]},,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,1,,,#{fields[1]},,,,#{fields[0]},,,,,,,#{fields[13]},,"
                       })
  end

  def create_person_name_csv
    # person_uid,person_name_seq,add_reason_cd,add_time,add_user_id,default_nm_ind,duration_amt,duration_unit_cd,first_nm,first_nm_sndx,from_time,last_chg_reason_cd,last_chg_time,last_chg_user_id,last_nm,last_nm_sndx,last_nm2,last_nm2_sndx,middle_nm,middle_nm2,nm_degree,nm_prefix,nm_suffix,nm_use_cd,record_status_cd,record_status_time,status_cd,status_time,to_time,user_affiliation_txt,as_of_date
    create_csv(output: 'person_name',
               format: proc { |fields|
                         "#{fields[0]},1,,,,,,,#{fields[4]},,,,,,#{fields[3]},,,,#{fields[5]},,,,#{fields[6]},L,ACTIVE,,A,#{fields[1]},,,"
                       })
  end

  def create_entity_id_csv
    # entity_uid,entity_id_seq,add_reason_cd,add_time,add_user_id,assigning_authority_cd,assigning_authority_desc_txt,duration_amt,duration_unit_cd,effective_from_time,effective_to_time,last_chg_reason_cd,last_chg_time,last_chg_user_id,record_status_cd,record_status_time,root_extension_txt,status_cd,status_time,type_cd,type_desc_txt,user_affiliation_txt,valid_from_time,valid_to_time,as_of_date,assigning_authority_id_type
    create_csv(output: 'entity_id',
               format: proc { |fields|
                         "#{fields[0]},1,,,,#{fields[30]},,,,,,,,,ACTIVE,,#{fields[31]},A,,#{id_type_code(fields[29])},#{fields[29]},,,,,"
                       })
  end

  def random_race_cd
    %w[1002-5 2028-9 2054-5 2076-8 2106-3 2131-1 NASK PHC1175 M U].sample
  end

  def create_person_race_csv
    # person_uid,race_cd,add_reason_cd,add_time,add_user_id,last_chg_reason_cd,last_chg_time,last_chg_user_id,race_category_cd,race_desc_txt,record_status_cd,record_status_time,user_affiliation_txt,as_of_date
    create_csv(output: 'person_race',
               format: proc do |fields|
                         race_cd = random_race_cd
                         "#{fields[0]},#{race_cd},,,,,,,#{race_cd},,ACTIVE,,,"
                       end)
  end

  def create_elp_csv
    elp_file = File.open('entity_locator_participation.csv', 'w')
    tele_file = File.open('tele_locator.csv', 'w')
    postal_file = File.open('postal_locator.csv', 'w')

    # elp: entity_uid,locator_uid,,,,cd,,class_cd,,,,,,,,record_status_cd,,status_cd,,,use_cd,,,version_ctrl_nbr,
    # tele: tele_locator_uid,,,,,email_address,extension_txt,,,,phone_nbr_txt,record_status_cd,,,
    locator_uid = STARTING_LOCATOR_UID
    IO.foreach(ID_CSV) do |line|
      fields = line.chomp.split(',')
      next unless fields.any? { |e| e&.size&.positive? }

      work_phone = fields[23]
      home_phone = fields[22]
      cell_phone = fields[25]
      email = fields[26]
      if work_phone&.size&.positive?
        elp_file.puts("#{fields[0]},#{locator_uid},,,,PH,,TELE,,,,,,,,ACTIVE,,A,,,WP,,,1,")
        tele_file.puts("#{fields[0]},,,,,,#{fields[24]},,,,#{work_phone},ACTIVE,,,")
        locator_uid += 1
      end
      if home_phone&.size&.positive?
        elp_file.puts("#{fields[0]},#{locator_uid},,,,PH,,TELE,,,,,,,,ACTIVE,,A,,,H,,,1,")
        tele_file.puts("#{locator_uid},,,,,,,,,,#{home_phone},ACTIVE,,,")
        locator_uid += 1
      end
      if cell_phone&.size&.positive?
        elp_file.puts("#{fields[0]},#{locator_uid},,,,CP,,TELE,,,,,,,,ACTIVE,,A,,,MC,,,1,")
        tele_file.puts("#{locator_uid},,,,,,,,,,#{cell_phone},ACTIVE,,,")
        locator_uid += 1
      end
      if email&.size&.positive?
        elp_file.puts("#{fields[0]},#{locator_uid},,,,NET,,TELE,,,,,,,,ACTIVE,,A,,,H,,,1,")
        tele_file.puts("#{locator_uid},,,,,#{email},,,,,,ACTIVE,,,")
        locator_uid += 1
      end
      if any_field_not_empty?(fields, [14, 15, 16, 17, 18, 19, 20, 21])
        elp_file.puts("#{fields[0]},#{locator_uid},,,,H,,PST,,,,,,,,ACTIVE,,A,,,H,,,1,")
        postal_file.puts("#{locator_uid},,,,,,,#{fields[16]},#{fields[16]},840,,#{county_code(fields[19])},,,,,,ACTIVE,,,#{state_code(fields[17])},#{fields[14]},#{fields[15]},,#{fields[18]},,,#{fields[20]}")
        locator_uid += 1
      end
    end
    elp_file.close
    tele_file.close
    postal_file.close
  end

  def any_field_not_empty?(fields, field_number_list)
    field_number_list.each { |index| return true if fields[index]&.size&.positive? }
    false
  end

  def id_type_code(name)
    {
      'account number' => 'AN',
      'quick entry code' => 'QEC'
    }[name.to_s.downcase]
  end

  def gender_code(name)
    {
      'male' => 'M',
      'female' => 'F',
      'unknown' => 'U'
    }[name.to_s.downcase]
  end

  def marital_status_code(name)
    {
      'annulled' => 'A',
      'common law' => 'C',
      'divorced' => 'D',
      'domestic partner' => 'T',
      'interlocutory' => 'I',
      'legally separated' => 'L',
      'living together' => 'G',
      'married' => 'M',
      'other' => 'O',
      'polygamous' => 'P',
      'refused to answer' => 'R',
      'separated' => 'E',
      'single, never married' => 'S',
      'unknown' => 'U',
      'unmarried' => 'B',
      'unreported' => 'F',
      'widowed' => 'W'
    }[name.to_s.downcase]
  end

  def state_code(name)
    @state_name_to_code[name.downcase]
  end

  def county_code(name)
    @county_name_to_code[name.downcase]
  end

  def create_csv(output:, format:)
    file = File.open("#{output}.csv", 'w')
    IO.foreach(ID_CSV) do |line|
      fields = line.chomp.split(',')
      next unless fields.any? { |e| e&.size&.positive? }

      file.puts(format.call(fields))
    end
    file.close
  end

  def load_lookups
    IO.foreach('county_codes.txt') do |line|
      code, name = line.chomp.split("\t")
      @county_name_to_code[name.downcase] = code
    end
    IO.foreach('state_codes.txt') do |line|
      code, name = line.chomp.split("\t")
      @state_name_to_code[name.downcase] = code
    end
  end

  def bcp(table)
    cmd = "bcp NBS_ODSE.dbo.#{table} in #{table}.csv -U #{@user} -S #{@db} -P '#{@pwd}' -u -t ',' -r '0x0a' -F 1 -k -c"
    puts cmd
    system(cmd)
  end
end

BulkPatientImport.new(ARGV[1], ARGV[2], ARGV[3]).main(ARGV[0])

# ruby bulk_patient_import.rb demo_patient_records.csv dbhost dbuser dbpassword
