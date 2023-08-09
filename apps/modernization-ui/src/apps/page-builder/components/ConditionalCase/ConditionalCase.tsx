import React, { useContext, useEffect, useRef, useState } from 'react';
import './ConditionalCase.scss';
import { ValuesetLibrary } from '../ValuesetLibrary/ValuesetLibrary';
import { Accordion, Button, ButtonGroup, Icon, ModalToggleButton, ModalRef } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { QuestionControllerService } from '../../generated';
import { UserContext } from '../../../../providers/UserContext';
import { ModalComponent } from '../../../../components/ModalComponent/ModalComponent';

export const ConditionalCase = () => {
    const { state } = useContext(UserContext);
    const authorization = `Bearer ${state.getToken()}`;
    const modalRef = useRef<ModalRef>(null);

    const count = 1;
    const subCount = 2;
    const [activeTab, setActiveTab] = useState('Question');
    const [isEditQtn, setEditQtn] = useState(false);
    const [qtnId, setQtnId] = useState(0);
    const [questions, setQuestions] = useState([]);

    useEffect(() => {
        QuestionControllerService.findAllQuestionsUsingGet({
            authorization
        }).then((resp: any) => {
            setQuestions(resp?.content);
            console.log(resp);
        });
    }, []);
    const deletQtn = () => {};
    const editQtn = (id: any) => {
        setQtnId(id);
        setEditQtn(!isEditQtn);
    };

    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const handleupdateQtn = (id: number, request: any) => {
        QuestionControllerService.updateQuestionUsingPut({
            authorization,
            id,
            request
        }).then((resp: any) => {
            console.log(resp);
        });
    };
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const handleCreateQtn = (request: any) => {
        QuestionControllerService.createQuestionUsingPost({
            authorization,
            request
        }).then((resp: any) => {
            console.log(resp);
        });
    };

    const singleSelect = (
        <svg
            xmlns="http://www.w3.org/2000/svg"
            width="22"
            height="18"
            viewBox="0 0 22 18"
            className="margin-right-2"
            fill="none">
            <path d="M7.42188 1.22363H20.9995" stroke="#3D4551" strokeLinecap="round" strokeLinejoin="round" />
            <path d="M2.48438 1.23637L2.49672 1.22266" stroke="#3D4551" strokeLinecap="round" strokeLinejoin="round" />
            <path d="M2.48438 8.64261L2.49672 8.62891" stroke="#3D4551" strokeLinecap="round" strokeLinejoin="round" />
            <path
                d="M1 15.7898L1.98746 16.7773L4.45611 14.3086"
                stroke="#3D4551"
                strokeLinecap="round"
                strokeLinejoin="round"
            />
            <path d="M7.42188 8.62988H20.9995" stroke="#3D4551" strokeLinecap="round" strokeLinejoin="round" />
            <path d="M7.42188 16.0352H20.9995" stroke="#3D4551" strokeLinecap="round" strokeLinejoin="round" />
        </svg>
    );

    const content = questions.map((que: any, index: number) => (
        <div key={index}>
            {que.id !== qtnId ? (
                <div className="que-wrap">
                    <div className="que-box">
                        <span className="strong-label">{que?.label}</span>
                        <span>
                            <ButtonGroup type="segmented">
                                <Button className="add-btn" type="button" onClick={() => {}} outline>
                                    Yes
                                </Button>
                                <Button className="add-btn" type="button" onClick={() => {}} outline>
                                    No
                                </Button>
                                <Button className="add-btn" type="button" onClick={() => {}} outline>
                                    Unknown
                                </Button>
                            </ButtonGroup>
                        </span>
                    </div>
                    <Icon.Edit className="margin-right-05" size={3} color="gray" onClick={() => editQtn(que.id)} />
                </div>
            ) : (
                <div className="que-wrap que-active ">
                    <div className="que-operator">
                        <ul className="tabs">
                            <li
                                className={activeTab == 'Question' ? 'active' : ''}
                                onClick={() => setActiveTab('Question')}>
                                Question
                            </li>
                            <li className={activeTab == 'Logic' ? 'active' : ''} onClick={() => setActiveTab('Logic')}>
                                Logic
                            </li>
                        </ul>
                        <ul className="icon-btn">
                            <li>
                                <Icon.Edit
                                    style={{ cursor: 'pointer' }}
                                    className="primary-color"
                                    onClick={() => editQtn(que.id)}
                                />
                            </li>
                            <li>
                                <Icon.Delete
                                    style={{ cursor: 'pointer' }}
                                    className="primary-color"
                                    onClick={() => deletQtn()}
                                />
                            </li>
                            <li>
                                <div />
                            </li>
                            <li className="label">Required</li>
                            <li>
                                <label className="switch">
                                    <input type="checkbox" checked />
                                    <span className="slider round"></span>
                                </label>
                            </li>
                        </ul>
                    </div>
                    <div className="que-content">
                        <div className="que-list">
                            {/* Where did the mother give birth? */}
                            <div className="strong-label">{que?.label}</div>
                            <div className="small-body-text">Please answer to the best of your knowledge.</div>
                        </div>
                        <div className="que-add-valueset">
                            <div className="label">
                                {singleSelect}
                                {/* <Icon.List size={4} color="gray" className="margin-right-2" /> */}
                                Radio boxes (single select)
                            </div>
                            <ModalToggleButton
                                className="add-btn"
                                modalRef={modalRef}
                                type="button"
                                onClick={() => {
                                    localStorage.setItem('selectedQuestion', que.id);
                                }}>
                                Add value set
                            </ModalToggleButton>
                        </div>
                    </div>
                </div>
            )}
        </div>
    ));

    const subItems: AccordionItemProps[] = [
        {
            title: (
                <div className="sub-title">
                    <div>
                        <h2 className="sub-heading">
                            Add Subsection <span className={'count'}>{subCount}</span>
                        </h2>
                        <p className="desc">Question group for patient</p>
                    </div>
                    <span className="header-actions">
                        <Button className="add-btn" type="button" onClick={() => {}}>
                            Add Questions
                        </Button>
                        <Icon.MoreVert className="primary-color" size={5} />
                        <Icon.ExpandLess className="primary-color" size={5} />
                    </span>
                </div>
            ),
            content,
            expanded: true,
            id: '1',
            headingLevel: 'h4',
            className: 'accordion-item'
        }
    ];
    const Items: AccordionItemProps[] = [
        {
            title: (
                <div className="main-title">
                    <div id="header-title">
                        <h2 className="heading" data-testid="header-title">
                            Jurisdictional Questions <span className={'count'}>{count}</span>
                        </h2>
                        <p className="desc">Please fill out all fields in this section</p>
                    </div>
                    <span className="header-actions">
                        <Button className="add-btn" type={'submit'} onClick={() => {}}>
                            Add Subsection
                        </Button>
                        <Icon.MoreVert className="primary-color" size={5} />
                        <Icon.ExpandLess className="primary-color" size={5} />
                    </span>
                </div>
            ),
            content: <Accordion bordered={false} items={subItems} />,
            expanded: true,
            id: '11',
            headingLevel: 'h4',
            className: 'accordion-item'
        }
    ];
    return (
        <div className="question-container">
            <Accordion bordered={false} items={Items} />
            <ModalComponent
                isLarge
                modalRef={modalRef}
                modalHeading={'Add value set'}
                modalBody={<ValuesetLibrary modalRef={modalRef} hideTabs types="recent" />}
            />
        </div>
    );
};
