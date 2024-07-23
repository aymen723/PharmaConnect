package dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "up_time")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"pharmacy"})
@Builder
public class UpTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "up_time_id")
    private Long id;


    @Column(name = "up_time_day")
    @Enumerated(EnumType.STRING)
    private DayOfWeek day;


    @Column(name = "up_time_open")
    private LocalTime openTime;


    @Column(name = "up_time_close")
    private LocalTime closeTime;

    private boolean working = true;


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Pharmacy.class)
    @JsonIgnore
    private Pharmacy pharmacy;


}
