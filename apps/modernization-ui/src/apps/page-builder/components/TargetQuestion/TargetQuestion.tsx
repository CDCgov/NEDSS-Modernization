import { useEffect } from 'react';
import {
    ButtonGroup,
    Checkbox,
    ModalFooter,
    ModalRef,
    ModalToggleButton,
    Radio,
    Tag,
    Icon as UswIcon
} from '@trussworks/react-uswds';
import {
    PageRuleControllerService,
    PagesQuestion,
    PagesResponse,
    PagesSection,
    PagesTab,
    Rule,
    Target
} from 'apps/page-builder/generated';
import { RefObject, useState } from 'react';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import './TargetQuestion.scss';
import { Icon } from '../../../../components/Icon/Icon';
import { authorization } from 'authorization';
import { QuestionProps } from 'apps/page-builder/pages/BusinessRulesLibrary/BusinessRulesForm';
import { useGetPageDetails } from 'apps/page-builder/page/management';

type CommonProps = {
    modalRef: RefObject<ModalRef>;
    getList: (data: QuestionProps[]) => void;
    multiSelected?: boolean;
    header?: string;
    isSource?: boolean;
    ruleFunction: string;
    targetType: string;
    sourceQuestion?: QuestionProps[] | PagesQuestion[] | undefined;
};

const codedDisplayType = [1024, 1025, 1013, 1007, 1031, 1027, 1028];
const staticType = [1014, 1003, 1012, 1030, 1036];

const TargetQuestion = ({
    modalRef,
    getList,
    header,
    multiSelected = true,
    isSource = false,
    ruleFunction,
    sourceQuestion
}: CommonProps) => {
    const [activeTab, setActiveTab] = useState(0);
    const [sourceList, setSourceList] = useState<QuestionProps[]>([]);
    const [subsectionOpen, setSubsectionOpen] = useState(false);
    const [sourceId, setSource] = useState(-1);
    const [filteredPage, setFilteredPage] = useState<PagesResponse>();
    const [allRules, setAllRules] = useState<Rule[]>();
    const [targetIdent, setTargetIdent] = useState<string[]>();

    const { page } = useGetPageDetails();

    const visible = true;
    const selectedRecord = sourceList.filter((list) => list.selected);
    const isSelectedAll = sourceList.length !== 0 && selectedRecord?.length === sourceList.length;

    const handleSelectAll = (e: any) => {
        setSourceList((prevState: any) => prevState.map((list: any) => ({ ...list, selected: e.target.checked })));
    };

    const handleRemove = (val: any) => {
        const updateList = [...sourceList];
        const index = sourceList.findIndex((list) => list?.name === val);
        updateList[index] = { ...updateList[index], selected: false };
        setSourceList(updateList);
    };

    const handleSelect = (e: any, key: number) => {
        let updateList = [...sourceList];

        if (!multiSelected) {
            updateList = updateList.map((qtn: QuestionProps) => ({
                name: qtn.name,
                id: qtn.id,
                question: qtn.question,
                valueSet: qtn.valueSet,
                selected: false,
                displayComponent: qtn.displayComponent,
                dataType: qtn.dataType,
                questionGroupSeq: qtn.questionGroupSeq,
                blockName: qtn.blockName
            }));
        }
        updateList[key] = { ...updateList[key], selected: e.target.checked };

        setSourceList(updateList);
    };

    const isCoded = (question: QuestionProps) => codedDisplayType.includes(question.displayComponent ?? 0);

    const isDate = (question: QuestionProps) => question.dataType === 'DATE';

    const isNotStatic = (question: QuestionProps) => !staticType.includes(question.displayComponent ?? 0);

    const handleList = (question: QuestionProps[]) => {
        const newList = question.map((qtn: QuestionProps) => ({
            name: qtn.name,
            id: qtn.id,
            question: qtn.question,
            valueSet: qtn.valueSet,
            selected: false,
            displayComponent: qtn.displayComponent,
            dataType: qtn.dataType,
            questionGroupSeq: qtn.questionGroupSeq,
            blockName: qtn.blockName
        }));
        setSourceList(newList);
    };

    const isNotUsed = (question: QuestionProps) => !targetIdent?.includes(question.question);

    const isNotSubsection = (question: QuestionProps) => question.displayComponent !== 1016;

    useEffect(() => {
        const targetsIdentifiers: string[] = [];

        if (allRules) {
            allRules.map((rule: Rule) => {
                rule.targets.map((target: Target) => {
                    targetsIdentifiers.push(target.targetIdentifier ?? '');
                });
            });
            setTargetIdent(targetsIdentifiers);
        }
    }, [allRules]);

    useEffect(() => {
        PageRuleControllerService.getAllRulesUsingGet({
            authorization: authorization(),
            id: page?.id ?? 0
        }).then((response) => {
            setAllRules(response);
        });
    }, []);

    const isSameGroup = (question: QuestionProps) =>
        question.questionGroupSeq === sourceQuestion?.[0]?.questionGroupSeq;

    const handleSourceCases = (question: QuestionProps[]): QuestionProps[] => {
        if (ruleFunction === Rule.ruleFunction.DATE_COMPARE) {
            const filteredList = question.filter(isDate);
            return filteredList;
        } else {
            if (isSource) {
                const filteredList = question.filter(isCoded);
                return filteredList;
            } else {
                let filteredList = question.filter(isNotUsed).filter(isNotStatic);
                if (sourceQuestion?.[0]?.blockName) {
                    filteredList = filteredList.filter(isSameGroup).filter(isNotSubsection);
                }
                return filteredList;
            }
        }
    };

    const handleSourceList = (question: QuestionProps[]) => {
        handleList(handleSourceCases(question));
    };

    useEffect(() => {
        if (page) {
            const result: PagesResponse = {
                id: page.id,
                description: page.description,
                name: page.name,
                root: page.root,
                rules: page.rules,
                status: page.status,
                tabs: []
            };
            page.tabs?.forEach((tab: PagesTab) => {
                const newTab: PagesTab = {
                    id: tab.id,
                    name: tab.name,
                    sections: [],
                    visible: tab.visible,
                    order: tab.order
                };

                tab.sections.forEach((section: PagesSection) => {
                    const newSection: PagesSection = {
                        id: section.id,
                        name: section.name,
                        order: section.order,
                        subSections: [],
                        visible: section.visible
                    };

                    section.subSections.forEach((subsection: any) => {
                        if (subsection.questions.length > 0) {
                            if (handleSourceCases(subsection.questions).length > 0) {
                                newSection?.subSections.push(subsection);
                            }
                        }
                    });

                    if (newSection) {
                        newSection.subSections.length > 0 && newTab?.sections.push(newSection);
                    }
                });
                if (newTab) {
                    newTab.sections.length > 0 && result?.tabs?.push(newTab);
                }
            });
            setFilteredPage(result);
        }
    }, [page, ruleFunction, sourceQuestion]);

    return (
        <ModalComponent
            size="wide"
            modalRef={modalRef}
            isLarge
            modalHeading={header || 'Target questions'}
            modalBody={
                <div className="target-question-modal__container">
                    <h5>{`Please select ${header ? header.toLowerCase() : 'targeted questions'}.`}</h5>
                    <div className="target-question-tabs">
                        <ul className="tabs">
                            {filteredPage?.tabs?.map(({ name }, key) => (
                                <li
                                    key={key}
                                    className={activeTab === key ? 'active' : ''}
                                    onClick={() => setActiveTab(key)}>
                                    {name}
                                </li>
                            ))}
                        </ul>
                    </div>
                    <div className="selected-question list">
                        <div className="title">Selected questions</div>
                        <div className="search-tags">
                            {sourceList
                                .filter((question) => question.selected)
                                .map((question: QuestionProps, index: number) => {
                                    return (
                                        <div className="tag-cover" key={index}>
                                            <Tag className="question-tag">
                                                {question.name} ({question.question})
                                            </Tag>
                                            <UswIcon.Close onClick={() => handleRemove(question.name)} />
                                        </div>
                                    );
                                })}
                        </div>
                    </div>
                    <div className="question-list-container">
                        <div className="tree-section">
                            {filteredPage?.tabs?.[activeTab] &&
                                filteredPage?.tabs?.[activeTab]?.sections?.map((section: any, key) => (
                                    <div key={key} className={`reorder-section ${visible ? '' : 'hidden'}`}>
                                        <div className="reorder-section__tile">
                                            <div
                                                className={`reorder-section__toggle ${
                                                    subsectionOpen == section.id ? 'open' : ''
                                                } `}
                                                onClick={() => setSubsectionOpen(section.id)}>
                                                <Icon name={'group'} size={'m'} />
                                                <span>{section.name}</span>
                                            </div>
                                        </div>
                                        <div
                                            className={`reorder-section__subsections ${
                                                subsectionOpen === section.id ? '' : 'closed'
                                            }`}>
                                            {section.subSections.map((sub: any, id: number) => (
                                                <div
                                                    key={id}
                                                    className="reorder-section__tiles"
                                                    onClick={() => {
                                                        setSource(id);
                                                        handleSourceList(sub.questions);
                                                    }}>
                                                    <div
                                                        className={`reorder-question__tile ${
                                                            id === sourceId && 'selected'
                                                        }`}>
                                                        <Icon name="group" size={'m'} />
                                                        <span>{sub.name}</span>
                                                    </div>
                                                </div>
                                            ))}
                                        </div>
                                    </div>
                                ))}
                        </div>
                        <div className="list-section">
                            {multiSelected && (
                                <Checkbox
                                    onChange={handleSelectAll}
                                    id="hots1"
                                    checked={isSelectedAll}
                                    name={'race1'}
                                    label="Select All"
                                />
                            )}
                            <br />
                            {sourceList.map((list: any, index) => {
                                if (multiSelected) {
                                    return (
                                        <Checkbox
                                            onChange={(e) => handleSelect(e, index)}
                                            key={index}
                                            id={`sourceId${index}`}
                                            checked={list.selected}
                                            name={`sourceName ${index}`}
                                            label={list?.name!}
                                        />
                                    );
                                } else {
                                    return (
                                        <Radio
                                            key={index}
                                            id={`sourceId${index}`}
                                            checked={list.selected}
                                            name={`sourceName ${index}`}
                                            label={list?.name!}
                                            onChange={(e) => handleSelect(e, index)}
                                        />
                                    );
                                }
                            })}
                        </div>
                    </div>
                </div>
            }
            modalFooter={
                <ModalFooter className="padding-2 margin-left-auto footer">
                    <ButtonGroup className="flex-justify-end">
                        <ModalToggleButton
                            modalRef={modalRef}
                            closer
                            outline
                            data-testid="condition-cancel-btn"
                            onClick={() => {
                                setSubsectionOpen(false);
                                setSource(0);
                                setSourceList([]);
                                setActiveTab(0);
                            }}>
                            Cancel
                        </ModalToggleButton>
                        <ModalToggleButton
                            modalRef={modalRef}
                            closer
                            data-testid="section-add-btn"
                            onClick={() => {
                                getList !== undefined && getList(selectedRecord);
                            }}>
                            Continue
                        </ModalToggleButton>
                    </ButtonGroup>
                </ModalFooter>
            }></ModalComponent>
    );
};

export default TargetQuestion;
