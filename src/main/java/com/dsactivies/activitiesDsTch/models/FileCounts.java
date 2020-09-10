package com.dsactivies.activitiesDsTch.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_filecounts")
public class FileCounts {
    @Id
    private long FileCount;

    public long getFileCount() {
        return FileCount;
    }

    public void setFileCount(long fileCount) {
        FileCount = fileCount;
    }
}

