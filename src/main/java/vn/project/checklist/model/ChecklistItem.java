package vn.project.checklist.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "checklist_items")
public class ChecklistItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name_item")
    private String nameItem;

    @ManyToOne(targetEntity = Checklist.class)
    @JoinColumn(name = "checklists_id",referencedColumnName = "id")
    private Checklist checklist;

    @OneToMany(mappedBy = "checklistItem",cascade = CascadeType.ALL)
    private List<AssignmentDetails> assignmentDetails;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public List<AssignmentDetails> getAssignmentDetails() {
        return assignmentDetails;
    }

    public void setAssignmentDetails(List<AssignmentDetails> assignmentDetails) {
        this.assignmentDetails = assignmentDetails;
    }

}

