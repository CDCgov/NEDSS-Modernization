package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.pagerules.Rule;
import gov.cdc.nbs.questionbank.pagerules.Rule.CreateRuleRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RuleRequestMother {

    public static CreateRuleRequest ruleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        Rule.SourceValue sourceValue1 = new Rule.SourceValue("Test Id", "TestSource1");
        Rule.SourceValue sourceValue2 = new Rule.SourceValue("Test Id2", "TestSource2");
        List<Rule.SourceValue> sourceValues = Arrays.asList(sourceValue1, sourceValue2);

        return new CreateRuleRequest(
            Rule.Function.ENABLE,
            "TestDescription",
            "INV214",
            true,
            sourceValues,
            Rule.Comparator.GREATER_THAN_OR_EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "testSource",
            targetValuesList);
    }

    public static CreateRuleRequest dateCompareRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        //        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        //        targetIdentifiers.add("INV132");
        targetIdentifiers.add("INV133");

        return new CreateRuleRequest(
            Rule.Function.DATE_COMPARE,
            "'Admission Date (INV132)' must be <= 'Discharge Date (INV133)'.",
            "INV132",
            false,
            null,
            Rule.Comparator.LESS_THAN_OR_EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "Admission Date",
            targetValuesList);
    }

    public static List<Rule.SourceValue> testData() {
        Rule.SourceValue sourceValue1 = new Rule.SourceValue("Test Id", "TestSource1");
        Rule.SourceValue sourceValue2 = new Rule.SourceValue("Test Id2", "TestSource2");
        List<Rule.SourceValue> sourceValues = Arrays.asList(sourceValue1, sourceValue2);
        return sourceValues;
    }

    public static CreateRuleRequest EnableRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        Rule.SourceValue sourceValue = new Rule.SourceValue("M", "Male");
        List<Rule.SourceValue> sourceDetails = Arrays.asList(sourceValue);

        return new CreateRuleRequest(
            Rule.Function.ENABLE,
            "Enable",
            "DEM113",
            false,
            sourceDetails,
            Rule.Comparator.EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest EnableRuleRequestNotEq() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        Rule.SourceValue sourceValue = new Rule.SourceValue("M", "Male");
        List<Rule.SourceValue> sourceDetails = Arrays.asList(sourceValue);

        return new CreateRuleRequest(
            Rule.Function.ENABLE,
            "Enable",
            "DEM113",
            false,
            sourceDetails,
            Rule.Comparator.NOT_EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList
        );
    }

    public static CreateRuleRequest DisableRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        List<Rule.SourceValue> sourceDetails = testData();

        return new CreateRuleRequest(
            Rule.Function.DISABLE,
            "Disable",
            "INV123",
            false,
            sourceDetails,
            Rule.Comparator.NOT_EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "test",
            targetValuesList);
    }

    public static CreateRuleRequest HideRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        Rule.SourceValue sourceValue = new Rule.SourceValue("M", "Male");
        List<Rule.SourceValue> sourceDetails = Arrays.asList(sourceValue);

        return new CreateRuleRequest(
            Rule.Function.HIDE,
            "Testing hid Function",
            "DEM113",
            false,
            sourceDetails,
            Rule.Comparator.EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest RequireIfRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        Rule.SourceValue sourceValue1 = new Rule.SourceValue("M", "Male");
        Rule.SourceValue sourceValue2 = new Rule.SourceValue("F", "Female");
        List<Rule.SourceValue> sourceDetails = Arrays.asList(sourceValue1, sourceValue2);

        return new CreateRuleRequest(
            Rule.Function.REQUIRE_IF,
            "Require If",
            "DEM113",
            false,
            sourceDetails,
            Rule.Comparator.EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest RequireIfRuleRequestAnyOtherComp() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        Rule.SourceValue sourceValue1 = new Rule.SourceValue("M", "Male");
        Rule.SourceValue sourceValue2 = new Rule.SourceValue("F", "Female");
        List<Rule.SourceValue> sourceDetails = Arrays.asList(sourceValue1, sourceValue2);

        return new CreateRuleRequest(
            Rule.Function.REQUIRE_IF,
            "Require If",
            "DEM113",
            true,
            sourceDetails,
            Rule.Comparator.NOT_EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest RequireIfRuleRequestAny() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        Rule.SourceValue sourceValue1 = new Rule.SourceValue("M", "Male");
        Rule.SourceValue sourceValue2 = new Rule.SourceValue("F", "Female");
        List<Rule.SourceValue> sourceDetails = Arrays.asList(sourceValue1, sourceValue2);

        return new CreateRuleRequest(
            Rule.Function.REQUIRE_IF,
            "Require If",
            "DEM113",
            true,
            sourceDetails,
            Rule.Comparator.EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest RequireIfRuleRequestOneSource() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        Rule.SourceValue sourceValue = new Rule.SourceValue("M", "Male");
        List<Rule.SourceValue> sourceDetails = Arrays.asList(sourceValue);

        return new CreateRuleRequest(
            Rule.Function.REQUIRE_IF,
            "Require If",
            "DEM113",
            false,
            sourceDetails,
            Rule.Comparator.NOT_EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest RequireIfRuleTestData_othercomparator() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        return new CreateRuleRequest(
            Rule.Function.REQUIRE_IF,
            "require if ",
            "INV123",
            true,
            null,
            Rule.Comparator.EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "test",
            targetValuesList);
    }


    public static CreateRuleRequest RequireIfRuleTestDataAnySourceIsTrue() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        return new CreateRuleRequest(
            Rule.Function.REQUIRE_IF,
            "Require If",
            "DEM113",
            true,
            null,
            Rule.Comparator.EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest HideRuleTestDataAnySourceIsTrue() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        return new CreateRuleRequest(
            Rule.Function.HIDE,
            "Hide",
            "DEM113",
            true,
            null,
            Rule.Comparator.EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest EnableRuleTestDataAnySourceIsTrue() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        return new CreateRuleRequest(
            Rule.Function.ENABLE,
            "Enable",
            "DEM113",
            true,
            null,
            Rule.Comparator.EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest DisableRuleTestDataAnySourceIsTrue() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");
        return new CreateRuleRequest(
            Rule.Function.DISABLE,
            "Disable",
            "DEM113",
            true,
            null,
            Rule.Comparator.EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest UnhideRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        Rule.SourceValue sourceValue = new Rule.SourceValue("M", "Male");
        List<Rule.SourceValue> sourceDetails = Arrays.asList(sourceValue);

        return new CreateRuleRequest(
            Rule.Function.UNHIDE,
            "Unhide",
            "DEM113",
            false,
            sourceDetails,
            Rule.Comparator.NOT_EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest UnhideRuleRequestEq() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        Rule.SourceValue sourceValue = new Rule.SourceValue("M", "Male");
        List<Rule.SourceValue> sourceDetails = Arrays.asList(sourceValue);

        return new CreateRuleRequest(
            Rule.Function.UNHIDE,
            "Unhide",
            "DEM113",
            false,
            sourceDetails,
            Rule.Comparator.EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest UnhideRuleRequestSubsectionType() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        Rule.SourceValue sourceValue = new Rule.SourceValue("M", "Male");
        List<Rule.SourceValue> sourceDetails = Arrays.asList(sourceValue);

        return new CreateRuleRequest(
            Rule.Function.UNHIDE,
            "Unhide",
            "DEM113",
            false,
            sourceDetails,
            Rule.Comparator.EQUAL_TO,
            Rule.TargetType.SUBSECTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest UnhideRuleRequestSubsectionTypeComparator() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        Rule.SourceValue sourceValue = new Rule.SourceValue("M", "Male");
        List<Rule.SourceValue> sourceDetails = Arrays.asList(sourceValue);

        return new CreateRuleRequest(
            Rule.Function.UNHIDE,
            "Unhide",
            "DEM113",
            false,
            sourceDetails,
            Rule.Comparator.NOT_EQUAL_TO,
            Rule.TargetType.SUBSECTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest HideRuleRequestSubsectionType() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        Rule.SourceValue sourceValue = new Rule.SourceValue("M", "Male");
        List<Rule.SourceValue> sourceDetails = Arrays.asList(sourceValue);

        return new CreateRuleRequest(
            Rule.Function.HIDE,
            "Hide",
            "DEM113",
            false,
            sourceDetails,
            Rule.Comparator.EQUAL_TO,
            Rule.TargetType.SUBSECTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest HideRuleRequestSubsectionTypeComparator() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Age at Onset");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("INV143");

        Rule.SourceValue sourceValue = new Rule.SourceValue("M", "Male");
        List<Rule.SourceValue> sourceDetails = Arrays.asList(sourceValue);

        return new CreateRuleRequest(
            Rule.Function.HIDE,
            "Hide",
            "DEM113",
            false,
            sourceDetails,
            Rule.Comparator.NOT_EQUAL_TO,
            Rule.TargetType.SUBSECTION,
            targetIdentifiers,
            "Current Sex",
            targetValuesList);
    }

    public static CreateRuleRequest UnhideRuleRequestIfAnySource() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        return new CreateRuleRequest(
            Rule.Function.UNHIDE,
            "Unhide",
            "INV123",
            true,
            null,
            Rule.Comparator.EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "test",
            targetValuesList);
    }

    public static CreateRuleRequest UnhideRuleRequestIfAnySourceComp() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        return new CreateRuleRequest(
            Rule.Function.UNHIDE,
            "Unhide",
            "INV123",
            true,
            null,
            Rule.Comparator.NOT_EQUAL_TO,
            Rule.TargetType.QUESTION,
            targetIdentifiers,
            "test",
            targetValuesList);
    }

    public static CreateRuleRequest InvalidRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");
        List<Rule.SourceValue> sourceDetails = testData();

        return new CreateRuleRequest(
            Rule.Function.UNHIDE,
            null,
            null,
            false,
            null,
            Rule.Comparator.NOT_EQUAL_TO,
            Rule.TargetType.QUESTION,
            null,
            "test",
            null);
    }
}
