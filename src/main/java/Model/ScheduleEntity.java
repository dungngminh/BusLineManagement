package Model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Schedule", schema = "dbo", catalog = "QuanLyNhaXeKhach")
public class ScheduleEntity {
    private int idSchedule;
    private Date departTime;
    private int duration;
    private boolean isDelete;

    @Id
    @Column(name = "idSchedule")
    public int getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(int idSchedule) {
        this.idSchedule = idSchedule;
    }

    @Basic
    @Column(name = "departTime")
    public Date getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }

    @Basic
    @Column(name = "duration")
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "isDelete")
    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleEntity that = (ScheduleEntity) o;

        if (idSchedule != that.idSchedule) return false;
        if (duration != that.duration) return false;
        if (isDelete != that.isDelete) return false;
        if (departTime != null ? !departTime.equals(that.departTime) : that.departTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSchedule;
        result = 31 * result + (departTime != null ? departTime.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + (isDelete ? 1 : 0);
        return result;
    }
}
