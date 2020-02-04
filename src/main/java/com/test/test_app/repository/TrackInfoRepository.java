package com.test.test_app.repository;

import com.test.test_app.domain.TrackInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackInfoRepository extends JpaRepository<TrackInfo, Long> {
}
