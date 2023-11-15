import { Button, Icon, ModalRef, Tag } from '@trussworks/react-uswds';
import { Template, TemplateControllerService } from 'apps/page-builder/generated';
import { Spinner } from 'components/Spinner/Spinner';
import React, { useContext, useState } from 'react';
import { UserContext } from 'user';
import { AlertBanner } from '../AlertBanner/AlertBanner';
import './ImportTemplate.scss';

type ImportTemplateProps = {
    onTemplateCreated: (template: Template) => void;
    modal: React.RefObject<ModalRef>;
};
export const ImportTemplate = ({ modal, onTemplateCreated }: ImportTemplateProps) => {
    const { state } = useContext(UserContext);
    const [file, setFile] = useState<File | undefined>();
    const [isError, setIsError] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');
    const [loading, setLoading] = useState(false);

    const handleFileChange = (event: any) => {
        const selectedFile = event.target.files[0];
        // Checking if the file type is allowed or not
        const allowedTypes = ['xml'];
        if (!allowedTypes.includes(selectedFile?.type)) {
            setIsError(true);
            setErrorMsg('Only XML files are allowed.');
        }
        setIsError(false);
        setFile(selectedFile);
    };

    const handleSubmit = () => {
        setErrorMsg('');

        // Checking if the file has been selected
        if (!file) {
            setIsError(true);
            setErrorMsg('Please select a file.');
            return;
        }
        setLoading(true);
        TemplateControllerService.importTemplateUsingPost({
            authorization: `Bearer ${state.getToken()}`,
            fileInput: file
        })
            .then(async (response) => {
                onTemplateCreated(response);
                setLoading(false);
                setFile(undefined);
                modal.current?.toggleModal();
            })
            .catch((error) => {
                setIsError(true);
                setErrorMsg(error.body?.message ?? 'An error occured');
                setLoading(false);
            });
        setIsError(false);
    };

    const handleCancel = () => {
        setFile(undefined);
        setIsError(false);
        modal.current?.toggleModal();
    };

    return (
        <div className="import-template">
            {loading ? <Spinner /> : null}
            <div className="drop-container">
                {isError && errorMsg ? (
                    <div className="banner">
                        <AlertBanner type="error">{errorMsg}</AlertBanner>
                    </div>
                ) : null}

                <div className="heading">
                    <label>Import a new template</label>
                </div>
                <label htmlFor="importTempId">
                    <input
                        value={''}
                        onChange={handleFileChange}
                        name="fileInput"
                        type="file"
                        id="importTempId"
                        accept="text/xml"
                        hidden
                    />

                    <div className="drop-area">
                        <div className="display-flex gap-10">
                            {file ? (
                                <div className="tag-cover">
                                    <Tag background="#005EA2">{file?.name}</Tag>
                                </div>
                            ) : null}
                        </div>
                        <Icon.Logout size={4} />
                        <label htmlFor="importTempId">
                            Drag & drop or <span>Choose file</span> to upload
                        </label>
                    </div>
                </label>
            </div>
            <div className="button-container">
                <Button
                    disabled={file === undefined || loading}
                    className="submit-btn"
                    type="button"
                    onClick={handleSubmit}>
                    Import
                </Button>
                <Button className="cancel-btn" type="button" onClick={handleCancel}>
                    Cancel
                </Button>
            </div>
        </div>
    );
};
