import { useEffect, useState } from 'react';
import styles from './SourceQuestion.module.scss';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import {
    PagesQuestion,
    PagesResponse,
    PagesSection,
    PagesSubSection,
    PagesTab,
    Rule
} from 'apps/page-builder/generated';
import { Icon } from 'components/Icon/Icon';
import { Button, Radio, Tag, Icon as UswIcon } from '@trussworks/react-uswds';

type Props = {
    ruleFunction?: string;
    onSubmit: (question?: PagesQuestion) => void;
    onCancel: () => void;
};

export const SourceQuestion = ({ ruleFunction, onSubmit, onCancel }: Props) => {
    const { page } = useGetPageDetails();
    const [filteredPage, setFilteredPage] = useState<PagesResponse>();
    const [activeTab, setActiveTab] = useState(0);
    const [activeSection, setActiveSection] = useState<number>(0);
    const [activeSubsection, setActiveSubsection] = useState<number>(0);
    const [sourceList, setSourceList] = useState<PagesQuestion[]>([]);
    const [questionSelect, setQuestionSelect] = useState<PagesQuestion | undefined>(undefined);

    const codedDisplayType = [1024, 1025, 1013, 1007, 1031, 1027, 1028];

    const isCoded = (question: PagesQuestion) => codedDisplayType.includes(question.displayComponent ?? 0);

    const isDate = (question: PagesQuestion) => question.dataType === 'DATE' || question.dataType === 'DATETIME';

    const handleSourceCases = (question: PagesQuestion[]) => {
        if (ruleFunction === Rule.ruleFunction.DATE_COMPARE) {
            const filteredList = question.filter(isDate);
            return filteredList;
        } else {
            const filteredList = question.filter(isCoded);
            return filteredList;
        }
    };

    const handleSourceQuestion = (questions: PagesQuestion[]) => {
        setSourceList(handleSourceCases(questions));
    };

    const onReset = () => {
        setActiveTab(0);
        setActiveSection(0);
        setActiveSubsection(0);
        setSourceList([]);
        setQuestionSelect(undefined);
    };

    const onContinue = (question?: PagesQuestion) => {
        onReset();
        onSubmit(question);
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

                    section.subSections.forEach((subsection: PagesSubSection) => {
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
    }, [page, ruleFunction]);

    const handleRemove = () => {
        setQuestionSelect(undefined);
    };

    return (
        <div className={styles.sourceQuestion}>
            <div className={styles.header}>
                <h2>Source question</h2>
            </div>
            <div className={styles.headerMessage}>Please select source question</div>
            <div className={styles.sourceTabs}>
                <ul className={styles.tabs}>
                    {page?.tabs?.map((tab: PagesTab, tabKey) => (
                        <li
                            key={tabKey}
                            className={activeTab === tabKey ? styles.active : ''}
                            onClick={() => {
                                setActiveTab(tabKey);
                                setActiveSection(0);
                                setSourceList([]);
                            }}>
                            {tab.name}
                        </li>
                    ))}
                </ul>
            </div>
            <div className={styles.selectedQuestions}>
                <div className={styles.title}>Selected questions</div>
                {questionSelect && (
                    <div className={styles.content}>
                        <Tag className={styles.selectedQuestion}>
                            {questionSelect.name} ({questionSelect.question})
                        </Tag>
                        <UswIcon.Close onClick={() => handleRemove()} />
                    </div>
                )}
            </div>
            <div className={styles.content}>
                <div className={styles.sections}>
                    {filteredPage?.tabs?.[activeTab] &&
                        filteredPage?.tabs?.[activeTab]?.sections.map((section: PagesSection, key) => (
                            <div key={key}>
                                <div key={key} className={styles.section}>
                                    <div
                                        className={styles.sectionToggle}
                                        onClick={() =>
                                            activeSection === section.id
                                                ? setActiveSection(0)
                                                : setActiveSection(section.id)
                                        }>
                                        <Icon name={'group'} size={'m'} />
                                        <span>{section.name}</span>
                                    </div>
                                </div>
                                {activeSection === section.id && (
                                    <div className={styles.subsections}>
                                        {section.subSections.map((subsection: PagesSubSection, id) => (
                                            <div
                                                key={id}
                                                className={styles.subsection}
                                                onClick={() => {
                                                    if (activeSubsection === subsection.id) {
                                                        setSourceList([]);
                                                    } else {
                                                        setActiveSubsection(subsection.id);
                                                        handleSourceQuestion(subsection.questions);
                                                    }
                                                }}>
                                                <Icon name={'group'} size={'m'} />
                                                <span>{subsection.name}</span>
                                            </div>
                                        ))}
                                    </div>
                                )}
                            </div>
                        ))}
                </div>
                <div className={styles.questionsList}>
                    {sourceList.map((question: PagesQuestion, key) => (
                        <Radio
                            key={key}
                            id={`sourceId${key}`}
                            checked={questionSelect?.id === question.id}
                            name={`sourceName ${question.id}`}
                            label={question.name}
                            onChange={() => setQuestionSelect(question)}
                        />
                    ))}
                </div>
            </div>

            <div className={styles.footerBtn}>
                <Button
                    type="button"
                    outline
                    onClick={() => {
                        onReset();
                        onCancel?.();
                    }}>
                    Cancel
                </Button>
                <Button
                    type="button"
                    onClick={() => {
                        onContinue(questionSelect);
                        onCancel?.();
                    }}>
                    Continue
                </Button>
            </div>
        </div>
    );
};
