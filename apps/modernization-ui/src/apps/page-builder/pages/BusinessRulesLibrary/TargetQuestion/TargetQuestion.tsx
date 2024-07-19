import React, { useEffect, useState } from 'react';
import styles from './TargetQuestion.module.scss';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import { PagesQuestion, PagesSection, PagesSubSection, Rule } from 'apps/page-builder/generated';
import { Icon } from 'components/Icon/Icon';
import { Button, Checkbox, ErrorMessage, Tag, Icon as UswIcon } from '@trussworks/react-uswds';
import { useGetTargetQuestions } from 'apps/page-builder/hooks/api/useGetTargetQuestions';

type Props = {
    ruleFunction?: Rule.ruleFunction;
    sourceQuestion: PagesQuestion | undefined;
    onSubmit: (questions: PagesQuestion[]) => void;
    onCancel: () => void;
    editTargetQuestion?: PagesQuestion[];
    selectedTargetQuestion?: PagesQuestion[];
};

export const TargetQuestion = ({
    ruleFunction,
    sourceQuestion,
    onCancel,
    onSubmit,
    editTargetQuestion,
    selectedTargetQuestion
}: Props) => {
    const [activeTab, setActiveTab] = useState(0);
    const [activeSection, setActiveSection] = useState<number>(0);
    const [activeSubsection, setActiveSubsection] = useState<number>(0);
    const [targetList, setTargetList] = useState<PagesQuestion[]>([]);
    const [selectedList, setSelectedList] = useState<PagesQuestion[]>([]);
    const [formError, setFormError] = useState<boolean>(false);

    const { fetch, response } = useGetTargetQuestions();

    const { page } = useGetPageDetails();

    const handleRemove = (question: PagesQuestion) => {
        setSelectedList(selectedList.filter((qtn) => qtn.id !== question.id));
    };

    useEffect(() => {
        if (ruleFunction && sourceQuestion) {
            if (editTargetQuestion) {
                fetch(page?.id ?? 0, {
                    ruleFunction: ruleFunction,
                    sourceQuestion: sourceQuestion,
                    targetQuestion: editTargetQuestion
                });
            } else {
                fetch(page?.id ?? 0, {
                    ruleFunction: ruleFunction,
                    sourceQuestion: sourceQuestion
                });
                setSelectedList([]);
            }
        }
    }, [ruleFunction, JSON.stringify(sourceQuestion)]);

    useEffect(() => {
        if (selectedList.length >= 10) {
            setFormError(true);
        } else {
            setFormError(false);
        }
    }, [selectedList]);

    const handleSelect = (question: PagesQuestion, e: React.ChangeEvent<HTMLInputElement>) => {
        const tempList = [...selectedList];

        if (e.target.checked) {
            tempList.push(question);
            setSelectedList(tempList);
        } else {
            const index = tempList.findIndex((qtn) => qtn.id === question.id);
            tempList.splice(index, 1);
            setSelectedList(tempList);
        }
    };

    const handleSelectAll = (questions: PagesQuestion[], e: React.ChangeEvent<HTMLInputElement>) => {
        const tempList = [...selectedList];
        if (e.target.checked) {
            questions?.map((question) => {
                if (!tempList.find((qtn) => qtn.id === question.id)) {
                    tempList.push(question);
                }
            });
            setSelectedList(tempList);
        } else {
            questions.map((question) => {
                const index = tempList.findIndex((qtn) => qtn.id === question.id);
                tempList.splice(index, 1);
            });
            setSelectedList(tempList);
        }
    };

    useEffect(() => {
        if (selectedTargetQuestion) {
            setSelectedList(selectedTargetQuestion);
        }
    }, [JSON.stringify(selectedTargetQuestion)]);

    const handleTargetQuestion = (questions: PagesQuestion[]) => {
        setTargetList(questions);
    };

    const onReset = () => {
        setActiveTab(0);
        setActiveSection(0);
        setActiveSubsection(0);
        setTargetList([]);
        setSelectedList([]);
    };

    return (
        <div className={styles.targetQuestion}>
            <div className={styles.headerTitle}>
                <h2>Target question</h2>
            </div>
            <div className={styles.header}>
                <div className={styles.headerMessage}>Please select target question</div>
                <div className={styles.targetTabs}>
                    <ul className={styles.tabs}>
                        {response?.tabs?.map(({ name }, tabKey) => (
                            <li
                                key={tabKey}
                                className={activeTab === tabKey ? styles.active : ''}
                                onClick={() => {
                                    setActiveTab(tabKey);
                                    setActiveSection(0);
                                    setTargetList([]);
                                }}>
                                {name}
                            </li>
                        ))}
                    </ul>
                </div>
                <div className={styles.selectedQuestions}>
                    <div className={styles.title}>Selected questions:</div>
                    <div className={styles.content}>
                        {selectedList.map((question, key) => (
                            <div key={key} className={styles.selectedQuestion}>
                                <Tag className={styles.selectedQuestion}>
                                    {question.name ?? question.componentName} ({question.question})
                                </Tag>
                                <UswIcon.Close onClick={() => handleRemove(question)} />
                            </div>
                        ))}
                    </div>
                    <div>
                        {formError ? (
                            <ErrorMessage>
                                <p>You can not select more than 10 target fields.</p>
                            </ErrorMessage>
                        ) : null}
                    </div>
                </div>
            </div>
            <div className={styles.body}>
                <div className={styles.content}>
                    <div className={styles.sections}>
                        {response?.tabs?.[activeTab] &&
                            response?.tabs?.[activeTab]?.sections.map((section: PagesSection, key) => (
                                <div key={key}>
                                    <div key={key} className={styles.section}>
                                        <div
                                            className={styles.sectionToggle}
                                            data-testid="listedSectionsTarget"
                                            onClick={() => {
                                                activeSection === section.id
                                                    ? setActiveSection(0)
                                                    : setActiveSection(section.id);
                                                setTargetList([]);
                                            }}>
                                            <Icon name={'group'} size={'m'} />
                                            <span className={activeSection === section.id ? styles.active : ''}>
                                                {section.name}
                                            </span>
                                        </div>
                                    </div>
                                    {activeSection === section.id && (
                                        <div className={styles.subsections}>
                                            {section.subSections.map((subsection: PagesSubSection, id) => (
                                                <div
                                                    key={id}
                                                    className={styles.subsection}
                                                    data-testid="listedSubsectionsTarget"
                                                    onClick={() => {
                                                        if (activeSubsection === subsection.id) {
                                                            setActiveSubsection(0);
                                                            setTargetList([]);
                                                        } else {
                                                            setActiveSubsection(subsection.id);
                                                            handleTargetQuestion(subsection.questions);
                                                        }
                                                    }}>
                                                    <Icon name={'group'} size={'m'} />
                                                    <span
                                                        className={
                                                            activeSubsection === subsection.id ? styles.active : ''
                                                        }>
                                                        {subsection.name}
                                                    </span>
                                                </div>
                                            ))}
                                        </div>
                                    )}
                                </div>
                            ))}
                    </div>
                    <div className={styles.questionsList}>
                        {targetList.length > 0 && (
                            <div className={styles.selectAll}>
                                <Checkbox
                                    onChange={(e) => handleSelectAll(targetList, e)}
                                    id="hots1"
                                    name={'race1'}
                                    label="Select All"
                                />
                            </div>
                        )}
                        {targetList.map((question: PagesQuestion, index) => (
                            <div className={styles.question} key={index}>
                                <Checkbox
                                    onChange={(e) => handleSelect(question, e)}
                                    checked={selectedList.find((qtn) => qtn.id === question.id) !== undefined}
                                    id={`sourceId${index}`}
                                    name={`sourceName ${index}`}
                                    label={question?.name ? question.name : question.componentName}
                                    disabled={formError}
                                />
                            </div>
                        ))}
                    </div>
                </div>
            </div>
            <div className={styles.footerBtn}>
                <Button
                    type="button"
                    outline
                    data-testid="targetQuestionModalCancelBtn"
                    onClick={() => {
                        onReset?.();
                        onCancel?.();
                    }}>
                    Cancel
                </Button>
                <Button
                    type="button"
                    onClick={() => {
                        onSubmit(selectedList);
                        onReset?.();
                        onCancel?.();
                    }}
                    data-testid="targetQuestionModalContinueBtn"
                    disabled={selectedList.length > 10}>
                    Continue
                </Button>
            </div>
        </div>
    );
};
