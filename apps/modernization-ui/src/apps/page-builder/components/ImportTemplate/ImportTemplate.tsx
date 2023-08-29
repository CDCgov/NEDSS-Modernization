import React, { useState } from 'react';
import { Button, Icon, Tag } from '@trussworks/react-uswds';
import './ImportTemplate.scss';

export const ImportTemplate = () => {
    const [file, setFile] = useState(null);
    const [files, setFiles] = useState<any>([]);
    const [isError, setIsError] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');
    const [isSuccess, setIsSuccess] = useState(false);

    const handleFileChange = (event: any) => {
        const selectedFile = event.target.files[0];
        setIsSuccess(false);
        // Checking if the file type is allowed or not
        const allowedTypes = ['xml'];
        if (!allowedTypes.includes(selectedFile?.type)) {
            setIsError(true);
            setErrorMsg('Only XML files are allowed.');
        }
        setIsError(false);
        setFiles([...files, selectedFile]);
        setFile(selectedFile);
    };

    const handleSubmit = (event: any) => {
        event.preventDefault();

        if (isError) return;
        setErrorMsg('');

        // Checking if the file has been selected
        if (!file) {
            setIsError(true);
            setErrorMsg('Please select a file.');
            return;
        }

        setIsError(false);
        setIsSuccess(true);
    };

    return (
        <div className="import-template">
            <Button className="usa-button--unstyled close-btn" type={'button'} onClick={() => {}}>
                <Icon.Close />
            </Button>
            <h3 className="main-header-title">
                <Button className="usa-button--unstyled back-btn" type={'button'} onClick={() => {}}>
                    <Icon.ArrowBack />
                </Button>
                <span data-testid="header-title">Import template</span>
            </h3>
            <div className="drop-container">
                <div className="heading">
                    <label>Import a new template</label>
                </div>
                <label onChange={handleFileChange} htmlFor="importTempId">
                    <input name="" type="file" id="importTempId" accept="text/xml" hidden />

                    <div className="drop-area">
                        <div className="display-flex gap-10">
                            {files.map((fil: any, index: number) => (
                                <div className="tag-cover" key={index}>
                                    <Tag background="#005EA2">{fil?.name}</Tag>
                                </div>
                            ))}
                        </div>
                        <Icon.Logout size={4} />
                        <label htmlFor="importTempId">
                            Drag & drop or <span>Choose file</span> to upload
                        </label>
                    </div>
                </label>
            </div>
            <div>
                {isError && <div className="error-text">{errorMsg}</div>}
                <Button className="submit-btn" type="button" onClick={handleSubmit}>
                    Import
                </Button>
                <Button className="cancel-btn" type="button">
                    Cancel
                </Button>
                {isSuccess && <div className="success-text">Valid File Type</div>}
            </div>
        </div>
    );
};
