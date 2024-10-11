-- Function to update 'updated_at' timestamps on update
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Table 1: Data Elements
CREATE TABLE data_elements (
                               id SERIAL PRIMARY KEY,
                               name VARCHAR(255) NOT NULL,   -- Field name, e.g., 'first_name'
                               label VARCHAR(255) NOT NULL,  -- Descriptive label for UI
                               category VARCHAR(255) NOT NULL, -- Category like 'Demographics', 'Location'
                               active BOOLEAN DEFAULT true,  -- Indicates if this field is active or not
                               m DECIMAL(10, 5),             -- M probability value
                               u DECIMAL(10, 5),             -- U probability value
                               threshold DECIMAL(10, 5),     -- Threshold value for the data element
                               odds_ratio DECIMAL(10, 5),    -- Odds ratio for the element
                               log_odds DECIMAL(10, 5),      -- Log of the odds ratio
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table 2: Methods
CREATE TABLE methods (
                         id SERIAL PRIMARY KEY,
                         value VARCHAR(255) NOT NULL,  -- Method value (e.g., 'exact', 'phonetic')
                         name VARCHAR(255) NOT NULL,   -- Method name (e.g., 'Exact Match')
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table 3: Pass Configurations
CREATE TABLE pass_configurations (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,       -- Name of the pass configuration
                                     description TEXT,                 -- Description of the pass configuration
                                     active BOOLEAN DEFAULT true,      -- Whether the configuration is active
                                     lower_bound DECIMAL(10, 5),       -- Lower bound for matching
                                     upper_bound DECIMAL(10, 5),       -- Upper bound for matching
                                     total_log_odds DECIMAL(10, 5),    -- Total log odds for this configuration
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table 4: Blocking Criteria
CREATE TABLE blocking_criteria (
                                   id SERIAL PRIMARY KEY,
                                   pass_configuration_id INT REFERENCES pass_configurations(id) ON DELETE CASCADE, -- Reference to the pass configuration
                                   data_element_id INT REFERENCES data_elements(id),  -- Reference to the data element used for blocking
                                   method_id INT REFERENCES methods(id),              -- Reference to the method used for blocking
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table 5: Matching Criteria
CREATE TABLE matching_criteria (
                                   id SERIAL PRIMARY KEY,
                                   pass_configuration_id INT REFERENCES pass_configurations(id) ON DELETE CASCADE, -- Reference to the pass configuration
                                   data_element_id INT REFERENCES data_elements(id),  -- Reference to the data element used for matching
                                   method_id INT REFERENCES methods(id),              -- Reference to the method used for matching
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table 6: Configuration Log
CREATE TABLE configuration_log (
                                   id SERIAL PRIMARY KEY, -- Auto-incremented unique ID for each log entry
                                   pass_configuration_id INT REFERENCES pass_configurations(id) ON DELETE SET NULL, -- Reference to the configuration being changed, can be null if the config is deleted
                                   field_name VARCHAR(255) NOT NULL, -- The name of the field being changed (e.g., 'name', 'description', etc.)
                                   old_value TEXT, -- The previous value before the change
                                   new_value TEXT, -- The new value after the change
                                   changed_by VARCHAR(255), -- The user or system that made the change (optional)
                                   change_type VARCHAR(50) NOT NULL, -- Type of change: 'CREATE', 'UPDATE', 'DELETE'
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Timestamp when the change occurred
);

-- Triggers to update 'updated_at' on update

CREATE TRIGGER update_data_elements_timestamp
    BEFORE UPDATE ON data_elements
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER update_methods_timestamp
    BEFORE UPDATE ON methods
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER update_pass_configurations_timestamp
    BEFORE UPDATE ON pass_configurations
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER update_blocking_criteria_timestamp
    BEFORE UPDATE ON blocking_criteria
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER update_matching_criteria_timestamp
    BEFORE UPDATE ON matching_criteria
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();
