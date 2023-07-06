package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;

import java.util.ArrayList;
import java.util.List;


public class RuleRequestMother {

    public static CreateRuleRequest.ruleRequest ruleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers= new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        List<CreateRuleRequest.sourceValues> sourceValues= new ArrayList<>();
        List<String> sourceIds= new ArrayList<>();
        sourceIds.add("Test Id");
        sourceIds.add("Test Id2");

        List<String> sourceValueText= new ArrayList<>();
        sourceValueText.add("TestSource1");
        sourceValueText.add("TestSource2");
        CreateRuleRequest.sourceValues sourceValue= new CreateRuleRequest.sourceValues(sourceIds,sourceValueText);
        sourceValues.add(sourceValue);
        return new CreateRuleRequest.ruleRequest(
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
                targetIdentifiers
                );
    }

    public static CreateRuleRequest.ruleRequest dateCompareRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers= new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        return new CreateRuleRequest.ruleRequest(
                123456L,
                "Date Compare",
                "TestDescription",
                "test",
                "INV123",
                null,false,">","TestQuestion",targetValuesList,targetIdentifiers
        );
    }

    public static CreateRuleRequest.sourceValues testData(){
        List<String> sourceIds= new ArrayList<>();
        sourceIds.add("Test Id");
        sourceIds.add("Test Id2");
        List<String> sourceValueText= new ArrayList<>();
        sourceValueText.add("TestSource1");
        sourceValueText.add("TestSource2");
        return new CreateRuleRequest.sourceValues(sourceIds,sourceValueText);
    }
    public static CreateRuleRequest.ruleRequest EnableRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers= new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");
        List<CreateRuleRequest.sourceValues> sourceValues= new ArrayList<>();
        CreateRuleRequest.sourceValues sourceDetails= testData();
        sourceValues.add(sourceDetails);

        return new CreateRuleRequest.ruleRequest(
                123456L,
                "Enable",
                "Enable",
                "test",
                "INV123",
                sourceValues,false,"<>","TestQuestion",targetValuesList,targetIdentifiers
        );
    }

    public static CreateRuleRequest.ruleRequest DisableRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers= new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");
        List<CreateRuleRequest.sourceValues> sourceValues= new ArrayList<>();
        CreateRuleRequest.sourceValues sourceDetails= testData();
        sourceValues.add(sourceDetails);

        return new CreateRuleRequest.ruleRequest(
                123456L,
                "Disable",
                "Disable",
                "test",
                "INV123",
                sourceValues,false,"<>","TestQuestion",targetValuesList,targetIdentifiers
        );
    }
    public static CreateRuleRequest.ruleRequest HideRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers= new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");
        List<CreateRuleRequest.sourceValues> sourceValues= new ArrayList<>();
        CreateRuleRequest.sourceValues sourceDetails= testData();
        sourceValues.add(sourceDetails);

        return new CreateRuleRequest.ruleRequest(
                123456L,
                "Hide",
                "Hide",
                "test",
                "INV123",
                sourceValues,false,"<>","TestQuestion",targetValuesList,targetIdentifiers
        );
    }

    public static CreateRuleRequest.ruleRequest RequireIfRuleTestData() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers= new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");
        List<CreateRuleRequest.sourceValues> sourceValues= new ArrayList<>();
        CreateRuleRequest.sourceValues sourceDetails= testData();
        sourceValues.add(sourceDetails);

        return new CreateRuleRequest.ruleRequest(
                123456L,
                "Require If",
                "require if ",
                "test",
                "INV123",
                sourceValues,false,"<>","TestQuestion",targetValuesList,targetIdentifiers
        );
    }

    public static CreateRuleRequest.ruleRequest RequireIfRuleTestDataAnySourceIsTrue() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers= new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        return new CreateRuleRequest.ruleRequest(
                123456L,
                "Require If",
                "require if ",
                "test",
                "INV123",
                null,true,"=","TestQuestion",targetValuesList,targetIdentifiers
        );
    }

    public static CreateRuleRequest.ruleRequest HideRuleTestDataAnySourceIsTrue() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers= new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");
        return new CreateRuleRequest.ruleRequest(
                123456L,
                "Hide",
                "Hide",
                "test",
                "INV123",
                null,true,"=","TestQuestion",targetValuesList,targetIdentifiers
        );
    }

    public static CreateRuleRequest.ruleRequest EnableRuleTestDataAnySourceIsTrue() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers= new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        return new CreateRuleRequest.ruleRequest(
                123456L,
                "Enable",
                "Enable",
                "test",
                "INV123",
                null,true,"=","TestQuestion",targetValuesList,targetIdentifiers
        );
    }

    public static CreateRuleRequest.ruleRequest DisableRuleTestDataAnySourceIsTrue() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers= new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");
        return new CreateRuleRequest.ruleRequest(
                123456L,
                "Disable",
                "Disable",
                "test",
                "INV123",
                null,true,"=","TestQuestion",targetValuesList,targetIdentifiers
        );
    }

    public static CreateRuleRequest.ruleRequest UnhideRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers= new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");
        List<CreateRuleRequest.sourceValues> sourceValues= new ArrayList<>();
        CreateRuleRequest.sourceValues sourceDetails= testData();
        sourceValues.add(sourceDetails);

        return new CreateRuleRequest.ruleRequest(
                123456L,
                "Unhide",
                "Unhide",
                "test",
                "INV123",
                sourceValues,false,"<>","TestQuestion",targetValuesList,targetIdentifiers
        );
    }

    public static CreateRuleRequest.ruleRequest UnhideRuleRequestIfAnySource() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers= new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");

        return new CreateRuleRequest.ruleRequest(
                123456L,
                "Unhide",
                "Unhide",
                "test",
                "INV123",
                null,true,"=","TestQuestion",targetValuesList,targetIdentifiers
        );
    }

    public static CreateRuleRequest.ruleRequest InvalidRuleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        targetValuesList.add("Discharge Date");
        List<String> targetIdentifiers= new ArrayList<>();
        targetIdentifiers.add("test123");
        targetIdentifiers.add("test234");
        List<CreateRuleRequest.sourceValues> sourceValues= new ArrayList<>();
        CreateRuleRequest.sourceValues sourceDetails= testData();
        sourceValues.add(sourceDetails);

        return new CreateRuleRequest.ruleRequest(
                123456L,
                "Invalid",
                "Invalid",
                "test",
                "INV123",
                sourceValues,false,"<>","TestQuestion",targetValuesList,targetIdentifiers
        );
    }
}
