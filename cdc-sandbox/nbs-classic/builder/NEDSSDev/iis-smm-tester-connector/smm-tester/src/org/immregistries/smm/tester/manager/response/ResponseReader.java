package org.immregistries.smm.tester.manager.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.immregistries.smm.tester.manager.HL7Reader;
import org.immregistries.smm.tester.manager.query.ImmunizationUpdate;
import org.immregistries.smm.tester.manager.query.Patient;
import org.immregistries.smm.tester.manager.query.PatientIdType;
import org.immregistries.smm.tester.manager.query.QueryResponse;
import org.immregistries.smm.tester.manager.query.QueryResponseType;
import org.immregistries.smm.tester.manager.query.Vaccination;

public class ResponseReader {
  public static ImmunizationMessage readMessage(String message) {
    if (message == null || message.equals("")) {
      return null;
    }
    HL7Reader reader = new HL7Reader(message);
    List<Patient> patientList = new ArrayList<Patient>();

    if (reader.advanceToSegment("MSH")) {
      String messageType = reader.getValue(9);
      if (messageType.equals("ACK")) {
        // Process ack
      } else if (messageType.equals("QBP")) {
        // Process
      } else if (messageType.equals("RSP")) {
        QueryResponse queryResponse = new QueryResponse();
        queryResponse.setPatientList(patientList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String profile = reader.getValue(21);
        while (reader.advanceToSegment("PID")) {
       // Create new patient and fill values.
          Patient patient = new Patient();
          patientList.add(patient);
          String nameLast = reader.getValue(5, 1);

          String idNumber = "";
          String idAuthority = "";
          PatientIdType idType = PatientIdType.MR;
          String nameFirst = reader.getValue(5, 2);
          String nameMiddle = reader.getValue(5, 3);
          String motherNameMaiden = reader.getValue(6, 1);
          String birthDateString = reader.getValue(7);
          String sr = reader.getValueBySearchingRepeats(3, 1, "SR", 5);
          Date birthDate = null;
          if (birthDateString.length() > 8) {
            birthDateString = birthDateString.substring(0, 8);
          }
          String sex = "";
          String addressStreet1 = "";
          String addressStreet2 = "";
          String addressCity = "";
          String addressState = "";
          String addressZip = "";
          String addressCountry = "USA";
          String phoneArea = "";
          String phoneLocal = "";
          boolean multipleBirthIndicator = false;
          int multipleBirthOrder = 0;


       // Get patient values.
          patient.setIdType(PatientIdType.SR);
          patient.setIdNumber(sr);
          patient.setNameLast(nameLast);
          patient.setNameFirst(nameFirst);
          patient.setBirthDate(birthDate);
          patient.setMotherNameMaiden(motherNameMaiden);
          try {
            patient.setBirthDate(sdf.parse(birthDateString));
          } catch (ParseException e) {
            // do nothing
          }
        }
        queryResponse.setPatientList(patientList);
        reader.resetPostion();
        QueryResponseType queryResponseType = null;
        if (profile.equalsIgnoreCase("")) {
          queryResponseType = QueryResponseType.PROFILE_ID_MISSING;
        } else if (profile.equalsIgnoreCase("Z32") || profile.equalsIgnoreCase("Z42")) {
          queryResponseType = QueryResponseType.MATCH;

          List<Vaccination> vaccinationList = new ArrayList<Vaccination>();
          queryResponse.setVaccinationList(vaccinationList);
          while (reader.advanceToSegment("RXA")) {
            String adminDateString = reader.getValue(3);
            if (adminDateString.length() > 8) {
              adminDateString = adminDateString.substring(0, 8);
            }
            String vaccineCvx = reader.getValue(5);
            String vaccineLabel = reader.getValue(5, 2);
            String administeredAmount = reader.getValue(6);
            String administeredAmountUnit = reader.getValue(7);
            String informationSource = reader.getValue(9);
            String informationSourceLabel = reader.getValue(9, 2);
            String lotNumber = reader.getValue(15);
            String manufacturerMvx = reader.getValue(17);
            String manufacturerLabel = reader.getValue(17, 2);
            String refusalReason = reader.getValue(18);
            String completionStatus = reader.getValue(20);
            String actionCode = reader.getValue(21);

            if (!vaccineCvx.equals("") && !vaccineCvx.equals("998")) {

              Vaccination vaccination = new Vaccination();
              try {
                vaccination.setAdministrationDate(sdf.parse(adminDateString));
              } catch (ParseException e) {
                // do nothing
              }
              vaccination.setVaccineCvx(vaccineCvx);
              vaccination.setVaccineLabel(vaccineLabel);
              vaccination.setAdministeredAmount(administeredAmount);
              vaccination.setAdministeredAmountUnit(administeredAmountUnit);
              vaccination.setInformationSource(informationSource);
              vaccination.setInformationSourceLabel(informationSourceLabel);
              vaccination.setLotNumber(lotNumber);
              vaccination.setManufacturerMvx(manufacturerMvx);
              vaccination.setManufacturerLabel(manufacturerLabel);
              vaccination.setRefusalReason(refusalReason);
              vaccination.setCompletionStatus(completionStatus);
              vaccination.setActionCode(actionCode);
              vaccinationList.add(vaccination);
            }
          }
        } else if (profile.equalsIgnoreCase("Z31")) {
          queryResponseType = QueryResponseType.LIST;
        } else if (profile.equalsIgnoreCase("Z33")) {
          if (reader.advanceToSegment("QAK")) {
            String responseStatus = reader.getValue(2);
            if (responseStatus.equals("NF")) {
              queryResponseType = QueryResponseType.NOT_FOUND;
            } else if (responseStatus.equals("TM")) {
              queryResponseType = QueryResponseType.TOO_MANY;
            } else if (responseStatus.equals("AE")) {
              queryResponseType = QueryResponseType.ERROR;
            }
          } else {
            queryResponseType = null;
          }
        }
        queryResponse.setQueryResponseType(queryResponseType);

        return queryResponse;
        // Process
      } else if (messageType.equals("ADT")) {
        // Process
      } else if (messageType.equals("VXU")) {
        ImmunizationUpdate iu = new ImmunizationUpdate();
        Patient patient = new Patient();
        iu.setPatient(patient);
        if (reader.advanceToSegment("PID")) {
          patient.setNameLast(reader.getValue(5, 1));
          patient.setNameFirst(reader.getValue(5, 2));
          patient.setNameMiddle(reader.getValue(5, 3));
          patient.setSex(reader.getValue(8));
          patient.setMotherNameMaiden(reader.getValue(6));
          String dobString = reader.getValue(7);
          if (dobString.length() > 8) {
            dobString = dobString.substring(0, 8);
          }
          if (dobString.length() == 8) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            try {
              patient.setBirthDate(sdf.parse(dobString));
            } catch (ParseException e) {
              // ignore
            }
          }
          patient.setAddressStreet1(reader.getValue(11, 1));
          patient.setAddressStreet2(reader.getValue(11, 2));
          patient.setAddressCity(reader.getValue(11, 3));
          patient.setAddressState(reader.getValue(11, 4));
          patient.setAddressZip(reader.getValue(11, 5));
          patient.setAddressCountry(reader.getValue(11, 6));
          patient.setPhoneArea(reader.getValue(13, 6));
          patient.setPhoneLocal(reader.getValue(13, 7));
          String mrn = reader.getValueBySearchingRepeats(3, 1, "MR", 5);
          String authority = reader.getValueBySearchingRepeats(3, 4, "MR", 5);
          if (mrn.equals("")) {
            mrn = reader.getValueBySearchingRepeats(3, 1, "PT", 5);
            authority = reader.getValueBySearchingRepeats(3, 4, "PT", 5);
          }
          patient.setIdNumber(mrn);
          patient.setIdAuthority(authority);
        }
        while (reader.advanceToSegment("NK1")) {
          String relationship = reader.getValue(3);
          if (relationship.equals("MTH")) {
            patient.setMotherNameFirst(reader.getValue(2, 2));
            patient.setMotherNameMiddle(reader.getValue(2, 3));
          }
        }
        return iu;
      }

    }
    return null;
  }
}
