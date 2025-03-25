import { useEffect, useState } from 'react';
import { Pass } from './model/Pass';
import { Config } from 'config';

export const useMatchConfiguration = () => {
    const [passes, setPasses] = useState<Pass[]>([]);
    const [selectedPass, setSelectedPass] = useState<Pass | undefined>();
    const [error, setError] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);

    const selectPass = (pass?: Pass) => {
        // a new, unsaved pass was previously selected. Remove it from list
        if (selectedPass !== undefined && selectedPass.id === undefined) {
            setPasses(passes.filter((p) => p.id !== undefined));
        }

        setSelectedPass(pass);
    };

    const addPass = () => {
        // adds a new pass to the beginning of the pass list
        const newPass: Pass = {
            name: 'New pass configuration',
            blockingCriteria: [],
            matchingCriteria: [],
            active: false
        };
        setPasses([newPass, ...passes.filter((p) => p.id !== undefined)]);
        setSelectedPass(newPass);
    };

    const fetchConfiguration = async () => {
        setLoading(true);

        fetch(`${Config.deduplicationUrl}/api/configuration`, {
            method: 'GET',
            headers: {
                Accept: 'application/json'
            }
        })
            .then((response) => {
                response
                    .json()
                    .then((algorithm) => {
                        setSelectedPass(undefined);
                        setPasses(algorithm.passes);
                    })
                    .catch(() => {
                        console.error('Failed to extract json for pass configuration.');
                    });
            })
            .catch((error) => {
                console.error(error);
                setError('Failed to retrieve pass configuration');
            });

        setLoading(false);
    };

    const deletePass = (id?: number, successCallback?: () => void) => {
        setLoading(true);
        // are we deleting the unsaved new pass
        if (id === undefined) {
            setPasses(passes.filter((p) => p.id !== undefined));
            successCallback?.();
        } else {
            fetch(`${Config.deduplicationUrl}/api/configuration/pass/${id}`, {
                method: 'DELETE',
                headers: {
                    Accept: 'application/json'
                }
            })
                .then((response) => {
                    response
                        .json()
                        .then((algorithm) => setPasses(algorithm.passes))
                        .catch(() => {
                            console.error('Failed to extract json for pass configuration.');
                        });
                })
                .catch((error) => {
                    console.error(error);
                    setError('Failed to delete pass');
                });

            successCallback?.();
        }
        setSelectedPass(undefined);
        setLoading(false);
    };

    const savePass = (pass: Pass, successCallback?: (passName: string) => void) => {
        setLoading(true);
        if (pass.id) {
            updatePass(pass, successCallback);
        } else {
            createPass(pass, successCallback);
        }
        setLoading(false);
    };

    const updatePass = (pass: Pass, successCallback?: (passName: string) => void) => {
        makeUpdateRequest(pass, (passes) => {
            setPasses(passes);
            const updatedPass = passes.find((p) => p.id === pass.id);
            setSelectedPass(updatedPass);
            successCallback?.(updatedPass?.name ?? '');
        });
    };

    const updatePassName = (pass: Pass, successCallback?: (passName: string) => void) => {
        makeUpdateRequest(pass, (passes) => {
            if (selectedPass !== undefined && selectedPass.id === undefined) {
                setPasses([selectedPass, ...passes]);
            } else {
                setPasses(passes);
            }
            const updatedPass = passes.find((p) => p.id === pass.id);
            successCallback?.(updatedPass?.name ?? '');
        });
    };

    const makeUpdateRequest = (pass: Pass, onResponse: (passes: Pass[]) => void) => {
        fetch(`${Config.deduplicationUrl}/api/configuration/pass/${pass.id}`, {
            method: 'PUT',
            headers: {
                Accept: 'application/json',
                'Content-type': 'application/json'
            },
            body: JSON.stringify(pass)
        })
            .then((response) => {
                response
                    .json()
                    .then((algorithm: { passes: Pass[] }) => onResponse(algorithm.passes))
                    .catch(() => {
                        console.error('Failed to extract json for pass configuration.');
                    });
            })
            .catch((error) => {
                console.error(error);
                setError('Failed to save pass');
            });
    };

    const createPass = (pass: Pass, successCallback?: (passName: string) => void) => {
        makeCreateRequest(pass, (passes) => {
            setPasses(passes);
            const newPass = passes[passes.length - 1];
            setSelectedPass(newPass);
            successCallback?.(newPass.name);
        });
    };

    const makeCreateRequest = (pass: Pass, onResponse: (passes: Pass[]) => void) => {
        fetch(`${Config.deduplicationUrl}/api/configuration/pass`, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-type': 'application/json'
            },
            body: JSON.stringify(pass)
        })
            .then((response) => {
                response
                    .json()
                    .then((algorithm: { passes: Pass[] }) => onResponse(algorithm.passes))
                    .catch(() => {
                        console.error('Failed to extract json for pass configuration.');
                    });
            })
            .catch((error) => {
                console.error(error);
                setError('Failed to save pass');
            });
    };

    useEffect(() => {
        fetchConfiguration();
    }, []);

    return {
        loading,
        error,
        passes,
        selectedPass,
        fetchConfiguration,
        deletePass,
        savePass,
        updatePassName,
        selectPass,
        addPass
    };
};
