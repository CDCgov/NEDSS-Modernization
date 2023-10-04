package gov.cdc.nbs.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;



@RestController
@RequestMapping("/nbs/api/configuration")
public class ConfigurationController {

    @Autowired
    public Configuration configuration;

    @ApiImplicitParam(
            name = "Authorization",
            required = true,
            paramType = "header",
            dataTypeClass = String.class)
    @GetMapping
    public Configuration getConfiguration() {
        return configuration;
    }
}
