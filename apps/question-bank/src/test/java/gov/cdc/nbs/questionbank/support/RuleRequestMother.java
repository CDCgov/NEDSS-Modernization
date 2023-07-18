package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;

import java.util.ArrayList;
import java.util.List;


public class RuleRequestMother {

    public static CreateRuleRequest ruleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        List<CreateRuleRequest.SourceValues> sourceValues = new ArrayList<>();
        List<String> sourceIds = new ArrayList<>();
        sourceIds.add("Test Id");
        sourceIds.add("Test Id2");

        List<String> sourceValueText = new ArrayList<>();
        sourceValueText.add("TestSource1");
        sourceValueText.add("TestSource2");
        CreateRuleRequest.SourceValues sourceValue = new CreateRuleRequest.SourceValues(sourceIds, sourceValueText);
        sourceValues.add(sourceValue);
        return new CreateRuleRequest(
                123456L,
                "Enable",
                "TestDescription",
                "testSource",
                "INV214",
                sourceValues,
                false,
                ">=",
                "testTargetValue",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest dateCompareRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        //        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        //        targetIdentifiers.add("INV132");
        targetIdentifiers.add("INV133");

        return new CreateRuleRequest(
                123456L,
                "Date Compare",
                "'Admission Date (INV132)' must be <= 'Discharge Date (INV133)'.",
                "Admission Date",
                "INV132",
                null,
                false,
                "<=",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest.SourceValues testData() {
        List<String> sourceIds = new ArrayList<>();
        sourceIds.add("Test Id");
        sourceIds.add("Test Id2");
        List<String> sourceValueText = new ArrayList<>();
        sourceValueText.add("TestSource1");
        sourceValueText.add("TestSource2");
        return new CreateRuleRequest.SourceValues(sourceIds, sourceValueText);
    }

    public static CreateRuleRequest EnableRuleRequest() {
        List<String> sourceValueId = new ArrayList<>();
        List<String> sourceValueText = new ArrayList<>();
        sourceValueId.add("Y");
        sourceValueText.add("Yes");
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Weeks");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("NBS128");
        List<CreateRuleRequest.SourceValues> sourceValues = new ArrayList<>();
        CreateRuleRequest.SourceValues sourceDetails =
                new CreateRuleRequest.SourceValues(sourceValueId, sourceValueText);
        sourceValues.add(sourceDetails);

        return new CreateRuleRequest(
                123456L,
                "Enable",
                "If the Patient Pregnant(INV178) is Yes, enable Weeks (NBS128)",
                "Is the patient pregnant?",
                "INV178",
                sourceValues,
                false,
                "=",
                "QUESTION",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest DisableRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");
        List<CreateRuleRequest.SourceValues> sourceValues = new ArrayList<>();
        CreateRuleRequest.SourceValues sourceDetails = testData();
        sourceValues.add(sourceDetails);

        return new CreateRuleRequest(
                123456L,
                "Disable",
                "Disable",
                "test",
                "INV123",
                sourceValues,
                false,
                "<>",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest HideRuleRequest() {
        List<String> sourceValueId = new ArrayList<>();
        List<String> sourceValueText = new ArrayList<>();
        sourceValueId.add("D");
        sourceValueText.add("Days");
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Additional Gender");
        //        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("NBS213");
        //        targetIdentifiers.add("test234");
        List<CreateRuleRequest.SourceValues> sourceValues = new ArrayList<>();
        CreateRuleRequest.SourceValues sourceDetails =
                new CreateRuleRequest.SourceValues(sourceValueId, sourceValueText);
        sourceValues.add(sourceDetails);

        return new CreateRuleRequest(
                123456L,
                "Hide",
                "Testing hide Function",
                "Age at Onset Units",
                "INV144",
                sourceValues,
                false,
                "=",
                "QUESTION",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest RequireIfRuleTestData() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Named");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("CON143");

        return new CreateRuleRequest(
                123456L,
                "Require If",
                "If the Naming Between question is answered, require the Named dropdown",
                "Relationship with Patient/Other infected Patient?",
                "CON141",
                null,
                true,
                "=",
                null,
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest RequireIfRuleTestData_othercomparator() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        return new CreateRuleRequest(
                123456L,
                "Require If",
                "require if ",
                "test",
                "INV123",
                null,
                true,
                "Something else",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }


    public static CreateRuleRequest RequireIfRuleTestDataAnySourceIsTrue() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        return new CreateRuleRequest(
                123456L,
                "Require If",
                "require if ",
                "test",
                "INV123",
                null,
                true,
                "=",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest HideRuleTestDataAnySourceIsTrue() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");
        return new CreateRuleRequest(
                123456L,
                "Hide",
                "Hide",
                "test",
                "INV123",
                null,
                true,
                "=",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest EnableRuleTestDataAnySourceIsTrue() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        return new CreateRuleRequest(
                123456L,
                "Enable",
                "Enable",
                "test",
                "INV123",
                null,
                true,
                "=",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest DisableRuleTestDataAnySourceIsTrue() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");
        return new CreateRuleRequest(
                123456L,
                "Disable",
                "Disable",
                "test",
                "INV123",
                null,
                true,
                "=",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest UnhideRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");
        List<CreateRuleRequest.SourceValues> sourceValues = new ArrayList<>();
        CreateRuleRequest.SourceValues sourceDetails = testData();
        sourceValues.add(sourceDetails);

        return new CreateRuleRequest(
                123456L,
                "Unhide",
                "Unhide",
                "test",
                "INV123",
                sourceValues,
                false,
                "<>",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest UnhideRuleRequestIfAnySource() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        return new CreateRuleRequest(
                123456L,
                "Unhide",
                "Unhide",
                "test",
                "INV123",
                null,
                true,
                "=",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest InvalidRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");
        List<CreateRuleRequest.SourceValues> sourceValues = new ArrayList<>();
        CreateRuleRequest.SourceValues sourceDetails = testData();
        sourceValues.add(sourceDetails);

        return new CreateRuleRequest(
                123456L,
                "Invalid",
                "Invalid",
                "test",
                "INV123",
                sourceValues,
                false,
                "<>",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }
}
