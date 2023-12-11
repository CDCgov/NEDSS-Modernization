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

        List<String> sourceIds = new ArrayList<>();
        sourceIds.add("Test Id");
        sourceIds.add("Test Id2");

        List<String> sourceValueText = new ArrayList<>();
        sourceValueText.add("TestSource1");
        sourceValueText.add("TestSource2");
        CreateRuleRequest.SourceValues sourceValue = new CreateRuleRequest.SourceValues(sourceIds, sourceValueText);
        return new CreateRuleRequest(
                "Enable",
                "TestDescription",
                "testSource",
                "INV214",
                sourceValue,
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
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        List<String> sourceValueId = new ArrayList<>();
        List<String> sourceValueText = new ArrayList<>();
        sourceValueId.add("M");
        sourceValueText.add("Male");

        CreateRuleRequest.SourceValues sourceDetails =
                new CreateRuleRequest.SourceValues(sourceValueId, sourceValueText);

        return new CreateRuleRequest(
                "Enable",
                "Enable",
                "Current Sex",
                "DEM113",
                sourceDetails,
                false,
                "=",
                "QUESTION",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest EnableRuleRequestNotEq() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        List<String> sourceValueId = new ArrayList<>();
        List<String> sourceValueText = new ArrayList<>();
        sourceValueId.add("M");
        sourceValueText.add("Male");

        CreateRuleRequest.SourceValues sourceDetails =
                new CreateRuleRequest.SourceValues(sourceValueId, sourceValueText);

        return new CreateRuleRequest(
                "Enable",
                "Enable",
                "Current Sex",
                "DEM113",
                sourceDetails,
                false,
                "<>",
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
        CreateRuleRequest.SourceValues sourceDetails = testData();

        return new CreateRuleRequest(
                "Disable",
                "Disable",
                "test",
                "INV123",
                sourceDetails,
                false,
                "<>",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest HideRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        List<String> sourceValueId = new ArrayList<>();
        List<String> sourceValueText = new ArrayList<>();
        sourceValueId.add("M");
        sourceValueText.add("Male");

        CreateRuleRequest.SourceValues sourceDetails =
                new CreateRuleRequest.SourceValues(sourceValueId, sourceValueText);

        return new CreateRuleRequest(
                "Hide",
                "Testing hid Function",
                "Current Sex",
                "DEM113",
                sourceDetails,
                false,
                "=",
                "Question",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest RequireIfRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        List<String> sourceValueId = new ArrayList<>();
        List<String> sourceValueText = new ArrayList<>();
        sourceValueId.add("M");
        sourceValueText.add("Male");
        sourceValueId.add("F");
        sourceValueText.add("Female");

        CreateRuleRequest.SourceValues sourceDetails =
                new CreateRuleRequest.SourceValues(sourceValueId, sourceValueText);

        return new CreateRuleRequest(
                "Require If",
                "Require If",
                "Current Sex",
                "DEM113",
                sourceDetails,
                false,
                "=",
                "Question",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest RequireIfRuleRequestAnyOtherComp() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        List<String> sourceValueId = new ArrayList<>();
        List<String> sourceValueText = new ArrayList<>();
        sourceValueId.add("M");
        sourceValueText.add("Male");
        sourceValueId.add("F");
        sourceValueText.add("Female");

        CreateRuleRequest.SourceValues sourceDetails =
                new CreateRuleRequest.SourceValues(sourceValueId, sourceValueText);

        return new CreateRuleRequest(
                "Require If",
                "Require If",
                "Current Sex",
                "DEM113",
                sourceDetails,
                true,
                "<>",
                "Question",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest RequireIfRuleRequestAny() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        List<String> sourceValueId = new ArrayList<>();
        List<String> sourceValueText = new ArrayList<>();
        sourceValueId.add("M");
        sourceValueText.add("Male");
        sourceValueId.add("F");
        sourceValueText.add("Female");

        CreateRuleRequest.SourceValues sourceDetails =
                new CreateRuleRequest.SourceValues(sourceValueId, sourceValueText);

        return new CreateRuleRequest(
                "Require If",
                "Require If",
                "Current Sex",
                "DEM113",
                sourceDetails,
                true,
                "=",
                "Question",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest RequireIfRuleRequestOneSource() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        List<String> sourceValueId = new ArrayList<>();
        List<String> sourceValueText = new ArrayList<>();
        sourceValueId.add("M");
        sourceValueText.add("Male");

        CreateRuleRequest.SourceValues sourceDetails =
                new CreateRuleRequest.SourceValues(sourceValueId, sourceValueText);

        return new CreateRuleRequest(
                "Require If",
                "Require If",
                "Current Sex",
                "DEM113",
                sourceDetails,
                false,
                "<>",
                "Question",
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
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        return new CreateRuleRequest(
                "Require If",
                "Require If",
                "Current Sex",
                "DEM113",
                null,
                true,
                "=",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest HideRuleTestDataAnySourceIsTrue() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        return new CreateRuleRequest(
                "Hide",
                "Hide",
                "Current Sex",
                "DEM113",
                null,
                true,
                "=",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest EnableRuleTestDataAnySourceIsTrue() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        return new CreateRuleRequest(
                "Enable",
                "Enable",
                "Current Sex",
                "DEM113",
                null,
                true,
                "=",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest DisableRuleTestDataAnySourceIsTrue() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");
        return new CreateRuleRequest(
                "Disable",
                "Disable",
                "Current Sex",
                "DEM113",
                null,
                true,
                "=",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest UnhideRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        List<String> sourceValueId = new ArrayList<>();
        List<String> sourceValueText = new ArrayList<>();
        sourceValueId.add("M");
        sourceValueText.add("Male");

        CreateRuleRequest.SourceValues sourceDetails =
                new CreateRuleRequest.SourceValues(sourceValueId, sourceValueText);

        return new CreateRuleRequest(
                "Unhide",
                "Unhide",
                "Current Sex",
                "DEM113",
                sourceDetails,
                false,
                "<>",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest UnhideRuleRequestEq() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        List<String> sourceValueId = new ArrayList<>();
        List<String> sourceValueText = new ArrayList<>();
        sourceValueId.add("M");
        sourceValueText.add("Male");

        CreateRuleRequest.SourceValues sourceDetails =
                new CreateRuleRequest.SourceValues(sourceValueId, sourceValueText);

        return new CreateRuleRequest(
                "Unhide",
                "Unhide",
                "Current Sex",
                "DEM113",
                sourceDetails,
                false,
                "=",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest UnhideRuleRequestSubsectionType() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        List<String> sourceValueId = new ArrayList<>();
        List<String> sourceValueText = new ArrayList<>();
        sourceValueId.add("M");
        sourceValueText.add("Male");

        CreateRuleRequest.SourceValues sourceDetails =
                new CreateRuleRequest.SourceValues(sourceValueId, sourceValueText);

        return new CreateRuleRequest(
                "Unhide",
                "Unhide",
                "Current Sex",
                "DEM113",
                sourceDetails,
                false,
                "=",
                "Subsection",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest UnhideRuleRequestSubsectionTypeComparator() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        List<String> sourceValueId = new ArrayList<>();
        List<String> sourceValueText = new ArrayList<>();
        sourceValueId.add("M");
        sourceValueText.add("Male");

        CreateRuleRequest.SourceValues sourceDetails =
                new CreateRuleRequest.SourceValues(sourceValueId, sourceValueText);

        return new CreateRuleRequest(
                "Unhide",
                "Unhide",
                "Current Sex",
                "DEM113",
                sourceDetails,
                false,
                "<>",
                "Subsection",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest HideRuleRequestSubsectionType() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        List<String> sourceValueId = new ArrayList<>();
        List<String> sourceValueText = new ArrayList<>();
        sourceValueId.add("M");
        sourceValueText.add("Male");

        CreateRuleRequest.SourceValues sourceDetails =
                new CreateRuleRequest.SourceValues(sourceValueId, sourceValueText);

        return new CreateRuleRequest(
                "Hide",
                "Hide",
                "Current Sex",
                "DEM113",
                sourceDetails,
                false,
                "=",
                "Subsection",
                targetValuesList,
                targetIdentifiers);
    }

    public static CreateRuleRequest HideRuleRequestSubsectionTypeComparator() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        List<String> sourceValueId = new ArrayList<>();
        List<String> sourceValueText = new ArrayList<>();
        sourceValueId.add("M");
        sourceValueText.add("Male");

        CreateRuleRequest.SourceValues sourceDetails =
                new CreateRuleRequest.SourceValues(sourceValueId, sourceValueText);

        return new CreateRuleRequest(
                "Hide",
                "Hide",
                "Current Sex",
                "DEM113",
                sourceDetails,
                false,
                "<>",
                "Subsection",
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

    public static CreateRuleRequest UnhideRuleRequestIfAnySourceComp() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        return new CreateRuleRequest(
                "Unhide",
                "Unhide",
                "test",
                "INV123",
                null,
                true,
                "<>",
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
        CreateRuleRequest.SourceValues sourceDetails = testData();

        return new CreateRuleRequest(
                "Invalid",
                "Invalid",
                "test",
                "INV123",
                sourceDetails,
                false,
                "<>",
                "TestQuestion",
                targetValuesList,
                targetIdentifiers);
    }
}
