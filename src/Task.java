import java.time.LocalDateTime;

public class Task {
    private double tid;
    private double microtasks;
    private LocalDateTime assigned_ts;
    private LocalDateTime closed_ts;

    public LocalDateTime getClosed_ts() {
        return closed_ts;
    }

    public double getMicrotasks() {
        return microtasks;
    }

    public Task(double tid, double microtasks, LocalDateTime assigned_ts, LocalDateTime closed_ts) {
        this.tid = tid;
        this.microtasks = microtasks;
        this.assigned_ts = assigned_ts;
        this.closed_ts = closed_ts;
    }

    public LocalDateTime getAssigned_ts() {
        return assigned_ts;
    }
}