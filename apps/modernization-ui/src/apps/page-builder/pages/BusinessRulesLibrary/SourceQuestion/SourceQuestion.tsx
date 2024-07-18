import { useEffect, useState } from 'react';
import styles from './SourceQuestion.module.scss';
import { PagesQuestion, PagesSection, PagesSubSection, PagesTab, Rule } from 'apps/page-builder/generated';
import { Icon } from 'components/Icon/Icon';
import { Button, Radio, Tag, Icon as UswIcon } from '@trussworks/react-uswds';
import { useGetSourceQuestion } from 'apps/page-builder/hooks/api/useGetSourceQuestions';
import { useGetPageDetails } from 'apps/page-builder/page/management';

type Props = {
    ruleFunction?: Rule.ruleFunction;
    onSubmit: (question?: PagesQuestion) => void;
    onCancel: () => void;
};

export const SourceQuestion = ({ ruleFunction, onSubmit, onCancel }: Props) => {
    const { page } = useGetPageDetails();
    const [activeTab, setActiveTab] = useState(0);
    const [activeSection, setActiveSection] = useState<number>(0);
    const [activeSubsection, setActiveSubsection] = useState<number>(0);
    const [sourceList, setSourceList] = useState<PagesQuestion[]>([]);
    const [questionSelect, setQuestionSelect] = useState<PagesQuestion | undefined>(undefined);

    const { fetch, response } = useGetSourceQuestion();

    const handleSourceQuestion = (questions: PagesQuestion[]) => {
        setSourceList(questions);
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
        if (ruleFunction && page) {
            fetch(page.id, { ruleFunction: ruleFunction });
        }
    }, [ruleFunction, page]);

    const handleRemove = () => {
        setQuestionSelect(undefined);
    };

    return (
        <div className={styles.sourceQuestion}>
            <div className={styles.headerTitle}>
                <h2>Source question</h2>
            </div>
            <div className={styles.header}>
                <div className={styles.headerMessage}>Please select source question</div>
                <div className={styles.sourceTabs}>
                    <ul className={styles.tabs}>
                        {response?.tabs?.map((tab: PagesTab, tabKey) => (
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
                    <div className={styles.title}>Selected questions: </div>
                    {questionSelect && (
                        <div className={styles.content}>
                            <Tag className={styles.selectedQuestion}>
                                {questionSelect.name} ({questionSelect.question})
                                <div className={styles.closeBtn}>
                                    <UswIcon.Close onClick={() => handleRemove()} size={3} />
                                </div>
                            </Tag>
                        </div>
                    )}
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
                                            data-testid="listedSections"
                                            onClick={() => {
                                                activeSection === section.id
                                                    ? setActiveSection(0)
                                                    : setActiveSection(section.id);
                                                setSourceList([]);
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
                                                    data-testid="listedSubsections"
                                                    onClick={() => {
                                                        if (activeSubsection === subsection.id) {
                                                            setActiveSubsection(0);
                                                            setSourceList([]);
                                                        } else {
                                                            handleSourceQuestion(subsection.questions);
                                                            setActiveSubsection(subsection.id);
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
                        {sourceList.map((question: PagesQuestion, key) => (
                            <div className={styles.question} key={key}>
                                <Radio
                                    id={`sourceId${key}`}
                                    checked={questionSelect?.id === question.id}
                                    name={`sourceName ${question.id}`}
                                    label={question.name}
                                    onChange={() => setQuestionSelect(question)}
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
                    data-testid="sourceQuestionModalCancelBtn"
                    onClick={() => {
                        onReset();
                        onCancel?.();
                    }}>
                    Cancel
                </Button>
                <Button
                    type="button"
                    data-testid="sourceQuestionModalContinueBtn"
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
