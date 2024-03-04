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
import { PageRuleControllerService, PagesResponse, Rule, Target } from 'apps/page-builder/generated';
import { RefObject, useState } from 'react';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import './TargetQuestion.scss';
import { Icon } from '../../../../components/Icon/Icon';
import { fetchPageDetails } from '../../services/pagesAPI';
import { authorization } from 'authorization';

type CommonProps = {
    modalRef: RefObject<ModalRef>;
    pageId: string;
    getList: (data: QuestionProps[]) => void;
    multiSelected?: boolean;
    header?: string;
    isSource?: boolean;
    ruleFunction: string;
};

type QuestionProps = {
    id: number;
    question: string;
    name: string;
    selected: boolean;
    valueSet: string;
    displayComponent?: number;
    dataType?: string;
};

const codedDisplayType = [1024, 1025, 1013, 1007, 1031, 1027, 1028];
const staticType = [1014, 1003, 1012, 1030, 1036];

const TargetQuestion = ({
    modalRef,
    pageId,
    getList,
    header,
    multiSelected = true,
    isSource = false,
    ruleFunction
}: CommonProps) => {
    const [activeTab, setActiveTab] = useState(0);
    const [sourceList, setSourceList] = useState<QuestionProps[]>([]);
    const [subsectionOpen, setSubsectionOpen] = useState(false);
    const [sourceId, setSource] = useState(-1);
    const [page, setPage] = useState<PagesResponse>();
    const [allRules, setAllRules] = useState<Rule[]>();
    const [targetIdent, setTargetIdent] = useState<string[]>();

    useEffect(() => {
        if (pageId) {
            fetchPageDetails(authorization(), Number(pageId)).then((data) => {
                setPage(data);
            });
        }
    }, [pageId, modalRef.current?.modalIsOpen]);

    const visible = true;
    const selectedRecord = sourceList.filter((list) => list.selected);
    const isSelectedAll = selectedRecord?.length === sourceList.length;

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
                selected: false
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
            selected: false
        }));
        setSourceList(newList);
    };

    const isNotUsed = (question: QuestionProps) => !targetIdent?.includes(question.question);

    useEffect(() => {
        const targetsIdentifiers: string[] = [];

        if (allRules) {
            console.log('nope');
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
            id: Number(pageId) ?? 0
        }).then((response) => {
            setAllRules(response);
        });
    }, []);

    const handleSourceList = (question: QuestionProps[]) => {
        if (ruleFunction === Rule.ruleFunction.DATE_COMPARE) {
            if (isSource) {
                const filteredList = question.filter(isDate);
                handleList(filteredList);
            } else {
                const filteredList = question.filter(isDate);
                handleList(filteredList);
            }
        } else {
            if (isSource) {
                const filteredList = question.filter(isCoded);
                handleList(filteredList);
            } else {
                const filteredList = question.filter(isNotUsed).filter(isNotStatic);
                handleList(filteredList);
            }
        }
    };

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
                            {page?.tabs?.map(({ name }, key) => (
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
                            {page?.tabs?.[activeTab] &&
                                page?.tabs?.[activeTab]?.sections?.map((section: any, key) => (
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
                        <ModalToggleButton modalRef={modalRef} closer outline data-testid="condition-cancel-btn">
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
