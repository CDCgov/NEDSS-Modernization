package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "NBS_Release")
public class NbsRelease {
    @Id
    @Column(name = "NBS_release_uid", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 250)
    private String name;

    @Column(name = "Version", nullable = false, length = 25)
    private String version;

    @Column(name = "Description", nullable = false, length = 4000)
    private String description;

    @Column(name = "Publish_date", nullable = false)
    private Instant publishDate;

    @Column(name = "Deployment_Date", nullable = false)
    private Instant deploymentDate;

}