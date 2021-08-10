package vn.project.checklist.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name="assignments",uniqueConstraints = @UniqueConstraint(columnNames = {"users_id","checklists_id","assigned_at"}))
public class Assignment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true,nullable = false)
    private Integer id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "users_id",referencedColumnName = "id")
    private User user;

    @ManyToOne(targetEntity = Checklist.class)
    @JoinColumn(name="checklists_id",referencedColumnName = "id")
    private Checklist checklist;

    @Column(name="comment")
        private String comment;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="assigned_by")
    @CreatedBy
    private User assignedBy;

    @Column(name="assigned_at")
    @CreatedDate
    private LocalDateTime assignedAt;

    @Column(name="progress")
    private Integer progress;

    @Column(name = "status")
    private Integer status;

    @Column(name = "reason")
    private String reason;

    @OneToMany(mappedBy = "assignment",cascade = CascadeType.ALL)
    private List<AssignmentDetails> assignmentDetails;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<AssignmentDetails> getAssignmentDetails() {
        return assignmentDetails;
    }

    public void setAssignmentDetails(List<AssignmentDetails> assignmentDetails) {
        this.assignmentDetails = assignmentDetails;
    }
}
