package com.test.test_app.domain;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity @Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "track_info")
public class TrackInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "producer_cur_cnt", updatable = false)
    private Integer producerCurCnt;

    @Column(name = "producer_cnt_inc_by", updatable = false)
    private Integer producerCntIncBy;

    @Column(name = "consumer_cur_cnt", updatable = false)
    private Integer consumerCurCnt;

    @Column(name = "consumer_cnt_inc_by", updatable = false)
    private Integer consumerCntIncBy;

    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;
}
