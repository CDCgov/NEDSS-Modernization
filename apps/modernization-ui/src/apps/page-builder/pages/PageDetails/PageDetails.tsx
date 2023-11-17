import { Concept, Condition, PagesResponse } from 'apps/page-builder/generated';
import { fetchConditions } from 'apps/page-builder/services/conditionAPI';
import { fetchMMGOptions } from 'apps/page-builder/services/valueSetAPI';
import { SelectInput } from 'components/FormInputs/SelectInput';
import React, { useContext, useEffect, useState } from 'react';
import { Button, Form, Icon } from '@trussworks/react-uswds';
import { UserContext } from 'user';
import './PageDetails.scss';
import { PageDetailsField } from './PageDetailsField';
import { fetchPageDetails } from '../../services/pagesAPI';
import { useParams } from 'react-router-dom';

export type FormValues = {
    conditionIds: string[];
    dataMartName?: string;
    eventType: string;
    messageMappingGuide: string;
    name: string;
    pageDescription?: string;
};
const eventType = [
    { value: 'CON', name: 'Contact Record' },
    { value: 'IXS', name: 'Interview' },
    { value: 'INV', name: 'Investigation' },
    { value: 'ISO', name: 'Lab Isolate Tracking' },
    { value: 'LAB', name: 'Lab Report' },
    { value: 'SUS', name: 'Lab Susceptibility' },
    { value: 'VAC', name: 'Vaccination' }
];

export const PageDetails = () => {
    const { state } = useContext(UserContext);
    const { pageId } = useParams();
    const [page, setPage] = useState<PagesResponse>({ name: '', id: 0, description: '' });
    const [conditions, setConditions] = useState<Condition[]>([]);
    const [mmgs, setMmgs] = useState<Concept[]>([]);
    // const form = useForm<FormValues>({ defaultValues: { eventType: 'INV' }, mode: 'onBlur' });
    const token = `Bearer ${state.getToken()}`;

    useEffect(() => {
        const token = `Bearer ${state.getToken()}`;
        fetchMMGOptions(token)
            .then((data) => {
                setMmgs(data);
            })
            .catch((error: any) => {
                console.log('Error', error);
            });
        fetchConditions(token).then((data) => {
            setConditions(data);
        });
    }, []);
    useEffect(() => {
        if (pageId) {
            fetchPageDetails(token, Number(pageId)).then((data) => {
                setPage(data);
            });
        }
    }, [pageId]);
    return (
        <div className="page-details">
            <Form onSubmit={() => {}}>
                <div className="page-details__form">
                    <div className="page-details__content">
                        <Button type="button" className="page-details-printer" name="Button" outline>
                            <Icon.Print size={3} />
                        </Button>
                        <h2 className="page-title">Page Details</h2>
                        <SelectInput label="Event type" value="INV" options={eventType} disabled />
                        <>
                            <PageDetailsField conditions={conditions} page={page} mmgs={mmgs} />
                        </>
                    </div>
                </div>
            </Form>
        </div>
    );
};
