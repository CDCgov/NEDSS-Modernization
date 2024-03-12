import { useEffect, useState } from 'react';
import styles from './TargetQuestion.module.scss';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import {
    PageRuleControllerService,
    PagesQuestion,
    PagesResponse,
    PagesSection,
    PagesSubSection,
    PagesTab,
    Rule,
    Target
} from 'apps/page-builder/generated';
import { handleSourceCases } from './FilterPage';
import { authorization } from 'authorization';
import { Icon } from 'components/Icon/Icon';
import { Button, Checkbox } from '@trussworks/react-uswds';

type Props = {
    ruleFunction?: string;
    sourceQuestion?: PagesQuestion;
    onCancel: () => void;
};

const staticType = [1014, 1003, 1012, 1030, 1036];

export const TargetQuestion = ({ ruleFunction, sourceQuestion, onCancel }: Props) => {
    const [activeTab, setActiveTab] = useState(0);
    const [activeSection, setActiveSection] = useState<number>(0);
    const [filteredPage, setFilteredPage] = useState<PagesResponse>();
    const [targetList, setTargetList] = useState<PagesQuestion[]>([]);
    const [targetIdent, setTargetIdent] = useState<string[]>([]);
    const [allRules, setAllRules] = useState<Rule[]>();

    const { page } = useGetPageDetails();

    const isSameGroup = (question: PagesQuestion) => question.questionGroupSeq === sourceQuestion?.questionGroupSeq;

    const isDate = (question: PagesQuestion) => question.dataType === 'DATE' || question.dataType === 'DATETIME';

    const isNotStatic = (question: PagesQuestion) => !staticType.includes(question.displayComponent ?? 0);

    const isNotUsed = (question: PagesQuestion) => !targetIdent?.includes(question.question ?? '');

    const isNotSubsection = (question: PagesQuestion) => question.displayComponent !== 1016;

    const handleTargetCases = (question: PagesQuestion[], ruleFunction?: string) => {
        if (ruleFunction === Rule.ruleFunction.DATE_COMPARE) {
            const filteredList = question.filter(isDate);
            return filteredList;
        } else {
            let filteredList = question.filter(isNotUsed).filter(isNotStatic);
            if (sourceQuestion?.blockName) {
                filteredList = filteredList.filter(isSameGroup).filter(isNotSubsection);
            }
            return filteredList;
        }
    };

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

    const handleTargetQuestion = (questions: PagesQuestion[]) => {
        setTargetList(handleTargetCases(questions));
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

    return (
        <div className={styles.targetQuestion}>
            <div className={styles.header}>
                <h2>Target question</h2>
            </div>
            <div className={styles.headerMessage}>Please select target question</div>
            <div className={styles.targetTabs}>
                <ul className={styles.tabs}>
                    {filteredPage?.tabs?.map(({ name }, tabKey) => (
                        <li
                            key={tabKey}
                            className={activeTab === tabKey ? styles.active : ''}
                            onClick={() => {
                                setActiveTab(tabKey);
                                setActiveSection(0);
                            }}>
                            {name}
                        </li>
                    ))}
                </ul>
            </div>
            <div className={styles.selectedQuestions}>
                <div className={styles.title}>Selected questions</div>
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
                                                    handleTargetQuestion(subsection.questions);
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
                    <Checkbox
                        onChange={() => console.log('handle select all')}
                        id="hots1"
                        name={'race1'}
                        label="Select All"
                    />
                    {targetList.map((question: PagesQuestion, index) => (
                        <Checkbox
                            onChange={() => console.log('fix this')}
                            key={index}
                            id={`sourceId${index}`}
                            name={`sourceName ${index}`}
                            label={question?.name!}
                        />
                    ))}
                </div>
            </div>
            <div className={styles.footerBtn}>
                <Button
                    type="button"
                    outline
                    onClick={() => {
                        onCancel?.();
                    }}>
                    Cancel
                </Button>
                <Button
                    type="button"
                    onClick={() => {
                        onCancel?.();
                    }}>
                    Continue
                </Button>
            </div>
        </div>
    );
};
