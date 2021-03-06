package com.example.todoapp.model;

import com.example.todoapp.model.event.TaskEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Task's description must not be empty")
    private String description;
    private boolean done;
    @Column()
    private LocalDateTime deadline;
    //@Transient - nie chcemy zapisywać kolumny w bazie danych
    //@PESEL - wyciąga pesel
    @Embedded // wstawia obiekt z klasy z adnotacją Embedable
    //@AttributeOverrides() - zmiana nazwy kolumn inne niż w obiekcie embedable
    private Audit audit = new Audit();

    @ManyToOne
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;


    public Task(String description, LocalDateTime now) {
    }

    public Task(String description, LocalDateTime deadline, TaskGroup group) {
        this.description = description;
        this.deadline = deadline;
        if(group!=null){
            this.group=group;
        }
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public TaskEvent toggle() {
        this.done = !this.done;
        return TaskEvent.changed(this);
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    TaskGroup getGroup() {
        return group;
    }

    public void updateFrom(final Task source){
        description=source.description;
        done= source.done;
        deadline=source.deadline;
        group= source.group;
    }


}
