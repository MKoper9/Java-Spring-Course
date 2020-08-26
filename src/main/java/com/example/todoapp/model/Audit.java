package com.example.todoapp.model;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

//@MappedSuperclass // wspólne kolumny między tabelami, musi być klasa abstrajcyjna
@Embeddable //klasa jest osadzalna w innym miejscu
class Audit {

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PrePersist
        //funkcja która wykona się przed zapisem do bazy danych
    void prePersist(){
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
        //aktualizacje
    void preMerge(){
        updatedOn=LocalDateTime.now();
    }
}
