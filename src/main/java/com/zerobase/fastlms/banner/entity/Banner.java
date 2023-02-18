package com.zerobase.fastlms.banner.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String bannerName;

    private String pathOnClick;
    private String target;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private boolean isPosting;
    private int sortNum;

    private String fileName;
    private String urlName;
}
